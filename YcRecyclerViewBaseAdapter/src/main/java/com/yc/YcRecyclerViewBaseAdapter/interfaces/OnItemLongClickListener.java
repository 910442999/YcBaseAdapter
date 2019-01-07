package com.yc.YcRecyclerViewBaseAdapter.interfaces;


import com.yc.YcRecyclerViewBaseAdapter.base.YcBaseViewHolder;

/**
 * 条目长按点击事件
 */
public interface OnItemLongClickListener<T> {
    void onItemLongClick(YcBaseViewHolder viewHolder, T data, int position);
}
