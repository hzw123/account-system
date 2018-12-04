package cn.mauth.account.mapper;

import java.util.List;

import cn.mauth.account.core.model.AccountRule;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountRuleMapper {
	List<AccountRule> findAccountRules(AccountRule param);

	List<AccountRule> getAllAccountRules();
}