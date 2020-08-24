package com.baidu.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EncodingFilter implements Filter{

    //初始化 只会执行一次
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub
        //System.out.println("过滤器初始化");

    }

    //过滤器销毁方法
    public void destroy() {
        // TODO Auto-generated method stub
        //System.out.println("过滤器销毁");

    }

    //过滤的核心方法
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc)
            throws IOException, ServletException {
        // TODO Auto-generated method stub
        HttpServletRequest req=(HttpServletRequest)request;
        HttpServletResponse resp=(HttpServletResponse)response;


        req.setCharacterEncoding("UTF-8");//处理 post乱码
        resp.setCharacterEncoding("UTF-8");


        //放行
        fc.doFilter(req, resp);

    }



}
