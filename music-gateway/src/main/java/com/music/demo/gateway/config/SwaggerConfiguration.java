package com.music.demo.gateway.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("音乐项目的Gateway接口测试")
                        .version("1.0.0")
                        .description("music项目接口测试")
                        .license(new License().name("Apache 2.0")
                                .url("https://www.nefu.edu.cn/"))
                );
    }
}
