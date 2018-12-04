package cn.mauth.account.dao.accountbook;

import cn.mauth.account.mapper.AccountBookMapper;
import cn.mauth.account.core.model.AccountBook;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 提供复式记账对象数据库操作服务
 */
@Repository
public class AccountBookService {

    private Logger logger = LoggerFactory.getLogger(AccountBookService.class);

    @Autowired
    private AccountBookMapper accountBookMapper;

    /**
     * 根据是否订单号和记账
     * @param orderId
     * @param ruleId
     * @return boolean
     */
    public boolean checkExsitsBookByOrder(String orderId, String ruleId) {
        try{
            Integer countRes =
                    accountBookMapper.countAccountBookByOrderId(orderId, ruleId);
            if(countRes == null){
                return true;
            }else if(countRes == 0){
                return false;
            }else{
                return true;
            }
        }catch (Exception e){
            logger.error("按照订单{},记账规则{}校验是否已进行过复式记账失败！", orderId, ruleId, e);
            return true;
        }
    }

    /**
     * 批量保存复式记账记录
     * @param accountBooks
     * @return boolean
     */
    public boolean batchSaveAccountBooks(List<AccountBook> accountBooks){
        try{
            accountBookMapper.saveAccountBooks(accountBooks);
            return true;
        }catch (Exception e){
            logger.error("保存复式记账对象{}失败", new Gson().toJson(accountBooks), e);
            return false;
        }
    }

    public void updateAccountBook(AccountBook accountBook){
        accountBookMapper.updateAccountBook(accountBook);
    }

    /**
     * 单笔保存复式记账记录
     * @param accountBook
     * @return boolean
     */
    public void saveAccountBook(AccountBook accountBook){
        accountBookMapper.saveAccountBook(accountBook);
    }

    /**
     * 根据查询条件获取复式记账记录
     */
    public List<AccountBook> queryAccountBook(AccountBook accountBook){
        List<AccountBook> accountBooks = null;
        try{
            accountBooks = accountBookMapper.queryAccountBook(accountBook);
        } catch (Exception e){
            logger.error("按照条件{}查询对应的复式记账明细列表失败！",
                    new Gson().toJson(accountBook), e);
        }
        return accountBooks;
    }

    /**
     * 根据查询条件分页查询
     */
    public PageInfo<AccountBook> pageQueryAccountBook(int pageNum, int pageSize, AccountBook accountBook){
        PageInfo<AccountBook> pageAccountBook = null;
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<AccountBook> accountBookList = accountBookMapper.queryAccountBook(accountBook);

            pageAccountBook = new PageInfo<>(accountBookList);
        } catch (Exception e){
            logger.error("按照条件{}分页查询复式明细对象失败！",new Gson().toJson(accountBook));
        }

        return pageAccountBook;
    }
}
