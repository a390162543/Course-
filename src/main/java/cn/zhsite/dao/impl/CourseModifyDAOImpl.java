package cn.zhsite.dao.impl;

import cn.zhsite.dao.CourseDAO;
import cn.zhsite.dao.CourseModifyDAO;
import cn.zhsite.model.Course;
import cn.zhsite.model.CourseModify;
import org.springframework.stereotype.Repository;


@Repository
public class CourseModifyDAOImpl extends BaseDAOImpl<CourseModify> implements CourseModifyDAO{

    @Override
    protected Class getModelClass() {
        return CourseModify.class;
    }
}
