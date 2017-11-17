package com.team14.socialapp.ansimhaeyoyang.model;

import com.team14.socialapp.ansimhaeyoyang.model.User;

/**
 * Created by dudwn on 2017-11-17.
 */

public class GalleryItem {

    private int image;
    private String date;
    private String content;
    private String title;
    private String writer;

    public GalleryItem(int image, String date, String content, String title, String writer) {
        this.image = image;
        this.date = date;
        this.content = content;
        this.title = title;
        this.writer = writer;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }
}
