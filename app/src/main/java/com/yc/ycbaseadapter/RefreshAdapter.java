package com.yc.ycbaseadapter;

import android.content.Context;

import com.yc.YcRecyclerViewBaseAdapter.adapter.YcCommonBaseAdapter;
import com.yc.YcRecyclerViewBaseAdapter.base.YcBaseViewHolder;

/**
 * Author: Othershe
 * ime: 2016/8/29 15:40
 */
public class RefreshAdapter extends YcCommonBaseAdapter<String> {
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
        //        holder.setOnClickClickListener(R.id.item_btn, new View.OnClickListener() {
        //            @Override
        //            public void onClick(View view) {
        //                Toast.makeText(mContext, "我是" + data + "的button", Toast.LENGTH_SHORT).show();
        //            }
        //        });
    }


}
