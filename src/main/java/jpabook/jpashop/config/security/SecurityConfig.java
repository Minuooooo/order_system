package jpabook.jpashop.config.security;

import jpabook.jpashop.config.jwt.JwtAccessDeniedHandler;
import jpabook.jpashop.config.jwt.JwtAuthenticationEntryPoint;
import jpabook.jpashop.config.jwt.JwtProvider;
import jpabook.jpashop.config.jwt.JwtSecurityConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.CorsFilter;

import java.util.List;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private static final String[] AUTH_WHITELIST = {  // 인증하지 않을 Url
            "/swagger-ui/**",
            "/api-docs/**",
            "/api",
            "/api/auth/sign-up/**",
            "/api/auth/sign-in/**"
    };

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain config(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .cors().configurationSource(request -> {
                    var cors = new CorsConfiguration();
                    cors.setAllowedOrigins(List.of("http://localhost:3000"));  // 요청 받는 Url
                    cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));  // 요청 받는 HTTP Method
                    cors.setAllowedHeaders(List.of("*"));  // 요청 Header
                    return cors;
                })

                .and()
                .apply(new JwtSecurityConfig(jwtProvider))

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeHttpRequests(authorize -> authorize
                        .shouldFilterAllDispatcherTypes(false)
                        .antMatchers(AUTH_WHITELIST)
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                        .and()
                )
                .build();
    }
}
