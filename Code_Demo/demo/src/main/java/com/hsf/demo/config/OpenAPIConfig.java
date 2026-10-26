package com.hsf.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Cấu hình thông tin hiển thị trên giao diện Swagger / OpenAPI UI
 */
@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI studentOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Quản Lý Sinh Viên (Student Management API)")
                        .description("Tài liệu API thực hành mô hình 3 lớp: Controller -> Service -> Repository -> Database.\n\n"
                                + "Bao gồm các phương thức GET, POST, PUT, DELETE và minh họa DTO, Interface, Enum.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Bộ môn Lập trình Web / Java")
                                .email("giangvien@fpt.edu.vn")));
    }
}
