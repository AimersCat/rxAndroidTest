package com.rxadnroid.rxandroidtest.app.rxhttp;

import rx.Subscriber;

/**
 * Created by 子非鱼 on 2017/1/19.
 * 与 onStart()相对应的有一个方法 doOnSubscribe()，
 * 它和 onStart()同样是在subscribe()调用后而且在事件发送前执行，
 * 但区别在于它可以指定线程。默认情况下，
 * doOnSubscribe()执行在 subscribe()发生的线程；
 * 而如果在 doOnSubscribe()之后有 subscribeOn()的话，
 * 它将执行在离它最近的subscribeOn()所指定的线程。
 * 可以看到在RxHelper中看到我们调用了两次subscribeOn
 * 最后一个调用也就是离doOnSubscribe()最近的一次subscribeOn是指定的AndroidSchedulers.mainThread()也就是主线程。
 * 这样我们就就能保证它永远都在主线运行了
 *
 * 在onCompleted()和onError()的时候取消Dialog。需要的时候调用showProgressDialog即可。
 *
 */
public abstract class RxSubscriber<T> extends Subscriber<T> {


    @Override
    public void onCompleted() {

    }




    @Override
    public void onNext(T t) {
        _onNext(t);
    }


    @Override
    public void onError(Throwable e) {

    }

    protected abstract void _onNext(T t);
    protected abstract void _onError(String message);
}
