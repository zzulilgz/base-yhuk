package com.yhuk.base.auth;

import com.yhuk.base.auth.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.yhuk.account.client.service"})
public class BaseAuthApplication extends WebSecurityConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(BaseAuthApplication.class, args);
    }

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER) //指定实例名称
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //动态配置,使用BCrypt加密算法
        auth.userDetailsService(myUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
//       auth.inMemoryAuthentication()
//               .withUser("guest").password("guest")
//               .authorities("WRIGHT_READ")
//               .and()
//               .withUser("admin").password("admin").authorities("WRIGHT_READ","WRIGHT_WRITE");
    }


}
