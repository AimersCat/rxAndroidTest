package com.rxadnroid.rxandroidtest.app.common.recycleview;

/**
 * Created by 子非鱼 on 2017/5/11.
 */
public interface ItemViewDelegate<T> {
    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(BaseViewHolder holder, T t, int position);
}
