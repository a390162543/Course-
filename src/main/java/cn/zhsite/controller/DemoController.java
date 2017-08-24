package cn.zhsite.controller;

import cn.zhsite.service.DemoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;


@Controller
public class DemoController{

    @Resource
    private DemoService demoService;

    @RequestMapping("/demo")
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView();

        String result = demoService.get();
        mav.addObject("str",result);
        mav.setViewName("inform.jsp");
        return mav;
    }

}
