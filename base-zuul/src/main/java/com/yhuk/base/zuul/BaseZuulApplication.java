package com.yhuk.base.zuul;

import com.yhuk.base.zuul.filters.LoggerFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class BaseZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseZuulApplication.class, args);
    }

    @Bean
    public LoggerFilter loggerFilter(){
        return new LoggerFilter();
    }
}
