package cn.zhsite.model;

import cn.zhsite.model.state.CourseState;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "course")
public class Course implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer collegeId;
    @OneToOne()
    @JoinColumn(name = "collegeId",insertable = false,updatable = false)
    private College college;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String name;
    private Double money;
    private Integer teacherId;
    @OneToOne()
    @JoinColumn(name = "teacherId",insertable = false,updatable = false)
    private Teacher teacher;
    private Integer limitNum;
    @Enumerated(EnumType.STRING)
    private CourseState state;
    private LocalDateTime createTime;
    private String type;

    private Integer days;
    private double moneyPerDay;

    public College getCollege() {
        return college;
    }

    public void setCollege(College college) {
        this.college = college;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public double getMoneyPerDay() {
        return moneyPerDay;
    }

    public void setMoneyPerDay(double moneyPerDay) {
        this.moneyPerDay = moneyPerDay;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(Integer collegeId) {
        this.collegeId = collegeId;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Integer getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(Integer limitNum) {
        this.limitNum = limitNum;
    }

    public CourseState getState() {
        return state;
    }

    public void setState(CourseState state) {
        this.state = state;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }


}
