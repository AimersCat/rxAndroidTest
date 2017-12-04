package com.rxadnroid.rxandroidtest.app.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import rx.subjects.PublishSubject;

/**
 * Created by 子非鱼 on 2017/2/13.
 */
public class BaseFragment extends Fragment {

    public final PublishSubject<FragmentLifeCycleEvent> lifecycleSubject = PublishSubject.create();
    @Override
    public void onStart() {
        lifecycleSubject.onNext(FragmentLifeCycleEvent.START);
        super.onStart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        lifecycleSubject.onNext(FragmentLifeCycleEvent.CREATE);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        lifecycleSubject.onNext(FragmentLifeCycleEvent.RESUME);
        super.onResume();
    }

    @Override
    public void onDestroy() {
        lifecycleSubject.onNext(FragmentLifeCycleEvent.DESTROY);
        super.onDestroy();
    }

    @Override
    public void onPause() {
        lifecycleSubject.onNext(FragmentLifeCycleEvent.PAUSE);
        super.onPause();
    }

    @Override
    public void onStop() {
        lifecycleSubject.onNext(FragmentLifeCycleEvent.STOP);
        super.onStop();
    }

}
