package com.rxadnroid.rxandroidtest.app.common.recycleview;

import android.content.Context;
import android.view.LayoutInflater;

import java.util.List;

/**
 * Created by 子非鱼 on 2017/5/11.
 */
public abstract class CommonRecycleAdapter<T> extends MultiItemTypeAdapter<T> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;

    public CommonRecycleAdapter(final Context context, final int layoutId, List<T> datas)
    {
        super(context, datas);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;
        addItemViewDelegate(new ItemViewDelegate<T>()
        {
            @Override
            public int getItemViewLayoutId()
            {
                return layoutId;
            }

            @Override
            public boolean isForViewType( T item, int position)
            {
                return true;
            }

            @Override
            public void convert(BaseViewHolder holder, T t, int position)
            {
                CommonRecycleAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(BaseViewHolder holder, T t, int position);
}
