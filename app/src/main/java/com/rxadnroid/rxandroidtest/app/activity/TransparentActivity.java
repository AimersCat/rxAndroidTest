package com.rxadnroid.rxandroidtest.app.activity;

import android.os.Bundle;
import android.os.Handler;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnDismissListener;
import com.bigkoo.alertview.OnItemClickListener;
import com.rxadnroid.rxandroidtest.R;
import com.rxadnroid.rxandroidtest.app.base.BaseActivity;

/**
 * Created by 子非鱼 on 2017/2/10.
 */
public class TransparentActivity extends BaseActivity {
    private Handler mHand = new Handler() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans);

        mHand.postDelayed(new Runnable() {
            @Override
            public void run() {
                showAlertDialog() ;
            }
        } , 5000) ;
    }


    private void showAlertDialog(){
        AlertView mAlertView = new AlertView("提示", "hehehehe", "取消", new String[]{"确定"}, null, this, AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {

            }
        }).setCancelable(true).setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(Object o) {

            }
        });
        mAlertView.show();
    }

}
