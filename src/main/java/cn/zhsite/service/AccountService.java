package cn.zhsite.service;

import cn.zhsite.extra.Result;
import cn.zhsite.model.Account;

public interface AccountService {

    public Result validate(String account);

    public Result pay(String account, double money);

    public Account getSpecialAccount();
}
