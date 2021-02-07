package com.atguigu.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * @author guoch0613@163.com
 * @Description
 * @create 2021-02-04 9:35
 */
@Component
public class LoginFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();

        String path = "/vod/video/get-play-auth";
        String requestURI = request.getRequestURI();
        if (!StringUtils.isEmpty(requestURI) && requestURI.toUpperCase().contains(path)){
            System.out.println("requestURI====="+requestURI);
            return true;
        }
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext =  RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        //token对象：JWT
        String token = request.getHeader("token");
        if(StringUtils.isEmpty((token))){
            token  = request.getParameter("token");
        }

        if (StringUtils.isEmpty(token)) {
            //设置为false：请求不会继续转发到下游服务器
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }
        return null;
    }
}
