package com.yc.YcRecyclerViewBaseAdapter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.yc.YcRecyclerViewBaseAdapter.base.YcBaseViewHolder;
import com.yc.YcRecyclerViewBaseAdapter.interfaces.OnItemChildClickListener;
import com.yc.YcRecyclerViewBaseAdapter.interfaces.OnMultiItemClickListeners;

import java.util.ArrayList;

public abstract class YcMultiBaseAdapter<T> extends YcBaseAdapter<T,YcBaseViewHolder> {
    //多条目视图的点击事件
    private OnMultiItemClickListeners<T> mItemClickListener;
    //条目子view的id
    private ArrayList<Integer> mItemChildIds = new ArrayList<>();
    //条目子view的点击事件
    private ArrayList<OnItemChildClickListener> mItemChildListeners = new ArrayList<>();

    public YcMultiBaseAdapter(Context context) {
        super(context);
    }

    protected abstract void convert(YcBaseViewHolder holder, T data, int position, int viewType);
    protected abstract int getItemLayoutId(int viewType);
    @Override
    public YcBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isCommonItemView(viewType)) {
            return YcBaseViewHolder.create(parent.getContext(), getItemLayoutId(viewType), parent);
        }
        return super.onCreateViewHolder(parent, viewType);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        if (isCommonItemView(viewType)) {
            bindCommonItem(holder, position - getNormalHeaderLayoutCount(), viewType);
        }
    }

    private void bindCommonItem(RecyclerView.ViewHolder holder, final int position, final int viewType) {
        final YcBaseViewHolder viewHolder = (YcBaseViewHolder) holder;
        convert(viewHolder, getAllData().get(position), position, viewType);

        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(viewHolder, getAllData().get(position), position, viewType);
                }
            }
        });

//        for (int i = 0; i < mItemChildIds.size(); i++) {
//            final int tempI = i;
//            if (viewHolder.getConvertView().findViewById(mItemChildIds.get(i)) != null) {
//                viewHolder.getConvertView().findViewById(mItemChildIds.get(i)).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mItemChildListeners.get(tempI).onItemChildClick(viewHolder, getAllData().get(position), position);
//                    }
//                });
//            }
//        }
    }

    public void setOnMultiItemClickListener(OnMultiItemClickListeners<T> itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public void setOnItemChildClickListener(int viewId, OnItemChildClickListener itemChildClickListener) {
        mItemChildIds.add(viewId);
        mItemChildListeners.add(itemChildClickListener);
    }
}
