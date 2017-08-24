package cn.zhsite.controller;

import cn.zhsite.dao.MemberDAO;
import cn.zhsite.extra.Inform;
import cn.zhsite.extra.Result;
import cn.zhsite.model.College;
import cn.zhsite.model.Member;
import cn.zhsite.service.CollegeService;
import cn.zhsite.service.MemberService;
import com.sun.deploy.net.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Resource
    private MemberService memberService;
    @Resource
    private CollegeService collegeService;
    @Resource
    private HttpServletRequest request;

    @RequestMapping("/member")
    public ModelAndView member(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("register/register-member.html");
        return mav;
    }

    @RequestMapping("/college")
    public ModelAndView college(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("register/register-college.html");
        return mav;
    }

    @RequestMapping("/member.do")
    public ModelAndView memberHandle(){
        ModelAndView mav = new ModelAndView();

        String username = request.getParameter("username");

        Result result = collegeService.isUsernameUnused(username);
        Inform inform = new Inform();
        if(result.isSuccess()){
            Member member = new Member();
            String password = request.getParameter("password");
            String idCard = request.getParameter("idCard");
            String accountId = request.getParameter("accountId");
            member.setUsername(username);
            member.setPassword(password);
            member.setIdcard(idCard);
            member.setAccountId(Integer.parseInt(accountId));
            result = memberService.register(member);
            if(result.isSuccess()){
                inform.setTitle("注册成功");
                inform.setContent("请进行登录");
                inform.setLink("/login/member");
            }else{
                inform.setTitle("注册失败");
                inform.setContent(result.getInfo());
                inform.setLink("/register/member");
            }
        }else{
            inform.setTitle("注册失败");
            inform.setContent(result.getInfo());
            inform.setLink("/register/member");
        }
        mav.addObject("inform",inform);
        mav.setViewName("inform.jsp");
        return mav;
    }

    @RequestMapping("/college.do")
    public ModelAndView collegeHandle(){
        ModelAndView mav = new ModelAndView();

        String username = request.getParameter("username");

        Result result = collegeService.isUsernameUnused(username);
        Inform inform = new Inform();
        if(result.isSuccess()){
            College college = new College();
            String password = request.getParameter("password");
            String name = request.getParameter("name");

            college.setUsername(username);
            college.setPassword(password);
            college.setName(name);
            result = collegeService.register(college);
            if(result.isSuccess()){
                inform.setTitle("注册成功");
                inform.setContent("请进行登录");
                inform.setLink("/login/college");
            }else{
                inform.setTitle("注册失败");
                inform.setContent(result.getInfo());
                inform.setLink("/register/college");
            }
        }else{
            inform.setTitle("注册失败");
            inform.setContent(result.getInfo());
            inform.setLink("/register/college");
        }
        mav.addObject("inform",inform);
        mav.setViewName("inform.jsp");
        return mav;
    }
}
