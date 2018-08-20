package com.yc.ycbaseadapter;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yc.YcRecyclerViewBaseAdapter.base.YcBaseViewHolder;
import com.yc.YcRecyclerViewBaseAdapter.interfaces.OnItemClickListener;
import com.yc.YcRecyclerViewBaseAdapter.interfaces.OnLoadMoreListener;
import com.yc.ycbaseadapter.adapter.RefreshAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yc.YcRecyclerViewBaseAdapter.adapter.YcBaseAdapter.STATUS_DEFAULT;

public class RefreshActivity1 extends AppCompatActivity {

    @BindView(R.id.tv_no_loading)
    TextView mTvNoLoading;
    @BindView(R.id.tv_is_loading)
    TextView mTvIsLoading;
    @BindView(R.id.tv_loadMoreEnd)
    TextView mTvLoadMoreEnd;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    private RefreshAdapter mAdapter;

    private boolean isFailed = true;
    private List<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_layout);
        ButterKnife.bind(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(layoutManager);

        //初始化adapter
        mAdapter = new RefreshAdapter(this);
        //初始化EmptyView
        mAdapter.setEmptyView();
        mRecyclerview.setAdapter(mAdapter);
        //设置是否开启加载更多
        mAdapter.setEnableLoadMore(true);

        //设置加载更多的监听
        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                //模拟网络请求加载数据
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadMore();
                        Log.d("test", "load more completed");

                    }
                }, 2000);
            }
        });

        //设置item点击事件监听
        mAdapter.setOnItemClickListener(new OnItemClickListener<String>() {

            @Override
            public void onItemClick(YcBaseViewHolder viewHolder, String data, int position) {
                Toast.makeText(RefreshActivity1.this, data, Toast.LENGTH_SHORT).show();
            }
        });




        //延时3s刷新列表
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < 10; i++) {
                    data.add("item--" + i);
                }
                //刷新数据

                mAdapter.setNewData(data);
                mAdapter.loadMoreComplete();

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
                    for (int i = 0; i < 5; i++) {
                        data.add("item--" + (mAdapter.getDataCount() + i));
                    }
                    //刷新数据
                    mAdapter.setLoadMoreData(data);
                }
            }
        }, 2000);
    }


    @OnClick({R.id.tv_no_loading, R.id.tv_is_loading, R.id.tv_loadMoreEnd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_no_loading:
                mAdapter.setEnableLoadMore(false);
                break;
            case R.id.tv_is_loading:
                mAdapter.setEnableLoadMore(true);
                loadMore();
                break;
            case R.id.tv_loadMoreEnd:

                mAdapter.loadMoreComplete();
                break;


        }
    }
}
