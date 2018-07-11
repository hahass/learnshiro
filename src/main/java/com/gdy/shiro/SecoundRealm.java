package com.gdy.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;

public class SecoundRealm extends AuthenticatingRealm {

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        System.out.println("secoundRealm");

        //1.把AuthenticationToken转为UsernamePasswordToken

        UsernamePasswordToken uptoken = (UsernamePasswordToken) token;

        //2.从UsernamePasswordToken中获取username

        String username = uptoken.getUsername();

        //3.调用数据库方法，从数据库中取出username对应的用户信息

        System.out.println("从数据库中取出username:"+username+"的所有用户信息");

        //4.若用户不存在，则可以抛出UnknownAccountException异常

        if("unknow".equals(username)){
            throw new UnknownAccountException("用户不存在");
        }

        //5.根据用户信息的情况，决定是否需要抛出其他的AuthenticationExcaptin异常

        if("monster".equals(username)){
            throw new LockedAccountException("用户被锁定");
        }

        //6.根据用户的情况，来构建AuthenticationInfo对象并返回
        //以下信息从数据库中获取
        //1)principal:认证实体的信息 ，可以是username,也可以是数据表中对应的用户实体类对象
        Object principal = username;
        //2)credentials :密码

        Object credentials = null;
        if("admin".equals(username)){
            credentials = "1b257f95ac34ef075aeb3c7b6c00f841a10268f2222";
        }else if("user".equals(username)){
            credentials = "c0ea2ff604cab89d32943d91e2510ce09b176f30";
        }


        //3)realmName:当前realm对象的name，调用父类的getname()方法即可
        String realmName = getName();
        //4)盐值
        ByteSource credentialsSalt = ByteSource.Util.bytes(username);

        SimpleAuthenticationInfo info = null;
        info = new SimpleAuthenticationInfo("secoundRealm",credentials,credentialsSalt,realmName);

        return info;
    }

    public static void main(String[] args) {
        String hashAlgorithmName = "SHA1";
        Object credentials = "12345";
        Object salt = ByteSource.Util.bytes("admin");
        int hashIterations = 1024;
        Object result = new SimpleHash(hashAlgorithmName,credentials,salt,hashIterations);
        System.out.println(result);
    }

}
