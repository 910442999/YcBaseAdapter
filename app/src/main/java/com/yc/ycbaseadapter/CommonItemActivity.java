package com.yc.ycbaseadapter;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.yc.YcRecyclerViewBaseAdapter.base.YcBaseViewHolder;
import com.yc.YcRecyclerViewBaseAdapter.interfaces.OnItemClickListener;
import com.yc.YcRecyclerViewBaseAdapter.interfaces.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import static com.yc.YcRecyclerViewBaseAdapter.adapter.YcBaseAdapter.STATUS_DEFAULT;

public class CommonItemActivity extends AppCompatActivity {

    private CommonRefreshAdapter mAdapter;

    private RecyclerView mRecyclerView;

    private boolean isFailed = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        //初始化adapter
        mAdapter = new CommonRefreshAdapter(this);

        //        //初始化EmptyView

        mAdapter.setEmptyView();

        //        //初始化 开始加载更多的loading View
        //        mAdapter.setLoadingView(R.layout.load_loading_layout);
        //        //加载失败，更新footer view提示
        //        mAdapter.setLoadFailedView(R.layout.load_failed_layout);
        //        //加载完成，更新footer view提示
        //        mAdapter.setLoadEndView(R.layout.load_end_layout);

        mAdapter.setEnableLoadMore(true);
        //设置加载更多触发的事件监听
        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                loadMore();
            }
        });

        //设置item点击事件监听
        mAdapter.setOnItemClickListener(new OnItemClickListener<String>() {

            @Override
            public void onItemClick(YcBaseViewHolder viewHolder, String data, int position) {
                Toast.makeText(CommonItemActivity.this, data, Toast.LENGTH_SHORT).show();
                mAdapter.setEnableLoadMore(false);
            }
        });


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(mAdapter);


        //延时3s刷新列表
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> data = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    data.add("item--" + i);
                }
                //刷新数据
                mAdapter.setNewData(data);

                //                TextView t1 = new TextView(CommonItemActivity.this);
                //                t1.setText("我是header-1");
                //                mAdapter.addHeaderView(t1);
                //                TextView t2 = new TextView(CommonItemActivity.this);
                //                t2.setText("我是header-2");
                //                mAdapter.addHeaderView(t2);
            }
        }, 2000);
    }


    private void loadMore() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (mAdapter.getItemCount() >= 10 && isFailed) {
                    isFailed = false;
                    mAdapter.loadFailed();
                } else if (mAdapter.getItemCount() >= 20) {
                    mAdapter.loadMoreEnd(STATUS_DEFAULT);
                } else {
                    final List<String> data = new ArrayList<>();
                    for (int i = 0; i < 3; i++) {
                        data.add("item--" + (mAdapter.getDataCount() + i));
                    }
                    //刷新数据
                    mAdapter.setLoadMoreData(data);
                }
            }
        }, 2000);
    }
}
