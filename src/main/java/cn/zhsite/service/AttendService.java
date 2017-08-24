package cn.zhsite.service;

import cn.zhsite.model.Attend;

import java.util.List;

public interface AttendService {

    public List<Attend> getAllAttendByCourseId(String courseId);

    public Attend getAttendById(String attendId);
}
