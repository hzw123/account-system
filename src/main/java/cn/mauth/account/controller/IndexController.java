package cn.mauth.account.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController extends BaseController{


    @RequestMapping("/login")
    public String login(Model model){
        model.addAttribute("sm","登录成功！");
        return "login";
    }

    @RequestMapping("/jobTask")
    public String jobTask(){

        return "jobTask";
    }
}
