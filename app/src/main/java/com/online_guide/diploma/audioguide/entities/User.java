package com.online_guide.diploma.audioguide.entities;

import com.online_guide.diploma.audioguide.constants.BaseField;
import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Unique;

public class User extends SugarRecord {

    @Unique
    private long ID;

    @Unique
    @Column(name = "phoneId")
    private String phoneId;

    @Column(name = "username")
    private String username;

    public User(String phoneId, String username) {
        this.phoneId = phoneId;
        this.username = username;
    }

    public User() {
    }

    public String getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(String phoneId) {
        this.phoneId = phoneId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + ID +
                ", phoneId='" + phoneId + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
