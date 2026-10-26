# 🛒 Product Catalog Management RESTful API (HSF302)

Dự án mẫu Backend RESTful API hoàn chỉnh theo mô hình **Database First**, kết nối Microsoft SQL Server, áp dụng DTO Pattern, Jakarta Validation và tích hợp **SpringDoc OpenAPI (Swagger UI)**.

---

## 🛠️ Công Nghệ Sử Dụng
* **Java:** 21 LTS
* **Framework:** Spring Boot 3.3.4 (Spring Web, Spring Data JPA, Spring Validation)
* **Database:** Microsoft SQL Server (có sẵn profile H2 in-memory dự phòng)
* **Tài liệu API:** SpringDoc OpenAPI 3 (Swagger UI) 2.5.0
* **Build Tool:** Maven

---

## 🗄️ Cấu Hình Database

### Cách 1: Sử dụng SQL Server thật (Khuyên dùng)
1. Mở **SQL Server Management Studio (SSMS)**.
2. Mở file `src/main/resources/schema-sqlserver.sql` và bấm **Execute** để tạo Database `ProductCatalogDB` cùng các bảng và dữ liệu mẫu.
3. Mở file `src/main/resources/application.properties`, sửa lại `username` và `password` khớp với tài khoản `sa` của bạn.

### Cách 2: Chạy nhanh bằng H2 In-Memory (Không cần cài SQL Server)
* Thêm tham số khi chạy: `--spring.profiles.active=h2`
* Hoặc đổi trong `application.properties`: `spring.profiles.active=h2`
* Dữ liệu mẫu sẽ được `DataSeeder` tự động nạp khi ứng dụng khởi động.

---

## 🚀 Cách Mở & Chạy Trên IntelliJ IDEA

1. Mở IntelliJ IDEA $\rightarrow$ **Open** $\rightarrow$ Chọn thư mục `product-catalog-api` (hoặc file `pom.xml`).
2. Mở file `src/main/java/com/hsf/catalog/ProductCatalogApplication.java`.
3. Nhấp chuột phải $\rightarrow$ Chọn **Run 'ProductCatalogApplication'**.
4. Truy cập Swagger UI trực tiếp trên trình duyệt:
   👉 **[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

---

## 🧭 Danh Sách API Endpoints

### 1. Quản lý Danh mục (`/api/v1/categories`)
* `GET /api/v1/categories`: Lấy toàn bộ danh mục đang hoạt động (`is_active = true`).
* `GET /api/v1/categories/{id}`: Chi tiết danh mục theo ID.
* `POST /api/v1/categories`: Thêm mới danh mục (Validate không trống, độ dài 3-20, không trùng `categoryCode`).
* `PUT /api/v1/categories/{id}`: Cập nhật danh mục.
* `DELETE /api/v1/categories/{id}`: Xóa mềm danh mục (`is_active = false`).

### 2. Quản lý Sản phẩm (`/api/v1/products`)
* `GET /api/v1/products?page=0&size=10&categoryId=1&keyword=Pro`: Phân trang, lọc theo Category, tìm kiếm theo tên hoặc SKU.
* `GET /api/v1/products/{id}`: Chi tiết sản phẩm kèm thông tin Category phẳng.
* `POST /api/v1/products`: Thêm mới sản phẩm (Tự động gán `OUT_OF_STOCK` nếu tồn kho = 0).
* `PUT /api/v1/products/{id}`: Cập nhật sản phẩm.
* `DELETE /api/v1/products/{id}`: Ngừng kinh doanh sản phẩm (`status = DISCONTINUED`).

---

## 🧪 Kịch Bản Kiểm Thử Bằng Swagger UI / Postman

1. **Test Validate trùng Category Code:**
   * Gửi `POST /api/v1/categories` với `categoryCode: "CAT_PHONE"` $\rightarrow$ Nhận mã `400 Bad Request` kèm thông báo *"Mã danh mục 'CAT_PHONE' đã tồn tại trong hệ thống"*.
2. **Test Validate dữ liệu âm:**
   * Gửi `POST /api/v1/products` với `price: -50000` và `stockQuantity: -2` $\rightarrow$ Nhận mã `400 Bad Request` với map chi tiết lỗi:
     ```json
     {
       "timestamp": "2026-10-08T15:30:00",
       "status": 400,
       "error": "Bad Request",
       "errors": {
         "price": "Giá sản phẩm phải lớn hơn 0",
         "stockQuantity": "Số lượng tồn kho không được âm"
       }
     }
     ```
3. **Test Phân trang & Tìm kiếm:**
   * Gọi `GET /api/v1/products?page=0&size=2&keyword=Pro` $\rightarrow$ Nhận kết quả 2 sản phẩm đầu tiên có chứa chữ "Pro", kèm thông tin `totalElements`, `totalPages`.
4. **Test Xóa mềm:**
   * Gọi `DELETE /api/v1/categories/1` $\rightarrow$ Nhận mã `204 No Content`.
   * Gọi lại `GET /api/v1/categories` $\rightarrow$ Danh mục 1 không còn xuất hiện trong danh sách.
