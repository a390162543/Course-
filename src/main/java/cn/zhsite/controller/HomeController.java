package cn.zhsite.controller;

import cn.zhsite.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Resource
    private MemberService memberService;

    @RequestMapping("/test")
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView();

        mav.setViewName("index.html");
        return mav;
    }

    @RequestMapping("/logout.do")
    public ModelAndView logoutHandle(){
        ModelAndView mav = new ModelAndView();

        mav.setViewName("redirect:/login/member");
        return mav;
    }
}
