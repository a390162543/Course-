package cn.zhsite.dao;

import cn.zhsite.extra.Result;
import cn.zhsite.model.Member;

public interface MemberDAO extends BaseDAO<Member>{

    public Result isUsernameUnused(String username);

    public Member getByIdCard(String idCard);

    public Result pay(String memberId,Double money);

    public Result isUserValid(String username,String password);

    public void runTask();

}
