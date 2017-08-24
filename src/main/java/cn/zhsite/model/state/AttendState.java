package cn.zhsite.model.state;

public enum AttendState {
    /*
    * book:when a member book a room
    * cancel:when the booked room is given up by a member
    */
    PRE("预定"),ATTEND("参与"),CANCEL("取消预定"),QUIT("中途退课"),END("结课");


    private String chinese;

    AttendState(String chinese){
        this.chinese = chinese;
    }

    public String getChinese(){
        return chinese;
    }
}
