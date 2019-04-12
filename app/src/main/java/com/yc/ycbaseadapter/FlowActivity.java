package com.yc.ycbaseadapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.yc.YcRecyclerViewBaseAdapter.utils.SpaceItemDecoration;
import com.yc.YcRecyclerViewBaseAdapter.view.FlowLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 流失布局
 */
public class FlowActivity extends AppCompatActivity {

    private List<FlowItemBean> list = new ArrayList<>();

    private FlowAdapter flowAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flow_layout);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.flow);
        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        recyclerView.addItemDecoration(new SpaceItemDecoration(dp2px(10)));
        recyclerView.setLayoutManager(flowLayoutManager);

        list.add(new FlowItemBean("1.发士大夫"));
        list.add(new FlowItemBean("2.的风格单方事故是电饭锅水电费的风格单方事故是电饭锅水电费"));
        list.add(new FlowItemBean("3.士大夫-C"));
        list.add(new FlowItemBean("4.大是大非"));
        list.add(new FlowItemBean("5.的分公司电饭锅"));
        list.add(new FlowItemBean("6.的风格单方事故是电饭锅水电费的风格单方事故是电饭锅水电费的风格单方事故是电饭锅水电费的风格单方事故是电饭锅水电费"));
        list.add(new FlowItemBean("7.官"));
        list.add(new FlowItemBean("8.dsfg"));
        list.add(new FlowItemBean("9.电饭锅和规范更好"));
        list.add(new FlowItemBean("10.发"));

        recyclerView.setAdapter(flowAdapter = new FlowAdapter(list));

    }

    class FlowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<FlowItemBean> list;

        public FlowAdapter(List<FlowItemBean> list) {
            this.list = list;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyHolder(View.inflate(FlowActivity.this, R.layout.flow_item, null));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            TextView textView = ((MyHolder) holder).text;
            textView.setText(list.get(position).label);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(FlowActivity.this, list.get(position).label, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {

            private TextView text;

            public MyHolder(View itemView) {
                super(itemView);
                text = (TextView) itemView.findViewById(R.id.flow_text);
            }
        }
    }

    private int dp2px(float value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
    }
}
