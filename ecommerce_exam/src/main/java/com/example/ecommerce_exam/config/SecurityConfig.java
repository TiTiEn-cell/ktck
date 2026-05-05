package com.example.ecommerce_exam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Khai báo công cụ mã hóa mật khẩu để Spring tự động dùng so sánh với database
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Cấu hình phân quyền và luồng đăng nhập/đăng xuất
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        // Cho phép tất cả mọi người truy cập file css, ảnh, js và trang login
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/login").permitAll()

                        // Yêu cầu quyền ROLE_ADMIN cho các link bắt đầu bằng /admin/
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")

                        // Yêu cầu quyền ROLE_ADMIN hoặc ROLE_USER cho link sản phẩm
                        .requestMatchers("/products/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")

                        // Các link khác nếu có phải đăng nhập mới xem được
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        // Xử lý chuyển hướng sau khi đăng nhập thành công
                        .successHandler((request, response, authentication) -> {
                            for (GrantedAuthority auth : authentication.getAuthorities()) {
                                if (auth.getAuthority().equals("ROLE_ADMIN")) {
                                    response.sendRedirect("/admin/products");
                                    return;
                                }
                            }
                            response.sendRedirect("/products");
                        })
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }
}