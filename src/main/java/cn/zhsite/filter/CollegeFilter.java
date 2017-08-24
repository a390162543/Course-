package cn.zhsite.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CollegeFilter extends OncePerRequestFilter{
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if(httpServletRequest.getSession().getAttribute("college") == null){
            httpServletResponse.sendRedirect("/login/invalid/college");
        }else{
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }
    }
}
