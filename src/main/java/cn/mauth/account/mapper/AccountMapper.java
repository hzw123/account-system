package cn.mauth.account.mapper;

import cn.mauth.account.core.model.Account;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AccountMapper{
    Account findAccountByKeyWithLock(String accountId);

    Account findAccountByKey(String accountId);

    Account findAccountIdByCustAndSubject(@Param("custId") String custId, @Param("subjectNo") String subjectNo);

    List<Account> getAllAccount();

    void saveAccount(Account account);

    Integer updateAccountAmt(Account account);

    int deleteAccountByAccountId(String accountId);
}