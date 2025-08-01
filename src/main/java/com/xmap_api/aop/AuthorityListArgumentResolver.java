package com.xmap_api.aop;

import com.xmap_api.util.DBCode;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class AuthorityListArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthorityList.class) &&
                Collection.class.isAssignableFrom(parameter.getParameterType()); // Параметр обязан быть коллекцией
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return Collections.emptyList(); // Пустой список, если пользователь не авторизован
        }

        Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
        return roles.stream()
                .map(ga -> DBCode.Authority.valueOf(ga.getAuthority()))
                .collect(Collectors.toList());
    }
}
