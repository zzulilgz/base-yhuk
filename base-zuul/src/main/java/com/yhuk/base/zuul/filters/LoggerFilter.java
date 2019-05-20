package com.yhuk.base.zuul.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.context.annotation.FilterType;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 请求日志记录
 * @Author gaozhi.liu
 * @Date 2019/5/20 16:36
 * @Version 1.0
 **/
public class LoggerFilter extends ZuulFilter {

    public static final Logger logger = LoggerFactory.getLogger(LoggerFilter.class);

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE; //前置拦截器必须指定类型
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() { //
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("test------------");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        logger.info("请求uri:{}",request.getServletPath());
        return null;
    }
}
