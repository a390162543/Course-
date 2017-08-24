package cn.zhsite.dao.impl;

import cn.zhsite.dao.AccountDAO;
import cn.zhsite.dao.StudentDAO;
import cn.zhsite.model.Account;
import cn.zhsite.model.Student;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;


@Repository
public class StudentDAOImpl extends BaseDAOImpl<Student> implements StudentDAO{

    @Override
    protected Class getModelClass() {
        return Student.class;
    }

    @Override
    public Student getByIdCard(String idCard) {
        Session session = sessionFactory.getCurrentSession();
        Student student = (Student) session
                .createQuery("from Student a where a.idcard = ?")
                .setParameter(0,idCard)
                .uniqueResult();
        return student;
    }

    @Override
    public Student getByMemberId(String memberId) {
        Session session = sessionFactory.getCurrentSession();
        Student student = (Student) session
                .createQuery("from Student a where a.memberId = ?")
                .setParameter(0,Integer.parseInt(memberId))
                .uniqueResult();
        System.out.println(memberId+"  "+(student==null));
        return student;
    }
}
