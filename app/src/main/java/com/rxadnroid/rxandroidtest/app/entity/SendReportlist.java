package com.rxadnroid.rxandroidtest.app.entity;

/**
 * Created by 子非鱼 on 2017/5/15.
 */
public class SendReportlist {

    private String result ;

    private String message ;
    private String currentpage ;

    private String totalcount ;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCurrentpage() {
        return currentpage;
    }

    public void setCurrentpage(String currentpage) {
        this.currentpage = currentpage;
    }

    public String getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(String totalcount) {
        this.totalcount = totalcount;
    }
}
