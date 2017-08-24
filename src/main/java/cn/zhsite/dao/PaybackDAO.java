package cn.zhsite.dao;

import cn.zhsite.model.Payback;

import java.util.List;

public interface PaybackDAO extends BaseDAO<Payback>{

    public List<Payback> getAllPaybackByCollegeId(String collegeId);
}
