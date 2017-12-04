package com.rxadnroid.rxandroidtest.app;

import android.app.Application;

import com.orhanobut.hawk.Hawk;




/**
 * Created by 子非鱼 on 2017/1/20.
 */
public class AppContext extends Application {
    private static AppContext instance;

//    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        Hawk.init(this).build();


//        MySQLiteOpenHelper helper = new  MySQLiteOpenHelper(this, "hongtu_persistence");
//        Database db =  helper.getWritableDb();
//        daoSession = new DaoMaster(db).newSession();
    }


//    public DaoSession getDaoSession() {
//        return daoSession;
//    }
}
