package cn.zhsite.dao;

import cn.zhsite.model.Course;

import java.util.List;

public interface CourseDAO extends BaseDAO<Course>{


    public List<Course> getAllCourseStudentNotIn(Integer memberId);

    public void runTask();

}
