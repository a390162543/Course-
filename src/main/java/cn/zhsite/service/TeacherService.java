package cn.zhsite.service;

import cn.zhsite.extra.Result;
import cn.zhsite.model.Teacher;

import java.util.List;

public interface TeacherService {

    public Result addTeacher(Teacher teacher);

    public Result modifyTeacher(Teacher teacher);

    public Teacher getTeacherById(String teacherId);

    public List<Teacher> getAllTeacher();




}
