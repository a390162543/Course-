package cn.zhsite.dao.impl;

import cn.zhsite.dao.AccountDAO;
import cn.zhsite.extra.Result;
import cn.zhsite.model.Account;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;


@Repository
public class AccountDAOImpl extends BaseDAOImpl<Account> implements AccountDAO{

    @Override
    protected Class getModelClass() {
        return Account.class;
    }

    @Override
    public Result isAccountValid(String account) {
        Session session = sessionFactory.getCurrentSession();
        Account a = (Account) session
                .createQuery("from Account a where a.account = ?")
                .setParameter(0,account)
                .uniqueResult();
        return new Result(a != null);
    }

    @Override
    public Result pay(String account, double money) {
        Session session = sessionFactory.getCurrentSession();
        Account a = (Account) session
                .createQuery("from Account a where a.account = ?")
                .setParameter(0,account)
                .uniqueResult();
        Result result = new Result();
        if(a == null){
            result.set(false,"不存在银行卡号:"+account);
        }else{
            double left = a.getMoney();
            if(money>left){
                result.set(false,"余额不足");
            }else{
                a.setMoney(left-money);
                result.set(true,"支付成功");
            }
        }
        return result;
    }
}
