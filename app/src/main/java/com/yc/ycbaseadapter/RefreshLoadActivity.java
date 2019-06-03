package com.yc.ycbaseadapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.yc.ycbaseadapter.adapter.RefreshAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

public class RefreshLoadActivity extends AppCompatActivity {
    private RefreshAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_load_layout);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, VERTICAL));
        recyclerView.setNestedScrollingEnabled(false);//禁止滑动
        mAdapter = new RefreshAdapter(this);
        mAdapter.setEmptyView();
        recyclerView.setAdapter(mAdapter);
        RefreshLayout refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setEnableAutoLoadMore(false);//是否启用列表惯性滑动到底部时自动加载更多
        refreshLayout.setEnableNestedScroll(false);//是否启用嵌套滚动
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //                        mAdapter.addData(buildItems());
                        refreshLayout.finishLoadMore();
                    }
                }, 2000);
            }
        });

        mAdapter.setNewData(buildItems());
    }

    private List<String> buildItems() {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            items.addAll(Arrays.asList("条目 " + i));
        }
        return items;
    }

}
