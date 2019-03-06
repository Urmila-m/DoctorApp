package com.myapp.doctorapp.model;

import java.io.Serializable;

public class Doctor implements Serializable {

    private String name, image, hospital, speciality;
    private int id, rating;

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Doctor(String name, String image, String hospital, String speciality, int id, int rating) {
        this.name = name;
        this.image = image;
        this.hospital = hospital;
        this.speciality = speciality;
        this.id = id;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", hospital='" + hospital + '\'' +
                ", speciality='" + speciality + '\'' +
                ", id=" + id +
                ", rating=" + rating +
                '}';
    }

}
