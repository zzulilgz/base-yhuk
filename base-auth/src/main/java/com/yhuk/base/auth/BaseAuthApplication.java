package com.yhuk.base.auth;

import com.yhuk.base.auth.service.MyUserDetailsService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.yhuk.account.client.service"})
@MapperScan(basePackages = {"com.yhuk.base.auth.dao"})
public class BaseAuthApplication extends WebSecurityConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(BaseAuthApplication.class, args);
    }

    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<?,Object> redisTemplate(RedisConnectionFactory
                                                         redisConnectionFactory){
        RedisTemplate<?,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER) //指定实例名称
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //动态配置,使用BCrypt加密算法
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
//       auth.inMemoryAuthentication()
//               .withUser("guest").password("guest")
//               .authorities("WRIGHT_READ")
//               .and()
//               .withUser("admin").password("admin").authorities("WRIGHT_READ","WRIGHT_WRITE");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http
           .formLogin()
           .disable()
           .authorizeRequests()

           .anyRequest().authenticated()
               .antMatchers("/uaa/principal/login")
               .permitAll()
           .and()
           .csrf()
           .disable();
    }

}
