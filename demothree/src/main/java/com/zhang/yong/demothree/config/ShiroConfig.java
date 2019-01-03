package com.zhang.yong.demothree.config;

import com.zhang.yong.demothree.security.shiro.CustomRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    //注入自定义的realm，告诉shiro如何获取用户信息来做登录或权限控制
    @Bean
    public Realm realm() {
        return new CustomRealm();
    }

    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        /**
         * setUsePrefix(false)用于解决一个奇怪的bug。在引入spring aop的情况下。
         * 在@Controller注解的类的方法中加入@RequiresRole注解，会导致该方法无法映射请求，导致返回404。
         * 加入这项配置能解决这个bug
         */
        creator.setUsePrefix(true);
        return creator;
    }


    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(Realm realm) {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        // 配置安全管理器
        filterFactoryBean.setSecurityManager(new DefaultWebSecurityManager(realm));
        // 配置登陆、登陆成功、未授权页面
        filterFactoryBean.setLoginUrl("/admin/login");
        filterFactoryBean.setSuccessUrl("/admin/index");
        filterFactoryBean.setUnauthorizedUrl("/admin/unauthc");
        // 配置过滤器
        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("authc",new FormAuthenticationFilter());
        filters.put("anon",new AnonymousFilter());
        filterFactoryBean.setFilters(filters);
        Map<String, String> filterChainDefinitions = new LinkedHashMap<>();
        // 配置拦截的规则 顺序判断
        filterChainDefinitions.put("/css/**", "anon");
        filterChainDefinitions.put("/img/**", "anon");
        filterChainDefinitions.put("/js/**", "anon");
        filterChainDefinitions.put("/thirdpart/**", "anon");
        filterChainDefinitions.put("/admin/login", "anon");
        filterChainDefinitions.put("/admin/unauthc", "anon");
        filterChainDefinitions.put("/redis/**", "anon");
        filterChainDefinitions.put("/**", "authc");
        filterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitions);
        return filterFactoryBean;
    }
}
