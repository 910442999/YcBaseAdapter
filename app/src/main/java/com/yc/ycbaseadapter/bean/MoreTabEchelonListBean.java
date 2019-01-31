package com.yc.ycbaseadapter.bean;

import java.io.Serializable;
import java.util.List;

public class MoreTabEchelonListBean implements Serializable {
    public List<MoreTabEchelonBean> mMoreTabEchelonBeans;

    public List<MoreTabEchelonBean> getMoreTabEchelonBeans() {
        return mMoreTabEchelonBeans;
    }

    public void setMoreTabEchelonBeans(List<MoreTabEchelonBean> moreTabEchelonBeans) {
        mMoreTabEchelonBeans = moreTabEchelonBeans;
    }
}
