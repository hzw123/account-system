package cn.mauth.account.zip;

import cn.mauth.account.core.bean.AccountChangeInf;
import cn.mauth.account.core.util.cache.AccountCache;
import cn.mauth.account.dubbo.dto.BaseRespDTO;
import cn.mauth.account.core.enums.DrCrEnum;
import cn.mauth.account.core.model.Account;
import cn.mauth.account.core.model.AccountBook;
import cn.mauth.account.dao.account.AccountService;
import cn.mauth.account.dao.accountbook.AccountBookService;
import cn.mauth.account.dao.subject.SubjectService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 用于处理账户余额变动等逻辑功能
 */
@Component
public class AccountZip {
    private Logger logger = LoggerFactory.getLogger(AccountZip.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountBookService accountBookService;

    @Autowired
    private AccountCache accountCache;

    /**
     * 根据复式明细记账对象处理涉及用户科目的余额变动
     * @param accountBooks
     * @return
     */
    public boolean balanceChange(List<AccountBook> accountBooks){
        if(accountBooks == null || accountBooks.size() < 1){
            logger.info("提供的复式记账列表为空，退出余额变动逻辑");
            return false;
        }

        //用于储存单个账户余额变动列表
        Map<String, List<AccountChangeInf>> amountChangeDetails = new HashMap<>();

        for(AccountBook accountBook : accountBooks){
            String drSubjectNo = accountBook.getDrSubjectNo();
            String drCustId = accountBook.getDrCustId();

            Account drAccount =
                    accountCache.getCacheValue(drSubjectNo, drCustId);
            if(drAccount == null){
                logger.info("按照客户编号{}，客户科目{}匹配对应账户失败！", drCustId, drSubjectNo);
                return false;
            }

            String drAccountId = drAccount.getAccountId();
            String drAcctFlag = drAccount.getDrCrFlag();

            long changeAmount = calculateAmount(accountBook.getAmount(), drAcctFlag, DrCrEnum.DR.getValue());
            accountBook.setDrAccountId(drAccountId);
            accountBook.setDrAmount(changeAmount);

            //判断借方科目是否属于用户级科目，用户科目需要实时更新账户余额
            if(!SubjectService.isPlatformSubNo(drSubjectNo)){
                List<AccountChangeInf> changeInfList;
                if(amountChangeDetails.containsKey(drAccountId)){
                    changeInfList = amountChangeDetails.get(drAccountId);
                } else {
                    changeInfList = new ArrayList<>();
                }
                AccountChangeInf changeInf = new AccountChangeInf();
                changeInf.setAccountId(drAccountId);
                changeInf.setAmount(changeAmount);
                changeInf.setDrAmount(changeAmount);
                changeInf.setCrAmount(0L);
                changeInf.setDrcrFlag(DrCrEnum.DR);
                changeInf.setAccountBook(accountBook);
                changeInfList.add(changeInf);
                amountChangeDetails.put(drAccountId, changeInfList);
            }

            String crSubjectNo = accountBook.getCrSubjectNo();
            String crCustId = accountBook.getCrCustId();

            Account crAccount =
                    accountCache.getCacheValue(crSubjectNo, crCustId);
            if(crAccount == null){
                logger.info("按照客户编号{}，客户科目{}匹配对应账户失败！", crCustId, crSubjectNo);
                return false;
            }
            String crAccountId = crAccount.getAccountId();
            String crAcctFlag = crAccount.getDrCrFlag();
            accountBook.setCrAccountId(crAccountId);

            changeAmount = calculateAmount(accountBook.getAmount(), crAcctFlag, DrCrEnum.CR.getValue());
            accountBook.setCrAmount(changeAmount);

            //判断贷方科目是否属于用户级科目，用户科目需要实时更新账户余额
            if(!SubjectService.isPlatformSubNo(crSubjectNo)){
                List<AccountChangeInf> changeInfList;
                if(amountChangeDetails.containsKey(crAccountId)){
                    changeInfList = amountChangeDetails.get(crAccountId);
                } else {
                    changeInfList = new ArrayList<>();
                }
                AccountChangeInf changeInf = new AccountChangeInf();
                changeInf.setAccountId(drAccountId);
                changeInf.setAmount(changeAmount);
                changeInf.setCrAmount(changeAmount);
                changeInf.setDrAmount(0L);
                changeInf.setDrcrFlag(DrCrEnum.CR);
                changeInf.setAccountBook(accountBook);
                changeInfList.add(changeInf);
                amountChangeDetails.put(crAccountId, changeInfList);
            }
        }

        try {
            if(amountChangeDetails.size() > 0){
                //存在用户账户余额变化时，实时对账户余额进行修改
                //同一事务需要将该订单所有复式记账记录入库
                updateAccountWithLock(amountChangeDetails, accountBooks);
            }
        } catch (Exception e) {
            logger.error("修改账户余额信息失败，余额变动明细{}",
                    new Gson().toJson(amountChangeDetails), e);
            return false;
        }

        return true;
    }

    @Transactional(readOnly = true)
    public BaseRespDTO updateAccountWithLock(Map<String, List<AccountChangeInf>> amountChangeDetails,
                                             List<AccountBook> accountBooks){
        BaseRespDTO respDTO = new BaseRespDTO();
        if(amountChangeDetails == null ||  amountChangeDetails.size() < 1){
            logger.error("余额变动明细集合为空，执行结束。");
            respDTO.setRetCode(false);
            respDTO.setRespDesc("用户账户余额变动明细集合为空");
            return respDTO;
        }
        for(Map.Entry<String, List<AccountChangeInf>> balanceChange : amountChangeDetails.entrySet()){
            List<AccountChangeInf> changeInfs = balanceChange.getValue();

            //根据account_id锁定需要修改的账户
            Account account =
                    accountService.findAccountByKeyWithLock(balanceChange.getKey());
            if(account == null){
                logger.error("按照账号{}查询用户账户失败，退出余额变动！", balanceChange.getKey());
                respDTO.setRetCode(false);
                respDTO.setRespDesc("根据账号"+balanceChange.getKey()+"不能匹配对应账户");
                return respDTO;
            }

            //首先累记该账户需要变动的金额，判断账户余额是否足够。
            Long changeAmount = 0L;
            Long drAmount = 0L;
            Long crAmount = 0L;
            for(AccountChangeInf changeInf : changeInfs){
                changeAmount += changeInf.getAmount();
                drAmount += changeInf.getDrAmount();
                crAmount += changeInf.getCrAmount();

                AccountBook accountBook = changeInf.getAccountBook();
                Long cashAmount = account.getCashAmount() + changeInf.getAmount();
                //实时计算accountBook借贷方账户余额
                if(DrCrEnum.DR == changeInf.getDrcrFlag()){
                    accountBook.setDrCashAmount(cashAmount);
                    accountBook.setDrAccountSeq((account.getAccountSeq() == null ?
                            0 : account.getAccountSeq()) + 1);
                } else {
                    accountBook.setCrCashAmount(cashAmount);
                    accountBook.setCrAccountSeq((account.getAccountSeq() == null ?
                            0 : account.getAccountSeq()) + 1);
                }
            }

            if(account.getCashAmount() == null ||
                    account.getCashAmount() + changeAmount < 0){
                logger.info("对账户{}进行余额变动时发现余额不足，变动金额{}",
                        new Gson().toJson(account), changeAmount);
                respDTO.setRetCode(false);
                respDTO.setRespDesc("账户"+account.getAccountId()+"扣减"+changeAmount+"时，余额不足！" );
                return respDTO;
            }


            Account changeAccount =  new Account();
            changeAccount.setAccountId(account.getAccountId());
            changeAccount.setAccountSeq((account.getAccountSeq() == null ?
                    0 : account.getAccountSeq()) + 1);
            changeAccount.setCashAmount(account.getCashAmount() + changeAmount);
            changeAccount.setDrAmount(drAmount);
            changeAccount.setCrAmount(crAmount);
            changeAccount.setUpdateTime(new Date());

            accountService.updateAccountAmt(changeAccount);

            logger.info("账户{}变更成功，变更后信息：{}", new Gson().toJson(account),
                    new Gson().toJson(changeAccount));

            //根据每次变动明细修改对应的复式记账明细中的账户余额
            for(AccountBook accountBook : accountBooks){
                accountBookService.saveAccountBook(accountBook);
            }
        }

        respDTO.setRetCode(true);
        return respDTO;
    }

    private long calculateAmount(long transAmount, String acctFlag, String drcrFlag){
        if(DrCrEnum.DR.getValue().equals(acctFlag)){
            //账户属于借记科目，进行借记时余额记增，贷记余额记减
            if(DrCrEnum.DR.getValue().equals(drcrFlag)){
                return transAmount;
            } else {
                return 0 - transAmount;
            }
        } else {
            //账户属于贷记科目，进行借记时余额记减，贷记余额记增
            if(DrCrEnum.CR.getValue().equals(drcrFlag)){
                return transAmount;
            } else {
                return 0 - transAmount;
            }
        }
    }
}
