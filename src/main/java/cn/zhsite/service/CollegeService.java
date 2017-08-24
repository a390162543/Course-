package cn.zhsite.service;

import cn.zhsite.extra.Result;
import cn.zhsite.model.*;

import java.util.List;

public interface CollegeService {

    /**
     *
     * @param college include username,password,name
     * @return
     */
    public Result register(College college);

    public Result validate(String username,String password);

    public Result isUsernameUnused(String username);

    public College getByUsername(String username);

    public College getCollege(String collegeId);

    /**
     *
     * @param course include collegeId,fromDate,toDate,name,money,teacher,limitNum
     * @return
     */
    public Result registerCourse(Course course);

    public Result modifyCourse(Course course);

    public Course getCourse(String courseId);

    public List<Course> getAllCoursesByCollegeId(String collegeId);

    public List<Course> getAllCourseMemberNotIn(String memberId);

    public Result score(String attendId,Double score);

    public Result quitCourse(String attendId);

    public List<Payment> getAllPaymentByCollegeId(String collegeId);

    public List<Payback> getAllPaybackByCollegeId(String collegeId);

}
