package cn.zhsite.service.impl;

import cn.zhsite.dao.*;
import cn.zhsite.extra.Result;
import cn.zhsite.extra.Util;
import cn.zhsite.model.*;
import cn.zhsite.model.state.*;
import cn.zhsite.service.CollegeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.temporal.TemporalField;
import java.util.List;

@Service
public class CollegeServiceImpl implements CollegeService{

    @Resource
    CollegeDAO collegeDAO;
    @Resource
    CourseDAO courseDAO;
    @Resource
    StudentDAO studentDAO;
    @Resource
    MemberDAO memberDAO;
    @Resource
    PaybackDAO paybackDAO;
    @Resource
    PaymentDAO paymentDAO;
    @Resource
    CourseModifyDAO courseModifyDAO;
    @Resource
    AttendDAO attendDAO;
    @Resource
    QuitDAO quitDAO;

    @Override
    public Result register(College college) {
        Result isUnused = collegeDAO.isUsernameUnused(college.getUsername());
        Result result = new Result();
        if(isUnused.isSuccess()){
            college.setState(CollegeState.NORMAL);
            college.setCreateTime(Util.getCurrentDateTime());
            college.setMoney(0.0);
            collegeDAO.insert(college);
            result.set(true,"注册机构成功");
        }else{
            result.set(false,"用户名已经被占用");
        }
        return result;
    }

    @Override
    public Result registerCourse(Course course) {
        Result result = new Result();
        course.setState(CourseState.APPLY);
        course.setCreateTime(Util.getCurrentDateTime());
        courseDAO.insert(course);
        result.set(true,"注册课程成功");
        return result;
    }

    @Override
    public Result modifyCourse(Course course) {
        Result result = new Result();
        Course oldCourse = courseDAO.getById(course.getId());
        oldCourse.setState(CourseState.MODIFY);
        courseDAO.update(oldCourse);
        CourseModify courseModify = new CourseModify();
        courseModify.setId(course.getId());
        courseModify.setName(course.getName());
        courseModify.setTeacher(course.getTeacher());
        courseModify.setLimitNum(course.getLimitNum());
        courseModify.setMoney(course.getMoney());
        courseModify.setFromDate(course.getFromDate());
        courseModify.setToDate(course.getToDate());
        courseModify.setState(CourseState.MODIFY);
        courseModifyDAO.insert(courseModify);
        result.set(true,"修改课程成功");
        return result;
    }

    @Override
    public Course getCourse(String courseId) {
        return courseDAO.getById(courseId);
    }

    @Override
    public List<Course> getAllCoursesByCollegeId(String collegeId) {
        return courseDAO.getAllBy("collegeId",Integer.parseInt(collegeId));
    }

    @Override
    public List<Course> getAllCourseMemberNotIn(String memberId) {
        Student student = studentDAO.getByMemberId(memberId);
        return courseDAO.getAllCourseStudentNotIn(student.getId());
    }

    @Override
    public Result score(String attendId, Double score) {
        Result result = new Result();
        Attend attend = attendDAO.getById(attendId);
        attend.setScore(score);
        attendDAO.update(attend);
        result.set(true,"已更新成绩");
        return result;
    }

    @Override
    public Result validate(String username, String password) {
        return collegeDAO.isUserValid(username,password);
    }

    public Result isUsernameUnused(String username){
        return collegeDAO.isUsernameUnused(username);
    }

    @Override
    public College getByUsername(String username) {
        return collegeDAO.getBy("username",username);
    }

    @Override
    public College getCollege(String collegeId) {
        return collegeDAO.getById(collegeId);
    }

    @Override
    public Result quitCourse(String attendId) {
        Attend attend = attendDAO.getById(attendId);
        Student student = attend.getStudent();
        Course course = attend.getCourse();
        Result result = new Result();
        boolean isMember = (student.getMember() != null);

        //退课
        if(attend.getState() == AttendState.ATTEND){
            attend.setState(AttendState.QUIT);
            attendDAO.update(attend);

            Quit quit = new Quit();
            quit.setStudentId(attend.getStudentId());
            quit.setCourseId(attend.getCourseId());
            quit.setCreateTime(Util.getCurrentDateTime());
            quit.setDate(Util.getCurrentDate());
            quitDAO.insert(quit);

            long toDate = course.getToDate().toEpochDay();
            long fromDate = course.getFromDate().toEpochDay();
            long currentDate = Util.getCurrentDate().toEpochDay();
            double backRate =  (toDate-currentDate)*1.0/(toDate-fromDate);

            Payback payback = new Payback();
            payback.setCourseId(attend.getCourseId());
            payback.setStudentId(attend.getStudentId());
            payback.setCreateTime(Util.getCurrentDateTime());


            if(isMember){
                //学员卡支付，需要修改payment的金额
                Payment payment = paymentDAO.getPaymentCanCancelBy(course.getId(),student.getId());
                Double oldPaymentMoney = payment.getMoney();
                Double backMoney = backRate*oldPaymentMoney;
                payback.setMoney(backMoney);
                //学员退款需要经理结算才返回金额，付款单需要部分退回
                payback.setState(PaybackState.CONFIRM);
                payment.setMoney(payment.getMoney()-backMoney);
                payment.setState(PaymentState.CANCEL);
                paybackDAO.insert(payback);
                paymentDAO.update(payment);
                result.set(true,"退课成功,退回学员卡余额"+payback.getMoney()+"元");
            }else{
                Double backMoney = backRate*course.getMoney();
                payback.setMoney(backMoney);
                //普通退款不更新付款单，采用现金交易，退款单不需要结算
                payback.setState(PaybackState.SETTLE);
                paybackDAO.insert(payback);
                result.set(true,"退课成功,退回现金"+payback.getMoney()+"元");
            }
            //取消预定
        }else if(attend.getState() == AttendState.PRE){
            attend.setState(AttendState.CANCEL);
            attendDAO.update(attend);

            Payback payback = new Payback();
            payback.setCourseId(attend.getCourseId());
            payback.setStudentId(attend.getStudentId());
            payback.setCreateTime(Util.getCurrentDateTime());

            if(isMember){
                Payment payment = paymentDAO.getPaymentCanCancelBy(course.getId(),student.getId());
                Double oldPaymentMoney = payment.getMoney();
                //付款变0，不需要结算
                payment.setMoney(0.0);
                payment.setState(PaymentState.ALLCANCEL);
                payback.setMoney(oldPaymentMoney);
                payback.setState(PaybackState.CONFIRM);
                paybackDAO.insert(payback);
                paymentDAO.update(payment);
                result.set(true,"取消预定成功,退回学员卡余额"+payback.getMoney()+"元");
            }else{
                Double backMoney = course.getMoney();
                payback.setMoney(backMoney);
                payback.setState(PaybackState.SETTLE);
                paybackDAO.insert(payback);
                result.set(true,"取消预定成功,退回现金"+payback.getMoney()+"元");
            }
        }
        return result;
    }

    @Override
    public List<Payment> getAllPaymentByCollegeId(String collegeId) {
        return paymentDAO.getAllPaymentByCollegeId(collegeId);
    }

    @Override
    public List<Payback> getAllPaybackByCollegeId(String collegeId) {
        return paybackDAO.getAllPaybackByCollegeId(collegeId);
    }


}
