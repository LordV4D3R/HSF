# 🎓 HSF302 — Toàn Bộ Tài Liệu Obsidian, Slide, Đề Thi PE & Code Giải Full (Spring Boot 3 & JPA)

Kho lưu trữ tổng hợp toàn bộ giáo trình **Obsidian Vault**, **5 dự án Code Demo & Giải đề thi PE thực chiến**, **Đề thi gốc** và **10 chương Slide** môn **HSF302 (Working with Spring Framework)**. Được đóng gói chuẩn hóa để làm việc liền mạch trên cả **Windows** và **Linux Mint**.

---

## 📂 1. Cấu Trúc Thư Mục Tổng Thể

```text
HSF/
├── 📁 Obsidian_HSF_Vault/               <-- Mở trực tiếp thư mục này bằng phần mềm Obsidian trên Linux Mint!
│   ├── 📄 00. Lộ Trình Nghiên Cứu & Giảng Dạy HSF302.md
│   ├── 📁 01. Giao Trinh & 10 Chuong Slide/
│   ├── 📁 02. Bo Code Mau & Starter Kits/
│   ├── 📁 03. Bai Lab & Thuc Hanh/
│   └── 📁 04. On Thi & Danh Gia/        <-- Chứa giải đề PE (AMG, SDM) & Tổng hợp Hỏi-Đáp chuyên sâu
│
├── 📁 Code_Demo/                        <-- 5 Dự án Spring Boot hoàn chỉnh (tích hợp sẵn Maven Wrapper ./mvnw)
│   ├── 📁 demo/                         <-- [1] Quản lý Sinh viên (3-Layer, DTO, Validation, H2 DB, Swagger UI)
│   ├── 📁 spring-boot-jpa-demo/         <-- [2] Quan hệ JPA 1-1 & 1-N (User - Profile - Order, JOIN FETCH, Cascade)
│   ├── 📁 product-catalog-api/          <-- [3] RESTful API Quản lý Danh mục & Sản phẩm (Swagger UI, Paging)
│   ├── 📁 SE2033_SE193910_SDM_PE/       <-- [4] Giải Full 10/10 Đề PE1 SU26 (Smart Device Management - Thymeleaf)
│   └── 📁 Sinh_Code/HSF302_FE_AGM-main/ <-- [5] Đề PE mẫu (Artifact Management System - Thymeleaf + script_db.sql)
│
├── 📁 De/                               <-- Đề thi PE gốc (hình ảnh đề + script SQL + bản code giải)
│   └── 📁 HSF302_SU26_PE1_432339_PaperNo1/
│
└── 📁 Slides/                           <-- 10 Chương Slide bài giảng gốc (.pptx)
```

---

## 🚀 2. Bảng Tra Cứu Nhanh 5 Dự Án Trong `Code_Demo/`

| STT | Thư mục Dự án | Kiến trúc & Giao diện | Cơ sở dữ liệu | Đường dẫn chạy / Test |
| :-: | :--- | :--- | :--- | :--- |
| **1** | `Code_Demo/demo` | RESTful API + **Swagger UI** | **H2 In-Memory** *(Chạy ngay không cần cài DB)* | `http://localhost:8080/swagger-ui.html` |
| **2** | `Code_Demo/spring-boot-jpa-demo` | `CommandLineRunner` (Console Test Cấp 1-3) + H2 Console | **H2 In-Memory** *(Chạy ngay không cần cài DB)* | Xem kết quả tự động trên Console hoặc `http://localhost:8080/h2-console` |
| **3** | `Code_Demo/product-catalog-api` | RESTful API + **Swagger UI** + Phân trang | **SQL Server** (`ProductCatalogDB`) hoặc H2 | `http://localhost:8080/swagger-ui.html` |
| **4** | `Code_Demo/SE2033_SE193910_SDM_PE` | **Spring MVC + Thymeleaf** *(Giải chuẩn đề PE1 SU26 - Không Lombok)* | **SQL Server** (`HSF302_2026_PE_SDM`)<br>Script: `script_db.sql` | `http://localhost:8080/` |
| **5** | `Code_Demo/Sinh_Code/HSF302_FE_AGM-main` | **Spring MVC + Thymeleaf** *(Đề PE Cổ vật bảo tàng)* | **SQL Server** (`HSF302_2026_PE_AMG`)<br>Script: `script_db.sql` | `http://localhost:8080/` |

---

## 🐧 3. Hướng Dẫn Sử Dụng Nhanh Trên Laptop Linux Mint

### Bước 1: Clone kho lưu trữ & Cấp quyền thực thi cho `mvnw`
Trên Linux Mint, sau khi `git clone`, bạn chạy lệnh sau ở thư mục gốc để cấp quyền thực thi (`+x`) cho toàn bộ file `./mvnw`:

```bash
git clone <URL_REPO_CUA_BAN> HSF302
cd HSF302
find . -name "mvnw" -exec chmod +x {} \;
```

### Bước 2: Mở Ghi chú Obsidian trên Linux Mint
1. Mở ứng dụng **Obsidian**.
2. Chọn **Open folder as vault** $\rightarrow$ trỏ vào thư mục **`HSF302/Obsidian_HSF_Vault`**.
3. Mở file **`00. Lộ Trình Nghiên Cứu & Giảng Dạy HSF302.md`** hoặc vào mục **`04. On Thi & Danh Gia/Tong Hop Hoi Dap Chuyen Sau & Meo Thi PE HSF302.md`** để xem toàn bộ phần giải thích kiến thức và mẹo làm bài.

### Bước 3: Kiểm tra JDK 17 trên Linux Mint
Tất cả 5 dự án đã được đồng bộ về **Java 17 LTS**. Nếu máy Linux Mint chưa có JDK 17, cài đặt chỉ với 1 lệnh:

```bash
sudo apt update && sudo apt install -y openjdk-17-jdk
java -version
```

### Bước 4: Chạy thử ngay dự án bằng Terminal hoặc IntelliJ IDEA
- **Chạy bằng Terminal (không cần cài Maven ngoài):**
  ```bash
  cd Code_Demo/demo
  ./mvnw spring-boot:run
  ```
- **Nếu cần chạy SQL Server trên Linux Mint (cho 2 bài PE `SDM` và `AGM`):**
  Cách nhẹ và sạch nhất trên Linux Mint là bật container SQL Server bằng Docker:
  ```bash
  docker run -e "ACCEPT_EULA=Y" -e "MSSQL_SA_PASSWORD=12345" -p 1433:1433 --name sqlserver -d mcr.microsoft.com/mssql/server:2022-latest
  ```
  *(Hoặc nếu đang ở ngoài chưa tiện bật Docker SQL Server, bạn chỉ cần mở `pom.xml` thêm dependency `com.h2database:h2` như bên bài `demo` là chạy In-Memory ngay lập tức!).*

---

## 📌 4. Tóm Tắt Siêu Ngắn Các Kiến Thức Vừa Trao Đổi (Đọc Nhanh)

1. **Vì sao Interface kế thừa `JpaRepository` không cần `@Repository` vẫn chạy?**  
   Vì Spring Data JPA tự quét các interface kế thừa `JpaRepository` và bản thân class cài đặt ngầm bên dưới của Spring là **`SimpleJpaRepository`** đã được gắn sẵn `@Repository` và `@Transactional`.
2. **Công thức ghép tên hàm trong `JpaRepository` (Derived Query):**  
   `findBy` + `[TênThuộcTính]` + `ContainingIgnoreCase` + `OrderBy` + `[ThuộcTínhSắpXếp]` + `Desc`  
   *(Lưu ý: Bắt buộc khai báo dòng tên hàm này trong Interface `Repository` trước thì bên `Service` mới gọi được).*
3. **`CONCAT` và `@Param` trong `@Query`:**  
   - `CONCAT('%', :name, '%')`: Nối chuỗi tạo thành `'%từ_khóa%'` để tìm kiếm chứa ở bất kỳ vị trí nào (Contains).
   - `@Param("name")`: Nối biến tham số của hàm Java vào đúng vị trí `:name` trong câu lệnh JPQL.
4. **Tại sao tách Interface `Service` và nhiều `ServiceImpl` (Strategy Pattern)?**  
   - Tránh gộp nhiều cổng thanh toán (VNPay, MoMo, ZaloPay - mỗi cổng dài hàng trăm dòng code, thuật toán mã hóa khác nhau) vào chung 1 file gây phình to và dễ làm hỏng code cũ khi thêm cổng mới (vi phạm SOLID).
   - Dùng **`Map<String, PaymentService>`** trong Controller để Spring tự động gom toàn bộ các bản `Impl` theo tên `@Service("MOMO")`, gọi động mà không cần `if-else`.
5. **Tạo nhanh DTO từ Entity trong 30 giây (Khi đề PE cấm Lombok):**  
   Copy các trường `private` từ Entity sang DTO (đổi `Category` $\rightarrow$ `CategoryDTO`) $\rightarrow$ Bấm **`Alt + Insert`** để sinh Constructor rỗng (nhớ `this.category = new CategoryDTO()`), Constructor đầy đủ và Getter/Setter $\rightarrow$ Gắn `@NotBlank`, `@Pattern`, `@Positive`, `@Future` theo bảng đề bài.
