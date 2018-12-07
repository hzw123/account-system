package cn.mauth.account.controller;

import cn.mauth.account.core.bean.Pageable;
import cn.mauth.account.core.model.Account;
import cn.mauth.account.core.util.PageUtil;
import cn.mauth.account.core.util.Result;
import cn.mauth.account.dao.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService server;

    @GetMapping("/page")
    public Map<String,Object> page(@RequestParam(value="pageNum")Integer pageNum,
                                   @RequestParam(value="pageSize")Integer pageSize){
        return PageUtil.result(server.page(Pageable.of(pageNum,pageSize)));
    }

    @GetMapping("/{accountId}")
    public Account findOne(@PathVariable String accountId){
        return server.findAccountByKey(accountId);
    }

    @PostMapping("/add")
    public Result add(Account account){

        if(!server.saveAccount(account)){
            return Result.error("添加转换失败");
        }

        return Result.SUCCESS;
    }

    @PutMapping("/{accountId}")
    public Result<String> update(@PathVariable String accountId,Account account){

        if(!accountId.equals(account.getAccountId())){
            return Result.error("信息不对应");
        }

        if(!server.updateAccountAndLock(account)){
            return Result.error("修改失败");
        }

        return Result.SUCCESS;
    }

    @DeleteMapping("/{accountId}")
    public Result<String> delete(@PathVariable String accountId){

        if(!server.deleteAccountByAccountId(accountId)){
            return Result.error("账户删除失败");
        }

        return Result.SUCCESS;
    }
}
