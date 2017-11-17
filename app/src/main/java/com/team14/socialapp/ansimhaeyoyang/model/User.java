package com.team14.socialapp.ansimhaeyoyang.model;

/**
 * Created by dudwn on 2017-11-06.
 */

public class User {
    private String userUID;
    private String userEmail;
    private String userName;
    private String patientName;
    private String patientBirth;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String userUID, String userName, String patientName) {
        this.userUID = userUID;
        this.userName = userName;
        this.patientName = patientName;
    }

    public User(String userUID, String userEmail, String userName, String patientName, String patientBirth) {
        this.userUID = userUID;
        this.userEmail = userEmail;
        this.userName = userName;
        this.patientName = patientName;
        this.patientBirth = patientBirth;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientBirth() {
        return patientBirth;
    }

    public void setPatientBirth(String patientBirth) {
        this.patientBirth = patientBirth;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }
}
