package cn.zhsite.controller;

import cn.zhsite.extra.Inform;
import cn.zhsite.extra.Result;
import cn.zhsite.model.*;
import cn.zhsite.service.AttendService;
import cn.zhsite.service.CollegeService;
import cn.zhsite.service.ManagerService;
import cn.zhsite.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/manage")
public class ManagerController {

    @Resource
    private ManagerService managerService;
    @Resource
    private CollegeService collegeService;
    @Resource
    private AttendService attendService;
    @Resource
    private HttpSession session;
    @Resource
    private HttpServletRequest request;

    @RequestMapping("/home")
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView();
        Manager manager = (Manager) session.getAttribute("manager");
        mav.addObject("manager",manager);
        mav.setViewName("manager/home.jsp");
        return mav;
    }

    @RequestMapping("/course")
    public ModelAndView course(){
        ModelAndView mav = new ModelAndView();
        List<Course> courseList = managerService.getAllCourseApproving();
        List<CourseModify> courseModifyList = managerService.getAllCourseModifying();
        mav.addObject("courseList",courseList);
        mav.addObject("courseModifyList",courseModifyList);
        mav.setViewName("manager/course.jsp");
        return mav;
    }

    @RequestMapping("/course/approve.do/{courseId}")
    public ModelAndView approveHandle(@PathVariable("courseId") String courseId){
        ModelAndView mav = new ModelAndView();
        Result result = managerService.approve(courseId);
        Inform inform = new Inform();
        if(result.isSuccess()){
            inform.setTitle("审批成功");
            inform.setLink("/manage/course");
        }else{
            inform.setTitle("审批失败");
            inform.setContent("请重新尝试");
            inform.setLink("/manage/course");
        }
        mav.addObject("inform",inform);
        mav.setViewName("inform.jsp");
        return mav;
    }

    @RequestMapping("/course/approveModify.do/{courseId}")
    public ModelAndView approveModify(@PathVariable("courseId") String courseId){
        ModelAndView mav = new ModelAndView();
        Result result = managerService.approveModify(courseId);
        Inform inform = new Inform();
        if(result.isSuccess()){
            inform.setTitle("审批修改成功");
            inform.setLink("/manage/course");
        }else{
            inform.setTitle("审批修改失败");
            inform.setContent("请重新尝试");
            inform.setLink("/manage/course");
        }
        mav.addObject("inform",inform);
        mav.setViewName("inform.jsp");
        return mav;
    }

    @RequestMapping("/payment")
    public ModelAndView payment(){
        ModelAndView mav = new ModelAndView();
        List<Payment> paymentList = managerService.getAllPaymentUnsettled();
        List<Payback> paybackList = managerService.getAllPaybackUnsettled();
        mav.addObject("paymentList",paymentList);
        mav.addObject("paybackList",paybackList);
        mav.setViewName("manager/payment.jsp");
        return mav;
    }

    @RequestMapping("/payment/paymentSettle.do/{paymentId}")
    public ModelAndView paymentHandle(@PathVariable("paymentId") String paymentId){
        ModelAndView mav = new ModelAndView();
        Result result = managerService.settlePayment(paymentId);
        Inform inform = new Inform();
        if(result.isSuccess()){
            inform.setTitle("结算付款成功");
            inform.setLink("/manage/payment");
        }else{
            inform.setTitle("结算付款失败");
            inform.setContent("请重新尝试");
            inform.setLink("/manage/payment");
        }
        mav.addObject("inform",inform);
        mav.setViewName("inform.jsp");
        return mav;
    }

    @RequestMapping("/payment/paybackSettle.do/{paybackId}")
    public ModelAndView paybackHandle(@PathVariable("paybackId") String paybackId){
        ModelAndView mav = new ModelAndView();
        Result result = managerService.settlePayback(paybackId);
        Inform inform = new Inform();
        if(result.isSuccess()){
            inform.setTitle("结算退款成功");
            inform.setLink("/manage/payment");
        }else{
            inform.setTitle("结算退款失败");
            inform.setContent("请重新尝试");
            inform.setLink("/manage/payment");
        }
        mav.addObject("inform",inform);
        mav.setViewName("inform.jsp");
        return mav;
    }

    @RequestMapping("/college")
    public ModelAndView college(){
        ModelAndView mav = new ModelAndView();
        List<College> collegeList = managerService.getAllCollege();
        mav.addObject("collegeList",collegeList);
        mav.setViewName("manager/college.jsp");
        return mav;
    }

    @RequestMapping("/college/{collegeId}")
    public ModelAndView collegeCourse(@PathVariable("collegeId") String collegeId){
        ModelAndView mav = new ModelAndView();
        List<Course> courseList = collegeService.getAllCoursesByCollegeId(collegeId);
        mav.addObject("collegeId",collegeId);
        mav.addObject("courseList",courseList);
        mav.setViewName("manager/college-course.jsp");
        return mav;
    }

    @RequestMapping("/college/course/{courseId}")
    public ModelAndView collegeCourseStudent(@PathVariable("courseId") String courseId){
        ModelAndView mav = new ModelAndView();
        List<Attend> attendList = attendService.getAllAttendByCourseId(courseId);
        Course course = collegeService.getCourse(courseId);
        mav.addObject("collegeId",course.getCollegeId());
        mav.addObject("attendList",attendList);
        mav.setViewName("manager/college-course-student.jsp");
        return mav;
    }

    @RequestMapping("/finance")
    public ModelAndView finance(){
        ModelAndView mav = new ModelAndView();
        List<Payment> paymentList = managerService.getAllPayment();
        List<Payback> paybackList = managerService.getAllPayback();
        mav.addObject("paymentList",paymentList);
        mav.addObject("paybackList",paybackList);
        mav.setViewName("manager/finance.jsp");
        return mav;
    }


}
