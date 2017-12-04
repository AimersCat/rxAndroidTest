package com.rxadnroid.rxandroidtest.app.rxhttp;

import com.google.gson.annotations.SerializedName;

/**
 * 接口返回格式以此为模板
 * Created by 子非鱼 on 2017/1/19.
 */
public class HttpResult<T> {
    private String recordcount ;

    private String errNum;
    private String errMsg;

//    private String title;

//    @SerializedName("taskList")
    private T taskList;

//    public int getCount() {
//        return count;
//    }
//
//    public void setCount(int count) {
//        this.count = count;
//    }


//    public String getRecordcount() {
//        return recordcount;
//    }
//
//    public void setRecordcount(String recordcount) {
//        this.recordcount = recordcount;
//    }


    public String getErrNum() {
        return errNum;
    }
//
    public void setErrNum(String errNum) {
        this.errNum = errNum;
    }
//
    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }

    public T getSubjects() {
        return taskList;
    }

    public void setSubjects(T subjects) {
        this.taskList = subjects;
    }

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }
}


