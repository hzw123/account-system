package cn.mauth.account.dao.account;

import cn.mauth.account.mapper.AccountMapper;
import cn.mauth.account.core.model.Account;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountService {
	private Logger logger = LoggerFactory.getLogger(AccountService.class);

	@Autowired
	private AccountMapper accountMapper;

	public Account findAccountIdByCustAndSubject(String custId, String subjectNo){
		Account account = null;
		try{
			account = accountMapper.findAccountIdByCustAndSubject(custId, subjectNo);
		}catch (Exception e){
			logger.error("按照客户编号{}，科目编号{}查找账户失败!", custId, subjectNo, e);
		}
		return account;
	}

	public Account findAccountByKeyWithLock(String accountId){
		Account account = null;
		try{
			account = accountMapper.findAccountByKeyWithLock(accountId);
		}catch (Exception e){
			logger.error("按照账户编号{}，锁定账户失败!", accountId, e);
		}
		return account;
	}

	public List<Account> getAllAccount(){
		List<Account> accounts = null;
		try{
			accounts = accountMapper.getAllAccount();
		} catch (Exception e){
			logger.error("获取所有开启的账户失败!", e);
		}
		return accounts;
	}

	public boolean saveAccount(Account account){
		boolean saveRes = false;
		try{
			accountMapper.saveAccount(account);
			saveRes = true;
		} catch (Exception e){
			logger.error("保存账户信息{}时出错", new Gson().toJson(account), e);
		}
		return saveRes;
	}

	public void updateAccountAmt(Account account){
		accountMapper.updateAccountAmt(account);
	}
}
