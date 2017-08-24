package cn.zhsite.model.state;

public enum CourseState {

    APPLY("申请中"),MODIFY("修改中"),WAIT("报名中"),ON("正常"),END("结束");

    private String chinese;

    CourseState(String chinese){
        this.chinese = chinese;
    }

    public String getChinese(){
        return chinese;
    }
}
