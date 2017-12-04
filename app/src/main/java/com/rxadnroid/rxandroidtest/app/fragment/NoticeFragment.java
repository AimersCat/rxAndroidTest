package com.rxadnroid.rxandroidtest.app.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rxadnroid.rxandroidtest.R;
import com.rxadnroid.rxandroidtest.app.base.BaseFragment;
import com.rxadnroid.rxandroidtest.app.base.FragmentLifeCycleEvent;
import com.rxadnroid.rxandroidtest.app.entity.UserEvent;
import com.rxadnroid.rxandroidtest.app.rxbus.RxBus;
import com.rxadnroid.rxandroidtest.app.rxhttp.HttpUtil;

import rx.Subscriber;

/**
 * Created by 子非鱼 on 2017/2/13.
 */
public class NoticeFragment extends BaseFragment{
    private TextView mTextView ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.fragment, container, false);
        onInitView(viewRoot) ;
        return viewRoot;
    }

    private void onInitView(View viewRoot) {
        mTextView =(TextView) viewRoot.findViewById(R.id.id_context) ;
        onSubject() ;
    }


    private void onSubject(){
        HttpUtil.getInstance().bindFragmentRxBus(RxBus.getDefault().toObservableSticky(UserEvent.class) , lifecycleSubject , new Subscriber<UserEvent>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(UserEvent userEvent) {
                Log.i("info-----fragment" , "hahah") ;
                mTextView.setText(userEvent.getName());
            }
        } , FragmentLifeCycleEvent.STOP);


    }
}
