package cn.zhsite.dao.impl;

import cn.zhsite.dao.AccountDAO;
import cn.zhsite.dao.PaybackDAO;
import cn.zhsite.model.Account;
import cn.zhsite.model.Payback;
import cn.zhsite.model.Payment;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class PaybackDAOImpl extends BaseDAOImpl<Payback> implements PaybackDAO{

    @Override
    protected Class getModelClass() {
        return Payback.class;
    }

    @Override
    public List<Payback> getAllPaybackByCollegeId(String collegeId) {
        Session session = sessionFactory.getCurrentSession();
        List<Payback> a = (List<Payback>) session
                .createQuery("from Payback a where a.courseId in (select id from Course b where b.collegeId = ?)")
                .setParameter(0,Integer.parseInt(collegeId))
                .list();
        return a;
    }
}
