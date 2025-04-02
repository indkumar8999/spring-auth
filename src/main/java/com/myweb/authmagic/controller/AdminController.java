package com.myweb.authmagic.controller;

import com.myweb.authmagic.models.Doctor;
import com.myweb.authmagic.db.DbConnection;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/update-doctors")
    public String updateDoctorsPage(Model model) {
        model.addAttribute("doctor", new Doctor()); // Empty doctor object for form
        List<Doctor> doctors = new ArrayList<>();
        try {
            var connection = DbConnection.getConnection();
            var statement = connection.createStatement();
            var rs = statement.executeQuery("SELECT * FROM doctors");
            while (rs.next()) {
                Doctor doctor = new Doctor();
                doctor.setId(rs.getInt("id"));
                doctor.setName(rs.getString("name"));
                doctor.setDepartment(rs.getString("department"));
                doctor.setEmail(rs.getString("email"));
                doctors.add(doctor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("doctors", doctors); // List of doctors
        return "update-doctors";
    }

    @PostMapping("/update-doctors")
    public String addDoctor(@ModelAttribute Doctor doctor, Model model) {
        // doctors.add(doctor); // Add the new doctor to the list
        try {
            var connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO doctors (name, department, email) VALUES (?, ?, ?)");
            statement.setString(1, doctor.getName());
            statement.setString(2, doctor.getDepartment());
            statement.setString(3, doctor.getEmail());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/admin/update-doctors"; // Redirect to refresh the page
    }

    public List<Doctor> getDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        try {
            var connection = DbConnection.getConnection();
            var statement = connection.createStatement();
            var rs = statement.executeQuery("SELECT * FROM doctors");
            while (rs.next()) {
                Doctor doctor = new Doctor();
                doctor.setId(rs.getInt("id"));
                doctor.setName(rs.getString("name"));
                doctor.setDepartment(rs.getString("department"));
                doctor.setEmail(rs.getString("email"));
                doctors.add(doctor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doctors;
    }
}
