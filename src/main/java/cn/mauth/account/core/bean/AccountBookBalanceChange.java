package cn.mauth.account.core.bean;

import cn.mauth.account.core.model.AccountBill;
import cn.mauth.account.core.model.AccountBook;

import java.io.Serializable;

/**
 * 用于记录一条记账规则带来的借贷方余额变动参数
 */
public class AccountBookBalanceChange implements Serializable {

    private static final long serialVersionUID = 1L;

    private AccountBook accountBook;

    private AccountBill[] accountBills;

    public AccountBook getAccountBook() {
        return accountBook;
    }

    public void setAccountBook(AccountBook accountBook) {
        this.accountBook = accountBook;
    }

    public AccountBill[] getAccountBills() {
        return accountBills;
    }

    public void setAccountBills(AccountBill[] accountBills) {
        this.accountBills = accountBills;
    }
}
