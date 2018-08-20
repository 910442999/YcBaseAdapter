package com.yc.ycbaseadapter.adapter;

import android.content.Context;

import com.yc.YcRecyclerViewBaseAdapter.adapter.YcBaseAdapter;
import com.yc.YcRecyclerViewBaseAdapter.adapter.YcCommonBaseAdapter;
import com.yc.YcRecyclerViewBaseAdapter.base.YcBaseViewHolder;
import com.yc.ycbaseadapter.R;

/**
 * Author: Othershe
 * ime: 2016/8/29 15:40
 */
public class RefreshAdapter extends YcBaseAdapter<String,YcBaseViewHolder> {
    @Override
    protected int getItemLayoutId() {
        return R.layout.item_layout;
    }


    public RefreshAdapter(Context context) {
        super(context);
    }

    @Override
    protected void convert(YcBaseViewHolder holder, final String data, int position) {
        holder.setText(R.id.item_title, data);
        holder.addOnClickListener(R.id.item_title);
        holder.addOnClickListener(R.id.item_btn);

    }


}
