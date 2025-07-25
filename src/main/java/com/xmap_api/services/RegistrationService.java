package com.xmap_api.services;

import com.xmap_api.dto.request.NewUserDTO;
import com.xmap_api.util.DBCode;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class RegistrationService {

    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void createUser(NewUserDTO user, BindingResult bindingResult) {
        if (userDetailsManager.userExists(user.username())) {
            bindingResult.rejectValue("username", "", "Пользователь с таким именем уже зарегистрирован.");
            return;
        }
        UserDetails newUserDetails = User.builder()
                .username(user.username())
                .password(passwordEncoder.encode(user.rawPassword()))
                .authorities(DBCode.Authority.USER.name())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
        userDetailsManager.createUser(newUserDetails);
    }
}
