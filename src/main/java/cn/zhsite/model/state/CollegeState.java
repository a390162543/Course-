package cn.zhsite.model.state;

public enum CollegeState {

    APPLY("申请中"),NORMAL("正常"),MODIFY("修改中");

    private String chinese;

    CollegeState(String chinese){
        this.chinese = chinese;
    }

    public String getChinese(){
        return chinese;
    }
}
