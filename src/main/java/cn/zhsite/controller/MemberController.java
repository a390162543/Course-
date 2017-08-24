package cn.zhsite.controller;

import cn.zhsite.extra.Inform;
import cn.zhsite.extra.Result;
import cn.zhsite.model.*;
import cn.zhsite.service.CollegeService;
import cn.zhsite.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Resource
    private MemberService memberService;
    @Resource
    private CollegeService collegeService;
    @Resource
    private HttpSession session;
    @Resource
    private HttpServletRequest request;

    @RequestMapping("/home")
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView();
        Member oldMember = (Member) session.getAttribute("member");
        String memberId = String.valueOf(oldMember.getId());
        Member member = memberService.getMember(memberId);
        session.setAttribute("member",member);
        mav.addObject("member",member);
        mav.setViewName("member/home.jsp");
        return mav;
    }

    @RequestMapping("/recharge")
    public ModelAndView recharge(){
        ModelAndView mav = new ModelAndView();
        Member member = (Member) session.getAttribute("member");
        mav.addObject("member",member);
        mav.setViewName("member/recharge.jsp");
        return mav;
    }

    @RequestMapping("/recharge.do")
    public ModelAndView rechargeHandle(){
        ModelAndView mav = new ModelAndView();

        String memberId = request.getParameter("memberId");
        String money = request.getParameter("money");
        Result result = memberService.recharge(memberId,Double.parseDouble(money));
        Inform inform = new Inform();
        if(result.isSuccess()){
            inform.setTitle("银行卡支付成功");
            inform.setContent("您已成功充值");
            inform.setLink("/member/home");
        }else{
            inform.setTitle("支付失败");
            inform.setContent(result.getInfo());
            inform.setLink("/member/home");
        }
        mav.addObject("inform",inform);
        mav.setViewName("inform.jsp");
        return mav;
    }

    @RequestMapping("/invalidate.do")
    public ModelAndView invalidateHandle(){
        ModelAndView mav = new ModelAndView();

        String memberId = String.valueOf(((Member)session.getAttribute("member")).getId());
        Result result = memberService.invalidate(memberId);
        Inform inform = new Inform();
        if(result.isSuccess()){
            inform.setTitle("停用成功");
            inform.setLink("/login/member");
        }else{
            inform.setTitle("停用失败");
            inform.setContent(result.getInfo());
            inform.setLink("/member/home");
        }
        mav.addObject("inform",inform);
        mav.setViewName("inform.jsp");
        return mav;
    }

    @RequestMapping("/course")
    public ModelAndView course(){
        ModelAndView mav = new ModelAndView();
        Member member = (Member) session.getAttribute("member");
        List<Course> courseList = collegeService.getAllCourseMemberNotIn(String.valueOf(member.getId()));
        mav.addObject("courseList",courseList);
        mav.setViewName("member/course.jsp");
        return mav;
    }

    @RequestMapping("/course.do/{courseId}")
    public ModelAndView attendCourseHandle(@PathVariable("courseId") String courseId){
        ModelAndView mav = new ModelAndView();

        String memberId = String.valueOf(((Member)session.getAttribute("member")).getId());
        Result result = memberService.attendAsMember(memberId,courseId);
        Inform inform = new Inform();
        if(result.isSuccess()){
            inform.setTitle("报名成功");
            inform.setLink("/member/course");
        }else{
            inform.setTitle("报名失败");
            inform.setContent(result.getInfo());
            inform.setLink("/member/course");
        }
        mav.addObject("inform",inform);
        mav.setViewName("inform.jsp");
        return mav;
    }

    @RequestMapping("/course/attend")
    public ModelAndView myCourse(){
        ModelAndView mav = new ModelAndView();
        Member member = (Member) session.getAttribute("member");
        List<Attend> attendList = memberService.getAllAttendByMemberId(String.valueOf(member.getId()));
        mav.addObject("attendList",attendList);
        mav.setViewName("member/attend.jsp");
        return mav;
    }

    @RequestMapping("/course/cancelAttend.do/{attendId}")
    public ModelAndView cancelAttendHandle(@PathVariable("attendId") String attendId){
        ModelAndView mav = new ModelAndView();

        Result result = memberService.cancelAttend(attendId);
        Inform inform = new Inform();
        if(result.isSuccess()){
            inform.setTitle("操作成功");
            inform.setLink("/member/course/attend");
        }else{
            inform.setTitle("操作失败");
            inform.setContent(result.getInfo());
            inform.setLink("/member/course/attend");
        }
        mav.addObject("inform",inform);
        mav.setViewName("inform.jsp");
        return mav;
    }

    @RequestMapping("/payment")
    public ModelAndView payment(){
        ModelAndView mav = new ModelAndView();
        Member member = (Member) session.getAttribute("member");
        String memberId = String.valueOf(member.getId());
        List<Payment> paymentList = memberService.getAllPaymentByMemberId(memberId);
        List<Payback> paybackList = memberService.getAllPaybackByMemberId(memberId);
        mav.addObject("paymentList",paymentList);
        mav.addObject("paybackList",paybackList);
        mav.setViewName("member/payment.jsp");
        return mav;
    }

    @RequestMapping("/points.do")
    public ModelAndView pointsHandle(){
        ModelAndView mav = new ModelAndView();
        Member member = (Member) session.getAttribute("member");
        String memberId = String.valueOf(member.getId());
        Result result = memberService.points(memberId);
        Inform inform = new Inform();
        if(result.isSuccess()){
            inform.setTitle("兑换成功");
            inform.setLink("/member/home");
        }else{
            inform.setTitle("兑换失败");
            inform.setContent("请尝试重新操作");
            inform.setLink("/member/home");
        }
        mav.addObject("inform",inform);
        mav.setViewName("inform.jsp");
        return mav;
    }
}
