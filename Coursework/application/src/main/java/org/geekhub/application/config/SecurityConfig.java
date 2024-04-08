package org.geekhub.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public AuthenticationManager authenticationManager(
        AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                auth -> auth.requestMatchers("/register", "/signup").permitAll()
                    .requestMatchers("/users/**", "/employees/**","/admin/**").hasAnyAuthority("ADMIN", "SUPER_ADMIN")
                    .requestMatchers("/users/**", "/employees/**").hasAuthority("USER")
                    .anyRequest().authenticated()
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/signup")
                .usernameParameter("email")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout(logout -> logout.logoutUrl("/signout").permitAll());

        return http.build();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_SUPER_ADMIN > ROLE_ADMIN > ROLE_USER");
        return roleHierarchy;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
