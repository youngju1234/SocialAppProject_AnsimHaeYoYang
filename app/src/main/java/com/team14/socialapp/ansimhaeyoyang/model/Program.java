package com.team14.socialapp.ansimhaeyoyang.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Program implements Serializable {
    private String key;
    private String date;
    private String time;
    private String title;
    private String detail;
    private String managerName;
    private String place;
    private ArrayList<User> users;

    public Program() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "프로그램명 : " + title + '\n' +
                "일시 : " + date + '\n' +
                "시간 : " + time + '\n' +
                "장소 : " + place + '\n' +
                "세부 활동 내용 : " + detail + '\n' +
                "담당자 : " + managerName + '\n';
    }
}
