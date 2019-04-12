package com.yc.ycbaseadapter.adapter;

import android.content.Context;

import com.yc.YcRecyclerViewBaseAdapter.adapter.YcCommonBaseAdapter;
import com.yc.YcRecyclerViewBaseAdapter.base.YcBaseViewHolder;
import com.yc.ycbaseadapter.R;

public class LadderAdapter extends YcCommonBaseAdapter<String> {
    public LadderAdapter(Context context) {
        super(context);
    }

    @Override
    protected void convert(YcBaseViewHolder holder, String data, int position, int viewType) {
        holder.setText(R.id.info_text, data);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_ladder;
    }
}
