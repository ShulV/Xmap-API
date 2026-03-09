package ru.spotic_api.controllers;

import org.springframework.boot.info.BuildProperties;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.spotic_api.security.SecurityService;
import ru.spotic_api.services.UserService;

import java.util.Locale;

@Controller
public class UserController {
    private final SecurityService securityService;
    private final MessageSource messageSource;
    private final BuildProperties buildProperties;

    public UserController(SecurityService securityService, MessageSource messageSource,
                          BuildProperties buildProperties) {
        this.securityService = securityService;
        this.messageSource = messageSource;
        this.buildProperties = buildProperties;
    }

    @GetMapping("/profile")
    public String profile(RedirectAttributes redirectAttributes, Model model) {
        if (securityService.isAuthenticated()) {
            model.addAttribute("activePage", "profile");
            model.addAttribute("appVersion", buildProperties.getVersion());
            return "profile";

        } else {
            redirectAttributes.addFlashAttribute("warnMessage",
                    messageSource.getMessage("warn.unauthorized.cannot-view-profile", null,
                            Locale.getDefault()));
            return "redirect:/sign-in";
        }
    }
}