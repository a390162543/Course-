package cn.zhsite.service.impl;

import cn.zhsite.dao.AccountDAO;
import cn.zhsite.extra.Result;
import cn.zhsite.model.Account;
import cn.zhsite.service.AccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AccountServiceImpl implements AccountService{

    private static final String SPECIAL_ACCOUNT = "4637261720";

    @Resource
    AccountDAO accountDAO;

    @Override
    public Result validate(String account) {
        return accountDAO.isAccountValid(account);
    }

    @Override
    public Result pay(String account, double money) {
        return accountDAO.pay(account,money);
    }

    @Override
    public Account getSpecialAccount() {
        return accountDAO.getById("4637261720");
    }
}
