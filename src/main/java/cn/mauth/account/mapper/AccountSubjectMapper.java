package cn.mauth.account.mapper;

import cn.mauth.account.core.model.AccountSubject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccountSubjectMapper{

    AccountSubject querySubjectByPrimaryKey(@Param("subjectNo") String subjectNo);

}
