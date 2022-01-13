package com.yjxxt.crm.controller;


import com.yjxxt.crm.base.BaseController;
import com.yjxxt.crm.bean.User;
import com.yjxxt.crm.service.UserService;
import com.yjxxt.crm.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController extends BaseController {

    @Autowired
    private UserService userService;

    //系统登录页面
    @RequestMapping("index")
    public String index(){
        return "index";
    }

    //系统界面欢迎页面
    @RequestMapping("welcome")
    public String welcome(){
        return "welcome";
    }

    //后端管理主页面
    @RequestMapping("main")
    public String main(HttpServletRequest req){
        //获取当前用户的信息
        int userId = LoginUserUtil.releaseUserIdFromCookie(req);
        //根据用户的ID查询用户信息
        System.out.println(req.getCookies().length);
        User user = userService.selectByPrimaryKey(userId);
        //存储
        req.setAttribute("user",user);
        //转发
        return "main";
    }
}
