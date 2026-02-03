package ru.spotic_api.controllers;

import ru.spotic_api.dto.request.NewUserDTO;
import ru.spotic_api.services.SignUpService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignUpController {

    private final SignUpService signUpService;

    public SignUpController(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @GetMapping("/sign-up")
    public String registrationPage(Model model) {
        model.addAttribute("user", new NewUserDTO("", ""));
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String registerUser(@Valid @ModelAttribute("user") NewUserDTO user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "sign-up";
        }
        signUpService.createUser(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "sign-up";
        }
        return "redirect:/sign-in?registered=true";
    }
}
