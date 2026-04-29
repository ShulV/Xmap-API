package ru.spotic_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig  {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "SELECT username, password_hash, enabled FROM \"user\" WHERE username = ?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "SELECT username, authority FROM authority WHERE username = ?");
        jdbcUserDetailsManager.setUserExistsSql(
                "SELECT username FROM \"user\" WHERE username = ?");
        jdbcUserDetailsManager.setCreateUserSql(
                "INSERT INTO \"user\" (username, password_hash, enabled) VALUES (?, ?, ?)");
        jdbcUserDetailsManager.setCreateAuthoritySql(
                "INSERT INTO authority (username, authority) VALUES (?, ?)");
        return jdbcUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(ahr -> ahr
                        .requestMatchers("/",
                                "/sign-in",
                                "/logout",
                                "/sign-up",
                                "/spots",
                                "/spot/{spotId}",
                                "/spot-adding-request",
                                "/profile",
                                "/locale"
                        ).permitAll()
                        .requestMatchers("/css/**", "/fonts/**", "/js/**", "/assets/**", "/favicon.ico").permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/**")).permitAll()
                        .requestMatchers("/actuator/**").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)//todo tmp (api запросы 403)
                .formLogin(fl -> fl
                        .loginPage("/sign-in")
                        .defaultSuccessUrl("/profile")
                        .failureUrl("/sign-in?error=true")
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/")
                        .deleteCookies("JSESSIONID")
                )
                .build();
    }
}