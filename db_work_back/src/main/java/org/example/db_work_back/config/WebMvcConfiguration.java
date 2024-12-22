package org.example.db_work_back.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class WebMvcConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30) // 使用 OpenAPI 3.0 规范
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example")) // 修改为你的 Controller 包路径
                .paths(PathSelectors.any())
                .build();
    }
}
