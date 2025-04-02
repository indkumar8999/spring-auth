package com.myweb.authmagic.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/error")
public class CustomErrorController implements ErrorController {

    @GetMapping
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            model.addAttribute("statusCode", statusCode);
            
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("message", "Page Not Found");
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                model.addAttribute("message", "You do not have permission to access this page.");
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                model.addAttribute("message", "An unexpected error occurred.");
            } else {
                model.addAttribute("message", "Something went wrong.");
            }
        }

        return "error"; // Returns error.html
    }
}
