package cn.zhsite.dao.impl;

import cn.zhsite.dao.AccountDAO;
import cn.zhsite.dao.AttendDAO;
import cn.zhsite.model.Account;
import cn.zhsite.model.Attend;
import cn.zhsite.model.Payment;
import cn.zhsite.model.state.PaymentState;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;


@Repository
public class AttendDAOImpl extends BaseDAOImpl<Attend> implements AttendDAO{

    @Override
    protected Class getModelClass() {
        return Attend.class;
    }

    @Override
    public Attend getAttendBy(Integer courseId, Integer studentId) {
        Session session = sessionFactory.getCurrentSession();
        Attend a = (Attend) session
                .createQuery("from Attend a where a.courseId = ? and a.studentId = ?")
                .setParameter(0,courseId)
                .setParameter(1,studentId)
                .uniqueResult();
        return a;
    }

    @Override
    public void runTask() {
        Session session = sessionFactory.getCurrentSession();
        String today = LocalDate.now().toString();

        String sql1 = "update attend set state = 'ATTEND' where state = 'PRE' and attend.courseId in " +
                " (select course.id from course where course.fromDate <= '"+today+"')";
        String sql2 = "update attend set state = 'END' where state = 'ATTEND' and attend.courseId in " +
                " (select course.id from course where course.toDate <= '"+today+"')";
        session.createSQLQuery(sql1)
                .executeUpdate();
        session.createSQLQuery(sql2)
                .executeUpdate();
    }
}
