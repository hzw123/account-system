package cn.mauth.account.dao.account;

import cn.mauth.account.core.bean.Pageable;
import cn.mauth.account.core.util.PageUtil;
import cn.mauth.account.mapper.AccountMapper;
import cn.mauth.account.core.model.Account;
import com.github.pagehelper.PageInfo;
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

	public Account findAccountByKey(String accountId){
		return accountMapper.findAccountByKey(accountId);
	}

	public PageInfo<Account> page(Pageable pageable){
		PageUtil.startPage(pageable);

		return new PageInfo<>(accountMapper.getAllAccount());
	}

	public boolean updateAccountAndLock(Account account){

		Account oldAccount=accountMapper.findAccountByKeyWithLock(account.getAccountId());

		if(oldAccount==null){
			logger.error("按照账号{}查询用户账户失败，退出账户修改！", account.getAccountId());
			return false;
		}

		accountMapper.updateAccountAmt(account);

		logger.info("账户{}变更成功，变更后信息：{}", new Gson().toJson(oldAccount),
				new Gson().toJson(account));
		return true;
	}

	public boolean deleteAccountByAccountId(String accountId){
		boolean saveRes = false;
		try{
			accountMapper.deleteAccountByAccountId(accountId);
			saveRes = true;
		} catch (Exception e){
			logger.error("删除账户{}时出错", accountId, e);
		}
		return saveRes;
	}
}
