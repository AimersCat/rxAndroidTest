package com.rxadnroid.rxandroidtest.app.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rxadnroid.rxandroidtest.app.fragment.NoticeFragment;


/**
 * Created by ytd007 on 2016/5/19.
 */
/*适配器中fragment设置*/
public class NoticeIncatorAdapter extends FragmentPagerAdapter {
    public String[] title;
    private  int index;
    public Fragment fragment;
    public NoticeIncatorAdapter(FragmentManager fm, String[] title) {
        super(fm);
        this.title=title;
    }

    @Override
    public Fragment getItem(int position) {
        if (title[position].equals("集团文件")){
            fragment = new NoticeFragment();
        }

        Bundle args = new Bundle();
        args.putString("arg", title[position]);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position % title.length];
    }

    @Override
    public int getCount() {
        return title.length;
    }
}
