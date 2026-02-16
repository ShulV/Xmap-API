package ru.spotic_api.controllers;

import org.springframework.context.MessageSource;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.spotic_api.dto.request.NewUserDTO;
import ru.spotic_api.services.SignUpService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Locale;

@Controller
public class SignUpController {

    private final SignUpService signUpService;
    private final MessageSource messageSource;

    public SignUpController(SignUpService signUpService, MessageSource messageSource) {
        this.signUpService = signUpService;
        this.messageSource = messageSource;
    }

    @GetMapping("/sign-up")
    public String registrationPage(Model model) {
        model.addAttribute("user", new NewUserDTO("", ""));
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String registerUser(RedirectAttributes redirectAttributes,
                               @Valid @ModelAttribute("user") NewUserDTO user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "sign-up";
        }
        signUpService.createUser(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "sign-up";
        }

        redirectAttributes.addFlashAttribute("successMessage",
                messageSource.getMessage("info.successful-registration", null, Locale.getDefault()));
        return "redirect:/sign-in";
    }
}
