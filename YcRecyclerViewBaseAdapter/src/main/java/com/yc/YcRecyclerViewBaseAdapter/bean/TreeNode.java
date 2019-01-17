package com.yc.YcRecyclerViewBaseAdapter.bean;

import android.os.Bundle;

import java.util.List;

public class TreeNode {
    private String type; //文件类型
    private String id; //文件id
    private String name; //文件名字
    private Bundle mBundle; //用来存放自定义的数据
    private int level; //层级偏移的距离
    private List<TreeNode> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Bundle getBundle() {
        return mBundle;
    }

    public void setBundle(Bundle bundle) {
        mBundle = bundle;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }
}
