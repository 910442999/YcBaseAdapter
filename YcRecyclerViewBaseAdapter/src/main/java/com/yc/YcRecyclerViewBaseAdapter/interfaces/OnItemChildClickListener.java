package com.yc.YcRecyclerViewBaseAdapter.interfaces;


import android.view.View;

import com.yc.YcRecyclerViewBaseAdapter.adapter.YcBaseAdapter;

/**
 * 条目 子控件的点击事件
 */
public interface OnItemChildClickListener {
    void onItemChildClick(YcBaseAdapter adapter, View view, int position);
}
