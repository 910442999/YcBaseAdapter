package com.yc.YcRecyclerViewBaseAdapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * base viewHolder
 */
public class YcBaseViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    private View mConvertView;

    public YcBaseViewHolder(View itemView) {
        super(itemView);
        mConvertView = itemView;
        mViews = new SparseArray<>();
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
