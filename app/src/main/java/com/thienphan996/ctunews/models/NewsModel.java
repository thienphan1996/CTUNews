package com.thienphan996.ctunews.models;

import android.os.Build;

import java.util.Objects;

import androidx.annotation.RequiresApi;

public class NewsModel {
    private String title;
    private String targetUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public NewsModel() {
    }

    public NewsModel(String title, String targetUrl) {
        this.title = title;
        this.targetUrl = targetUrl;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NewsModel)) return false;
        NewsModel newsModel = (NewsModel) o;
        return Objects.equals(title, newsModel.title) ||
                Objects.equals(targetUrl, newsModel.targetUrl);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(title, targetUrl);
    }
}
