package com.team14.socialapp.ansimhaeyoyang.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Program implements Serializable {
    private String key;
    private String date;
    private String time;
    private String title;
    private String detail;
    private String managerName;
    private String place;
    private ArrayList<User> participants;

    public Program() {
    }

    public Program(String date, String time, String title, String detail, String managerName, String place, ArrayList<User> participants) {
        this.date = date;
        this.time = time;
        this.title = title;
        this.detail = detail;
        this.managerName = managerName;
        this.place = place;
        this.participants = participants;
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

    public ArrayList<User> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<User> participants) {
        this.participants = participants;
    }
}
