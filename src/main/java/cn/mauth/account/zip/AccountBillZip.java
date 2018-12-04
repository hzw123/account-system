package cn.mauth.account.zip;

import cn.mauth.account.core.bean.AccountBookBalanceChange;
import cn.mauth.account.core.enums.BookStateEnum;
import cn.mauth.account.core.enums.DrCrEnum;
import cn.mauth.account.core.model.Account;
import cn.mauth.account.core.model.AccountBill;
import cn.mauth.account.core.model.AccountBook;
import cn.mauth.account.dao.account.AccountService;
import cn.mauth.account.dao.accountbill.AccountBillService;
import cn.mauth.account.dao.accountbook.AccountBookService;
import cn.mauth.account.core.util.Constants;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 余额变动明细业务类
 */
@Component
public class AccountBillZip {
    private Logger logger = LoggerFactory.getLogger(AccountBillZip.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountBillService accountBillService;

    @Autowired
    private AccountBookService accountBookService;

    //通过复式明细记账对象创建单边余额变动明细
    public List<AccountBill> initAccountBillByBookInf(AccountBook accountBook){
        List<AccountBill>  accountBills = new ArrayList<>();

        //创建借方余额变动明细
        AccountBill drAccountBill = new AccountBill();
        drAccountBill.setAccountId(accountBook.getDrAccountId());
        drAccountBill.setAmount(accountBook.getDrAmount());
        if(accountBook.getDrCashAmount() != null){
            //仅有用户所属账户在余额变动时就计算出变动后的账户余额
            drAccountBill.setAccountAmount(accountBook.getDrCashAmount());
            drAccountBill.setAccountSeq(accountBook.getDrAccountSeq());
        } else {
            Account account =
                    accountService.findAccountByKeyWithLock(drAccountBill.getAccountId());
            if(account == null){
                logger.info("按照账户编号{}查询平台账户失败，退出复式明细记账！", drAccountBill.getAccountId());
                return null;
            }
            Long cashAmount = account.getCashAmount() + accountBook.getDrAmount();
            if(cashAmount < 0){
                logger.info("账户{}余额不足，退出复式明细记账！", drAccountBill.getAccountId());
                return null;
            }
            drAccountBill.setAccountAmount(cashAmount);
            drAccountBill.setAccountSeq((account.getAccountSeq() == null
                    ? 0 : account.getAccountSeq()) + 1);

            //修改账户信息
            account.setCashAmount(drAccountBill.getAccountAmount());
            account.setAccountSeq(drAccountBill.getAccountSeq());
            account.setDrAmount(Math.abs(accountBook.getDrAmount()));
            account.setUpdateTime(new Date());

            accountService.updateAccountAmt(account);
        }
        drAccountBill.setAccountDate(accountBook.getAccountDate());
        drAccountBill.setCustId(accountBook.getDrCustId());
        drAccountBill.setDrCrFlag(DrCrEnum.DR.getValue());
        drAccountBill.setOrderId(accountBook.getOrderId());
        drAccountBill.setRecordTime(new Date());

        accountBills.add(drAccountBill);

        //创建贷方余额变动明细
        AccountBill crAccountBill = new AccountBill();
        crAccountBill.setAccountId(accountBook.getCrAccountId());
        crAccountBill.setAmount(accountBook.getCrAmount());
        if(accountBook.getCrCashAmount() != null){
            //仅有用户所属账户在余额变动时就计算出变动后的账户余额
            crAccountBill.setAccountAmount(accountBook.getCrCashAmount());
            crAccountBill.setAccountSeq(accountBook.getCrAccountSeq());
        } else {
            Account account =
                    accountService.findAccountByKeyWithLock(crAccountBill.getAccountId());
            if(account == null){
                logger.info("按照账户编号{}查询平台账户失败，退出复式明细记账！", crAccountBill.getAccountId());
                return null;
            }
            Long cashAmount = account.getCashAmount() +
                    accountBook.getCrAmount();
            if(cashAmount < 0){
                logger.info("账户{}余额不足，退出复式明细记账！", crAccountBill.getAccountId());
                return null;
            }
            crAccountBill.setAccountAmount(cashAmount);
            crAccountBill.setAccountSeq((account.getAccountSeq() == null
                    ? 0 : account.getAccountSeq()) + 1);

            //修改账户信息
            account.setCashAmount(crAccountBill.getAccountAmount());
            account.setAccountSeq(crAccountBill.getAccountSeq());
            account.setCrAmount(Math.abs(accountBook.getCrAmount()));
            account.setUpdateTime(new Date());

            accountService.updateAccountAmt(account);
        }
        crAccountBill.setAccountDate(accountBook.getAccountDate());
        crAccountBill.setCustId(accountBook.getCrCustId());
        crAccountBill.setDrCrFlag(DrCrEnum.CR.getValue());
        crAccountBill.setOrderId(accountBook.getOrderId());
        crAccountBill.setRecordTime(new Date());

        accountBills.add(crAccountBill);
        return accountBills;
    }

    /**
     * 根据一批复式记账记录构造单边余额变动账务明细
     * 之后进行余额变动入库及复式记账记录更新操作
     * @param bookList
     * @return
     */
    @Transactional
    public void recordBill(List<AccountBook> bookList) throws Exception {
        Map<String, Account> cashAmountMap = new HashMap<>();
        List<AccountBookBalanceChange> accountBookBalanceChanges = new ArrayList<>();

        for(AccountBook accountBook : bookList) {
            AccountBookBalanceChange accountBookBalanceChange =
                    new AccountBookBalanceChange();
            AccountBill[] accountBillArray = new AccountBill[2];
            int index = 0;
            for (DrCrEnum drcrFlag : DrCrEnum.values()) {
                AccountBill accountBill = new AccountBill();
                String custId, accountId;
                Long amount, cashAmount, accountSeq;
                if (drcrFlag == DrCrEnum.DR) {
                    accountId = accountBook.getDrAccountId();
                    custId = accountBook.getDrCustId();
                    amount = accountBook.getDrAmount();
                    cashAmount = accountBook.getDrCashAmount();
                    accountSeq = accountBook.getDrAccountSeq();
                } else {
                    accountId = accountBook.getCrAccountId();
                    custId = accountBook.getCrCustId();
                    amount = accountBook.getCrAmount();
                    cashAmount = accountBook.getCrCashAmount();
                    accountSeq = accountBook.getCrAccountSeq();
                }
                accountBill.setAccountId(accountId);
                accountBill.setCustId(custId);
                accountBill.setOrderId(accountBook.getOrderId());
                accountBill.setDrCrFlag(drcrFlag.getValue());
                accountBill.setAmount(amount);
                accountBill.setAccountDate(accountBook.getAccountDate());
                accountBill.setRecordTime(new Date());

                if (!Constants.PLATFORM_INSID.equals(custId)) {
                    accountBill.setAccountAmount(cashAmount);
                    accountBill.setAccountSeq(accountSeq);
                } else {
                    if (cashAmountMap.containsKey(accountId)) {
                        Account account = cashAmountMap.get(accountId);
                        cashAmount = account.getCashAmount();
                        accountSeq = (account.getAccountSeq() == null ? 0 : account.getAccountSeq()) + 1;
                    } else {
                        //锁定账户，获取账户余额
                        Account account = accountService.findAccountByKeyWithLock(accountId);
                        if (account == null) {
                            logger.info("按照账户编号{}查询平台账户失败，退出复式明细记账！", accountId);
                            return;
                        }
                        cashAmount = account.getCashAmount();
                        accountSeq = (account.getAccountSeq() == null ? 0 : account.getAccountSeq()) + 1;
                    }
                    accountBill.setAccountSeq(accountSeq);
                    cashAmount = cashAmount + amount;
                    if (cashAmount < 0) {
                        logger.info("账户{}余额不足，退出复式明细记账！", accountId);
                        throw new Exception("账户余额不足");
                    }
                    if (drcrFlag == DrCrEnum.DR) {
                        accountBook.setDrCashAmount(cashAmount);
                        accountBook.setDrAccountSeq(accountSeq);
                    } else {
                        accountBook.setCrCashAmount(cashAmount);
                        accountBook.setCrAccountSeq(accountSeq);
                    }
                    accountBill.setAccountAmount(cashAmount);

                    Account tempAcct = new Account();
                    tempAcct.setCashAmount(cashAmount);
                    tempAcct.setAccountSeq(accountSeq);
                    //将更新的账户余额和账户更新序列号放入缓存中等待下次使用。
                    cashAmountMap.put(accountId, tempAcct);
                }
                accountBillArray[index++] = accountBill;
                accountBook.setBookState(BookStateEnum.ACCOUNT_SUCC.getValue());
            }
            accountBookBalanceChange.setAccountBook(accountBook);
            accountBookBalanceChange.setAccountBills(accountBillArray);
            accountBookBalanceChanges.add(accountBookBalanceChange);
        }

        //完成拆分明细入库操作
        //完成复式记账更新操作
        for(AccountBookBalanceChange bookBalanceChange : accountBookBalanceChanges){
            AccountBook accountBook = bookBalanceChange.getAccountBook();
            AccountBill[] accountBills = bookBalanceChange.getAccountBills();
            if(accountBills == null || accountBills.length != 2){
                logger.info("复式明细{}拆分的余额变动明细为空或长度不为2", new Gson().toJson(accountBook));
                throw new Exception();
            }

            accountBillService.batchSaveAccountBill(Arrays.asList(accountBills));

            accountBookService.updateAccountBook(accountBook);
        }
    }
}
