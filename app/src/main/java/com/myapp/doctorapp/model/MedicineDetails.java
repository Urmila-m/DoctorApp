package com.myapp.doctorapp.model;

public class MedicineDetails{
    private String patient, doctor, time, medicine;
    private boolean day, morning, night;
    private float rating;

    public MedicineDetails(String patient, String doctor, String time, String medicine, boolean day, boolean morning, boolean night, float rating) {
        this.patient = patient;
        this.doctor = doctor;
        this.time = time;
        this.medicine = medicine;
        this.day = day;
        this.morning = morning;
        this.night = night;
        this.rating = rating;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public boolean isDay() {
        return day;
    }

    public void setDay(boolean day) {
        this.day = day;
    }

    public boolean isMorning() {
        return morning;
    }

    public void setMorning(boolean morning) {
        this.morning = morning;
    }

    public boolean isNight() {
        return night;
    }

    public void setNight(boolean night) {
        this.night = night;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "MedicineDetails{" +
                "patient='" + patient + '\'' +
                ", doctor='" + doctor + '\'' +
                ", time='" + time + '\'' +
                ", medicine='" + medicine + '\'' +
                ", day=" + day +
                ", morning=" + morning +
                ", night=" + night +
                ", rating=" + rating +
                '}';
    }
}
