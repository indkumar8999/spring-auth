package com.myweb.authmagic.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Authentication authentication, Model model) {
        if (authentication != null) {
            model.addAttribute("username", authentication.getName());

            // Check if the user has "ROLE_ADMIN"
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            boolean isAdmin = authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
            model.addAttribute("isAdmin", isAdmin);
        }
        return "home";
    }
}
