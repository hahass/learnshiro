package com.gdy;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

public class ShiroMain {

    public static void main(String[] args) {

        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();

        SecurityUtils.setSecurityManager(securityManager);

        Subject currentUser = SecurityUtils.getSubject();

        //测试sessin
        Session session = currentUser.getSession();
        session.setAttribute("somkey","value");
        String value = (String) session.getAttribute("somkey");
        if(value.equals("value")){
            System.out.println("测试通过");
        }

        //测试用户是否被验证，及是否已经登录
        if(!currentUser.isAuthenticated()){
            //将用户名和密码封装成token对象
            UsernamePasswordToken token = new UsernamePasswordToken("gdy","hs");
            token.setRememberMe(true);

            try{
                //执行登录操作
                currentUser.login(token);
            } catch (UnknownAccountException uae){
                System.out.println("there is no user width useranme of "+token.getPrincipal());
                return;
            } catch (IncorrectCredentialsException ice){
                System.out.println("password for account "+token.getPrincipal() +" is incorrect");
                return;
            } catch (LockedAccountException lae){
                System.out.println("the account for username "+token.getPrincipal()+" is locked");
                return;
            } catch (AuthenticationException ae){
                System.out.println("所有认证异常的父类");
            }

            System.out.println("登录成功！");
        }

        /**
         * 测试用户是否有某个角色
         */
        if(currentUser.hasRole("root")){
            System.out.println("有root角色");
        }else {
            System.out.println("没有root角色");
        }

        /**
         * 测试用户是否具备某个行为
         */
        if(currentUser.isPermitted("add:user")){
            System.out.println("可以时候添加用户的权限");
        }else{
            System.out.println("没有权限添加用户");
        }

        /**
         * 登出
         */
        currentUser.logout();

    }
}
