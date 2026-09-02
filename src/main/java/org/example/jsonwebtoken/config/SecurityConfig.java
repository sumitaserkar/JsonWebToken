package org.example.jsonwebtoken.config;

import org.example.jsonwebtoken.filter.JwtAuthenticationFilter;
import org.example.jsonwebtoken.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                          CustomUserDetailsService customUserDetailsService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   AuthenticationProvider authenticationProvider) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        // ── Public static pages (browser GET — no JWT header possible) ──
                        .requestMatchers(
                                "/", "/index.html",
                                "/register.html", "/login.html", "/admin-login.html",
                                "/customer_home.html", "/view-cart.html",
                                "/viewAllProducts.html", "/viewProduct.html",
                                // Admin HTML pages served publicly; JS guard does the redirect
                                "/admin_home.html", "/admin-login.html",
                                "/addProduct.html", "/updateProduct.html",
                                "/deleteProduct.html", "/product_management.html","/forgot_password.html",
                                "/auth/forgot-password",
                                "/auth/verify-otp",
                                "/auth/reset-password",
                                "/css/**", "/js/**"
                        ).permitAll()
                        // ── Auth endpoints ──
                        .requestMatchers("/auth/**", "/hello").permitAll()
                        // ── Public product READ REST APIs ──
                        .requestMatchers("/viewAllProducts", "/viewProduct/**").permitAll()
                        // ── Admin-only product mutation REST APIs ──
                        .requestMatchers("/addProduct", "/updateProduct", "/deleteProduct/**").hasRole("ADMIN")
                        // ── Cart & Payment (USER or ADMIN) ──
                        .requestMatchers("/customer/cart/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/customer/payment/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/products/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/customer/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable());

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}