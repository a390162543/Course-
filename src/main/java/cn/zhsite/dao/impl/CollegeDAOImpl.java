package cn.zhsite.dao.impl;

import cn.zhsite.dao.AccountDAO;
import cn.zhsite.dao.CollegeDAO;
import cn.zhsite.extra.Result;
import cn.zhsite.model.Account;
import cn.zhsite.model.College;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;


@Repository
public class CollegeDAOImpl extends BaseDAOImpl<College> implements CollegeDAO{

    @Override
    protected Class getModelClass() {
        return College.class;
    }

    @Override
    public Result isUsernameUnused(String username) {
        Session session = sessionFactory.getCurrentSession();
        College a = (College) session
                .createQuery("from College a where a.username = ?")
                .setParameter(0,username)
                .uniqueResult();
        boolean isUnused = (a==null);
        Result result = new Result();
        if(isUnused){
            result.setSuccess(true);
        }else{
            result.setSuccess(false);
            result.setInfo("用户名已被占用");
        }
        return result;
    }

    @Override
    public Result isUserValid(String username, String password) {
        Session session = sessionFactory.getCurrentSession();
        College a = (College) session
                .createQuery("from College a where a.username = ? and a.password = ?")
                .setParameter(0,username)
                .setParameter(1,password)
                .uniqueResult();
        return new Result(a != null);
    }

}
