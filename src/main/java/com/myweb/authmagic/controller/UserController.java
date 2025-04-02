package com.myweb.authmagic.controller;

import com.myweb.authmagic.models.Doctor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {

    private List<Doctor> doctors;
    private final AdminController controller;

    // Inject existing doctors list from AdminController
    public UserController(AdminController adminController) {
        this.controller = adminController;
        this.doctors = adminController.getDoctors();
    }

    @GetMapping("/doctors")
    public String viewDoctors(Model model) {
        this.doctors = controller.getDoctors();
        model.addAttribute("doctors", doctors);
        return "doctors-list";
    }
}
