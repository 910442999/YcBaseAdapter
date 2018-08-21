package com.yc.YcRecyclerViewBaseAdapter.adapter;

import android.content.Context;

import com.yc.YcRecyclerViewBaseAdapter.base.YcBaseViewHolder;

public abstract class YcCommonBaseAdapter<T> extends YcBaseAdapter<T, YcBaseViewHolder> {

    public YcCommonBaseAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getViewType(int position, T data) {
        return TYPE_COMMON_VIEW;
    }
}
