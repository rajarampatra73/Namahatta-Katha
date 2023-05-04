package com.Gangasagar.namahatta_katha.Model;

public class NotiModel {
    String title, desc, img;

    public NotiModel() {
    }

    public NotiModel(String title, String desc, String img) {
        this.title = title;
        this.desc = desc;
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
