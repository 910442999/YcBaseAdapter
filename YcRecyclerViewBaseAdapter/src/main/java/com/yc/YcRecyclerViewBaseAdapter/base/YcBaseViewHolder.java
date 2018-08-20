package com.yc.YcRecyclerViewBaseAdapter.base;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yc.YcRecyclerViewBaseAdapter.adapter.YcBaseAdapter;
import com.yc.YcRecyclerViewBaseAdapter.adapter.YcCommonBaseAdapter;

import java.util.LinkedHashSet;

/**
 * base viewHolder
 */
public class YcBaseViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    private View mConvertView;
    private final LinkedHashSet<Integer> childClickViewIds;
    private YcBaseAdapter mYcBaseAdapter;

    public YcBaseViewHolder(View itemView) {
        super(itemView);
        mConvertView = itemView;
        mViews = new SparseArray<>();
        childClickViewIds = new LinkedHashSet<>();
    }

    /**
     * 创建 viewHolder
     *
     * @param context
     * @param layoutId
     * @param parent
     * @return
     */
    public static YcBaseViewHolder create(Context context, int layoutId, ViewGroup parent) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new YcBaseViewHolder(itemView);
    }

    public static YcBaseViewHolder create(View itemView) {
        return new YcBaseViewHolder(itemView);
    }
    /**
     * Sets the adapter of a adapter view.
     *
     * @param adapter The adapter;
     * @return The BaseViewHolder for chaining.
     */
    public YcBaseViewHolder setAdapter(YcBaseAdapter adapter) {
        this.mYcBaseAdapter = adapter;
        return this;
    }

    /**
     * 通过id获得控件
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }


    public View getConvertView() {
        return mConvertView;
    }

    public View getSwipeView() {
        ViewGroup itemLayout = ((ViewGroup) mConvertView);
        if (itemLayout.getChildCount() == 2) {
            return itemLayout.getChildAt(1);
        }
        return null;
    }

    //设置条目按钮的监听
    public void setOnClickClickListener(int viewId, View.OnClickListener clickListener) {
        View view = getView(viewId);
        view.setOnClickListener(clickListener);
    }

    /**
     * Sets the on click listener of the view.
     *
     * @param viewId   The view id.
     * @param listener The on click listener;
     * @return The BaseViewHolder for chaining.
     */
    @Deprecated
    public YcBaseViewHolder setOnClickListener(@IdRes int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    private int getClickPosition() {
        if (getLayoutPosition() >= mYcBaseAdapter.getNormalHeaderLayoutCount()) {
            return getLayoutPosition() - mYcBaseAdapter.getNormalHeaderLayoutCount();
        }
        return 0;
    }

    /**
     * add childView id
     *
     * @param viewId add the child view id   can support childview click
     * @return if you use adapter bind listener
     * @link {(adapter.setOnItemChildClickListener(listener))}
     * <p>
     * or if you can use  recyclerView.addOnItemTouch(listerer)  wo also support this menthod
     */
    @SuppressWarnings("unchecked")
    public YcBaseViewHolder addOnClickListener(@IdRes final int viewId) {
        childClickViewIds.add(viewId);
        final View view = getView(viewId);
        if (view != null) {
            if (!view.isClickable()) {
                view.setClickable(true);
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mYcBaseAdapter.getOnItemChildClickListener() != null) {
                        mYcBaseAdapter.getOnItemChildClickListener().onItemChildClick(mYcBaseAdapter, v, getClickPosition());
                    }
                }
            });
        }

        return this;
    }


    public void setText(int viewId, String text) {
        TextView textView = getView(viewId);
        textView.setText(text);
    }

    public void setText(int viewId, int textId) {
        TextView textView = getView(viewId);
        textView.setText(textId);
    }

    public void setTextColor(int viewId, int colorId) {
        TextView textView = getView(viewId);
        textView.setTextColor(colorId);
    }

    public void setBgResource(int viewId, int resId) {
        View view = getView(viewId);
        view.setBackgroundResource(resId);
    }

    public void setBgColor(int viewId, int colorId) {
        View view = getView(viewId);
        view.setBackgroundColor(colorId);
    }

    public void setVisibility(int viewId, int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
    }
}
