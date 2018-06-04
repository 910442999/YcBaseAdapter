package com.yc.YcRecyclerViewBaseAdapter.interfaces;


import com.yc.YcRecyclerViewBaseAdapter.base.YcBaseViewHolder;

/**
 * 条目 子控件的点击事件
 */
public interface OnItemChildClickListener<T> {
    void onItemChildClick(YcBaseViewHolder viewHolder, T data, int position);
}
