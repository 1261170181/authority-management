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

    @Autowired
    private MyRealm myRealm;

    //创建安全管理器
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //创建加密对象,指定加密策略
        HashedCredentialsMatcher matcher=new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(6);
        myRealm.setCredentialsMatcher(matcher);
        securityManager.setRealm(myRealm);
        return securityManager;
    }

    //配置Shiro过滤器
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //给ShiroFilter配置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> map = new HashMap<>();
        //配置系统公共资源
        map.put("/auth/**", "anon");//anon表示这个资源无需认证//配置系统受限资源
        map.put("/**", "authc");//authc表示这个资源需要认证和授权
        shiroFilterFactoryBean.setLoginUrl("/auth/login");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

}