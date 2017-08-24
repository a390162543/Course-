package cn.zhsite.dao;

import cn.zhsite.model.Payment;

import java.util.List;

public interface PaymentDAO extends BaseDAO<Payment>{


    public Payment getPaymentCanCancelBy(Integer courseId,Integer studentId);

    public List<Payment> getAllPaymentByCollegeId(String collegeId);

    public List<Payment> getAllPaymentCanSettle();

    public void runTask();
}
