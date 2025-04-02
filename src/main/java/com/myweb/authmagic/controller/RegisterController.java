package com.myweb.authmagic.controller;

import com.myweb.authmagic.model.MyUser;
import com.myweb.authmagic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new MyUser()); // Empty user object for form binding
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute MyUser user) {
        userService.registerUser(user);
        return "redirect:/login?registered"; // Redirect to login after successful registration
    }
}
