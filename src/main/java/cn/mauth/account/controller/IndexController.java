package cn.mauth.account.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController extends BaseController{


    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("sm","登录成功！");
        return "login";
    }

    @GetMapping("/jobTask")
    public void jobTask(){
    }

    @GetMapping("/account")
    public void account(){
    }

    @GetMapping("/")
    public String index(){
        return "jobTask";
    }
}
