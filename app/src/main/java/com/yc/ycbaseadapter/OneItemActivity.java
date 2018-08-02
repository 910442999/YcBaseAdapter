package com.yc.ycbaseadapter;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yc.YcRecyclerViewBaseAdapter.adapter.YcCommonBaseAdapter;
import com.yc.YcRecyclerViewBaseAdapter.interfaces.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yc.YcRecyclerViewBaseAdapter.adapter.YcBaseAdapter.STATUS_DEFAULT;
import static com.yc.YcRecyclerViewBaseAdapter.adapter.YcBaseAdapter.STATUS_END_GONE;

public class OneItemActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_no_loading)
    TextView mTvNoLoading;
    @BindView(R.id.tv_is_loading)
    TextView mTvIsLoading;
    @BindView(R.id.tv_loadMoreEnd)
    TextView mTvLoadMoreEnd;
    private List<String> data = new ArrayList<>();
    private YcCommonBaseAdapter adapter;
    boolean loadMoreEnd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_item);
        ButterKnife.bind(this);


        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new RefreshAdapter(this);
        //设置 空布局
        adapter.setEmptyView();
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

                    }
                }, 2000);
            }
        });
        View headerView = getHeaderView(0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addHeaderView(getHeaderView(1, getRemoveHeaderListener()), 0);
            }
        });
        adapter.addHeaderView(headerView);

        View footerView = getFooterView(0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addFooterView(getFooterView(1, getRemoveFooterListener()), 0);
            }
        });
        adapter.addFooterView(footerView, 0);

        //延时3s刷新列表
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> data = new ArrayList<>();
                for (int i = 0; i < 1; i++) {
                    data.add("item--" + i);
                }
                //刷新数据
                adapter.setNewData(data);
                adapter.loadMoreEnd(STATUS_END_GONE);
                //                TextView t1 = new TextView(OneItemActivity.this);
                //                t1.setText("我是header-1");
                //                adapter.addHeaderView(t1);
                //                TextView t2 = new TextView(OneItemActivity.this);
                //                t2.setText("我是header-2");
                //                adapter.addHeaderView(t2);
                //                adapter.notifyDataSetChanged();
            }
        }, 2000);


    }

    private View getHeaderView(int type, View.OnClickListener listener) {
        View view = getLayoutInflater().inflate(R.layout.top_view, (ViewGroup) mRecyclerView.getParent(), false);
        TextView textView = (TextView) view.findViewById(R.id.tv_header);
        textView.setText("这是头布局");
        if (type == 1) {
            textView.setText("这是添加头布局");
        }
        view.setOnClickListener(listener);
        return view;
    }

    private View.OnClickListener getRemoveHeaderListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.removeNormalHeaderView(v);
            }
        };
    }

    private View getFooterView(int type, View.OnClickListener listener) {
        View view = getLayoutInflater().inflate(R.layout.top_view, (ViewGroup) mRecyclerView.getParent(), false);
        TextView textView = (TextView) view.findViewById(R.id.tv_header);
        textView.setText("这是脚布局");
        if (type == 1) {
            textView.setText("这是添加脚布局");
        }
        view.setOnClickListener(listener);
        return view;
    }

    private View.OnClickListener getRemoveFooterListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.removeFooterView(v);
            }
        };
    }

    private int mInt = 10;

    /**
     * 获取测试数据
     */
    private void getData() {
        if (mInt < 20) {
            data.clear();
            for (int i = 0; i < 10; i++) {
                data.add("加载条目 : " + i);
            }
            mInt += 10;
            adapter.setLoadMoreData(data);
        } else {
            //            adapter.loadMoreComplete();
            adapter.loadMoreEnd(STATUS_DEFAULT);
        }
    }

    @OnClick({R.id.tv_no_loading, R.id.tv_is_loading, R.id.tv_loadMoreEnd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_no_loading:
                adapter.setEnableLoadMore(false);
                break;
            case R.id.tv_is_loading:
                adapter.setEnableLoadMore(true);
                break;
            case R.id.tv_loadMoreEnd:
                loadMoreEnd = true;
                adapter.loadMoreComplete();
                break;


        }
    }
}
