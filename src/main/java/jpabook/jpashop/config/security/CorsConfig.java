package jpabook.jpashop.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);  // Credential 사용
        config.addAllowedOrigin("*");  // 요청 Url
        config.addAllowedHeader("*");  // 요청 Header
        config.addAllowedMethod("*");  // 요청 HTTP Method

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
