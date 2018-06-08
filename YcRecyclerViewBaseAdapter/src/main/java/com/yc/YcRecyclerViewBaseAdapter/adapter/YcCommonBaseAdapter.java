package com.yc.YcRecyclerViewBaseAdapter.adapter;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.yc.YcRecyclerViewBaseAdapter.base.YcBaseViewHolder;
import com.yc.YcRecyclerViewBaseAdapter.interfaces.OnItemChildClickListener;
import com.yc.YcRecyclerViewBaseAdapter.interfaces.OnItemClickListener;

import java.util.ArrayList;

public abstract class YcCommonBaseAdapter<T> extends YcBaseAdapter<T> {

    public YcCommonBaseAdapter(Context context) {
        super(context);
    }

    private OnItemClickListener<T> mItemClickListener;
    private ArrayList<Integer> mViewId = new ArrayList<>();
    private ArrayList<Integer> mItemChildIds = new ArrayList<>();
    private ArrayList<OnItemChildClickListener<T>> mItemChildListeners = new ArrayList<>();

    public void setOnItemClickListener(OnItemClickListener<T> itemClickListener) {
        mItemClickListener = itemClickListener;
    }
    //
    //    public void setOnSwipMenuClickListener(int viewId, OnSwipeMenuClickListener<T> swipeMenuClickListener) {
    //        mViewId.add(viewId);
    //        mListener.add(swipeMenuClickListener);
    //    }

    public void setOnItemChildClickListener(int viewId, OnItemChildClickListener<T> itemChildClickListener) {
        mItemChildIds.add(viewId);
        mItemChildListeners.add(itemChildClickListener);
    }

    protected abstract void convert(YcBaseViewHolder holder, T data, int position);

    protected abstract int getItemLayoutId();


    @Override
    public YcBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isCommonItemView(viewType)) {
            return YcBaseViewHolder.create(parent.getContext(), getItemLayoutId(), parent);
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        if (isCommonItemView(viewType)) {
            position = position - getNormalHeaderLayoutCount();
            if (getItem(position) != null) {
                bindCommonItem(holder, position);
            }
        }
    }

    private void bindCommonItem(RecyclerView.ViewHolder holder, final int position) {
        final YcBaseViewHolder viewHolder = (YcBaseViewHolder) holder;

        convert(viewHolder, getAllData().get(position), position);

        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(viewHolder, getAllData().get(position), position);
                }
            }
        });

        for (int i = 0; i < mItemChildIds.size(); i++) {
            final int tempI = i;
            if (viewHolder.getConvertView().findViewById(mItemChildIds.get(i)) != null) {
                viewHolder.getConvertView().findViewById(mItemChildIds.get(i)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemChildListeners.get(tempI).onItemChildClick(viewHolder, getAllData().get(position), position);
                    }
                });
            }
        }

        //        if (mViewId.size() > 0 && mListener.size() > 0 && viewHolder.getSwipeView() != null) {
        //            ViewGroup swipeView = (ViewGroup) viewHolder.getSwipeView();
        //
        //            for (int i = 0; i < mViewId.size(); i++) {
        //                final int tempI = i;
        //                swipeView.findViewById(mViewId.get(i)).setOnClickListener(new View.OnClickListener() {
        //                    @Override
        //                    public void onClick(View v) {
        //                        mListener.get(tempI).onSwipMenuClick(viewHolder, getAllData().get(position), position);
        //                    }
        //                });
        //            }
        //        }
    }

    @Nullable
    public T getItem(@IntRange(from = 0) int position) {
        if ((position >= 0 && position < getAllData().size()))
            return getAllData().get(position);
        else
            return null;
    }
}
