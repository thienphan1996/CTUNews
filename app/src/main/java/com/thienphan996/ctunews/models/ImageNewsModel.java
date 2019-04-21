package com.thienphan996.ctunews.models;

import android.graphics.Bitmap;

public class ImageNewsModel {
    private String title;
    private String imgUrl;
    private String targetUrl;
    private String content;
    private Bitmap imgBitmap;
    private int actionMode;

    public ImageNewsModel(String title, String imgUrl, String targetUrl, String content, Bitmap imgBitmap) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.targetUrl = targetUrl;
        this.content = content;
        this.imgBitmap = imgBitmap;
    }

    public ImageNewsModel() {
    }

    public int getActionMode() {
        return actionMode;
    }

    public void setActionMode(int actionMode) {
        this.actionMode = actionMode;
    }

    public Bitmap getImgBitmap() {
        return imgBitmap;
    }

    public void setImgBitmap(Bitmap imgBitmap) {
        this.imgBitmap = imgBitmap;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
