package com.neuhealth.demo.util;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
@Configuration
public class SwaggerConfig {

     @Bean
     OpenAPI openAPI() {
            return new OpenAPI()
                    .info(new Info()
                            .title("东软颐养中心接口文档")
                            .description("SpringBoot3 集成 Swagger3接口文档")
                            .version("v1"))
                    .externalDocs(new ExternalDocumentation()
                            .description("项目API文档")
                            .url("/"));
        }


}