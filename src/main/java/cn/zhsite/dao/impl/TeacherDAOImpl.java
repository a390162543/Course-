package cn.zhsite.dao.impl;

import cn.zhsite.dao.TeacherDAO;
import cn.zhsite.model.Teacher;
import org.springframework.stereotype.Repository;

@Repository
public class TeacherDAOImpl extends BaseDAOImpl<Teacher> implements TeacherDAO{
    @Override
    protected Class getModelClass() {
        return Teacher.class;
    }
}
