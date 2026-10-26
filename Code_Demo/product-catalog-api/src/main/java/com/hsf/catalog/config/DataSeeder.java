package com.hsf.catalog.config;

import com.hsf.catalog.entity.Category;
import com.hsf.catalog.entity.Product;
import com.hsf.catalog.repository.CategoryRepository;
import com.hsf.catalog.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * Tự động chèn dữ liệu mẫu nếu Database đang rỗng (hữu ích khi chạy profile H2 hoặc DB mới tạo).
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public DataSeeder(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {
        if (categoryRepository.count() == 0) {
            System.out.println(">>> [DataSeeder] Cơ sở dữ liệu đang rỗng, tiến hành chèn dữ liệu mẫu (Seed Data)...");

            Category cat1 = new Category("CAT_PHONE", "Điện thoại thông minh", "Các dòng smartphone chính hãng");
            Category cat2 = new Category("CAT_LAPTOP", "Laptop & Máy tính xách tay", "Laptop văn phòng, gaming");
            Category cat3 = new Category("CAT_ACC", "Phụ kiện công nghệ", "Tai nghe, cáp sạc, ốp lưng");

            categoryRepository.saveAll(List.of(cat1, cat2, cat3));

            Product p1 = new Product("IP15PM-256", "iPhone 15 Pro Max 256GB", new BigDecimal("29990000.00"), 25, "Bản 256GB titan tự nhiên", "AVAILABLE", cat1);
            Product p2 = new Product("SS-S24U-512", "Samsung Galaxy S24 Ultra 512GB", new BigDecimal("28500000.00"), 18, "AI phone cao cấp", "AVAILABLE", cat1);
            Product p3 = new Product("MBA-M3-16", "MacBook Air M3 16GB 512GB", new BigDecimal("31990000.00"), 10, "Màu Midnight", "AVAILABLE", cat2);
            Product p4 = new Product("DELL-XPS13", "Dell XPS 13 9340", new BigDecimal("34500000.00"), 0, "Màn hình OLED 3K", "OUT_OF_STOCK", cat2);
            Product p5 = new Product("AP-PRO-2", "AirPods Pro Gen 2 Type-C", new BigDecimal("5490000.00"), 50, "Chống ồn chủ động ANC", "AVAILABLE", cat3);

            productRepository.saveAll(List.of(p1, p2, p3, p4, p5));

            System.out.println(">>> [DataSeeder] Đã chèn thành công 3 Danh mục và 5 Sản phẩm mẫu.");
            System.out.println(">>> Truy cập Swagger UI tại: http://localhost:8080/swagger-ui.html để kiểm thử ngay!");
        }
    }
}
