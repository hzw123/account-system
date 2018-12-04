package cn.mauth.account.dao.accountbill;

import cn.mauth.account.mapper.AccountBillMapper;
import cn.mauth.account.core.model.AccountBill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountBillService {

    private Logger logger = LoggerFactory.getLogger(AccountBillService.class);

    @Autowired
    private AccountBillMapper accountBillMapper;

    public void saveAccountBill(AccountBill accountBill){
        accountBillMapper.saveAccountBill(accountBill);
    }

    public void batchSaveAccountBill(List<AccountBill> accountBillList){
        accountBillMapper.batchSaveAccountBill(accountBillList);
    }
}
