package com.yc.YcRecyclerViewBaseAdapter.interfaces;


import com.yc.YcRecyclerViewBaseAdapter.base.YcBaseViewHolder;

/**
 * 条目点击事件
 */
public interface OnItemClickListener<T> {
    void onItemClick(YcBaseViewHolder viewHolder, T data, int position);
}
