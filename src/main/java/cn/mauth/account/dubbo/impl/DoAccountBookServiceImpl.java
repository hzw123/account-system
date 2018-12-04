package cn.mauth.account.dubbo.impl;

import cn.mauth.account.dubbo.dto.AccountBookDTO;
import cn.mauth.account.dubbo.dto.BaseRespDTO;
import cn.mauth.account.zip.AccountBookZip;
import cn.mauth.account.zip.AccountZip;
import cn.mauth.account.dubbo.dto.AccountBookRespDTO;
import cn.mauth.account.core.model.AccountBook;
import cn.mauth.account.core.model.AccountRule;
import cn.mauth.account.core.util.cache.TransRuleCache;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class DoAccountBookServiceImpl {
    Logger logger = LoggerFactory.getLogger(DoAccountBookServiceImpl.class);

    @Autowired
    private AccountBookZip accountBookZip;

    @Autowired
    private AccountZip accountZip;

    @Autowired
    private TransRuleCache transRuleCache;

    public BaseRespDTO doAccountBook(AccountBookDTO accountBookReq){
        BaseRespDTO respDTO = validateAccountReq(accountBookReq);
        if(!respDTO.isRetCode()){
            logger.info(respDTO.getRespDesc());
            return respDTO;
        }
        String transType = accountBookReq.getTransType();
        //根据交易类型获取需要执行的记账规则
        logger.info("---------根据交易类型{}匹配记账规则开始---------", transType);
        long matchRuleBegin = System.currentTimeMillis();
        List<AccountRule> accountRules = transRuleCache.getAccountRuleByTransType(transType);
        if(accountRules == null || accountRules.size() < 1){
            respDTO.setRetCode(false);
            respDTO.setRespDesc("根据交易类型匹配记账规则失败！");
            return respDTO;
        }
        logger.info("--------匹配规则耗用时间 {}ms---------", (System.currentTimeMillis()-matchRuleBegin));

        //初始化需要记录的复式账务对象
        logger.info("---------初始化复式记账明细开始-----------",
                new Gson().toJson(accountBookReq), new Gson().toJson(accountRules));
        long initAccountBookBegin = System.currentTimeMillis();
        respDTO =  accountBookZip.initTransAccountBook(accountBookReq, accountRules);
        logger.info("---------复式记账明细初始化结束，耗时：{}----------",
                System.currentTimeMillis() - initAccountBookBegin);
        if(!respDTO.isRetCode()){
            return respDTO;
        } else {
            AccountBookRespDTO acctRespDTO = (AccountBookRespDTO)respDTO;
            List<AccountBook> accountBooks = acctRespDTO.getAccountBooks();
            logger.info("--------账户余额信息变动开始---------");
            long balanceChangeBegin = System.currentTimeMillis();
            accountZip.balanceChange(accountBooks);
            logger.info("--------根据复式明细记录进行账户余额信息变动结束，耗时：{}---------", System.currentTimeMillis() - balanceChangeBegin);
        }
        return respDTO;
    }

    private BaseRespDTO validateAccountReq(AccountBookDTO accountBookReq){
        BaseRespDTO respDTO = new BaseRespDTO();
        String orderId = accountBookReq.getOrderId();
        if(StringUtils.isEmpty(orderId) || orderId.length() == 0){
            respDTO.setRetCode(false);
            respDTO.setRespDesc("复式记账请求对象校验--请求交易订单号不能为空！");
            return respDTO;
        }
        String transType = accountBookReq.getTransType();
        if(StringUtils.isEmpty(transType) || transType.length() == 0){
            respDTO.setRetCode(false);
            respDTO.setRespDesc("复式记账请求对象校验--请求交易类型不能为空！");
            return respDTO;
        }
        Long amount = accountBookReq.getAmount();
        if(amount == null ||  amount.longValue() < 0L){
            respDTO.setRetCode(false);
            respDTO.setRespDesc("复式记账请求对象校验--请求金额数据不合法！");
            return respDTO;
        }
        respDTO.setRetCode(true);
        return respDTO;
    }
}
