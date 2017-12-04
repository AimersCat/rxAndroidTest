package com.rxadnroid.rxandroidtest.app.entity;

import com.rxadnroid.rxandroidtest.app.rxhttp.HttpResult;

/**
 * Created by 子非鱼 on 2017/1/19.
 */
public class TaskList extends HttpResult{

    private String taskid;
    private String title;
    private String content;
    private String senderuserid;
    private String useravatar;
    private String backcolor;
    private String zname;
    private String starttime;
    private String endtime;
    private String status;
    private String isnew;
    private String urgencystatus;


    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSenderuserid() {
        return senderuserid;
    }

    public void setSenderuserid(String senderuserid) {
        this.senderuserid = senderuserid;
    }

    public String getUseravatar() {
        return useravatar;
    }



    public String getBackcolor() {
        return backcolor;
    }

    public void setBackcolor(String backcolor) {
        this.backcolor = backcolor;
    }

    public String getZname() {
        return zname;
    }

    public void setZname(String zname) {
        this.zname = zname;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsnew() {
        return isnew;
    }

    public void setIsnew(String isnew) {
        this.isnew = isnew;
    }

    public String getUrgencystatus() {
        return urgencystatus;
    }

    public void setUrgencystatus(String urgencystatus) {
        this.urgencystatus = urgencystatus;
    }

}
