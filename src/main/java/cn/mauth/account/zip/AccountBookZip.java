package cn.mauth.account.zip;

import cn.mauth.account.core.bean.AccountBookBalanceChange;
import cn.mauth.account.dubbo.dto.AccountBookDTO;
import cn.mauth.account.dubbo.dto.AccountBookRespDTO;
import cn.mauth.account.dubbo.dto.BaseRespDTO;
import cn.mauth.account.core.enums.BookStateEnum;
import cn.mauth.account.core.enums.DrCrEnum;
import cn.mauth.account.core.model.AccountBill;
import cn.mauth.account.core.model.AccountBook;
import cn.mauth.account.core.model.AccountRule;
import cn.mauth.account.dao.accountbill.AccountBillService;
import cn.mauth.account.dao.accountbook.AccountBookService;
import cn.mauth.account.dao.subject.SubjectService;
import cn.mauth.account.core.util.Constants;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 提供复式记账对象相关服务
 */
@Component
public class AccountBookZip {

    private Logger logger = LoggerFactory.getLogger(AccountBookZip.class);

    @Autowired
    private AccountBookService accountBookService;

    @Autowired
    private AccountBillZip accountBillBusi;

    @Autowired
    private AccountBillService accountBillService;

    public BaseRespDTO initTransAccountBook(AccountBookDTO accountBookDTO, List<AccountRule> accountRules){
        //首先根据订单号和记账规则检查该笔订单是否已经入库
        BaseRespDTO respDTO = checkIsRepeatAccount(accountBookDTO, accountRules);
        if(!respDTO.isRetCode()){
            return respDTO;
        }

        AccountBookRespDTO acctRespDTO = new AccountBookRespDTO();
        List<AccountBook> accountBooks = new ArrayList<>();
        for(AccountRule rule : accountRules){
            AccountBook accountBook = new AccountBook();
            accountBook.setRuleId(rule.getRuleId());
            accountBook.setAccountDate(accountBookDTO.getAccountDate());
            accountBook.setOrderId(accountBookDTO.getOrderId());

            String payerSubNo = rule.getPayerSubjectNo();
            String payerFlag = rule.getPayerFlag();
            accountBook.setPayerFlag(payerFlag);

            String payeeSubNo = rule.getPayeeSubjectNo();

            if(DrCrEnum.DR.getValue().equals(payerFlag)){
                accountBook.setDrSubjectNo(payerSubNo);
                accountBook.setDrCustId(SubjectService.isPlatformSubNo(payerSubNo) ?
                        Constants.PLATFORM_INSID : accountBookDTO.getPayerCustId());
                accountBook.setCrSubjectNo(payeeSubNo);
                accountBook.setCrCustId(SubjectService.isPlatformSubNo(payeeSubNo) ?
                        Constants.PLATFORM_INSID : accountBookDTO.getPayeeCustId());
            } else {
                accountBook.setCrSubjectNo(payerSubNo);
                accountBook.setCrCustId(SubjectService.isPlatformSubNo(payerSubNo) ?
                        Constants.PLATFORM_INSID : accountBookDTO.getPayerCustId());
                accountBook.setDrSubjectNo(payeeSubNo);
                accountBook.setDrCustId(SubjectService.isPlatformSubNo(payeeSubNo) ?
                        Constants.PLATFORM_INSID : accountBookDTO.getPayeeCustId());
            }
            accountBook.setAmount(accountBookDTO.getAmount());
            accountBook.setBookState(BookStateEnum.INIT.getValue());
            accountBook.setBookTime(new Date());
            accountBooks.add(accountBook);
        }
        if(accountBooks.size() < 1){
            logger.info("订单{}已进行过复式记账，退出记账流程！",accountBookDTO.getOrderId());
        }
        acctRespDTO.setRetCode(true);
        acctRespDTO.setAccountBooks(accountBooks);
        return acctRespDTO;
    }

    /**
     * 检查指定订单是否已经记账
     * @return
     */
    private BaseRespDTO checkIsRepeatAccount(AccountBookDTO accountBookDTO, List<AccountRule> accountRules){
        BaseRespDTO respDTO = new BaseRespDTO();

        AccountBook params = new AccountBook();
        String orderId = accountBookDTO.getOrderId();
        params.setOrderId(orderId);

        List<AccountBook> accountBooks = accountBookService.queryAccountBook(params);
        if(accountBooks == null){
            respDTO.setRetCode(false);
            respDTO.setRespDesc("按照订单查询是否已进行复式记账失败！");
            return respDTO;
        }
        if(accountBooks.size() < 1){
            respDTO.setRetCode(true);
            return respDTO;
        }
        Map<String, AccountBook> acctsMap = new HashMap<>();

        for(AccountBook accountBook : accountBooks){
            acctsMap.put(accountBook.getRuleId(), accountBook);
        }

        //按照当前查询出的记账逻辑匹配数据库该订单已有的记账记录
        Long orderAmount = accountBookDTO.getAmount();
        Long servFee = accountBookDTO.getServFee();
        for(AccountRule accountRule : accountRules){
            String ruleId = accountRule.getRuleId();
            if(acctsMap.containsKey(ruleId)) {
                AccountBook accountBook = acctsMap.get(ruleId);
                if(orderAmount != accountBook.getAmount()){
                    respDTO.setRetCode(false);
                    respDTO.setRespDesc("记账失败");
                    return respDTO;
                }
            } else {
                respDTO.setRetCode(false);
                respDTO.setRespDesc("记账失败");
                return respDTO;
            }
        }
        respDTO.setRetCode(false);
        respDTO.setRespDesc("订单已进行复式记账");
        return respDTO;
    }

    @Transactional
    public void recordBookAndBill(List<AccountBookBalanceChange> bookBalanceChanges) throws Exception {
        if(bookBalanceChanges == null || bookBalanceChanges.size() < 1){
            logger.info("复式记账明细需入库集合为空");
            return;
        }

        for(AccountBookBalanceChange accountBookBalanceChange : bookBalanceChanges){
            AccountBook accountBook = accountBookBalanceChange.getAccountBook();
            AccountBill[] accountBills = accountBookBalanceChange.getAccountBills();
            if(accountBills == null || accountBills.length != 2){
                logger.info("复式明细{}拆分的余额变动明细为空或长度不为2", new Gson().toJson(accountBook));
                throw new Exception();
            }
            accountBillService.batchSaveAccountBill(Arrays.asList(accountBills));

        }
    }

    /**
     * 复式记账内部类，用于异步将复式明细记录及余额变动明细记录入库
     */
    public class SaveAccountBookZip implements Runnable{

        private List<AccountBook> accountBookList;

        private Logger logger = LoggerFactory.getLogger(SaveAccountBookZip.class);

        public SaveAccountBookZip(List<AccountBook> accountBookList){
            this.accountBookList = accountBookList;
        }

        @Override
        public void run() {
            if(accountBookList == null ||  accountBookList.size() < 1){
                logger.info("复式记账明细列表为空，退出明细记账线程。");
                return;
            }

            try {
                recordBookAndBill();
            }catch (Exception e){
                logger.error("复式记账明细及余额变动明细入库失败！", e);
            }
        }

        @Transactional
        public void recordBookAndBill() throws InvocationTargetException, IllegalAccessException {
            //借贷方余额变动明细入库
            for(AccountBook accountBook : accountBookList){
                List<AccountBill> accountBills =
                        accountBillBusi.initAccountBillByBookInf(accountBook);
                if(accountBills == null || accountBills.size() < 1){
                    logger.info("根据复式余额变动对象初始化余额变动明细失败，退出明细记账线程。");
                    return;
                }
                for(AccountBill accountBill : accountBills){
                    accountBillService.saveAccountBill(accountBill);
                    logger.info("单边余额变动明细记录入库{}"+new Gson().toJson(accountBill));
                }

                //复式明细入库
                accountBookService.saveAccountBook(accountBook);
            }
        }
    }
}
