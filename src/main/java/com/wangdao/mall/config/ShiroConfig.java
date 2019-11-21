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
        shiroFilterFactoryBean.setLoginUrl("/wx/filter/redirect");
        //配置的是拦截器 shiro提供的filter
        //这儿一定要使用linkedHashMap 否则，chain的顺序会有问题
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //第一个参数是请求url 第二个参数是过滤器  “anon” 就是这边可以匿名
        filterChainDefinitionMap.put("/admin/auth/login","anon");
        filterChainDefinitionMap.put("/wx/auth/login","anon");

//        filterChainDefinitionMap.put("/wx/cart/**", "user");
        filterChainDefinitionMap.put("/wx/collect/**", "user");
        filterChainDefinitionMap.put("/wx/comment/post", "user");
        filterChainDefinitionMap.put("/wx/search/clearhistory", "user");
        filterChainDefinitionMap.put("/wx/address/**", "user");
        filterChainDefinitionMap.put("/wx/region/list", "user");
        filterChainDefinitionMap.put("/wx/order/**", "user");
        filterChainDefinitionMap.put("/wx/footprint/**", "user");
        filterChainDefinitionMap.put("/wx/feedback/**", "user");
        filterChainDefinitionMap.put("/wx/groupon/my", "user");
        filterChainDefinitionMap.put("/wx/groupon/detail", "user");
        filterChainDefinitionMap.put("/wx/groupon/join", "user");
        filterChainDefinitionMap.put("/wx/coupon/mylist", "user");
        filterChainDefinitionMap.put("/wx/coupon/selectlist", "user");
        filterChainDefinitionMap.put("/wx/coupon/receive", "user");
        filterChainDefinitionMap.put("/wx/coupon/exchange", "user");
        filterChainDefinitionMap.put("/wx/storage/upload", "user");
        filterChainDefinitionMap.put("/wx/user/index", "user");

        filterChainDefinitionMap.put("/wx/cart/index", "user");
        filterChainDefinitionMap.put("/wx/cart/add", "user");
        filterChainDefinitionMap.put("/wx/cart/fastadd", "user");
        filterChainDefinitionMap.put("/wx/cart/update", "user");
        filterChainDefinitionMap.put("/wx/cart/delete", "user");
        filterChainDefinitionMap.put("/wx/cart/checked", "user");
        filterChainDefinitionMap.put("/wx/cart/checkout", "user");


        //filterChainDefinitionMap.put("/user/query","perms[user:query]");
//        filterChainDefinitionMap.put("/**","anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }



    /*SecurityManager*/
    @Bean
    public DefaultWebSecurityManager securityManager(AdminRealm adminRealm, WxRealm wxRealm,
                                                     CustomSessionManager sessionManager,
                                                     CustomRealmAuthenticator authenticator){

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //securityManager.setRealm(customRealm);  把自己写的 realm 都放到reals里面   1
        ArrayList<Realm> realms = new ArrayList<>();
        realms.add(adminRealm);
        realms.add(wxRealm);
        //单个realm
        securityManager.setRealms(realms);    // 并且在securitymanager中配置上
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

    /**
     *
     * 注册这个sessionManager 并且设置了时间
     * @return
     */
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
        realms.add(wxRealm);  // 注册 两个Realm
        customRealmAuthenticator.setRealms(realms);
        return customRealmAuthenticator;
    }
}
