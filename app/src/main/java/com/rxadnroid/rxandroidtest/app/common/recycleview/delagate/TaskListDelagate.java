package com.rxadnroid.rxandroidtest.app.common.recycleview.delagate;

import com.rxadnroid.rxandroidtest.R;
import com.rxadnroid.rxandroidtest.app.common.recycleview.BaseViewHolder;
import com.rxadnroid.rxandroidtest.app.common.recycleview.ItemViewDelegate;
import com.rxadnroid.rxandroidtest.app.entity.TaskList;

/**
 * Created by 子非鱼 on 2017/5/11.
 */
public class TaskListDelagate implements ItemViewDelegate<TaskList> {

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_recycle;
    }

    @Override
    public boolean isForViewType(TaskList item, int position) {
        return false;
    }

    @Override
    public void convert(BaseViewHolder holder, TaskList taskList, int position) {
        holder.setText(R.id.id_name , taskList.getZname()) ;
        holder.setText(R.id.tv_msg_title , taskList.getTitle());
        holder.setText(R.id.tv_msg_begin_time , taskList.getStarttime().replace("T" , " ")) ;
        holder.setText(R.id.tv_msg_end_time , taskList.getEndtime().replace("T" , " ")) ;
        holder.setImageUrlByGlide(R.id.id_icon , "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1494483671&di=ec43591ee32b6b410457169ee0e2c235&src=http://img3.duitang.com/uploads/people/201609/02/20160902093329_YCXnL.jpeg") ;

    }
}
