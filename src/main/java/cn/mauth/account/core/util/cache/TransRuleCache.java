package cn.mauth.account.core.util.cache;

import cn.mauth.account.core.model.AccountRule;
import cn.mauth.account.dao.accountrule.AccountRuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于缓存交易类型应对应的记账规则
 */
@Repository
public class TransRuleCache extends CacheManager {
    private static Logger logger = LoggerFactory.getLogger(TransRuleCache.class);

    private static final Class service = AccountRuleService.class;

    private static Method serviceMethod;

    private static final Class dataType = AccountRule.class;

    private static String[] keyNames = new String[]{"transType"};

    @Autowired
    private AccountRuleService accountRuleService;

    static{
        try{
            serviceMethod =
                    service.getMethod("getAllAccountRules", null);
        } catch (Exception e){
            logger.error("不存在获取平台记账规则的数据库执行方法。", e);
        }
    }

    public static void initTransRuleCache(){
        initCache(service, serviceMethod, dataType, keyNames);
    }

    public List<AccountRule> getAccountRuleByTransType(String transType){
        if(serviceMethod == null){
            logger.error("不存在获取平台记账规则的数据库执行方法时出错。");
            return null;
        }
        List<AccountRule> accounts =
                (List<AccountRule>)this.getCacheValue(service, serviceMethod, dataType, keyNames, transType);
        return accounts;
    }

    @Override
    public List<Object> addNewCacheValue(String transType) {
        AccountRule params = new AccountRule();
        params.setTransType(transType);
        List<AccountRule> accountRules =
                accountRuleService.findAccountRules(params);
        List<Object> ruleObjs = new ArrayList<>(accountRules.size());
        for(AccountRule accountRule : accountRules){
            ruleObjs.add(accountRule);
        }
        return ruleObjs;
    }
}
