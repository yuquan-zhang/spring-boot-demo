package com.zhang.yong.demothree.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class AttributeFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest)request;
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+servletRequest.getContextPath();
        request.setAttribute("basePath",basePath);
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
