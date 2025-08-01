package com.xmap_api.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    /**
     * Проверяет наличие заданной роли у текущего пользователя.
     */
    public static void hasAuthority(String requiredAuthority) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream()
                .noneMatch(r -> r.getAuthority().equals(requiredAuthority))) {
            throw new AccessDeniedException("Доступ запрещён.");
        }
    }
}
