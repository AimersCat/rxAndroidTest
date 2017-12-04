package com.rxadnroid.rxandroidtest.app.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import com.rxadnroid.rxandroidtest.R;
import com.rxadnroid.rxandroidtest.app.api.TaskApi;
import com.rxadnroid.rxandroidtest.app.common.recycleview.BaseViewHolder;
import com.rxadnroid.rxandroidtest.app.common.recycleview.CommonRecycleAdapter;
import com.rxadnroid.rxandroidtest.app.common.recycleview.WrapContentLinearLayoutManager;
import com.rxadnroid.rxandroidtest.app.common.recycleview.wrapper.AllOverWrapper;
import com.rxadnroid.rxandroidtest.app.common.recycleview.wrapper.EmptyWrapper;
import com.rxadnroid.rxandroidtest.app.common.recycleview.wrapper.HeaderAndFooterWrapper;
import com.rxadnroid.rxandroidtest.app.common.recycleview.wrapper.LoadMoreWrapper;
import com.rxadnroid.rxandroidtest.app.entity.TaskList;
import com.rxadnroid.rxandroidtest.app.base.ActivityLifeCycleEvent;
import com.rxadnroid.rxandroidtest.app.base.BaseActivity;
import com.rxadnroid.rxandroidtest.app.entity.UserEvent;
import com.rxadnroid.rxandroidtest.app.rxbus.RxBus;
import com.rxadnroid.rxandroidtest.app.rxhttp.HttpUtil;
import com.rxadnroid.rxandroidtest.app.rxhttp.ProgressSubscriber;
import com.rxadnroid.rxandroidtest.app.rxhttp.RetrofitService;


import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by 子非鱼 on 2017/1/19.
 */
public class PublicCardActivity extends BaseActivity{

    private RecyclerView mRecyclerView ;

    private ProgressDialog dialog ;
    private SwipeRefreshLayout mPullToRefreshView ;

    private List<TaskList> data = new ArrayList<TaskList>() ;
    private View footerView ;

    private CommonRecycleAdapter<TaskList> mAdapter ;
    private  WrapContentLinearLayoutManager linearLayoutManager ;

    private HeaderAndFooterWrapper<String> footerWrapper ;
    private LoadMoreWrapper<String> loadMoreWrapper ;
    private EmptyWrapper mEmptyWrapper;
    private AllOverWrapper mAllOverWrapper ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public);

        mRecyclerView =(RecyclerView) findViewById(R.id.id_recyclerView) ;
        mPullToRefreshView = (SwipeRefreshLayout) findViewById(R.id.pull_to_refresh);

        mPullToRefreshView.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);

        mPullToRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRetrofitData(false) ;
            }
        });

//        footerView = getLayoutInflater().inflate(R.layout.menu_layou,null);
        linearLayoutManager = new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) ;
        mRecyclerView.setLayoutManager(linearLayoutManager);
        /**
         * 确保只有完全滑到顶部，然后再下拉时才刷新
         */
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                mPullToRefreshView.setEnabled(linearLayoutManager.findFirstVisibleItemPosition() == 0);
            }
        });

        mAdapter = new CommonRecycleAdapter<TaskList>(this , R.layout.item_recycle , data) {
            @Override
            protected void convert(BaseViewHolder holder, TaskList taskList, int position) {

                holder.setText(R.id.id_name , taskList.getZname()) ;
                holder.setText(R.id.tv_msg_title , taskList.getTitle());
                holder.setText(R.id.tv_msg_begin_time , taskList.getStarttime().replace("T" , " ")) ;
                holder.setText(R.id.tv_msg_end_time , taskList.getEndtime().replace("T" , " ")) ;
                holder.setImageUrlByGlide(R.id.id_icon , "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1494483671&di=ec43591ee32b6b410457169ee0e2c235&src=http://img3.duitang.com/uploads/people/201609/02/20160902093329_YCXnL.jpeg") ;
            }
        } ;

        initEmptyView() ;
        initAllOverView() ;

        loadMoreWrapper = new LoadMoreWrapper<String>(mAllOverWrapper) ;

        loadMoreWrapper.setLoadMoreView(R.layout.menu_layou);

        loadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getRetrofitData(false) ;
            }

        }) ;
        mRecyclerView.setAdapter(loadMoreWrapper);

        getRetrofitData(true) ;
    }

    private void getRetrofitData(boolean isShow){
        Observable ob = RetrofitService.getInstance().createService(TaskApi.class).getUserObservable("900005");
        HttpUtil.getInstance().toSubscribe(ob, new ProgressSubscriber<List<TaskList>>(this) {
            @Override
            protected void _onError(String message) {

                Toast.makeText(PublicCardActivity.this, message, Toast.LENGTH_LONG).show();
                mPullToRefreshView.setRefreshing(false);
            }

            @Override
            protected void _onNext(List<TaskList> list) {

                data.addAll(list.subList(0 , 10)) ;

                if(data.size() > 18){
                    loadMoreWrapper.setLoadOver(false);
                    loadMoreWrapper.notifyItemRangeRemoved(data.size(), data.size() + 1) ;

                    mAllOverWrapper.setLoadOver(true);
                    mAllOverWrapper.notifyItemRangeInserted(data.size(), data.size() + 1);
                }

                loadMoreWrapper.notifyDataSetChanged();
                mPullToRefreshView.setRefreshing(false);
            }


        }, "cacheKey", ActivityLifeCycleEvent.CREATE, lifecycleSubject, true, false , isShow);
    }

    private void iniRxBus(){
        HttpUtil.getInstance().bindActivityRxBus(RxBus.getDefault().toObservableSticky(UserEvent.class) , lifecycleSubject , new Subscriber<UserEvent>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(PublicCardActivity.this , "activity_error" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(UserEvent userEvent) {
                Log.i("info-----activity" , "hahah") ;
                Toast.makeText(PublicCardActivity.this , "activity_error" , Toast.LENGTH_SHORT).show();
            }
        } , ActivityLifeCycleEvent.STOP);
    }


    private void initEmptyView()
    {
        mEmptyWrapper = new EmptyWrapper(mAdapter);
        mEmptyWrapper.setEmptyView(LayoutInflater.from(this).inflate(R.layout.empty_view, mRecyclerView, false));
    }

    private void initAllOverView() {
        mAllOverWrapper = new AllOverWrapper(mEmptyWrapper);
        mAllOverWrapper.setAllOverView(LayoutInflater.from(this).inflate(R.layout.over_item, mRecyclerView, false));
    }

}
