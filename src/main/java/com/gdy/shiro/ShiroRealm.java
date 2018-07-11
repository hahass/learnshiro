package com.gdy.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashSet;
import java.util.Set;

public class ShiroRealm extends AuthorizingRealm {

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {


        System.out.println("firstshiroRealm");

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
            credentials = "13215500ae4ea87b410201e85837af7f";
        }else if("user".equals(username)){
            credentials = "169f8cc66024d24306331acfe1064381";
        }


        //3)realmName:当前realm对象的name，调用父类的getname()方法即可
        String realmName = getName();
        //4)盐值
        ByteSource credentialsSalt = ByteSource.Util.bytes(username);

        SimpleAuthenticationInfo info = null;
        info = new SimpleAuthenticationInfo(principal,credentials,credentialsSalt,realmName);

        return info;
    }

    public static void main(String[] args) {
        String hashAlgorithmName = "MD5";
        Object credentials = "12345";
        Object salt = ByteSource.Util.bytes("user");
        int hashIterations = 1024;
        Object result = new SimpleHash(hashAlgorithmName,credentials,salt,hashIterations);
        System.out.println(result);
    }

    //授权 会被 shiro 回调方法

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        //1. 从PrincipalCollection中来获取登录用户信息
        Object principal = principals.getPrimaryPrincipal();
        //2.利用登录的用户信息来当前用户的角色和权限(可能需要查询数据库)
        Set<String> roles = new HashSet<String>();
        roles.add("user");
        if("admin".equals(principal)){
            roles.add("admin");
        }
        //3.创建SimpleAuthorizationInfo，并设置其reles属性
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
        //4.返回SimpleAuthorizationInfo对象
        return info;
    }
}
