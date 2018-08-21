package com.yc.ycbaseadapter.adapter;

import android.content.Context;

import com.yc.YcRecyclerViewBaseAdapter.adapter.YcBaseAdapter;
import com.yc.YcRecyclerViewBaseAdapter.adapter.YcCommonBaseAdapter;
import com.yc.YcRecyclerViewBaseAdapter.base.YcBaseViewHolder;
import com.yc.ycbaseadapter.R;

public class OneItemAdapter extends YcCommonBaseAdapter<String>  {
    public OneItemAdapter(Context context) {
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


    //    @Override
//    protected int getItemLayoutId() {
//        return R.layout.item_layout;
//    }
//
//
//    public OneItemAdapter(Context context) {
//        super(context);
//    }
//
//    @Override
//    protected void convert(YcBaseViewHolder holder, final String data, int position) {
//        holder.setText(R.id.item_title, data);
//        holder.addOnClickListener(R.id.item_title);
//        holder.addOnClickListener(R.id.item_btn);
//    }
}
