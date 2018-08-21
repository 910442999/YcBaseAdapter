package com.yc.YcRecyclerViewBaseAdapter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.yc.YcRecyclerViewBaseAdapter.base.YcBaseViewHolder;
import com.yc.YcRecyclerViewBaseAdapter.interfaces.OnItemChildClickListener;
import com.yc.YcRecyclerViewBaseAdapter.interfaces.OnMultiItemClickListeners;

import java.util.ArrayList;

public abstract class YcMultiBaseAdapter<T> extends YcBaseAdapter<T, YcBaseViewHolder> {

    public YcMultiBaseAdapter(Context context) {
        super(context);
    }
}
