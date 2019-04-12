package com.yc.ycbaseadapter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.yc.YcRecyclerViewBaseAdapter.view.OverFlyingLayoutManager;
import com.yc.ycbaseadapter.adapter.MoreTypeItemAdapter;
import com.yc.ycbaseadapter.adapter.VertialAdatpter;
import com.yc.ycbaseadapter.bean.MoreTabEchelonBean;
import com.yc.yclibrary.YcImageUtils;
import com.yc.yclibrary.YcScreenCaptureUtils;
import com.yc.yclibrary.YcToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 折叠梯形布局2
 */
public class OverFlyingActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    //    private MoreTypeItemAdapter mMoreTypeItemAdapter;
    private VertialAdatpter mVertialAdatpter;
    private List<MoreTabEchelonBean> mMoreTabEchelonBeans;
    private int position;
    private ArrayList<String> items = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertial);
        //构建一个临时数据源
        for (int i = 0; i < 10; i++) {
            items.add("Item:第" + i + "项");
        }
        mRecyclerView = findViewById(R.id.recyclerView);
        final LinearLayout ll_content = findViewById(R.id.ll_content);
        TextView add = findViewById(R.id.add);
        TextView vertial1 = findViewById(R.id.vertial1);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //                position++;
                //                Bitmap bitmap = YcScreenCaptureUtils.loadBitmapFromView(ll_content);
                //                MoreTabEchelonBean moreTabEchelonBean = new MoreTabEchelonBean();
                //                moreTabEchelonBean.setTitle("标题" + position);
                //                moreTabEchelonBean.setIcon("http://www.baidu.com");
                //                moreTabEchelonBean.setHomePage(true);
                //                saveMoreTabBitmap(String.valueOf(System.currentTimeMillis()), false, bitmap, moreTabEchelonBean);
                //                mMoreTabEchelonBeans.add(moreTabEchelonBean);
                //                mMoreTypeItemAdapter.setNewData(mMoreTabEchelonBeans);
                //                mRecyclerView.scrollToPosition(mMoreTabEchelonBeans.size() - 1);
                //                mMoreTypeItemAdapter.notifyDataSetChanged();
            }
        });
        TextView remove = findViewById(R.id.remove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YcToastUtils.normal(OverFlyingActivity.this, "侧滑啊").show();
            }
        });


        OverFlyingLayoutManager layoutManager = new OverFlyingLayoutManager(OrientationHelper.VERTICAL, true);
        mRecyclerView.setLayoutManager(layoutManager);

        //        mMoreTypeItemAdapter = new MoreTypeItemAdapter(this);
        //        mRecyclerView.setAdapter(mMoreTypeItemAdapter);

        mVertialAdatpter = new VertialAdatpter(this);
        mRecyclerView.setAdapter(mVertialAdatpter);
        mVertialAdatpter.setNewData(items);
        mMoreTabEchelonBeans = new ArrayList<>();
        //滑动移除条目和数据
        removeItem();

    }

    private void removeItem() {
        ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(1, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //滑动时的一些操作
                return true;
            }

            /**
             * 处理滑动事件回调
             *
             * @param viewHolder
             * @param direction
             */
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int pos = viewHolder.getAdapterPosition();
//                YcImageUtils.removeLruCacheBitmap(mMoreTabEchelonBeans.get(pos).getBmImage());
//                List<MoreTabEchelonBean> allData = mMoreTypeItemAdapter.getAllData();
//                mMoreTabEchelonBeans.remove(pos);
//                allData.remove(pos);
//                mMoreTypeItemAdapter.notifyDataSetChanged();


            }

            //处理动画
            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                int adapterPosition = viewHolder.getAdapterPosition();
                //判断是否是第一条 ,禁止滑动删除
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    //设置滑动条目是视图跟随手指滑动 ， 否则条目静止状态
                    viewHolder.itemView.setTranslationX(dX);
                } else {
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            }
        });
        touchHelper.attachToRecyclerView(mRecyclerView);
    }

    public void saveMoreTabBitmap(String bmName, boolean isRemoveOld, Bitmap bitmap, MoreTabEchelonBean moreTabEchelonBean) {
        if (isRemoveOld) {
            YcImageUtils.removeLruCacheBitmap(bmName);
        }
        YcImageUtils.saveLruCacheBitmap(bmName, bitmap);
        moreTabEchelonBean.setBmImage(bmName);
    }

}
