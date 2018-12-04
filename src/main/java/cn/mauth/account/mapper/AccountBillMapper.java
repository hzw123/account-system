package cn.mauth.account.mapper;

import cn.mauth.account.core.model.AccountBill;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AccountBillMapper{
    void saveAccountBill(AccountBill accountBill);

    void batchSaveAccountBill(List<AccountBill> accountBillList);
}