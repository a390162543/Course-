package cn.zhsite.dao;

import cn.zhsite.extra.Result;
import cn.zhsite.model.Manager;

public interface ManagerDAO extends BaseDAO<Manager>{

    public Result isUserValid(String username,String password);

}
