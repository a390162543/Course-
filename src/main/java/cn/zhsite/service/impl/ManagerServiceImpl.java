package cn.zhsite.service.impl;

import cn.zhsite.dao.*;
import cn.zhsite.extra.Result;
import cn.zhsite.model.*;
import cn.zhsite.model.state.CourseState;
import cn.zhsite.model.state.PaybackState;
import cn.zhsite.model.state.PaymentState;
import cn.zhsite.service.ManagerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService{

    @Resource
    StudentDAO studentDAO;
    @Resource
    MemberDAO memberDAO;
    @Resource
    CourseDAO courseDAO;
    @Resource
    PaymentDAO paymentDAO;
    @Resource
    PaybackDAO paybackDAO;
    @Resource
    ManagerDAO managerDAO;
    @Resource
    CourseModifyDAO courseModifyDAO;
    @Resource
    CollegeDAO collegeDAO;

    @Override
    public Result approve(String courseId) {
        Course course = courseDAO.getById(courseId);
        course.setState(CourseState.WAIT);
        courseDAO.update(course);
        Result result = new Result();
        result.set(true,"审批成功");
        return result;
    }

    @Override
    public Result approveModify(String courseId) {
        CourseModify courseModify = courseModifyDAO.getById(courseId);
        Course course = courseDAO.getById(courseId);
        course.setName(courseModify.getName());
        course.setTeacher(courseModify.getTeacher());
        course.setLimitNum(courseModify.getLimitNum());
        course.setMoney(courseModify.getMoney());
        course.setFromDate(courseModify.getFromDate());
        course.setToDate(courseModify.getToDate());
        course.setState(CourseState.WAIT);
        courseDAO.update(course);
        courseModifyDAO.delete(courseModify);
        Result result = new Result();
        result.set(true,"审批成功");
        return result;
    }

    @Override
    public Result settlePayment(String paymentId) {
        Payment payment = paymentDAO.getById(paymentId);
        Integer courseId = payment.getCourseId();
        Course course = courseDAO.getById(courseId);
        College college = collegeDAO.getById(course.getCollegeId());
        college.setMoney(college.getMoney()+payment.getMoney());
        collegeDAO.update(college);
        payment.setState(PaymentState.SETTLE);
        paymentDAO.update(payment);
        Result result = new Result();
        result.set(true,"结算成功");
        return result;
    }

    @Override
    public Result settlePayback(String paybackId) {
        Payback payback = paybackDAO.getById(paybackId);
        if(payback.getState() == PaybackState.CONFIRM){
            payback.setState(PaybackState.SETTLE);
            Student student = studentDAO.getById(payback.getStudentId());
            Member member = memberDAO.getById(student.getMemberId());
            member.setMoney(member.getMoney()+payback.getMoney());
            member.setPoints(member.getPoints()-payback.getMoney());
            memberDAO.update(member);
            paybackDAO.update(payback);
        }
        Result result = new Result();
        result.set(true,"结算成功");
        return result;
    }

    @Override
    public Result validate(String username, String password) {
        return managerDAO.isUserValid(username, password);
    }

    @Override
    public Manager getByUsername(String username) {
        return managerDAO.getBy("username",username);
    }

    @Override
    public List<Course> getAllCourseApproving() {
        return courseDAO.getAllBy("state",CourseState.APPLY);
    }

    @Override
    public List<CourseModify> getAllCourseModifying() {
        return courseModifyDAO.getAll();
    }

    @Override
    public List<Payment> getAllPaymentUnsettled() {
        return paymentDAO.getAllPaymentCanSettle();
    }

    @Override
    public List<Payback> getAllPaybackUnsettled() {
        return paybackDAO.getAllBy("state",PaybackState.CONFIRM);
    }

    @Override
    public List<Payment> getAllPayment() {
        return paymentDAO.getAll();
    }

    @Override
    public List<Payback> getAllPayback() {
        return paybackDAO.getAll();
    }

    @Override
    public List<College> getAllCollege() {
        return collegeDAO.getAll();
    }
}
