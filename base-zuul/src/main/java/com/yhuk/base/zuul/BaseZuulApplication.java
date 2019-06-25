package com.yhuk.base.zuul;

import com.yhuk.account.client.service.RoleClient;
import com.yhuk.account.client.service.UserClient;
import com.yhuk.base.zuul.config.MyAccessDecisionManager;
import com.yhuk.base.zuul.config.MyFilterSecurityInterceptor;
import com.yhuk.base.zuul.config.MySecurityMetadataSource;
import com.yhuk.base.zuul.config.SecuritySettings;
import com.yhuk.base.zuul.filters.LoggerFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
@EnableOAuth2Sso
@EnableConfigurationProperties(SecuritySettings.class)
@EnableFeignClients(basePackages = {"com.yhuk.account.client"})
public class BaseZuulApplication extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    SecuritySettings settings;

    @Autowired
    private RoleClient roleClient;

    public static void main(String[] args) {
        SpringApplication.run(BaseZuulApplication.class, args);
    }


    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().disable()
                .authorizeRequests()
                .antMatchers("/oauth/token")
                .permitAll()
                .anyRequest().authenticated();
    }
    @Bean
    public MyFilterSecurityInterceptor customFilter() throws Exception{
        MyFilterSecurityInterceptor customFilter = new MyFilterSecurityInterceptor();
        customFilter.setSecurityMetadataSource(securityMetadataSource());
        customFilter.setAccessDecisionManager(accessDecisionManager());
        customFilter.setAuthenticationManager(authenticationManager);
        return customFilter;
    }

    @Bean
    public MyAccessDecisionManager accessDecisionManager() {
        return new MyAccessDecisionManager();
    }

    @Bean
    public MySecurityMetadataSource securityMetadataSource() {
        return new MySecurityMetadataSource(roleClient);
    }
    @Bean
    public LoggerFilter loggerFilter(){
        return new LoggerFilter();
    }


//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//                 http
//                .authorizeRequests()
//                .antMatchers("/login")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .csrf()
//                .disable();
//    }
}
