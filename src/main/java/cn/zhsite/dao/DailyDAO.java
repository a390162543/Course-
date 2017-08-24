package cn.zhsite.dao;

import cn.zhsite.model.Daily;
import cn.zhsite.model.Teacher;

public interface DailyDAO extends BaseDAO<Daily> {

    public void runTask();
}
