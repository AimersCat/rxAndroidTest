package com.rxadnroid.rxandroidtest.app.rxhttp;

import android.support.annotation.NonNull;

import com.rxadnroid.rxandroidtest.app.base.ActivityLifeCycleEvent;
import com.rxadnroid.rxandroidtest.app.base.FragmentLifeCycleEvent;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by 子非鱼 on 2017/1/19.
 */
public class RxHelper {

    /**
     * 利用Observable.takeUntil()停止网络请求
     *
     * @param event
     * @param lifecycleSubject
     * @param <T>
     * @return
     */
    @NonNull
    public static <T> Observable.Transformer<T, T> bindActivityUntilEvent(@NonNull final ActivityLifeCycleEvent event, final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject) {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> sourceObservable) {
                Observable<ActivityLifeCycleEvent> compareLifecycleObservable =
                        lifecycleSubject.takeFirst(new Func1<ActivityLifeCycleEvent, Boolean>() {
                            @Override
                            public Boolean call(ActivityLifeCycleEvent activityLifeCycleEvent) {
                                return activityLifeCycleEvent.equals(event);
                            }
                        });
                return sourceObservable.takeUntil(compareLifecycleObservable);
            }
        };
    }


    @NonNull
    public static <T> Observable.Transformer<T, T> bindFragmentUntilEvent(@NonNull final FragmentLifeCycleEvent event, final PublishSubject<FragmentLifeCycleEvent> lifecycleSubject) {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> sourceObservable) {
                Observable<FragmentLifeCycleEvent> compareLifecycleObservable =
                        lifecycleSubject.takeFirst(new Func1<FragmentLifeCycleEvent, Boolean>() {
                            @Override
                            public Boolean call(FragmentLifeCycleEvent activityLifeCycleEvent) {
                                return activityLifeCycleEvent.equals(event);
                            }
                        });
                return sourceObservable.takeUntil(compareLifecycleObservable);
            }
        };
    }


    /**
     * 对结果进行预处理
     * Transformer实际上就是一个Func1<Observable<T>, Observable<R>>换言之就是：可以通过
     * 它将一种类型的Observable转换成另一种类型的Observable，和调用一系列的内联操作符
     * 是一模一样的。这里我们首先使用flatMap操作符把Obserable<HttpResult<T>>,转换成
     * 为Observable<T>在内部对code进行了预处理。如果成功则把结果Observable<T>发射给订
     * 阅者。反之则把code交给ApiException并返回一个异常，ApiException中我们对code进行
     * 相应的处理并返回对应的错误信息
     *
     * TakeUntil订阅原始的Observable并发射数据，此外它还监视你提供的第二个Observable。
     * 当第二个Observable发射了一项数据或者发射一项终止的通知时（onError通知或者onCompleted通知），
     * TakeUntil返回的Observable会停止发射原始的Observable
     *
     *
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<HttpResult<T>, T> handleResult(final ActivityLifeCycleEvent event, final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject) {
        return new Observable.Transformer<HttpResult<T>, T>() {
            @Override
            public Observable<T> call(Observable<HttpResult<T>> tObservable) {
                Observable<ActivityLifeCycleEvent> compareLifecycleObservable =
                        lifecycleSubject.takeFirst(new Func1<ActivityLifeCycleEvent, Boolean>() {
                            @Override
                            public Boolean call(ActivityLifeCycleEvent activityLifeCycleEvent) {
                                return activityLifeCycleEvent.equals(event);
                            }
                        });
                return tObservable.flatMap(new Func1<HttpResult<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(HttpResult<T> result) {
                        if (result.getSubjects() != null) {
                            return createData(result.getSubjects());
                        } else {
                            return Observable.error(new ApiException(0));
                        }
                    }
                })
                        .takeUntil(compareLifecycleObservable)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
//                        .subscribeOn(AndroidSchedulers.mainThread())
                        ;
            }
        };
    }

    /**
     * 创建成功的数据
     * @param data
     * @param <T>
     * @return
     */
    private static <T> Observable<T> createData(final T data) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
