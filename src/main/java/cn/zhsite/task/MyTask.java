package cn.zhsite.task;

import cn.zhsite.dao.AttendDAO;
import cn.zhsite.dao.CourseDAO;
import cn.zhsite.dao.MemberDAO;
import cn.zhsite.dao.PaymentDAO;
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

    @Scheduled(cron = "0 0 2 * * ?")
    public void run(){
        System.out.println("现在时间:"+ LocalDateTime.now());
        System.out.println("----  开始进行定时任务  ----");

        runMemberTask();
        runCourseTask();
        runAttendTask();
        runPaymentTask();

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
}
