package com.rxadnroid.rxandroidtest.app.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.rxadnroid.rxandroidtest.R;
import com.rxadnroid.rxandroidtest.app.api.SendReportlistApi;
import com.rxadnroid.rxandroidtest.app.base.ActivityLifeCycleEvent;
import com.rxadnroid.rxandroidtest.app.base.BaseActivity;
import com.rxadnroid.rxandroidtest.app.entity.Signinlist;
import com.rxadnroid.rxandroidtest.app.rxhttp.HttpUtil;
import com.rxadnroid.rxandroidtest.app.rxhttp.ProgressSubscriber;
import com.rxadnroid.rxandroidtest.app.rxhttp.RetrofitService;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;
import java.util.List;
import rx.Observable;
import rx.functions.Action1;

public class MainActivity extends BaseActivity {

    public Button mPublicButton ;
    public Button mVedioButton ;
    public Button mKotlin ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPublicButton = (Button) findViewById(R.id.id_btn) ;
        mVedioButton = (Button) findViewById(R.id.id_vedio) ;
        mKotlin = (Button) findViewById(R.id.id_kotlin) ;

        mPublicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this , PublicCardActivity.class));
            }
        });

        mVedioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this , RecordVideoActivity.class));
            }
        });
        mKotlin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this , KotlinTestActivity.class));
            }
        });


        requestPermission() ;
    }



    private void requestPermission(){
        RxPermissions.getInstance(MainActivity.this)
                .requestEach(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_CALENDAR,
                        Manifest.permission.READ_CALL_LOG,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_SMS,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.SEND_SMS)
                .subscribe(new Action1<Permission>() {
                    @Override
                    public void call(Permission permission) {
                        if (permission.name.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                            //当ACCESS_FINE_LOCATION权限获取成功时，permission.granted=true
                            Log.i("permissions", Manifest.permission.ACCESS_FINE_LOCATION + "：" + permission.granted);
                        }
                        if (permission.name.equals(Manifest.permission.RECORD_AUDIO)) {
                            //当RECORD_AUDIO 权限获取成功时，permission.granted=true
                            Log.i("permissions", Manifest.permission.RECORD_AUDIO + "：" + permission.granted);
                        }
                        if (permission.name.equals(Manifest.permission.CAMERA)) {
                            //当CAMERA权限获取成功时，permission.granted=true
                            Log.i("permissions", Manifest.permission.CAMERA + "：" + permission.granted);
                        }
                    }
                });


        /**
         * 与requesteach或ensureeach观察细致的结果
         */


    }

    private void getRetrofitData(boolean isShow){
        Observable ob = RetrofitService.getInstance().createService(SendReportlistApi.class).getSendReportlist("1604002" , 10 , 1);
        HttpUtil.getInstance().toSubscribe(ob, new ProgressSubscriber<List<Signinlist>>(this) {
            @Override
            protected void _onError(String message) {

                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();

            }

            @Override
            protected void _onNext(List<Signinlist> list) {

                Toast.makeText(MainActivity.this, list.size(), Toast.LENGTH_LONG).show();
            }


        }, "signinlist", ActivityLifeCycleEvent.CREATE, lifecycleSubject, true, false , isShow);
    }


}