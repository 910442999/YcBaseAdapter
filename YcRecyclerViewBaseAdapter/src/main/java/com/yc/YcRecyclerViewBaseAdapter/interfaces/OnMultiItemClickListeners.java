package com.yc.YcRecyclerViewBaseAdapter.interfaces;

import com.yc.YcRecyclerViewBaseAdapter.base.YcBaseViewHolder;

/**
 * 多条目的点击事件
 */
public interface OnMultiItemClickListeners<T> {
    void onItemClick(YcBaseViewHolder viewHolder, T data, int position, int viewType);
}
