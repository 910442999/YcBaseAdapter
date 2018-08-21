package com.yc.ycbaseadapter.adapter;

import android.content.Context;

import com.yc.YcRecyclerViewBaseAdapter.adapter.YcBaseAdapter;
import com.yc.YcRecyclerViewBaseAdapter.adapter.YcCommonBaseAdapter;
import com.yc.YcRecyclerViewBaseAdapter.base.YcBaseViewHolder;
import com.yc.ycbaseadapter.R;

/**
 * 加载更多适配器
 */
public class RefreshAdapter extends YcCommonBaseAdapter<String> {


    public RefreshAdapter(Context context) {
        super(context);
    }

    @Override
    protected void convert(YcBaseViewHolder holder, String data, int position, int viewType) {
        holder.setText(R.id.item_title, data);
        holder.addOnClickListener(R.id.item_title);
        holder.addOnClickListener(R.id.item_btn);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_layout;
    }
}
