package com.rxadnroid.rxandroidtest.app.entity;

/**
 * Created by 子非鱼 on 2017/2/13.
 */
public class UserEvent {
    long id;
    String name;
    public UserEvent(long id,String name) {
        this.id= id;
        this.name= name;
    }
    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
}
