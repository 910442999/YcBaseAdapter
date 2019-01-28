package com.yc.ycbaseadapter.adapter;

import android.content.Context;
import android.os.Build;

import com.yc.YcRecyclerViewBaseAdapter.R;
import com.yc.YcRecyclerViewBaseAdapter.adapter.YcTreeViewBaseAdapter;
import com.yc.YcRecyclerViewBaseAdapter.base.YcBaseViewHolder;
import com.yc.YcRecyclerViewBaseAdapter.bean.TreeNode;

public class YcTreeViewAdapter extends YcTreeViewBaseAdapter<TreeNode> {
    private final int VIEW_TYPE_FOLDER = 1; //文件夹
    private final int VIEW_TYPE_FILE = 2;//文件

    public YcTreeViewAdapter(Context context) {
        super(context);
    }

    @Override
    protected void convert(YcBaseViewHolder holder, TreeNode data, int position, int viewType) {

        if (viewType == VIEW_TYPE_FILE) {
            holder.setText(R.id.file_name, data.getName());
        } else {
            holder.setText(R.id.folder_name, data.getName());
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            holder.itemView.setPaddingRelative(LEVEL_PADDING * data.getLevel(), TOP_PADDING, RIGHT_PADDING, BOTTOM_PADDING);
        } else {
            holder.itemView.setPadding(LEVEL_PADDING * data.getLevel(), TOP_PADDING, RIGHT_PADDING, BOTTOM_PADDING);
        }
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        if (viewType == VIEW_TYPE_FILE) {
            return R.layout.item_file;
        }
        return R.layout.item_folder;
    }

    @Override
    protected int getViewType(int position, TreeNode data) {
        return data.getType().equals("folder") ? VIEW_TYPE_FOLDER : VIEW_TYPE_FILE;
    }
}
