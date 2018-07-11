package com.gdy;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

public class RealmMain implements Realm{

    @Override
    public String getName() {
        return "myrealm1";
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        //仅支持usernamePasswordToken类型的token
        return token instanceof UsernamePasswordToken;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
       String username = (String) token.getPrincipal();
       String password = new String((char[])token.getCredentials());
       if(!StringUtils.equals("zhangsan",username)){
           throw new UnknownAccountException();
       }
       if(StringUtils.equals("123",password)){
           throw new IncorrectCredentialsException();
       }
       //如果认证成功，返回一个AuthenticationInfo实现
        return new SimpleAuthenticationInfo(username,password,getName());

    }
}
