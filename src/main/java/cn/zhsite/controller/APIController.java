package cn.zhsite.controller;

import cn.zhsite.json.JSON;
import cn.zhsite.json.util.DBUtil;
import cn.zhsite.model.Manager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/API")
public class APIController {

    @Resource
    private HttpServletRequest request;

    @RequestMapping("/POST")
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView();
        String sql = request.getParameter("sql");

        mav.addObject("sql", DBUtil.query(sql));
        mav.setViewName("json.jsp");
        return mav;
    }
}
