package cn.zhsite.task;

import cn.zhsite.dao.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component("myTask")
public class MyTask {

    @Resource
    MemberDAO memberDAO;
    @Resource
    CourseDAO courseDAO;
    @Resource
    AttendDAO attendDAO;
    @Resource
    PaymentDAO paymentDAO;
    @Resource
    DailyDAO dailyDAO;

    @Scheduled(cron = "0 0 2 * * ?")
    public void run(){
        System.out.println("现在时间:"+ LocalDateTime.now());
        System.out.println("----  开始进行定时任务  ----");

        runMemberTask();
        runCourseTask();
        runAttendTask();
        runPaymentTask();
        runDailyTask();


        System.out.println("----  定时任务结束处理  ----");
    }

    private void runMemberTask(){
        memberDAO.runTask();
    }

    private void runCourseTask(){
        courseDAO.runTask();
    }

    private void runAttendTask(){
        attendDAO.runTask();
    }

    private void runPaymentTask(){
        paymentDAO.runTask();
    }

    private void runDailyTask(){
        dailyDAO.runTask();
    }
}
