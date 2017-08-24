package cn.zhsite.service.impl;

import cn.zhsite.dao.TeacherDAO;
import cn.zhsite.extra.Result;
import cn.zhsite.model.Teacher;
import cn.zhsite.service.TeacherService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService{
    @Resource
    TeacherDAO teacherDAO;

    @Override
    public Result addTeacher(Teacher teacher) {
        teacherDAO.insert(teacher);
        Result result = new Result();
        result.set(true,"添加成功");
        return result;
    }

    @Override
    public Result modifyTeacher(Teacher teacher) {
        teacherDAO.update(teacher);
        Result result = new Result();
        result.set(true,"修改成功");
        return result;
    }

    @Override
    public Teacher getTeacherById(String teacherId) {
        return teacherDAO.getById(teacherId);
    }

    @Override
    public List<Teacher> getAllTeacher() {
        return teacherDAO.getAll();
    }
}
