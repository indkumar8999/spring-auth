package com.myweb.authmagic.models;

public class Appointment {
    private int id;
    private String date;
    private String time;
    public int doctor_id;
    private String patient;
    
    public Appointment() {
    }

    public Appointment(int id, String date, String time, int doctor_id, String patient) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.doctor_id = doctor_id;
        this.patient = patient;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getDoctorId() {
        return doctor_id;
    }

    public String getPatient() {
        return patient;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDoctorId(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }
}