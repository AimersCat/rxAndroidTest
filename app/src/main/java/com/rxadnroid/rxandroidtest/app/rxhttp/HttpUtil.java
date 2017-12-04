package com.rxadnroid.rxandroidtest.app.rxhttp;

import com.rxadnroid.rxandroidtest.app.base.ActivityLifeCycleEvent;
import com.rxadnroid.rxandroidtest.app.base.FragmentLifeCycleEvent;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by 子非鱼 on 2017/1/19.
 */
public class HttpUtil {
    /**
     * 构造方法私有
     */
    private HttpUtil() {
    }




    /**
     * 在访问HttpUtil时创建单例
     */
    private static class SingletonHolder {
        private static final HttpUtil INSTANCE = new HttpUtil();
    }

    /**
     * 获取单例
     */
    public static HttpUtil getInstance() {
        return SingletonHolder.INSTANCE;
    }

    //添加线程管理并订阅

    /**
     *
     /**
     * 添加线程管理并订阅
     * @param ob
     * @param subscriber
     * @param cacheKey 缓存kay
     * @param event Activity 生命周期
     * @param lifecycleSubject
     * @param isSave 是否缓存
     * @param forceRefresh 是否强制刷新
     * @param isShowDialog 是否显示等待框
     */
    public void toSubscribe(Observable ob, final ProgressSubscriber subscriber, String cacheKey, final ActivityLifeCycleEvent event, final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject, boolean isSave, boolean forceRefresh , final boolean isShowDialog) {
        //数据预处理
        Observable.Transformer<HttpResult<Object>, Object> result = RxHelper.handleResult(event,lifecycleSubject);
        //重用操作符
        Observable observable = ob.compose(result)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //显示Dialog和一些其他操作
                        if(isShowDialog){
                            subscriber.showProgressDialog();
                        }
                    }
                });
        //缓存
        RetrofitCache.load(cacheKey, observable, isSave, forceRefresh).subscribe(subscriber);
    }



    public void bindActivityRxBus(Observable ob, final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject, Subscriber mSubscriber ,final ActivityLifeCycleEvent event){
        Observable.Transformer<Object, Object> result = RxHelper.bindActivityUntilEvent(event,lifecycleSubject);

        ob.compose(result)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(mSubscriber);
    }

    public void bindFragmentRxBus(Observable ob, final PublishSubject<FragmentLifeCycleEvent> lifecycleSubject, Subscriber mSubscriber ,final FragmentLifeCycleEvent event){
        Observable.Transformer<Object, Object> result = RxHelper.bindFragmentUntilEvent(event,lifecycleSubject);

        ob.compose(result)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(mSubscriber);
    }
}
