package cn.mauth.account.core.util.cache;

import cn.mauth.account.core.model.Account;
import cn.mauth.account.core.model.AccountSubject;
import cn.mauth.account.dao.account.AccountService;
import cn.mauth.account.dao.accountsubject.AccountSubjectService;
import cn.mauth.account.core.util.DateUtil;
import cn.mauth.account.core.util.GlobalConstant;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author liuxin
 */
@Repository
public class AccountCache extends CacheManager {
    private static Logger logger = LoggerFactory.getLogger(CacheManager.class);

    private static final Class service = AccountService.class;

    private static Method serviceMethod;

    private static final Class dataType = Account.class;

    private static String[] keyNames = new String[]{"subjectNo", "custId"};

    @Autowired
    private AccountSubjectService accountSubjectService;

    @Autowired
    private AccountService accountService;

    static{
        try{
            serviceMethod =
                    service.getMethod("getAllAccount", null);
        } catch (Exception e){
            logger.error("不存在获取平台账户的数据库执行方法。", e);
        }
    }

    public static void initAccountCache(){
        initCache(service, serviceMethod, dataType, keyNames);
    }

    public Account getCacheValue(String subjectNo, String custId){
        if(serviceMethod == null){
            logger.error("获取平台账户的数据库执行方法时出错。");
            return null;
        }
        String searchDataKey = subjectNo + "-" +custId;
        List<Account> accounts =
                (List<Account>)this.getCacheValue(service, serviceMethod, dataType, keyNames, searchDataKey);
        if(accounts != null){
            return accounts.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<Object> addNewCacheValue(String searchKey) {
        String[] keyArray = searchKey.split(GlobalConstant.keyLinkSymbol);
        String subjectNo = keyArray[0];
        String custId = keyArray[1];

        //首先去数据库检查是否已经存在该账户
        Account account =
                accountService.findAccountIdByCustAndSubject(custId, subjectNo);
        if(account != null){
            Object acctObj = account;
            List<Object> accts = new ArrayList<>();
            accts.add(acctObj);
            return accts;
        }

        //数据库不存在该账户时主动为客户该科目创建一个账户
        account = new Account();

        AccountSubject accountSubject =
                accountSubjectService.queryAccountSubjectByPK(subjectNo);
        if(accountSubject == null){
            logger.error("初始化客户账户时，无法匹配交易规则中的科目{}", subjectNo);
            return null;
        }

        account.setAccountName(accountSubject.getSubjectName());
        String accountId = DateUtil.formatDate(new Date(), "yyyyMMdd")+
                custId.substring(0,4);
        account.setAccountId(accountId);
        account.setAccountSeq(0L);
        account.setSubjectNo(subjectNo);
        account.setCustId(custId);
        account.setSubjectType(accountSubject.getSubjectType());
        account.setAccountState("00");
        account.setCashAmount(0L);
        account.setCreateTime(new Date());
        account.setDrCrFlag(accountSubject.getDrCrFlag());
        account.setDrAmount(0L);
        account.setCrAmount(0L);
        Object acctObj = account;
        List<Object> accts = new ArrayList<>();
        accts.add(acctObj);

        boolean saveRes = accountService.saveAccount(account);

        if(saveRes) {
            logger.info("根据科目{}初始化生成客户账户{}成功", subjectNo, new Gson().toJson(account));
            return accts;
        } else {
            return null;
        }
    }
}
