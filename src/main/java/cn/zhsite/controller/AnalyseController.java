package cn.zhsite.controller;

import cn.zhsite.extra.Inform;
import cn.zhsite.extra.Result;
import cn.zhsite.model.Manager;
import cn.zhsite.model.Teacher;
import cn.zhsite.service.InitService;
import cn.zhsite.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/analyse")
public class AnalyseController {

    @Resource
    private TeacherService teacherService;
    @Resource
    private InitService initService;
    @Resource
    private HttpSession session;
    @Resource
    private HttpServletRequest request;

    @RequestMapping("/home")
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView();
        Manager manager = (Manager) session.getAttribute("manager");
        mav.addObject("manager",manager);
        mav.setViewName("analyse/home.jsp");
        return mav;
    }

    @RequestMapping("/init")
    public ModelAndView init(){
        ModelAndView mav = new ModelAndView();
        initService.init();
        mav.setViewName("analyse/home.jsp");
        return mav;
    }
//    @RequestMapping("/student")
//    public ModelAndView student(){
//        ModelAndView mav = new ModelAndView();
//        mav.setViewName("analyse/student.jsp");
//        return mav;
//    }
//
//    @RequestMapping("/student/{studentId}")
//    public ModelAndView student(@PathVariable("studentId") String studentId){
//        ModelAndView mav = new ModelAndView();
//        mav.setViewName("analyse/student-info.jsp");
//        return mav;
//    }

    @RequestMapping("/total/{path}")
    public ModelAndView routeTotal(@PathVariable("path")String path){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("analyse/"+path+"-total.jsp");
        return mav;
    }

    @RequestMapping("/{path}")
    public ModelAndView route(@PathVariable("path")String path){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("analyse/"+path+".jsp");
        return mav;
    }

    @RequestMapping("/{path}/{id}")
    public ModelAndView route(@PathVariable("path")String path,@PathVariable("id")Integer id){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("analyse/"+path+"-info.jsp");
        mav.addObject("id",id);
        return mav;
    }


}
