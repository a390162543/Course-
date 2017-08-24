package cn.zhsite.model.state;

public enum PaymentState {
    /*
    * prepay:预定时候付款，不能被经理结算，在课程结束后变为付款状态，
    * confirm:付款状态，可以被经理结算
    * cancel:部分退款，剩余部分可以结算
    * all cancel:全额退款，经理不需要结算
    * settle:结算后状态（普通学生对机构付款直接进入该状态）
    * */
    PREPAY("预付款"),CONFIRM("付款"),CANCEL("部分退款"),ALLCANCEL("全额退款"),SETTLE("结算");

    private String chinese;

    PaymentState(String chinese){
        this.chinese = chinese;
    }

    public String getChinese(){
        return chinese;
    }
}
