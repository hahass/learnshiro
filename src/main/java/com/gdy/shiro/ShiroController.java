package com.gdy.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/shiro/")
public class ShiroController {
    @RequestMapping("login")
    public String login(String username , String password){

        //获取subject
        Subject currentUser = SecurityUtils.getSubject();
        //判断用户是否登录
        if(!currentUser.isAuthenticated()){

            //将用户名和密码封装成token对象
            UsernamePasswordToken token = new UsernamePasswordToken(username,password);
            //实现记住我功能
            token.setRememberMe(true);

            try {
                System.out.println(token.hashCode());
                //执行登录操作
                currentUser.login(token);
            } catch (AuthenticationException e) {
                System.out.println("登录失败："+e);
            }
        }
        return "redirect:/list.jsp";
    }
}