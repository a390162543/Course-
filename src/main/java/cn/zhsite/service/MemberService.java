package cn.zhsite.service;

import cn.zhsite.extra.PayType;
import cn.zhsite.extra.Result;
import cn.zhsite.model.Attend;
import cn.zhsite.model.Member;
import cn.zhsite.model.Payback;
import cn.zhsite.model.Payment;

import java.util.List;

public interface MemberService {

    /**
     *
     * @param member include username,password,idcard
     * @return
     */
    public Result register(Member member);

    public Result recharge(String memberId,Double money);

    public Result invalidate(String memberId);

    public Result payInCash(String idCard,String courseId,Double money);

    public Result payAsMember(String memberId,String courseId,Double money,PayType type);

    public Member getMember(String memberId);

    public Result modify(Member member);

    public Result attendAsMember(String memberId,String courseId);

    public Result attend(String idCard,String courseId);

    public Result cancelAttend(String attendId);

    public Result exit(String attendId);

    public List<Attend> getAllAttendByMemberId(String memberId);

    public Result validate(String username,String password);

    public Result isUsernameUnused(String username);

    public Member getByUsername(String username);

    public List<Payment> getAllPaymentByMemberId(String memberId);

    public List<Payback> getAllPaybackByMemberId(String memberId);

    public Result points(String memberId);

}
