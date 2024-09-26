package com.zhu.authoritymanagement.config;

import com.zhu.authoritymanagement.shiro.MyRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
*
*
*@author zhu
*@since 2024-9-24
*/
@Configuration
public class ShiroConfig {

    private MyRealm myRealm;

    @Autowired
    public void setMyRealm(MyRealm myRealm) {
        this.myRealm = myRealm;
    }

    /**
     * 创建加密对象,指定加密策略
     */
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        HashedCredentialsMatcher matcher=new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(1);
        myRealm.setCredentialsMatcher(matcher);
        securityManager.setRealm(myRealm);
        return securityManager;
    }

    /**
     * 配置Shiro过滤器
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> map = new HashMap<>(16);
        /*
          anon: 无需认证就可以访问
          authc: 必须认证了才能访问
          perms: 拥有对某个资源的权限才能访问
          roles: 拥有某个角色权限才能访问
         */
        map.put("/auth/**", "anon");
        map.put("/**", "authc");
        map.put("/account/**","perms[account/]");
        map.put("/role/**","perms[role/]");
        map.put("/resource/**","perms[resource/]");
        map.put("/customer/**","perms[customer/]");
        shiroFilterFactoryBean.setLoginUrl("/auth/login");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

}