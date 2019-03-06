package com.myapp.doctorapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EmailPasswordResponse implements Serializable {

    @SerializedName("Email")
    private String email;

    @SerializedName("Password")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "EmailPasswordResponse{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
