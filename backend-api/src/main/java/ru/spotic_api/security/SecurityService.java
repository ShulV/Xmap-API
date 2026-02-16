package ru.spotic_api.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityService {

    /**
     * Проверяет наличие заданной роли у текущего пользователя.
     * Бросает исключение, если не прошли валидацию.
     */
    public void validateAuthority(String requiredAuthority) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream()
                .noneMatch(r -> r.getAuthority().equals(requiredAuthority))) {
            throw new AccessDeniedException("Доступ запрещён");
        }
    }

    /**
     * Проверяет, анонимный ли пользователь.
     * Возвращает результаты проверки.
     */
    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken);
    }
}
