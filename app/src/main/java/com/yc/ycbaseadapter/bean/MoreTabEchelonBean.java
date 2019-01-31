package com.yc.ycbaseadapter.bean;

import java.io.Serializable;

public class MoreTabEchelonBean implements Serializable {
    boolean isHomePage;
    String title;
    String icon;
    String bmImage;

    public boolean isHomePage() {
        return isHomePage;
    }

    public void setHomePage(boolean homePage) {
        isHomePage = homePage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBmImage() {
        return bmImage;
    }

    public void setBmImage(String bmImage) {
        this.bmImage = bmImage;
    }
}
