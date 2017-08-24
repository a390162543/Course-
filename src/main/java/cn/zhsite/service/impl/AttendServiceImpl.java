package cn.zhsite.service.impl;

import cn.zhsite.dao.AttendDAO;
import cn.zhsite.model.Attend;
import cn.zhsite.service.AttendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AttendServiceImpl implements AttendService{

    @Resource
    AttendDAO attendDAO;

    @Override
    public List<Attend> getAllAttendByCourseId(String courseId) {
        return attendDAO.getAllBy("courseId",Integer.parseInt(courseId));
    }

    @Override
    public Attend getAttendById(String attendId) {
        return attendDAO.getById(attendId);
    }


}
