package com.example.manage.Model;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Account {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("tenhienthi")
    @Expose
    private String tenhienthi;
    @SerializedName("type")
    @Expose
    private String type;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTenhienthi() {
        return tenhienthi;
    }

    public void setTenhienthi(String tenhienthi) {
        this.tenhienthi = tenhienthi;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}