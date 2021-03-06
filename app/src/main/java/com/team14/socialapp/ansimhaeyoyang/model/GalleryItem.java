package com.team14.socialapp.ansimhaeyoyang.model;

import com.team14.socialapp.ansimhaeyoyang.model.User;

/**
 * Created by dudwn on 2017-11-17.
 */

public class GalleryItem {

    private String date;
    private String content;
    private String title;
    private String photoPath;
    private User user;
    private String writer;

    public GalleryItem() {
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

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }
}
