package com.wangdao.mall.config;

import com.wangdao.mall.shiro.AdminRealm;
import com.wangdao.mall.shiro.CustomRealmAuthenticator;
import com.wangdao.mall.shiro.CustomSessionManager;
import com.wangdao.mall.shiro.WxRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig {

    //login → anon匿名
    //index → 认证之后才能访问
    //info
    //success


    /*shiroFilter*/
    @Bean
    public ShiroFilterFactoryBean myShiroFilterFactoryBean(DefaultWebSecurityManager securityManager){

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //认证失败重定向的url
//        shiroFilterFactoryBean.setLoginUrl("/admin/redirect");
        //配置的是拦截器 shiro提供的filter
        //这儿一定要使用linkedHashMap 否则，chain的顺序会有问题
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //第一个参数是请求url 第二个参数是过滤器
        filterChainDefinitionMap.put("/admin/auth/login","anon");
        filterChainDefinitionMap.put("/wx/auth/login","anon");
        //filterChainDefinitionMap.put("/user/query","perms[user:query]");

        filterChainDefinitionMap.put("/**","anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }
    /*SecurityManager*/
    @Bean
    public DefaultWebSecurityManager securityManager(AdminRealm adminRealm, WxRealm wxRealm,
                                                     CustomSessionManager sessionManager,
                                                     CustomRealmAuthenticator authenticator){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //securityManager.setRealm(customRealm);
        ArrayList<Realm> realms = new ArrayList<>();
        realms.add(adminRealm);
        realms.add(wxRealm);
        //单个realm
        securityManager.setRealms(realms);
        securityManager.setSessionManager(sessionManager);
        securityManager.setAuthenticator(authenticator);
        return securityManager;
    }

    /*声明式鉴权*/
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager){

        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public CustomSessionManager sessionManager(){
        CustomSessionManager customSessionManager = new CustomSessionManager();
        customSessionManager.setDeleteInvalidSessions(true);
        customSessionManager.setGlobalSessionTimeout(24*60*60*1000);
        return customSessionManager;
    }

    @Bean
    public CustomRealmAuthenticator authenticator(AdminRealm adminRealm, WxRealm wxRealm){
        CustomRealmAuthenticator customRealmAuthenticator = new CustomRealmAuthenticator();
        ArrayList<Realm> realms = new ArrayList<>();
        realms.add(adminRealm);
        realms.add(wxRealm);
        customRealmAuthenticator.setRealms(realms);
        return customRealmAuthenticator;
    }
}
