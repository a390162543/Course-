package cn.zhsite.dao.impl;

import cn.zhsite.dao.AccountDAO;
import cn.zhsite.dao.DateDAO;
import cn.zhsite.extra.Result;
import cn.zhsite.model.Account;
import cn.zhsite.model.Date;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;


@Repository
public class DateDAOImpl extends BaseDAOImpl<Date> implements DateDAO{

    @Override
    protected Class getModelClass() {
        return Date.class;
    }

}
