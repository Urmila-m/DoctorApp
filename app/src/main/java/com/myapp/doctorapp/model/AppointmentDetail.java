package com.myapp.doctorapp.model;

public class AppointmentDetail {
    String doctor;
    String appointment_date;
    String appointment_time;

    public String getAppointment_time() {
        return appointment_time;
    }

    public void setAppointment_time(String appointment_time) {
        this.appointment_time = appointment_time;
    }

    public AppointmentDetail(String doctor, String appointment_date, String appointment_time) {
        this.doctor = doctor;
        this.appointment_date = appointment_date;
        this.appointment_time=appointment_time;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getAppointment_date() {
        return appointment_date;
    }

    public void setAppointment_date(String appointment_date) {
        this.appointment_date = appointment_date;
    }

    @Override
    public String toString() {
        return "AppointmentDetail{" +
                "doctor='" + doctor + '\'' +
                ", appointment_date='" + appointment_date + '\'' +
                ", appointment_time='"+appointment_time+'\''+
                '}';
    }
}
