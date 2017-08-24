package cn.zhsite.controller;

import cn.zhsite.extra.Inform;
import cn.zhsite.extra.Result;
import cn.zhsite.model.*;
import cn.zhsite.service.AttendService;
import cn.zhsite.service.CollegeService;
import cn.zhsite.service.ManagerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/finance")
public class FinanceController {

    @Resource
    private ManagerService managerService;
    @Resource
    private HttpSession session;

    @RequestMapping("/home")
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView();
        Manager manager = (Manager) session.getAttribute("manager");
        mav.addObject("manager",manager);
        mav.setViewName("finance/home.jsp");
        return mav;
    }


    @RequestMapping("/payment")
    public ModelAndView payment(){
        ModelAndView mav = new ModelAndView();
        List<Payment> paymentList = managerService.getAllPaymentUnsettled();
        List<Payback> paybackList = managerService.getAllPaybackUnsettled();
        mav.addObject("paymentList",paymentList);
        mav.addObject("paybackList",paybackList);
        mav.setViewName("finance/payment.jsp");
        return mav;
    }

    @RequestMapping("/payment/paymentSettle.do/{paymentId}")
    public ModelAndView paymentHandle(@PathVariable("paymentId") String paymentId){
        ModelAndView mav = new ModelAndView();
        Result result = managerService.settlePayment(paymentId);
        Inform inform = new Inform();
        if(result.isSuccess()){
            inform.setTitle("结算付款成功");
            inform.setLink("/finance/payment");
        }else{
            inform.setTitle("结算付款失败");
            inform.setContent("请重新尝试");
            inform.setLink("/finance/payment");
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
            inform.setLink("/finance/payment");
        }else{
            inform.setTitle("结算退款失败");
            inform.setContent("请重新尝试");
            inform.setLink("/finance/payment");
        }
        mav.addObject("inform",inform);
        mav.setViewName("inform.jsp");
        return mav;
    }


    @RequestMapping("/finance")
    public ModelAndView finance(){
        ModelAndView mav = new ModelAndView();
        List<Payment> paymentList = managerService.getAllPayment();
        List<Payback> paybackList = managerService.getAllPayback();
        mav.addObject("paymentList",paymentList);
        mav.addObject("paybackList",paybackList);
        mav.setViewName("finance/finance.jsp");
        return mav;
    }


}
