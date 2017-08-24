package cn.zhsite.dao.impl;

import cn.zhsite.dao.AccountDAO;
import cn.zhsite.dao.CourseDAO;
import cn.zhsite.model.Account;
import cn.zhsite.model.Course;
import cn.zhsite.model.state.AttendState;
import cn.zhsite.model.state.CourseState;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public class CourseDAOImpl extends BaseDAOImpl<Course> implements CourseDAO{

    @Override
    protected Class getModelClass() {
        return Course.class;
    }

    @Override
    public List<Course> getAllCourseStudentNotIn(Integer studentId) {
        Session session = sessionFactory.getCurrentSession();
        List<Course> ts = (List<Course>) session
                .createQuery("from "+getTableName()+" a where ( a.state = ?  or a.state = ?) and a.id not in (select b.courseId from Attend b where b.studentId=? and b.state <> ?)")
                .setParameter(0, CourseState.ON)
                .setParameter(1, CourseState.WAIT)
                .setParameter(2,studentId)
                .setParameter(3, AttendState.CANCEL)
                .list();
        return ts;
    }

    @Override
    public void runTask() {
        Session session = sessionFactory.getCurrentSession();
        String today = LocalDate.now().toString();

        String sql1 = "update course set state = 'ON' " +
                " where course.state = 'WAIT' and fromDate <= '"+today+"' ";
        String sql2 = "update course set state = 'END' " +
                " where course.state = 'ON' and toDate <= '"+today+"' ";
        session.createSQLQuery(sql1)
                .executeUpdate();
        session.createSQLQuery(sql2)
                .executeUpdate();
    }

}
