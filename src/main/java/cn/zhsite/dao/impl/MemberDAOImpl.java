package cn.zhsite.dao.impl;

import cn.zhsite.dao.AccountDAO;
import cn.zhsite.dao.MemberDAO;
import cn.zhsite.extra.Result;
import cn.zhsite.model.Account;
import cn.zhsite.model.College;
import cn.zhsite.model.Member;
import cn.zhsite.model.Student;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;


@Repository
public class MemberDAOImpl extends BaseDAOImpl<Member> implements MemberDAO{

    private static final int LIMIT_LOWER_BOUND = 200;
    @Override
    protected Class getModelClass() {
        return Member.class;
    }

    @Override
    public Result isUsernameUnused(String username) {
        Session session = sessionFactory.getCurrentSession();
        Member a = (Member) session
                .createQuery("from Member a where a.username = ?")
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
    public Member getByIdCard(String idCard) {
        Session session = sessionFactory.getCurrentSession();
        Member member = (Member) session
                .createQuery("from Member a where a.idcard = ?")
                .setParameter(0,idCard)
                .uniqueResult();
        return member;
    }

    @Override
    public Result pay(String memberId, Double money) {
        Member a = getById(memberId);
        Result result = new Result();

        Double moneyAfterDiscount = money*(1-a.getRate());
        double left = a.getMoney()+LIMIT_LOWER_BOUND;
        if(moneyAfterDiscount>left){
            result.set(false,"余额不足");
        }else{
            a.setMoney(left-moneyAfterDiscount);
            a.setPoints(a.getPoints()+moneyAfterDiscount);
            result.set(true,"支付成功");
        }
        return result;
    }

    @Override
    public Result isUserValid(String username, String password) {
        Session session = sessionFactory.getCurrentSession();
        Member a = (Member) session
                .createQuery("from Member a where a.username = ? and a.password = ?")
                .setParameter(0,username)
                .setParameter(1,password)
                .uniqueResult();
        return new Result(a != null);
    }

    @Override
    public void runTask() {
        Session session = sessionFactory.getCurrentSession();
        String todayLastYear = LocalDate.now().minusYears(1).toString();
        String today = LocalDate.now().toString();
        String todayNextYear = LocalDate.now().plusYears(1).toString();

        String sql1 = "update member set state = 'STOP', validDate = "+today
                +" where state = 'VALID' and validDate < '"+todayLastYear+"' and money < 0";
        String sql2 = "update member set state = 'INVALID' "+
                " where state = 'STOP' and validDate < '"+todayLastYear+"' and money < 0;";
        session.createSQLQuery(sql1)
                .executeUpdate();
        session.createSQLQuery(sql2)
                .executeUpdate();
    }
}
