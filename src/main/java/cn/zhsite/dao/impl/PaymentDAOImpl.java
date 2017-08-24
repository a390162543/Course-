package cn.zhsite.dao.impl;

import cn.zhsite.dao.AccountDAO;
import cn.zhsite.dao.PaymentDAO;
import cn.zhsite.model.Account;
import cn.zhsite.model.Member;
import cn.zhsite.model.Payment;
import cn.zhsite.model.state.PaymentState;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public class PaymentDAOImpl extends BaseDAOImpl<Payment> implements PaymentDAO{

    @Override
    protected Class getModelClass() {
        return Payment.class;
    }

    @Override
    public Payment getPaymentCanCancelBy(Integer courseId, Integer studentId) {
        Session session = sessionFactory.getCurrentSession();
        Payment a = (Payment) session
                .createQuery("from Payment a where a.courseId = ? and a.studentId = ? and a.state<>? and a.state<>?")
                .setParameter(0,courseId)
                .setParameter(1,studentId)
                .setParameter(2, PaymentState.CANCEL)
                .setParameter(3, PaymentState.ALLCANCEL)
                .uniqueResult();
        return a;
    }

    @Override
    public List<Payment> getAllPaymentByCollegeId(String collegeId) {
        Session session = sessionFactory.getCurrentSession();
        List<Payment> a = (List<Payment>) session
                .createQuery("from Payment a where a.courseId in (select id from Course b where b.collegeId = ?)")
                .setParameter(0,Integer.parseInt(collegeId))
                .list();
        return a;
    }

    @Override
    public List<Payment> getAllPaymentCanSettle() {
        Session session = sessionFactory.getCurrentSession();
        List<Payment> a = (List<Payment>) session
                .createQuery("from Payment a where a.state = ? or a.state = ? or a.state = ? ")
                .setParameter(0,PaymentState.CANCEL)
                .setParameter(1,PaymentState.CONFIRM)
                .setParameter(2,PaymentState.PREPAY)
                .list();
        return a;
    }

    @Override
    public void runTask() {
        Session session = sessionFactory.getCurrentSession();
        String today = LocalDate.now().toString();

        String sql = "update payment set state = 'CONFIRM' where state = 'PREPAY' and courseId in " +
                " (select course.id from course where course.toDate <= '"+today+"')";

        session.createSQLQuery(sql)
                .executeUpdate();
    }
}
