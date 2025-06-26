package com.music.demo.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * 网关的跨域配置
 */

@Configuration
public class MusicGatewayConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        UrlBasedCorsConfigurationSource source
                = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration
                = new CorsConfiguration();

        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedOrigin("*");
        source.registerCorsConfiguration("/**", corsConfiguration);//对所有跨域

        return new CorsWebFilter(source);
    }

}

