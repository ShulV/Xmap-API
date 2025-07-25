package com.xmap_api.controllers;

import com.xmap_api.dto.request.NewUserDTO;
import com.xmap_api.services.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/register")
    public String registrationPage(Model model) {
        model.addAttribute("user", new NewUserDTO("", ""));
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") NewUserDTO user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        registrationService.createUser(user, bindingResult);
        return "redirect:/login?registered=true";
    }
}
