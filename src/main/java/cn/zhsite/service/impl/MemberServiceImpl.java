package cn.zhsite.service.impl;

import cn.zhsite.dao.*;
import cn.zhsite.extra.PayType;
import cn.zhsite.extra.Result;
import cn.zhsite.extra.Util;
import cn.zhsite.model.*;
import cn.zhsite.model.state.AttendState;
import cn.zhsite.model.state.MemberState;
import cn.zhsite.model.state.PaymentState;
import cn.zhsite.service.CollegeService;
import cn.zhsite.service.MemberService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService{

    @Resource
    CollegeService collegeService;
    @Resource
    MemberDAO memberDAO;
    @Resource
    AccountDAO accountDAO;
    @Resource
    StudentDAO studentDAO;
    @Resource
    PaymentDAO paymentDAO;
    @Resource
    PaybackDAO paybackDAO;
    @Resource
    AttendDAO attendDAO;
    @Resource
    CourseDAO courseDAO;



    @Override
    public Result register(Member member) {
        Result isUnused = memberDAO.isUsernameUnused(member.getUsername());
        Result result = new Result();
        if(isUnused.isSuccess()){
            member.setMoney(0.0);
            member.setPoints(0.0);
            member.setUsedPoints(0.0);
            member.setValidDate(Util.getCurrentDate());
            member.setCreateTime(Util.getCurrentDateTime());
            //用户注册时的状态为暂停，需要充值激活
            member.setState(MemberState.STOP);
            memberDAO.insert(member);
            if(studentDAO.getByIdCard(member.getIdcard())==null){
                Student student = new Student();
                student.setMember(member);
                student.setIdcard(member.getIdcard());
                student.setCreateTime(Util.getCurrentDateTime());
                studentDAO.insert(student);

            }
            result.set(true,"注册成功");
        }else{
            result.set(false,"用户名已被占用");
        }
        return result;
    }


    @Override
    public Result recharge(String memberId, Double money) {
        Member member = memberDAO.getById(memberId);
        Integer account = member.getAccountId();
        boolean hasAccount = account!=null;
        Result result = new Result();
        if(hasAccount){
            result = accountDAO.pay(String.valueOf(account),money);
            if(result.isSuccess()){
                member.setMoney(member.getMoney()+money);
                //支付后检查学员状态与余额
                if(member.getMoney()>0 && member.getState() == MemberState.STOP){
                    member.setState(MemberState.VALID);
                }
                memberDAO.update(member);
                result.set(true,"支付成功");
            }else{
                result.set(false,"银行卡余额不足");
            }
        }else{
            result.set(false,"请绑定银行账户");
        }
        return result;
    }

    @Override
    public Result invalidate(String memberId) {
        Member member = memberDAO.getById(memberId);
        Result result = new Result();
        if(member.getState() == MemberState.INVALID){
            result.set(false,"会员已停用");
        }else{
            member.setState(MemberState.INVALID);
            memberDAO.update(member);
            result.set(true,"停用成功");
        }
        return result;
    }

    @Override
    public Result payInCash(String idCard, String courseId, Double money) {
        Student student = studentDAO.getByIdCard(idCard);
        boolean hasStudent = student!=null;
        Result result = new Result();
        if(!hasStudent){
            student = new Student();
            student.setIdcard(idCard);
            student.setCreateTime(Util.getCurrentDateTime());
            studentDAO.insert(student);
        }
        Integer studentId = student.getId();
        Payment payment = new Payment();
        payment.setMoney(money);
        payment.setCourseId(Integer.parseInt(courseId));
        payment.setStudentId(studentId);
        payment.setState(PaymentState.SETTLE);
        payment.setCreateTime(Util.getCurrentDateTime());
        paymentDAO.insert(payment);
        result.set(true,"现金付款登记成功");
        return result;
    }

    @Override
    public Result payAsMember(String memberId, String courseId, Double money, PayType type) {
        Student student = studentDAO.getByMemberId(memberId);
        Integer studentId = student.getId();
        Result result = new Result();
        if(type == PayType.ACCOUNT){
            result = memberDAO.pay(memberId,money);
            Member member = memberDAO.getById(memberId);
            //如果欠款，设置暂停
            if(member.getMoney()<0){
                member.setState(MemberState.STOP);
                memberDAO.update(member);
            }
        }else if(type == PayType.CARD){
            Member member = memberDAO.getById(memberId);
            Integer accountId = member.getAccountId();
            Account account = accountDAO.getById(accountId);
            result = accountDAO.pay(account.getAccount(),money);
        }
        if(result.isSuccess()){
            Payment payment = new Payment();
            payment.setMoney(money);
            payment.setCourseId(Integer.parseInt(courseId));
            payment.setStudentId(studentId);
            payment.setState(PaymentState.PREPAY);
            payment.setCreateTime(Util.getCurrentDateTime());
            paymentDAO.insert(payment);
        }
        return result;
    }

    @Override
    public Member getMember(String memberId) {
        return memberDAO.getById(memberId);
    }

    @Override
    public Result modify(Member member) {
        memberDAO.update(member);
        Result result = new Result();
        result.set(true,"修改成功");
        return null;
    }

    @Override
    public Result attendAsMember(String memberId, String courseId) {
        Student student = studentDAO.getByMemberId(memberId);
        Course course = courseDAO.getById(courseId);
        Result result = payAsMember(memberId,courseId,course.getMoney(),PayType.ACCOUNT);
        Attend attend = attendDAO.getAttendBy(course.getId(),student.getId());
        if(attend == null){
            attend = new Attend();
        }
        if(result.isSuccess()){
            attend.setStudentId(student.getId());
            attend.setCourseId(Integer.parseInt(courseId));
            attend.setState(AttendState.PRE);
            attend.setCreateTime(Util.getCurrentDateTime());
            attendDAO.insert(attend);
            result.set(true,"报名成功");
        }else{
            result.set(false,result.getInfo());
        }
        return result;
    }

    @Override
    public Result attend(String idCard, String courseId) {
        Course course = courseDAO.getById(courseId);
        Result result = payInCash(idCard,courseId,course.getMoney());
        Student student = studentDAO.getByIdCard(idCard);
        Attend attend = attendDAO.getAttendBy(course.getId(),student.getId());
        if(attend == null){
            attend = new Attend();
        }
        if(result.isSuccess()){
            attend.setStudentId(student.getId());
            attend.setCourseId(Integer.parseInt(courseId));
            attend.setState(AttendState.PRE);
            attend.setCreateTime(Util.getCurrentDateTime());
            attendDAO.insert(attend);
            result.set(true,"报名成功");
        }else{
            result.set(false,result.getInfo());
        }
        return result;
    }

    @Override
    public Result cancelAttend(String attendId) {
        Result result = collegeService.quitCourse(attendId);
        return result;
    }


    @Override
    @Deprecated
    public Result exit(String attendId) {
        Attend attend = attendDAO.getById(attendId);
        Result result = new Result();
        attend.setState(AttendState.QUIT);
        attendDAO.update(attend);
        result.set(true,"退课成功");
        return result;
    }

    @Override
    public List<Attend> getAllAttendByMemberId(String memberId) {
        Student student =studentDAO.getByMemberId(memberId);
        return attendDAO.getAllBy("studentId",student.getId());
    }

    @Override
    public Result validate(String username, String password) {
        return memberDAO.isUserValid(username, password);
    }

    @Override
    public Result isUsernameUnused(String username) {
        return memberDAO.isUsernameUnused(username);
    }

    @Override
    public Member getByUsername(String username) {
        return memberDAO.getBy("username",username);
    }

    @Override
    public List<Payment> getAllPaymentByMemberId(String memberId) {
        Student student = studentDAO.getByMemberId(memberId);
        return paymentDAO.getAllBy("studentId",student.getId());
    }

    @Override
    public List<Payback> getAllPaybackByMemberId(String memberId) {
        Student student = studentDAO.getByMemberId(memberId);
        return paybackDAO.getAllBy("studentId",student.getId());
    }

    @Override
    public Result points(String memberId) {
        Member member = memberDAO.getById(memberId);
        Double points = member.getPoints() - member.getUsedPoints();
        member.setUsedPoints(member.getPoints());
        member.setMoney(member.getMoney()+points*0.1);
        memberDAO.update(member);
        Result result = new Result();
        result.set(true,"兑换成功");
        return result;
    }
}
