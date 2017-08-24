package cn.zhsite.model.state;

import java.io.Serializable;

public enum MemberState implements Serializable{
    /*
    * valid: valid to use
    * stop: should pay to re-valid
    * invalid: no longer for use
    */
    VALID("正常"),STOP("冻结"),INVALID("失效");

    private String chinese;

    MemberState(String chinese){
        this.chinese = chinese;
    }

    public String getChinese(){
        return chinese;
    }
}
