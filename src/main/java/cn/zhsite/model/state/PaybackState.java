package cn.zhsite.model.state;

public enum PaybackState {
    /*
    * confirm:学员退款会进入该状态，等待经理结算
    * settle:普通学生退款进入该状态
    * */
    CONFIRM("未结算"),SETTLE("已结算");

    private String chinese;

    PaybackState(String chinese){
        this.chinese = chinese;
    }

    public String getChinese(){
        return chinese;
    }
}
