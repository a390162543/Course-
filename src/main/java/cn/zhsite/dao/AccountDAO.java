package cn.zhsite.dao;

import cn.zhsite.extra.Result;
import cn.zhsite.model.Account;

public interface AccountDAO extends BaseDAO<Account>{


    public Result isAccountValid(String account);

    public Result pay(String account, double money);
}
