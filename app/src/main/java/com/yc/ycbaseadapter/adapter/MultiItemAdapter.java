package com.yc.ycbaseadapter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.yc.YcRecyclerViewBaseAdapter.adapter.YcBaseAdapter;
import com.yc.YcRecyclerViewBaseAdapter.adapter.YcMultiBaseAdapter;
import com.yc.YcRecyclerViewBaseAdapter.base.YcBaseViewHolder;
import com.yc.ycbaseadapter.R;

import java.util.List;

public class MultiItemAdapter<T> extends YcMultiBaseAdapter<String> {
    public MultiItemAdapter(Context context) {
        super(context);
    }

    @Override
    protected void convert(YcBaseViewHolder holder, String data, int position, int viewType) {
        if (viewType == 0) {
            holder.setText(R.id.item_title, data);
            holder.addOnClickListener(R.id.item_title);
            holder.addOnClickListener(R.id.item_btn);
        } else {
            holder.setText(R.id.item_title1, data);
            holder.addOnClickListener(R.id.item_title1);
        }

    }

    @Override
    protected int getItemLayoutId(int viewType) {
        if (viewType == 0) {
            return R.layout.item_layout;
        }
        return R.layout.item_layout1;
    }

    @Override
    protected int getViewType(int position, String data) {
        if (position % 2 == 0) {
            return 0;
        }
        return 1;
    }
}
