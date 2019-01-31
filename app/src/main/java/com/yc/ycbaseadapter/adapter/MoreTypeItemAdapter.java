package com.yc.ycbaseadapter.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.yc.YcRecyclerViewBaseAdapter.adapter.YcCommonBaseAdapter;
import com.yc.YcRecyclerViewBaseAdapter.base.YcBaseViewHolder;
import com.yc.ycbaseadapter.R;
import com.yc.ycbaseadapter.bean.MoreTabEchelonBean;
import com.yc.yclibrary.YcImageUtils;

public class MoreTypeItemAdapter extends YcCommonBaseAdapter<MoreTabEchelonBean> {
    public MoreTypeItemAdapter(Context context) {
        super(context);
    }

    @Override
    protected void convert(YcBaseViewHolder holder, MoreTabEchelonBean data, int position, int viewType) {
        final boolean homePage = data.isHomePage();
        if (homePage) {
            holder.setText(R.id.tv_text, "首页");
            ImageView imageView = (ImageView) holder.getView(R.id.iv_icon);
            imageView.setImageResource(R.drawable.home_icon);
            holder.setVisibility(R.id.iv_remove, View.GONE);

        } else {
            holder.setVisibility(R.id.iv_remove, View.VISIBLE);
            holder.setText(R.id.tv_text, data.getTitle());
            ImageView imageView = (ImageView) holder.getView(R.id.iv_icon);
            imageView.setImageResource(R.drawable.ic_file);

        }

        String bmImage = data.getBmImage();
        Bitmap bitmap = YcImageUtils.getLruCacheBitmap(bmImage);
        ImageView imageView = (ImageView) holder.getView(R.id.img_bg);
        imageView.setImageBitmap(bitmap);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_more_tab_echelon;
    }
}
