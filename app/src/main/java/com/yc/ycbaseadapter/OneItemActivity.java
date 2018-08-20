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
import android.widget.Toast;

import com.yc.YcRecyclerViewBaseAdapter.adapter.YcBaseAdapter;
import com.yc.YcRecyclerViewBaseAdapter.interfaces.OnItemChildClickListener;
import com.yc.YcRecyclerViewBaseAdapter.interfaces.OnLoadMoreListener;
import com.yc.ycbaseadapter.adapter.OneItemAdapter;
import com.yc.ycbaseadapter.adapter.RefreshAdapter;

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
    private OneItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_item);
        ButterKnife.bind(this);


        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new OneItemAdapter(this);
        //设置 空布局
        adapter.setEmptyView();
        mRecyclerView.setAdapter(adapter);

        //添加头布局
        View headerView = getHeaderView(0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击添加头布局
                adapter.addHeaderView(getHeaderView(1, getRemoveHeaderListener()), 0);
            }
        });
        //将头布局文件添加到适配器中
        adapter.addHeaderView(headerView);

        //脚布局
        View footerView = getFooterView(0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addFooterView(getFooterView(1, getRemoveFooterListener()), 0);
            }
        });
        //添加脚布局到适配器中
        adapter.addFooterView(footerView, 0);

        //延时3s刷新列表
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> data = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
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


        //添加子view的点击事件
        adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(YcBaseAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.item_btn:
                        Object o = adapter.getAllData().get(position);
                        Toast.makeText(OneItemActivity.this, "当前条目 :" + position + "----" + "条目数据 :" + o.toString(), Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.item_title:
                        Toast.makeText(OneItemActivity.this, "当前条目 :" + position + "----" + "这是title", Toast.LENGTH_SHORT).show();
                        break;


                }

            }
        });

    }

    //添加头布局的方法
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

    //移除头布局的监听
    private View.OnClickListener getRemoveHeaderListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.removeNormalHeaderView(v);
            }
        };
    }

    //添加脚布局的方法
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

    //移除脚布局的监听
    private View.OnClickListener getRemoveFooterListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.removeFooterView(v);
            }
        };
    }

}
