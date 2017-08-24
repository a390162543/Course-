package cn.zhsite.controller;

import cn.zhsite.extra.Inform;
import cn.zhsite.extra.Result;
import cn.zhsite.model.*;
import cn.zhsite.service.AttendService;
import cn.zhsite.service.CollegeService;
import cn.zhsite.service.ManagerService;
import cn.zhsite.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/human")
public class HumanController {

    @Resource
    private TeacherService teacherService;
    @Resource
    private HttpSession session;
    @Resource
    private HttpServletRequest request;

    @RequestMapping("/home")
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView();
        Manager manager = (Manager) session.getAttribute("manager");
        mav.addObject("manager",manager);
        mav.setViewName("human/home.jsp");
        return mav;
    }

    @RequestMapping("/teacher")
    public ModelAndView teacher(){
        ModelAndView mav = new ModelAndView();
        List<Teacher> teacherList = teacherService.getAllTeacher();
        mav.addObject("teacherList",teacherList);
        mav.setViewName("human/teacher.jsp");
        return mav;
    }


    @RequestMapping("/addTeacher")
    public ModelAndView addTeacher(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("human/add-teacher.jsp");
        return mav;
    }

    @RequestMapping("/addTeacher.do")
    public ModelAndView addTeacherHandle(){
        ModelAndView mav = new ModelAndView();
        String name = request.getParameter("name");
        String level = request.getParameter("level");
        String age = request.getParameter("age");
        Teacher teacher = new Teacher();
        teacher.setName(name);
        teacher.setLevel(level);
        teacher.setAge(Integer.parseInt(age));
        Result result = teacherService.addTeacher(teacher);
        Inform inform = new Inform();
        if(result.isSuccess()){
            inform.setTitle("添加成功");
            inform.setContent("您已成功添加教师");
            inform.setLink("/human/teacher");
        }else{
            inform.setTitle("添加失败");
            inform.setContent(result.getInfo());
            inform.setLink("/human/teacher");
        }
        mav.addObject("inform",inform);
        mav.setViewName("inform.jsp");
        return mav;
    }

    @RequestMapping("/modifyTeacher/{teacherId}")
    public ModelAndView modifyTeacher(@PathVariable("teacherId") Integer teacherId){
        ModelAndView mav = new ModelAndView();
        Teacher teacher = teacherService.getTeacherById(teacherId.toString());
        mav.addObject("teacher",teacher);
        mav.setViewName("human/modify-teacher.jsp");
        return mav;
    }

    @RequestMapping("/modifyTeacher.do")
    public ModelAndView modifyTeacher(){
        ModelAndView mav = new ModelAndView();
        String teacherId = request.getParameter("id");
        String name = request.getParameter("name");
        String level = request.getParameter("level");
        String age = request.getParameter("age");
        Teacher teacher = teacherService.getTeacherById(teacherId);
        teacher.setName(name);
        teacher.setLevel(level);
        teacher.setAge(Integer.parseInt(age));
        Result result = teacherService.modifyTeacher(teacher);
        Inform inform = new Inform();
        if(result.isSuccess()){
            inform.setTitle("修改成功");
            inform.setContent("您已成功修改教师");
            inform.setLink("/human/teacher");
        }else{
            inform.setTitle("修改失败");
            inform.setContent(result.getInfo());
            inform.setLink("/human/teacher");
        }
        mav.addObject("inform",inform);
        mav.setViewName("inform.jsp");
        return mav;
    }
}
