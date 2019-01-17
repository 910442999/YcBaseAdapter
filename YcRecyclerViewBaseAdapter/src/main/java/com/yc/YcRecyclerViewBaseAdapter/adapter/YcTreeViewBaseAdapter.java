package com.yc.YcRecyclerViewBaseAdapter.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;

import com.yc.YcRecyclerViewBaseAdapter.base.YcBaseViewHolder;

public abstract class YcTreeViewBaseAdapter<T> extends YcBaseAdapter<T, YcBaseViewHolder> {

    public int LEVEL_PADDING = 20; //左侧水平填充的距离
    public int RIGHT_PADDING = 10;//右侧水平填充的距离
    public int TOP_PADDING = 10;//顶部填充的距离
    public int BOTTOM_PADDING = 10;//底部填充的距离

    public YcTreeViewBaseAdapter(Context context) {
        super(context);
    }

    public YcTreeViewBaseAdapter setItemLevet(int levet) {
        LEVEL_PADDING = levet;
        return this;
    }

    public YcTreeViewBaseAdapter setItemRight(int itemRight) {
        RIGHT_PADDING = itemRight;
        return this;
    }

    public YcTreeViewBaseAdapter setItemTop(int itemTop) {
        TOP_PADDING = itemTop;
        return this;
    }

    public YcTreeViewBaseAdapter setItemBottom(int itemBottom) {
        BOTTOM_PADDING = itemBottom;
        return this;
    }
}
