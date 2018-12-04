package cn.mauth.account.dao.accountrule;

import cn.mauth.account.mapper.AccountRuleMapper;
import cn.mauth.account.core.model.AccountRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountRuleService {

    private Logger logger = LoggerFactory.getLogger(AccountRuleService.class);

    @Autowired
    private AccountRuleMapper accountRuleMapper;

    public List<AccountRule> findAccountRules(AccountRule param) {
        List<AccountRule> accountRules = null;
        try{
            accountRules = accountRuleMapper.findAccountRules(param);
        }catch (Exception e){
            logger.error("复式记账获取记账规则--根据交易类型【{}】匹配记账规则失败！" ,
                    param.getTransType(), e);
        }
        return accountRules;
    }

    public List<AccountRule> getAllAccountRules(){
        List<AccountRule> accountRules = null;
        try{
            accountRules = accountRuleMapper.getAllAccountRules();
        } catch (Exception e){
            logger.error("获取所有复式记账规则失败！", e);
        }
        return accountRules;
    }
}
