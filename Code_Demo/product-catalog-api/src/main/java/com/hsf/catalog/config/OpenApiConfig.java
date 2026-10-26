package com.hsf.catalog.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Cấu hình thông tin tài liệu Swagger UI / OpenAPI 3.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI productCatalogOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Product Catalog Management RESTful API")
                        .description("Tài liệu API quản lý Danh mục & Sản phẩm - Khóa học HSF302 (Spring Boot 3 + SQL Server)")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Giảng Viên / Bộ Môn HSF302")
                                .email("contact@fpt.edu.vn"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org")));
    }
}
