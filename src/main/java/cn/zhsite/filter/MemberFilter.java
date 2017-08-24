package cn.zhsite.filter;

import cn.zhsite.model.Member;
import cn.zhsite.model.state.MemberState;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MemberFilter extends OncePerRequestFilter{
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        Member member = (Member) httpServletRequest.getSession().getAttribute("member");
        if( member == null){
            httpServletResponse.sendRedirect("/login/invalid/member");
        }else if(member.getState() == MemberState.INVALID){
            httpServletResponse.sendRedirect("/login/invalid/member/error");
        }else{
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }
    }
}
