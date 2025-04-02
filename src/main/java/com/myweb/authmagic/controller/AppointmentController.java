package com.myweb.authmagic.controller;

import com.myweb.authmagic.models.Appointment;
import com.myweb.authmagic.db.DbConnection;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


@Controller
public class AppointmentController {

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/appointments")
    public String getAllUserAppointmentDetails(@RequestParam String userName, Model model) {

        List<Appointment> appointmentsList = new ArrayList<Appointment>();
        try{
            Statement statement = DbConnection.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM appointments WHERE patient = '" + userName + "'");
            while(rs.next()){
                Appointment appointment = new Appointment();
                appointment.setId(rs.getInt("id"));
                appointment.setDate(rs.getString("date"));
                appointment.setTime(rs.getString("time"));
                appointment.setDoctorId(rs.getInt("doctor_id"));
                appointment.setPatient(rs.getString("patient"));
                appointmentsList.add(appointment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("username", userName);
        model.addAttribute("appointments", appointmentsList);
        return "all-appointments";
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/allappointments")
    public String getAllAppointmentDetails(@RequestParam String userName, Model model) {

        List<Appointment> appointmentsList = new ArrayList<Appointment>();
        try{
            Statement statement = DbConnection.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM appointments");
            while(rs.next()){
                Appointment appointment = new Appointment();
                appointment.setId(rs.getInt("id"));
                appointment.setDate(rs.getString("date"));
                appointment.setTime(rs.getString("time"));
                appointment.setDoctorId(rs.getInt("doctor_id"));
                appointment.setPatient(rs.getString("patient"));
                appointmentsList.add(appointment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("username", userName);
        model.addAttribute("appointments", appointmentsList);
        return "all-appointments";
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/deleteappointments")
    public String deleteAppointments(@RequestParam String userName,
                                    @RequestParam(required = false) List<Long> appointmentIdList,
                                    Model model) {

        for (Long appointmentId : appointmentIdList){
            try {
                Statement statement = DbConnection.getConnection().createStatement();
                statement.executeUpdate("DELETE FROM appointments WHERE id = " + appointmentId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("username", userName);
        return "delete-appointments";
    }

}