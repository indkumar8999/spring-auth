package com.myweb.authmagic.models;

import java.util.ArrayList;
import java.util.List;
import com.myweb.authmagic.models.Slot;


public class Doctor {
    private int id;
    private String name;
    private String department;
    private String email;
    private List<Slot> slots;

    public Doctor() {
    }

    public Doctor(int id, String name, String department, String email) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.email = email;
        this.slots = new ArrayList<Slot>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public void setSlots(List<Slot> slots) {
        this.slots = slots;
    }

    @Override
    public String toString() {
        return "Doctor{id=" + id + ", name='" + name + "', department='" + department + "', email='" + email + "'}";
    }
}
