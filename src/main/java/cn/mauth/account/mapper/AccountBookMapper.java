package cn.mauth.account.mapper;

import cn.mauth.account.core.model.AccountBook;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AccountBookMapper{
    Integer countAccountBookByOrderId(@Param("orderId") String orderId, @Param("ruleId") String ruleId);

    void saveAccountBooks(List<AccountBook> accountBooks);

    void saveAccountBook(AccountBook accountBook);

    void updateAccountBook(AccountBook accountBook);

    List<AccountBook> queryAccountBook(AccountBook accountBook);
}