package com.rxadnroid.rxandroidtest.app.common.recycleview.wrapper;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.rxadnroid.rxandroidtest.app.common.recycleview.BaseViewHolder;

/**
 * Created by 子非鱼 on 2017/5/12.
 */
public class AllOverWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM_TYPE_ALL = Integer.MAX_VALUE - 3;

    private RecyclerView.Adapter mInnerAdapter;
    private View mAllOverView;
    private int mAllOverLayoutId;

    private boolean mIsAllOver = false;


    public AllOverWrapper(RecyclerView.Adapter adapter)
    {
        mInnerAdapter = adapter;
    }

    private boolean hasAllOver()
    {
        return mAllOverView != null || mAllOverLayoutId != 0;
    }



    private boolean isShowAllOver(int position)
    {
        return hasAllOver() && (position >= mInnerAdapter.getItemCount());
//        return hasAllOver() && (mInnerAdapter.getItemCount() > 10);
    }


    @Override
    public int getItemViewType(int position) {
        if (isShowAllOver(position)) {
            return ITEM_TYPE_ALL;

        }
        return mInnerAdapter.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_ALL) {
            BaseViewHolder holder;
            if (mAllOverView != null) {
                holder = BaseViewHolder.createViewHolder(parent.getContext(), mAllOverView);
            } else {
                holder = BaseViewHolder.createViewHolder(parent.getContext(), parent, mAllOverLayoutId);
            }
            return holder;
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if (isShowAllOver(position))
        {

            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback()
        {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position)
            {
                if (isShowAllOver(position))
                {
                    return layoutManager.getSpanCount();
                }
                if (oldLookup != null)
                {
                    return oldLookup.getSpanSize(position);
                }
                return 1;
            }
        });
    }


    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder)
    {
        mInnerAdapter.onViewAttachedToWindow(holder);

        if (isShowAllOver(holder.getLayoutPosition()))
        {
            setFullSpan(holder);
        }
    }

    private void setFullSpan(RecyclerView.ViewHolder holder)
    {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();

        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams)
        {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;

            p.setFullSpan(true);
        }
    }

    @Override
    public int getItemCount()
    {
        return mInnerAdapter.getItemCount() + ((hasAllOver() && mIsAllOver) ? 1 : 0);
    }

    public AllOverWrapper setAllOverView(View loadMoreView)
    {
        mAllOverView = loadMoreView;
        return this;
    }

    public AllOverWrapper setAllOverView(int layoutId)
    {
        mAllOverLayoutId = layoutId;
        return this;
    }

    public void setLoadOver(boolean loadOver) {
        mIsAllOver = loadOver;
    }

}
