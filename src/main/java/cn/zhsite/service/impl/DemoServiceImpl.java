package cn.zhsite.service.impl;

import cn.zhsite.dao.AccountDAO;
import cn.zhsite.model.Account;
import cn.zhsite.service.DemoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DemoServiceImpl implements DemoService{

    @Resource
    private AccountDAO accountDAO;

    @Override
    public String get() {
        Account a = new Account();
        a.setAccount("182734");
        a.setMoney(19.2);
        accountDAO.insert(a);
        return "hello demo";
    }
}
