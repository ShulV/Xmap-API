package com.xmap_api.config.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * для расширения объекта аутентификации расширить класс JwtAuthenticationToken
 */
@Component
public class KCJwtAuthenticationConverter implements Converter<Jwt, JwtAuthenticationToken> {

    @Override
    public JwtAuthenticationToken convert(Jwt jwt) {
        // Преобразуем claims из JWT в роли
        List<String> roles = new ArrayList<>();
        try {
            roles = ((Map<String, Map<String, List<String>>>) jwt.getClaims()
                    .get("resource_access")).get("xmap-frontend-client-id").get("roles");//TODO сделать красивее
        } catch (Exception e) {
            roles.add("anonymous");
        }

        if (roles != null && !roles.isEmpty()) {
            List<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .toList();
            return new JwtAuthenticationToken(jwt, authorities);
        } else {
            throw new IllegalArgumentException("JWT does not contain 'roles' claim.");
        }
    }
}
