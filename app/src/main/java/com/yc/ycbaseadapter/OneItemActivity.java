package com.yc.ycbaseadapter;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.yc.YcRecyclerViewBaseAdapter.adapter.YcBaseAdapter;
import com.yc.YcRecyclerViewBaseAdapter.interfaces.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OneItemActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private List<String> data = new ArrayList<>();
    private YcBaseAdapter adapter;
    boolean isLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_item);
        ButterKnife.bind(this);


        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new YcBaseAdapter(this, data);
        mRecyclerView.setAdapter(adapter);
        adapter.setEnableLoadMore(true);
        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getData();
                        Log.d("test", "load more completed");
                        isLoading = false;
                    }
                }, 2000);
            }
        });
        //添加点击事件
        adapter.setOnItemClickListener(new YcBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d("test", "item position = " + position);
                //                adapter.notifyItemRemoved(adapter.getItemCount());
                adapter.setEnableLoadMore(false);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        getData();
    }

    public void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        }, 1500);

    }

    /**
     * 获取测试数据
     */
    private void getData() {
        if (data.size() < 20) {
            for (int i = 0; i < 10; i++) {
                data.add("条目 : " + i);
            }
            adapter.setLoadMoreData(data);
        } else {
            adapter.loadMoreComplete();
        }
    }
}
