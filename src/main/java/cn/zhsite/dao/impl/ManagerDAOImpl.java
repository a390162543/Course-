package cn.zhsite.dao.impl;

import cn.zhsite.dao.ManagerDAO;
import cn.zhsite.extra.Result;
import cn.zhsite.model.Manager;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;


@Repository
public class ManagerDAOImpl extends BaseDAOImpl<Manager> implements ManagerDAO{

    @Override
    protected Class getModelClass() {
        return Manager.class;
    }

    @Override
    public Result isUserValid(String username, String password) {
        Session session = sessionFactory.getCurrentSession();
        Manager a = (Manager) session
                .createQuery("from Manager a where a.username = ? and a.password = ?")
                .setParameter(0,username)
                .setParameter(1,password)
                .uniqueResult();
        return new Result(a != null);
    }
}
