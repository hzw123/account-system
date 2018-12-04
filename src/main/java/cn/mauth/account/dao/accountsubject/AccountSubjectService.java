package cn.mauth.account.dao.accountsubject;

import cn.mauth.account.mapper.AccountSubjectMapper;
import cn.mauth.account.core.model.AccountSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AccountSubjectService {

    private Logger logger = LoggerFactory.getLogger(AccountSubjectService.class);

    @Autowired
    private AccountSubjectMapper accountSubjectMapper;

    public AccountSubject queryAccountSubjectByPK(String subjectNo){
        AccountSubject accountSubject = null;
        try{
            accountSubject = accountSubjectMapper.querySubjectByPrimaryKey(subjectNo);
        } catch (Exception e) {
            logger.error("根据科目编号{}获取科目失败", e);
        }
        return accountSubject;
    }
}
