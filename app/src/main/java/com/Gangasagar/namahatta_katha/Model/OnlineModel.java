package com.Gangasagar.namahatta_katha.Model;

public class OnlineModel {
    String icon, title,url,con,h1;


    public OnlineModel() {
    }

    public OnlineModel(String icon, String title, String url, String con, String h1) {
        this.icon = icon;
        this.title = title;
        this.url = url;
        this.con = con;
        this.h1 = h1;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }

    public String getH1() {
        return h1;
    }

    public void setH1(String h1) {
        this.h1 = h1;
    }
}
