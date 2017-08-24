package cn.zhsite.dao.impl;

import cn.zhsite.dao.DailyDAO;
import cn.zhsite.dao.TeacherDAO;
import cn.zhsite.json.util.DBUtil;
import cn.zhsite.model.Daily;
import cn.zhsite.model.Teacher;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DailyDAOImpl extends BaseDAOImpl<Daily> implements DailyDAO{
    @Override
    protected Class getModelClass() {
        return Daily.class;
    }

    @Override
    public void runTask() {
        String sql = "insert into daily(collegeId,teacherId,courseId,studentId,date,money) "
                +"select collegeId,teacherId,courseId,studentId,date,moneyPerDay from attend "
                +"where attend.toDate>="+ LocalDate.now().toString();
        String sql2 = "insert into daily(collegeId,teacherId,courseId,studentId,date,money) "
                +"select collegeId,teacherId,courseId,studentId,date,moneyPerDay from quit "
                +"where quit.toDate>="+ LocalDate.now().toString();

        DBUtil.update(sql);
        DBUtil.update(sql2);
    }

//    @Override
//    public void insertAll(List<Daily> dailies) {
//        String sqlBase = "insert into daily(collegeId,teacherId,courseId,studentId," +
//                "date,year,month,week,income) values ";
//        StringBuilder stringBuilder = new StringBuilder(90000*9*10);
//        stringBuilder.append(sqlBase);
//        for(int i=0;i<dailies.size();i++){
//            Daily d = dailies.get(i);
//            stringBuilder.append("(");
//            stringBuilder.append(d.getCollegeId());
//            stringBuilder.append(",");
//            stringBuilder.append(d.getTeacherId());
//            stringBuilder.append(",");
//            stringBuilder.append(d.getCourseId());
//            stringBuilder.append(",");
//            stringBuilder.append(d.getStudentId());
//            stringBuilder.append(",");
//            stringBuilder.append(d.getDate());
//            stringBuilder.append(",");
//            stringBuilder.append(d.getYear());
//            stringBuilder.append(",");
//            stringBuilder.append(d.getMonth());
//            stringBuilder.append(",");
//            stringBuilder.append(d.getWeek());
//            stringBuilder.append(",");
//            stringBuilder.append(d.getIncome());
//            stringBuilder.append(")");
//
//            if(i != dailies.size()-1){
//                stringBuilder.append(",");
//            }
//        }
//        DBUtil.update(stringBuilder.toString());
//    }
}
