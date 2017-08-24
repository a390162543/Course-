package cn.zhsite.controller;

import cn.zhsite.extra.Inform;
import cn.zhsite.extra.Result;
import cn.zhsite.model.*;
import cn.zhsite.service.AttendService;
import cn.zhsite.service.CollegeService;
import cn.zhsite.service.MemberService;
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
@RequestMapping("/college")
public class CollegeController {

    @Resource
    private CollegeService collegeService;
    @Resource
    private MemberService memberService;
    @Resource
    private AttendService attendService;
    @Resource
    private HttpSession session;
    @Resource
    private HttpServletRequest request;

    @RequestMapping("/home")
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView();
        College oldCollege = (College) session.getAttribute("college");
        String collegeId = String.valueOf(oldCollege.getId());
        College college = collegeService.getCollege(collegeId);
        session.setAttribute("college",college);
        mav.addObject("college",college);
        mav.setViewName("college/home.jsp");
        return mav;
    }

    @RequestMapping("/course")
    public ModelAndView course(){
        ModelAndView mav = new ModelAndView();
        College college = (College) session.getAttribute("college");
        List<Course> courseList = collegeService.getAllCoursesByCollegeId(String.valueOf(college.getId()));
        mav.addObject("courseList",courseList);
        mav.setViewName("college/course.jsp");
        return mav;
    }

    @RequestMapping("/course/modify/{courseId}")
    public ModelAndView modifyCourse(@PathVariable("courseId") Integer courseId){
        ModelAndView mav = new ModelAndView();
        Course course = collegeService.getCourse(String.valueOf(courseId));
        College college = (College) session.getAttribute("college");
        mav.addObject("course",course);
        mav.setViewName("college/modify-course.jsp");
        return mav;
    }

    @RequestMapping("/modifyCourse.do")
    public ModelAndView modifyCourseHandle(){
        ModelAndView mav = new ModelAndView();
        String courseId = request.getParameter("courseId");
        String name = request.getParameter("name");
        String teacher = request.getParameter("teacher");
        String limitNum = request.getParameter("limitNum");
        String money = request.getParameter("money");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        Course course = collegeService.getCourse(courseId);
        course.setName(name);
        course.setTeacher(teacher);
        course.setLimitNum(Integer.parseInt(limitNum));
        course.setMoney(Double.parseDouble(money));
        course.setFromDate(LocalDate.parse(fromDate));
        course.setToDate(LocalDate.parse(toDate));
        Result result = collegeService.modifyCourse(course);
        Inform inform = new Inform();
        if(result.isSuccess()){
            inform.setTitle("修改成功");
            inform.setLink("/college/course");
        }else{
            inform.setTitle("修改失败");
            inform.setContent("请尝试重新修改课程");
            inform.setLink("/college/course");
        }
        mav.addObject("inform",inform);
        mav.setViewName("inform.jsp");
        return mav;
    }

    @RequestMapping("/registerCourse")
    public ModelAndView registerCourse(){
        ModelAndView mav = new ModelAndView();
        College college = (College) session.getAttribute("college");
        mav.addObject("college",college);
        mav.setViewName("college/register-course.jsp");
        return mav;
    }

    @RequestMapping("/registerCourse.do")
    public ModelAndView registerClassHandle(){
        ModelAndView mav = new ModelAndView();
        String collegeId = request.getParameter("collegeId");
        String name = request.getParameter("name");
        String teacher = request.getParameter("teacher");
        String limitNum = request.getParameter("limitNum");
        String money = request.getParameter("money");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        Course course = new Course();
        course.setCollegeId(Integer.parseInt(collegeId));
        course.setName(name);
        course.setTeacher(teacher);
        course.setLimitNum(Integer.parseInt(limitNum));
        course.setMoney(Double.parseDouble(money));
        course.setFromDate(LocalDate.parse(fromDate));
        course.setToDate(LocalDate.parse(toDate));
        Result result = collegeService.registerCourse(course);
        Inform inform = new Inform();
        if(result.isSuccess()){
            inform.setTitle("开课成功");
            inform.setContent("您已成功开课");
            inform.setLink("/college/course");
        }else{
            inform.setTitle("开课失败");
            inform.setContent(result.getInfo());
            inform.setLink("/college/course");
        }
        mav.addObject("inform",inform);
        mav.setViewName("inform.jsp");
        return mav;
    }

    @RequestMapping("/course/{courseId}/student")
    public ModelAndView student(@PathVariable("courseId") String courseId){
        ModelAndView mav = new ModelAndView();
        Course course = collegeService.getCourse(courseId);
        mav.addObject("course",course);
        mav.setViewName("college/course-student.jsp");
        return mav;
    }

    @RequestMapping("/course/{courseId}/member")
    public ModelAndView member(@PathVariable("courseId") String courseId){
        ModelAndView mav = new ModelAndView();
        Course course = collegeService.getCourse(courseId);
        mav.addObject("course",course);
        mav.setViewName("college/course-member.jsp");
        return mav;
    }

    @RequestMapping("/course/member.do")
    public ModelAndView memberHandle(){
        ModelAndView mav = new ModelAndView();
        String memberId = request.getParameter("memberId");
        String courseId = request.getParameter("courseId");
        Result result = memberService.attendAsMember(memberId,courseId);
        Inform inform = new Inform();
        if(result.isSuccess()){
            inform.setTitle("会员登记成功");
            inform.setLink("/college/course");
        }else{
            inform.setTitle("会员登记失败");
            inform.setContent(result.getInfo());
            inform.setLink("/college/course");
        }
        mav.addObject("inform",inform);
        mav.setViewName("inform.jsp");
        return mav;
    }

    @RequestMapping("/course/student.do")
    public ModelAndView studentHandle(){
        ModelAndView mav = new ModelAndView();
        String idCard = request.getParameter("idCard");
        String courseId = request.getParameter("courseId");
        Result result = memberService.attend(idCard,courseId);
        Inform inform = new Inform();
        if(result.isSuccess()){
            inform.setTitle("学生登记成功");
            inform.setLink("/college/course");
        }else{
            inform.setTitle("学生登记失败");
            inform.setContent(result.getInfo());
            inform.setLink("/college/course");
        }
        mav.addObject("inform",inform);
        mav.setViewName("inform.jsp");
        return mav;
    }

    @RequestMapping("/course/students/{courseId}")
    public ModelAndView courseStudents(@PathVariable("courseId") String courseId){
        ModelAndView mav = new ModelAndView();
        Course course = collegeService.getCourse(courseId);
        List<Attend> attendList = attendService.getAllAttendByCourseId(courseId);

        mav.addObject("course",course);
        mav.addObject("attendList",attendList);
        mav.setViewName("/college/course-allstudent.jsp");
        return mav;
    }

    @RequestMapping("/course/quit.do/{attendId}")
    public ModelAndView courseQuit(@PathVariable("attendId") String attendId){
        ModelAndView mav = new ModelAndView();
        Result result = collegeService.quitCourse(attendId);
        Integer courseId = attendService.getAttendById(attendId).getCourseId();
        Inform inform = new Inform();
        if(result.isSuccess()){
            inform.setTitle("退课成功");
            inform.setContent(result.getInfo());
            inform.setLink("/college/course/students/"+courseId);
        }else{
            inform.setTitle("退课失败");
            inform.setContent(result.getInfo());
            inform.setLink("/college/course/students/"+courseId);
        }
        mav.addObject("inform",inform);
        mav.setViewName("inform.jsp");
        return mav;
    }

    @RequestMapping("/course/score/{attendId}")
    public ModelAndView courseScore(@PathVariable("attendId") String attendId){
        ModelAndView mav = new ModelAndView();
        Attend attend = attendService.getAttendById(attendId);
        mav.addObject("attend",attend);
        mav.setViewName("college/course-score.jsp");
        return mav;
    }

    @RequestMapping("/course/score.do/{attendId}")
    public ModelAndView courseScoreHandle(@PathVariable("attendId") String attendId){
        ModelAndView mav = new ModelAndView();
        String score = request.getParameter("score");
        Result result = collegeService.score(attendId,Double.parseDouble(score));
        Attend attend = attendService.getAttendById(attendId);
        Integer courseId = attend.getCourseId();
        Inform inform = new Inform();
        if(result.isSuccess()){
            inform.setTitle("登记成功");
            inform.setLink("/college/course/students/"+courseId);
        }else{
            inform.setTitle("登记失败");
            inform.setContent("请尝试重新登记");
            inform.setLink("/college/course/students/"+courseId);
        }
        mav.addObject("inform",inform);
        mav.setViewName("inform.jsp");
        return mav;
    }

    @RequestMapping("/payment")
    public ModelAndView payment(){
        ModelAndView mav = new ModelAndView();
        College college = (College) session.getAttribute("college");
        String collegeId = String.valueOf(college.getId());
        List<Payment> paymentList = collegeService.getAllPaymentByCollegeId(collegeId);
        List<Payback> paybackList = collegeService.getAllPaybackByCollegeId(collegeId);
        mav.addObject("paymentList",paymentList);
        mav.addObject("paybackList",paybackList);
        mav.setViewName("college/payment.jsp");
        return mav;
    }
}
