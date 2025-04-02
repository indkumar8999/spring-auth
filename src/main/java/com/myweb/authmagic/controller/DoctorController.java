package com.myweb.authmagic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.myweb.authmagic.models.Doctor;
import com.myweb.authmagic.models.Slot;
import com.myweb.authmagic.db.DbConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.List;

@Controller
public class DoctorController {

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/home")
    public String Home(Model model) {
        return "home";
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/alldoctors")
    public String getAllDoctorDetails(@RequestParam String patientName, 
                                    @RequestParam String eventDateTime, Model model) {

        List<Doctor> doctorList = new ArrayList<Doctor>();
        try{
            Statement statement = DbConnection.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM doctors");
            while(rs.next()){
                Doctor doctor = new Doctor();
                doctor.setId(rs.getInt("id"));
                doctor.setName(rs.getString("name"));
                doctor.setDepartment(rs.getString("department"));
                doctor.setEmail(rs.getString("email"));
                doctorList.add(doctor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Doctor doctor : doctorList) {
            List<Slot> allSlots = new ArrayList<Slot>();
            for (int i = 9; i<=17 ; i++) {
                if(i < 12 || (i >= 14 && i<=17)){
                    Slot slot = new Slot();
                    slot.setTime(String.valueOf(i) + ":00");
                    allSlots.add(slot);
                }
            }
            doctor.setSlots(allSlots);
        }

        try{
            Statement statement = DbConnection.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM appointments WHERE date = '" + eventDateTime + "'");
            while(rs.next()){
                for (Doctor doctor : doctorList) {
                    if (doctor.getId() == rs.getInt("doctor_id")) {
                        String existingSlot = rs.getString("time");
                        for (Slot slot : doctor.getSlots()) {
                            if (slot.getTime().equals(existingSlot)) {
                                doctor.getSlots().remove(slot);
                                break;
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        model.addAttribute("eventDateTime", eventDateTime);
        model.addAttribute("patientName", patientName);
        model.addAttribute("users", doctorList);
        // model.addAttribute("slots", allSlots);
        return "all-doctors";
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/doctor/{id}")
    public String getDoctorsSlots(@PathVariable int id, Model model) {
        try {
            Statement statement = DbConnection.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM doctors where id = " + id + " LIMIT 1");
            Doctor doctor = new Doctor();
            while(rs.next()){
                doctor.setId(rs.getInt("id"));
                doctor.setName(rs.getString("name"));
                doctor.setDepartment(rs.getString("department"));
                doctor.setEmail(rs.getString("email"));
            }
            model.addAttribute("doctor", doctor);
        }catch (Exception e) {
            model.addAttribute("errorMessage", "Doctor not found");
            e.printStackTrace();
            return "doctor-slots";
        }

        List<Slot> allSlots = new ArrayList<Slot>();
        for (int i = 9; i<=17 ; i++) {
            if (i < 12 || (i >= 14 && i<=17)) {
                Slot slot = new Slot();
                slot.setTime(String.valueOf(i) + ":00");
                allSlots.add(slot);
            }  
        }
        model.addAttribute("slots", allSlots);
        return "doctor-slots";
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/doctor/{id}")
    public String bookDoctorsSlots(@PathVariable String id,
                                    @RequestParam(required = false) List<String> selectedSlots,
                                    @RequestParam(required = false) String email,
                                    @RequestParam(required = false) String eventDateTime,
                                    @RequestParam(required = false) String patientName,
                                    Model model) {
        System.out.println("Booking slot for doctor with id: " + id);
        System.out.println("Selected slots: " + selectedSlots);
        System.out.println("Email: " + email);
        System.out.println("eventDateTime: " + eventDateTime);
        System.out.println("patientName: " + patientName);

        try{
            for (String slot : selectedSlots) {
                PreparedStatement stmt = DbConnection.getConnection().prepareStatement("INSERT INTO appointments(doctor_id, date, time, patient) VALUES (?, ?, ?, ?)");
                stmt.setString(1, String.valueOf(id));
                stmt.setString(2, eventDateTime);
                stmt.setString(3, slot);
                stmt.setString(4, patientName);
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "slot-booked";
    }

}

