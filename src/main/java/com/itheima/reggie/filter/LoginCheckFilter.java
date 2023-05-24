package com.itheima.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "LoginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    //路径匹配器，可以使用通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        /*
        * 1.获取本次请求的URL
          2.判断本次请求是否需要处理
          3.如果不需要处理，则直接放行
          4.判断登录状态，如果已经登录，则直接放行
          5.如果未登录则返回未登录的结果

        * */
        //1.获取本次请求的URL
        String requestURI = request.getRequestURI();
        log.info("本次请求的路径是：{}",requestURI);
        //定义不需要被处理的请求路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**"

        };
        //2.判断本次请求是否需要处理
        boolean check = check(urls,requestURI);
        // 3.如果不需要处理，则直接放行
        if(check){
            log.info("本次请求不需要处理:{}",requestURI);
            filterChain.doFilter(request,response);//放行
            return;
        }
        //4.判断登录状态，如果已经登录，则直接放行
        if (request.getSession().getAttribute("employee") != null) {
            log.info("已登录状态，用户id为：{}",request.getSession().getAttribute("employee"));
            Long empId =(Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);
            /*long id = Thread.currentThread().getId();
            log.info("filter线程id是：{}",id);*/
            filterChain.doFilter(request,response);//放行
            return;
        }
        //5.如果未登录则返回未登录的结果
        log.info("未登录");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;




      /*  log.info("拦截到请求：{}",request.getRequestURI());*/

    }

    /**
     *
     * @param urls 不需要被处理的请求路径
     * @param requestURI 请求路径
     * @return
     */
     public boolean check(String[] urls,String requestURI){
        for(String url:urls){
            boolean match = PATH_MATCHER.match(url, requestURI);
            if(match){
                return true;}
        }
        return false;
    }
}
