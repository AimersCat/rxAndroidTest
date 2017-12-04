package com.rxadnroid.rxandroidtest.app.rxhttp;

import com.orhanobut.hawk.Hawk;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 子非鱼 on 2017/1/19.
 */
public class RetrofitCache {
    /**
     * @param cacheKey     缓存的Key
     * @param fromNetwork
     * @param isSave       是否缓存
     * @param forceRefresh 是否强制刷新
     * @param <T>
     * @return
     */


     /*
    这里我们先取了一个Observable<T>对象fromCache,里面的操作很简单，
    去缓存里面找个key对应的缓存，如果有就发射数据。
    在fromNetwork里面做的操作仅仅是缓存数据这一操作。
    最后判断如果强制刷新就直接返回fromNetwork反之用Observable.concat()做一个合并。
    concat操作符将多个Observable结合成一个Observable并发射数据。这里又用了first()。
    fromCache和fromNetwork任何一步一旦发射数据后面的操作都不执行
     */
    public static <T> Observable<T> load(final String cacheKey,
                                         Observable<T> fromNetwork,
                                         boolean isSave, boolean forceRefresh) {
        Observable<T> fromCache = Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                T cache = (T) Hawk.get(cacheKey);
                if (cache != null) {
                    subscriber.onNext(cache);
                } else {
                    subscriber.onCompleted();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        //是否缓存
        if (isSave) {
            /**
             * 这里的fromNetwork 不需要指定Schedule,在handleRequest中已经变换了
             */
            fromNetwork = fromNetwork.map(new Func1<T, T>() {
                @Override
                public T call(T result) {
                    Hawk.put(cacheKey, result);

                    return result;
                }
            });
        }
        //强制刷新
        if (forceRefresh) {
            return fromNetwork;
        } else {
            return Observable.concat(fromCache, fromNetwork).first();
        }
    }

}
