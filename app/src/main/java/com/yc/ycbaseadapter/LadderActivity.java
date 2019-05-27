package com.yc.ycbaseadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yc.ycbaseadapter.adapter.LadderAdapter;

import java.util.ArrayList;

public class LadderActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private LadderAdapter mLadderAdapter;
    private ArrayList<String> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ladder);

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

            }
        });

//        LadderLayoutManager layoutManager = new LadderLayoutManager(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mLadderAdapter = new LadderAdapter(this);
        mRecyclerView.setAdapter(mLadderAdapter);
        mLadderAdapter.setNewData(items);
    }
}
