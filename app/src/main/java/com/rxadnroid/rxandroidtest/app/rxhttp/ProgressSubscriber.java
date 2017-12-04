package com.rxadnroid.rxandroidtest.app.rxhttp;

import android.content.Context;

import com.rxadnroid.rxandroidtest.app.ui.dialog.SimpleLoadDialog;

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
public abstract class ProgressSubscriber <T> extends Subscriber<T> implements ProgressCancelListener {
    private SimpleLoadDialog dialogHandler;

    public ProgressSubscriber(Context context) {
        dialogHandler = new SimpleLoadDialog(context,this,true);
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }


    /**
     * 显示Dialog
     */
    public void showProgressDialog(){
        if (dialogHandler != null) {
            dialogHandler.obtainMessage(SimpleLoadDialog.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    /**
     * 隐藏Dialog
     */
    private void dismissProgressDialog(){
        if (dialogHandler != null) {
            dialogHandler.obtainMessage(SimpleLoadDialog.DISMISS_PROGRESS_DIALOG).sendToTarget();
            dialogHandler=null;
        }
    }
    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (false) { //这里自行替换判断网络的代码
            _onError("网络不可用");
        } else if (e instanceof ApiException) {
            _onError(e.getMessage());
        } else {
            _onError("请求失败，请稍后再试...");
        }
        dismissProgressDialog();
    }


    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
    protected abstract void _onNext(T t);
    protected abstract void _onError(String message);
}
