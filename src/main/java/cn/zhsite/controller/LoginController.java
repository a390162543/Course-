package cn.zhsite.controller;

import cn.zhsite.extra.Inform;
import cn.zhsite.extra.Result;
import cn.zhsite.model.College;
import cn.zhsite.model.Manager;
import cn.zhsite.model.Member;
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

@Controller
@RequestMapping("/login")
public class LoginController {

    @Resource
    private MemberService memberService;
    @Resource
    private CollegeService collegeService;
    @Resource
    private ManagerService managerService;
    @Resource
    private HttpServletRequest request;
    @Resource
    private HttpSession session;

    @RequestMapping("/index")
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index.html");
        return mav;
    }

    @RequestMapping("/member")
    public ModelAndView member(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login/login-member.html");
        return mav;
    }

    @RequestMapping("/college")
    public ModelAndView college(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login/login-college.html");
        return mav;
    }

    @RequestMapping("/manager")
    public ModelAndView manager(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login/login-manager.html");
        return mav;
    }

    @RequestMapping("/finance")
    public ModelAndView finance(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login/login-finance.html");
        return mav;
    }

    @RequestMapping("/human")
    public ModelAndView human(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login/login-human.html");
        return mav;
    }


    @RequestMapping("/member.do")
    public ModelAndView memberHandle(){
        ModelAndView mav = new ModelAndView();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Result result = memberService.validate(username,password);
        if(result.isSuccess()){
            Member member = memberService.getByUsername(username);
            session.setAttribute("member",member);
            mav.addObject("member",member);
            mav.setViewName("redirect:/member/home");
        }else{
            Inform inform = new Inform();
            inform.setTitle("登录失败");
            inform.setContent("请检查用户名与密码，并尝试重新登录");
            inform.setLink("/login/member");
            mav.addObject("inform",inform);
            mav.setViewName("inform.jsp");
        }
        return mav;
    }

    @RequestMapping("/college.do")
    public ModelAndView collegeHandle(){
        ModelAndView mav = new ModelAndView();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Result result = collegeService.validate(username,password);
        if(result.isSuccess()){
            College college = collegeService.getByUsername(username);
            session.setAttribute("college",college);
            mav.addObject("college",college);
            mav.setViewName("redirect:/college/home");
        }else{
            Inform inform = new Inform();
            inform.setTitle("登录失败");
            inform.setContent("请检查用户名与密码，并尝试重新登录");
            inform.setLink("/login/college");
            mav.addObject("inform",inform);
            mav.setViewName("inform.jsp");
        }
        return mav;
    }


    @RequestMapping("/manager.do")
    public ModelAndView managerHandle(){
        ModelAndView mav = new ModelAndView();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Result result = managerService.validate(username,password);
        if(result.isSuccess()){
            Manager manager = managerService.getByUsername(username);
            session.setAttribute("manager",manager);
            mav.addObject("manager",manager);
            mav.setViewName("redirect:/manage/home");
        }else{
            Inform inform = new Inform();
            inform.setTitle("登录失败");
            inform.setContent("请检查用户名与密码，并尝试重新登录");
            inform.setLink("/login/manager");
            mav.addObject("inform",inform);
            mav.setViewName("inform.jsp");
        }
        return mav;
    }

    @RequestMapping("/finance.do")
    public ModelAndView financeHandle(){
        ModelAndView mav = new ModelAndView();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Result result = managerService.validate(username,password);
        if(result.isSuccess()){
            Manager manager = managerService.getByUsername(username);
            session.setAttribute("manager",manager);
            mav.addObject("manager",manager);
            mav.setViewName("redirect:/finance/home");
        }else{
            Inform inform = new Inform();
            inform.setTitle("登录失败");
            inform.setContent("请检查用户名与密码，并尝试重新登录");
            inform.setLink("/login/finance");
            mav.addObject("inform",inform);
            mav.setViewName("inform.jsp");
        }
        return mav;
    }


    @RequestMapping("/human.do")
    public ModelAndView humanHandle(){
        ModelAndView mav = new ModelAndView();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Result result = managerService.validate(username,password);
        if(result.isSuccess()){
            Manager manager = managerService.getByUsername(username);
            session.setAttribute("manager",manager);
            mav.addObject("manager",manager);
            mav.setViewName("redirect:/human/home");
        }else{
            Inform inform = new Inform();
            inform.setTitle("登录失败");
            inform.setContent("请检查用户名与密码，并尝试重新登录");
            inform.setLink("/login/human");
            mav.addObject("inform",inform);
            mav.setViewName("inform.jsp");
        }
        return mav;
    }

    @RequestMapping("/invalid/{user}")
    public ModelAndView invalidHandle(@PathVariable("user")String user){
        ModelAndView mav = new ModelAndView();
        Inform inform = new Inform();
        inform.setTitle("用户未登录");
        inform.setContent("请重新登录后使用系统");
        inform.setLink("/login/"+user);
        mav.addObject("inform",inform);
        mav.setViewName("inform.jsp");

        return mav;
    }

    @RequestMapping("/invalid/member/error")
    public ModelAndView invalidMemberHandle(){
        ModelAndView mav = new ModelAndView();
        Inform inform = new Inform();
        inform.setTitle("用户停用");
        inform.setContent("用户账户已经停止使用");
        inform.setLink("/login/member");
        mav.addObject("inform",inform);
        mav.setViewName("inform.jsp");

        return mav;
    }
}
