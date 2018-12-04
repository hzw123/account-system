package cn.mauth.account.core.bean;

import cn.mauth.account.core.model.AccountBook;

import java.io.Serializable;
import java.util.List;

/**
 * 用于复式记账的加工数据储存
 */
public class DoAccountBookInf implements Serializable {

    private static final long serialVersionUID = 1L;
    /**s
     * 按照账户汇总余额变动明细，仅用于用户级别科目
     * 初始化复式记账对象明细时赋值
     */
    private List<AccountBookBalanceChange> accountBalanceChangeDetails;

    /**
     * 根据交易产生的复式记账对象
     */
    private List<AccountBook> accountBooks;

    public List<AccountBookBalanceChange> getAccountBalanceChangeDetails() {
        return accountBalanceChangeDetails;
    }

    public void setAccountBalanceChangeDetails(List<AccountBookBalanceChange> accountBalanceChangeDetails) {
        this.accountBalanceChangeDetails = accountBalanceChangeDetails;
    }

    public List<AccountBook> getAccountBooks() {
        return accountBooks;
    }

    public void setAccountBooks(List<AccountBook> accountBooks) {
        this.accountBooks = accountBooks;
    }
}
