package com.yc.ycbaseadapter.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 书签父类
 */
public class DataBean implements Serializable {

    /**
     * children : []
     * date_added :
     * date_modified :
     * id : 1
     * name : 书签栏
     * type : folder
     */

    private String date_added;
    private String date_modified;
    private String id;
    private String name;
    private String type;
    private List<ChildrenBean> children;

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ChildrenBean> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenBean> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "date_added='" + date_added + '\'' +
                ", date_modified='" + date_modified + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", children=" + children +
                '}';
    }
}
