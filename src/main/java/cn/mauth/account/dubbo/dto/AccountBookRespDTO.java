package cn.mauth.account.dubbo.dto;

import cn.mauth.account.core.model.AccountBook;

import java.util.List;

public class AccountBookRespDTO extends BaseRespDTO{
    private List<AccountBook> accountBooks;

    public List<AccountBook> getAccountBooks() {
        return accountBooks;
    }

    public void setAccountBooks(List<AccountBook> accountBooks) {
        this.accountBooks = accountBooks;
    }
}
