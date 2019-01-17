package com.yc.ycbaseadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yc.YcRecyclerViewBaseAdapter.adapter.YcTreeViewAdapter;
import com.yc.YcRecyclerViewBaseAdapter.base.YcBaseViewHolder;
import com.yc.YcRecyclerViewBaseAdapter.bean.TreeNode;
import com.yc.YcRecyclerViewBaseAdapter.interfaces.OnItemClickListener;
import com.yc.ycbaseadapter.bean.ChildrenBean;
import com.yc.ycbaseadapter.bean.DataBean;

import java.util.ArrayList;
import java.util.List;

public class TreeViewItemActivity extends AppCompatActivity implements OnItemClickListener<TreeNode> {
    private List<TreeNode> mTreeNodeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_view_item);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        mTreeNodeList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        YcTreeViewAdapter ycTreeViewAdapter = new YcTreeViewAdapter(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(ycTreeViewAdapter);

        List<ChildrenBean> data = getData();
        getHandleData(0, data);
        Log.e("tag", new Gson().toJson(mTreeNodeList));
        ycTreeViewAdapter.setNewData(mTreeNodeList);
        ycTreeViewAdapter.setOnItemClickListener(this);
    }

    private List<ChildrenBean> getData() {
        String data = "{\n" +
                "\t\"children\": [{\n" +
                "\t\t\"date_added\": \"1547196270572\",\n" +
                "\t\t\"id\": \"2\",\n" +
                "\t\t\"name\": \"冬天\",\n" +
                "\t\t\"type\": \"url\",\n" +
                "\t\t\"url\": \"\"\n" +
                "\t}, {\n" +
                "\t\t\"date_added\": \"1547200085709\",\n" +
                "\t\t\"id\": \"3\",\n" +
                "\t\t\"name\": \"沙滩\",\n" +
                "\t\t\"type\": \"url\",\n" +
                "\t\t\"url\": \"\"\n" +
                "\t}, {\n" +
                "\t\t\"children\": [{\n" +
                "\t\t\t\"children\": [{\n" +
                "\t\t\t\t\"children\": [{\n" +
                "\t\t\t\t\t\"children\": [{\n" +
                "\t\t\t\t\t\t\"children\": [{\n" +
                "\t\t\t\t\t\t\t\"children\": [{\n" +
                "\t\t\t\t\t\t\t\t\"children\": [{\n" +
                "\t\t\t\t\t\t\t\t\t\"children\": [{\n" +
                "\t\t\t\t\t\t\t\t\t\t\"children\": [{\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\"children\": [{\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\"children\": [],\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\"date_added\": \"13192163882299214\",\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\"date_modified\": \"13192163882299221\",\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\"id\": \"14\",\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\"name\": \"继续看辛苦辛苦的\",\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\"type\": \"folder\"\n" +
                "\t\t\t\t\t\t\t\t\t\t\t}],\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\"date_added\": \"13192163875396489\",\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\"date_modified\": \"13192163875396493\",\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\"id\": \"13\",\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\"name\": \"聚聚绿绿旅客\",\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\"type\": \"folder\"\n" +
                "\t\t\t\t\t\t\t\t\t\t}],\n" +
                "\t\t\t\t\t\t\t\t\t\t\"date_added\": \"13192163870814762\",\n" +
                "\t\t\t\t\t\t\t\t\t\t\"date_modified\": \"13192163870814770\",\n" +
                "\t\t\t\t\t\t\t\t\t\t\"id\": \"12\",\n" +
                "\t\t\t\t\t\t\t\t\t\t\"name\": \"继续继续开心开心看到\",\n" +
                "\t\t\t\t\t\t\t\t\t\t\"type\": \"folder\"\n" +
                "\t\t\t\t\t\t\t\t\t}],\n" +
                "\t\t\t\t\t\t\t\t\t\"date_added\": \"13192163866136170\",\n" +
                "\t\t\t\t\t\t\t\t\t\"date_modified\": \"13192163866136177\",\n" +
                "\t\t\t\t\t\t\t\t\t\"id\": \"11\",\n" +
                "\t\t\t\t\t\t\t\t\t\"name\": \"继续开心开心开心\",\n" +
                "\t\t\t\t\t\t\t\t\t\"type\": \"folder\"\n" +
                "\t\t\t\t\t\t\t\t}],\n" +
                "\t\t\t\t\t\t\t\t\"date_added\": \"13192163861169658\",\n" +
                "\t\t\t\t\t\t\t\t\"date_modified\": \"13192163861169662\",\n" +
                "\t\t\t\t\t\t\t\t\"id\": \"10\",\n" +
                "\t\t\t\t\t\t\t\t\"name\": \"继续凯旋快餐拆开看\",\n" +
                "\t\t\t\t\t\t\t\t\"type\": \"folder\"\n" +
                "\t\t\t\t\t\t\t}],\n" +
                "\t\t\t\t\t\t\t\"date_added\": \"13192163832248713\",\n" +
                "\t\t\t\t\t\t\t\"date_modified\": \"13192163832248719\",\n" +
                "\t\t\t\t\t\t\t\"id\": \"9\",\n" +
                "\t\t\t\t\t\t\t\"name\": \"京东快递上课\",\n" +
                "\t\t\t\t\t\t\t\"type\": \"folder\"\n" +
                "\t\t\t\t\t\t}],\n" +
                "\t\t\t\t\t\t\"date_added\": \"13192163826835741\",\n" +
                "\t\t\t\t\t\t\"date_modified\": \"13192163826835747\",\n" +
                "\t\t\t\t\t\t\"id\": \"8\",\n" +
                "\t\t\t\t\t\t\"name\": \"减肥减肥经典款\",\n" +
                "\t\t\t\t\t\t\"type\": \"folder\"\n" +
                "\t\t\t\t\t}],\n" +
                "\t\t\t\t\t\"date_added\": \"13192163819318985\",\n" +
                "\t\t\t\t\t\"date_modified\": \"13192163819318992\",\n" +
                "\t\t\t\t\t\"id\": \"7\",\n" +
                "\t\t\t\t\t\"name\": \"记得记得酷酷的\",\n" +
                "\t\t\t\t\t\"type\": \"folder\"\n" +
                "\t\t\t\t}],\n" +
                "\t\t\t\t\"date_added\": \"13192163813564015\",\n" +
                "\t\t\t\t\"date_modified\": \"13192163813564018\",\n" +
                "\t\t\t\t\"id\": \"6\",\n" +
                "\t\t\t\t\"name\": \"你从哪乡宁县的吗\",\n" +
                "\t\t\t\t\"type\": \"folder\"\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"date_added\": \"13192185116269893\",\n" +
                "\t\t\t\t\"id\": \"31\",\n" +
                "\t\t\t\t\"name\": \"近近景近景家\",\n" +
                "\t\t\t\t\"type\": \"url\",\n" +
                "\t\t\t\t\"url\": \"\"\n" +
                "\t\t\t}],\n" +
                "\t\t\t\"date_added\": \"13192163808224794\",\n" +
                "\t\t\t\"date_modified\": \"13192185116269893\",\n" +
                "\t\t\t\"id\": \"5\",\n" +
                "\t\t\t\"name\": \"继续开心开心\",\n" +
                "\t\t\t\"type\": \"folder\"\n" +
                "\t\t}, {\n" +
                "\t\t\t\"date_added\": \"13192185127697059\",\n" +
                "\t\t\t\"id\": \"32\",\n" +
                "\t\t\t\"name\": \"积极\",\n" +
                "\t\t\t\"type\": \"url\",\n" +
                "\t\t\t\"url\": \"\"\n" +
                "\t\t}],\n" +
                "\t\t\"date_added\": \"13192163797345624\",\n" +
                "\t\t\"date_modified\": \"13192185127697059\",\n" +
                "\t\t\"id\": \"4\",\n" +
                "\t\t\"name\": \"记得记得就\",\n" +
                "\t\t\"type\": \"folder\"\n" +
                "\t}, {\n" +
                "\t\t\"children\": [{\n" +
                "\t\t\t\"children\": [{\n" +
                "\t\t\t\t\"children\": [{\n" +
                "\t\t\t\t\t\"children\": [],\n" +
                "\t\t\t\t\t\"date_added\": \"13192185062302216\",\n" +
                "\t\t\t\t\t\"date_modified\": \"13192185062302224\",\n" +
                "\t\t\t\t\t\"id\": \"28\",\n" +
                "\t\t\t\t\t\"name\": \"继续继续家\",\n" +
                "\t\t\t\t\t\"type\": \"folder\"\n" +
                "\t\t\t\t}, {\n" +
                "\t\t\t\t\t\"children\": [],\n" +
                "\t\t\t\t\t\"date_added\": \"13192185066201114\",\n" +
                "\t\t\t\t\t\"date_modified\": \"13192185066201120\",\n" +
                "\t\t\t\t\t\"id\": \"29\",\n" +
                "\t\t\t\t\t\"name\": \"那些那些那些\",\n" +
                "\t\t\t\t\t\"type\": \"folder\"\n" +
                "\t\t\t\t}, {\n" +
                "\t\t\t\t\t\"children\": [],\n" +
                "\t\t\t\t\t\"date_added\": \"13192185069355673\",\n" +
                "\t\t\t\t\t\"date_modified\": \"13192185069355682\",\n" +
                "\t\t\t\t\t\"id\": \"30\",\n" +
                "\t\t\t\t\t\"name\": \"继续坚持就行\",\n" +
                "\t\t\t\t\t\"type\": \"folder\"\n" +
                "\t\t\t\t}, {\n" +
                "\t\t\t\t\t\"date_added\": \"13192185163072207\",\n" +
                "\t\t\t\t\t\"id\": \"35\",\n" +
                "\t\t\t\t\t\"name\": \"咪咕\",\n" +
                "\t\t\t\t\t\"type\": \"url\",\n" +
                "\t\t\t\t\t\"url\": \"\"\n" +
                "\t\t\t\t}],\n" +
                "\t\t\t\t\"date_added\": \"13192185051460875\",\n" +
                "\t\t\t\t\"date_modified\": \"13192185163072207\",\n" +
                "\t\t\t\t\"id\": \"26\",\n" +
                "\t\t\t\t\"name\": \"记得记得就\",\n" +
                "\t\t\t\t\"type\": \"folder\"\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"children\": [],\n" +
                "\t\t\t\t\"date_added\": \"13192185057688457\",\n" +
                "\t\t\t\t\"date_modified\": \"13192185057688464\",\n" +
                "\t\t\t\t\"id\": \"27\",\n" +
                "\t\t\t\t\"name\": \"记得记得就姐姐\",\n" +
                "\t\t\t\t\"type\": \"folder\"\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"date_added\": \"13192185138051502\",\n" +
                "\t\t\t\t\"id\": \"33\",\n" +
                "\t\t\t\t\"name\": \"北京交警\",\n" +
                "\t\t\t\t\"type\": \"url\",\n" +
                "\t\t\t\t\"url\": \"\"\n" +
                "\t\t\t}],\n" +
                "\t\t\t\"date_added\": \"13192185042717545\",\n" +
                "\t\t\t\"date_modified\": \"13192185138051502\",\n" +
                "\t\t\t\"id\": \"24\",\n" +
                "\t\t\t\"name\": \"记得记得就\",\n" +
                "\t\t\t\"type\": \"folder\"\n" +
                "\t\t}, {\n" +
                "\t\t\t\"children\": [{\n" +
                "\t\t\t\t\"date_added\": \"13192185149129148\",\n" +
                "\t\t\t\t\"id\": \"34\",\n" +
                "\t\t\t\t\"name\": \"空空\",\n" +
                "\t\t\t\t\"type\": \"url\",\n" +
                "\t\t\t\t\"url\": \"\"\n" +
                "\t\t\t}],\n" +
                "\t\t\t\"date_added\": \"13192185046913198\",\n" +
                "\t\t\t\"date_modified\": \"13192185149129148\",\n" +
                "\t\t\t\"id\": \"25\",\n" +
                "\t\t\t\"name\": \"继续坚持家\",\n" +
                "\t\t\t\"type\": \"folder\"\n" +
                "\t\t}],\n" +
                "\t\t\"date_added\": \"13192163802094807\",\n" +
                "\t\t\"date_modified\": \"13192163802094813\",\n" +
                "\t\t\"id\": \"15\",\n" +
                "\t\t\"name\": \"年休假咸鸡蛋加\",\n" +
                "\t\t\"type\": \"folder\"\n" +
                "\t}],\n" +
                "\t\"date_added\": \"13190978161399841\",\n" +
                "\t\"date_modified\": \"13191576899184235\",\n" +
                "\t\"id\": \"1\",\n" +
                "\t\"name\": \"收藏栏\",\n" +
                "\t\"type\": \"folder\"\n" +
                "\n" +
                "}";
        DataBean dataBean = new Gson().fromJson(data, DataBean.class);
        String name = dataBean.getName();
        List<ChildrenBean> children = dataBean.getChildren();
        return children;
    }

    private void getHandleData(int level, List<ChildrenBean> childrenBeanList) {
        for (int i = 0; i < childrenBeanList.size(); i++) {
            //先判断是否是文件夹,不是的话直接添加文件
            ChildrenBean newChildrenBean = childrenBeanList.get(i);
            if ("folder".equals(newChildrenBean.getType())) {
                TreeNode treeNode = new TreeNode();
                treeNode.setName(newChildrenBean.getName());
                treeNode.setType(newChildrenBean.getType());
                treeNode.setLevel(level);
                mTreeNodeList.add(treeNode);
                List<ChildrenBean> children = newChildrenBean.getChildren();
                //说明子类文件夹下面还有文件夹 递归进入
                if (children != null && children.size() > 0) {
                    getHandleData(level + 1, children);
                    //                    treeNode.setChildren(newChildrenChildrenBeans);
                }
            } else {
                TreeNode treeNode = new TreeNode();
                treeNode.setName(newChildrenBean.getName());
                treeNode.setType(newChildrenBean.getType());
                treeNode.setLevel(level);
                //添加新的数据到 父类集合
                mTreeNodeList.add(treeNode);
            }
        }
    }

    @Override
    public void onItemClick(YcBaseViewHolder viewHolder, TreeNode data, int position) {
        Toast.makeText(this, data.getName(), Toast.LENGTH_SHORT).show();
    }
}
