package cn.zhsite.dao;

import cn.zhsite.model.Student;

public interface StudentDAO extends BaseDAO<Student>{


    public Student getByIdCard(String idCard);

    public Student getByMemberId(String memberId);
}
