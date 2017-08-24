package cn.zhsite.model;

import cn.zhsite.model.state.AttendState;
import cn.zhsite.model.state.PaymentState;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "attend")
public class Attend implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer studentId;
    @OneToOne()
    @JoinColumn(name = "studentId",insertable = false,updatable = false)
    private Student student;
    private Integer courseId;
    @OneToOne()
    @JoinColumn(name = "courseId",insertable = false,updatable = false)
    private Course course;
    private Double score;
    @Enumerated(EnumType.STRING)
    private AttendState state;
    private LocalDateTime createTime;
    private LocalDate createDate;

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public AttendState getState() {
        return state;
    }

    public void setState(AttendState state) {
        this.state = state;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getStudentType(){
        if(student.getMember() != null){
            return "会员用户";
        }else{
            return "普通用户";
        }
    }
}
