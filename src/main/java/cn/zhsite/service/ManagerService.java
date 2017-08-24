package cn.zhsite.service;

import cn.zhsite.extra.Result;
import cn.zhsite.model.*;

import java.util.List;

public interface ManagerService {

    public Result approve(String courseId);

    public Result approveModify(String courseId);

    public Result settlePayment(String paymentId);

    public Result settlePayback(String paybackId);

    public Result validate(String username, String password);

    public Manager getByUsername(String username);

    public List<Course> getAllCourseApproving();

    public List<CourseModify> getAllCourseModifying();

    public List<Payment> getAllPaymentUnsettled();

    public List<Payback> getAllPaybackUnsettled();

    public List<Payment> getAllPayment();

    public List<Payback> getAllPayback();

    public List<College> getAllCollege();
}
