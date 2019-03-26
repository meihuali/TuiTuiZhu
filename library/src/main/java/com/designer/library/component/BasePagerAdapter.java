/*
 * Copyright (C) 2018,杭州燊禾电子商务有限公司 All rights reserved.
 * Project：TongHeChuanMei
 * Author：姜涛
 * Date：11/19/18 9:50 AM
 */

package com.designer.library.component;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager.widget.PagerAdapter;
import com.designer.library.base.Library;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BasePagerAdapter extends PagerAdapter {

    private List<String> titles = new ArrayList<>();
    private List<ViewDataBinding> viewDataBindings = new ArrayList<>();

    /**
     * 布局
     */
    public BasePagerAdapter(Integer[] layoutIds) {
        for (Integer layoutId : layoutIds) {
            ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(Library.INSTANCE.getApp()), layoutId, null, false);
            viewDataBindings.add(viewDataBinding);
        }
    }

    public BasePagerAdapter() {
    }

    /**
     * 标题和布局
     */
    public BasePagerAdapter(String[] titles, Integer[] layoutIds) {
        this.titles.addAll(Arrays.asList(titles));

        for (Integer layoutId : layoutIds) {
            ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(Library.INSTANCE.getApp()), layoutId, null, false);
            viewDataBindings.add(viewDataBinding);
        }
    }


    @Override
    public int getCount() {
        return viewDataBindings.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ViewDataBinding viewDataBinding = viewDataBindings.get(position);
        View view = viewDataBinding.getRoot();
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null) {
            return titles.get(position);
        } else {
            return null;
        }
    }

    public <B extends ViewDataBinding> B getViewDataBinding(int position) {
        return (B) viewDataBindings.get(position);
    }

    /**
     * 通过布局创建并且返回ViewDataBinding
     */
    public <B extends ViewDataBinding> B add(int layoutId) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(Library.INSTANCE.getApp()), layoutId, null, false);
        viewDataBindings.add(viewDataBinding);
        return (B) viewDataBinding;
    }

    public <B extends ViewDataBinding> B add(int layoutId, String title) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(Library.INSTANCE.getApp()), layoutId, null, false);
        viewDataBindings.add(viewDataBinding);
        titles.add(title);
        return (B) viewDataBinding;
    }
}
