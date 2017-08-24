package cn.zhsite.dao;

import cn.zhsite.extra.Result;
import cn.zhsite.model.College;

public interface CollegeDAO extends BaseDAO<College>{

    public Result isUsernameUnused(String username);

    public Result isUserValid(String username,String password);
}
