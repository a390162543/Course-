package cn.zhsite.extra;

import java.io.Serializable;

public class Result implements Serializable{

    private boolean success;
    private String info;

    public Result(){
    }

    public Result(String info){
        success = false;
        this.info = info;
    }

    public Result(boolean success){
        this.success = success;
    }

    public Result(boolean success,String info){
        this.success = success;
        this.info = info;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void set(boolean success,String info){
        this.success = success;
        this.info = info;
    }
}
