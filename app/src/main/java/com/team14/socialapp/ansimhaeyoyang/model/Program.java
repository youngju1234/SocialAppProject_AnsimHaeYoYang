package com.team14.socialapp.ansimhaeyoyang.model;


/**
 * Created by YB on 2017-12-12.
 */

public class Program {
    private String date;
    private String time;
    private String program_name;
    private int num;
    private String content;
    private String manager;
    private String subject;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    public Program() {
    }
    public Program(String date, String time, String program_name, int num) {
        this.date = date;
        this.time = time;
        this.program_name = program_name;
        this.num = num;
    }

    public Program(String date, String time, String program_name, int num, String content, String manager, String subject) {
        this.date = date;
        this.time = time;
        this.program_name = program_name;
        this.num = num;
        this.content = content;
        this.manager = manager;
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setProgram_name(String program_name) {
        this.program_name = program_name;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTime() {

        return time;
    }

    public String getProgram_name() {
        return program_name;
    }

    public int getNum() {
        return num;
    }
}
