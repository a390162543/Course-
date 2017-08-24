package cn.zhsite.dao;

import cn.zhsite.model.Attend;

public interface AttendDAO extends BaseDAO<Attend>{

    public Attend getAttendBy(Integer courseId,Integer studentId);

    public void runTask();
}
