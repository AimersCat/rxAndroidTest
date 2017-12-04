package com.rxadnroid.rxandroidtest.app.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.rxadnroid.rxandroidtest.app.activity.TransparentActivity;


public class MyService extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        Intent intent_ = new Intent(this , TransparentActivity.class) ;
        intent_.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        startActivity(intent_);


        return super.onStartCommand(intent, flags, startId);
    }







}
