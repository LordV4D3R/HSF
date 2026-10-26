# 💻 Hướng Dẫn Mở & Chạy Dự Án Demo JPA (User - Profile - Order) Trên IntelliJ IDEA

> [!abstract] **Thông tin dự án mã nguồn:**
> - **Tên dự án:** `spring-boot-jpa-demo`
> - **Đường dẫn thư mục mã nguồn:** [`E:\Mindy\HSF\Code_Demo\spring-boot-jpa-demo`](file:///E:/Mindy/HSF/Code_Demo/spring-boot-jpa-demo)
> - **Nền tảng kỹ thuật:** Java 21 LTS | Spring Boot 3.3.4 | Spring Data JPA | H2 In-Memory Database | Maven
> - **Quản lý Git:** Đã khởi tạo Git cục bộ tại thư mục gốc của project và commit toàn bộ code.

---

## 🧭 1. Cách Mở Dự Án Trong IntelliJ IDEA

1. **Mở IntelliJ IDEA:**
   * Chọn menu **File** $\rightarrow$ **Open...**
   * Dán hoặc duyệt đến đường dẫn: `E:\Mindy\HSF\Code_Demo\spring-boot-jpa-demo`
   * Chọn tệp `pom.xml` hoặc thư mục dự án và nhấn **OK** $\rightarrow$ Chọn **Open as Project**.
2. **Kiểm tra JDK:**
   * Nhấn `Ctrl + Alt + Shift + S` (Project Structure).
   * Tại tab **Project**, đảm bảo mục **SDK** chọn **JDK 21** (hoặc JetBrains Runtime 21/25 có sẵn).
3. **Chạy ứng dụng:**
   * Mở cây thư mục: `src/main/java/com/hsf/jpademo/JpaDemoApplication.java`.
   * Nhấn chuột phải $\rightarrow$ Chọn **Run 'JpaDemoApplication'** (hoặc bấm nút tam giác xanh `▶️` ở góc trên bên phải).

---

## 📺 2. Kết Quả Chạy Tự Động (CommandLineRunner)

Ứng dụng được tích hợp sẵn lớp `DataInitializerAndDemoRunner`. Khi bấm Run, Spring Boot khởi động và tự động chạy lần lượt 10 bài tập qua 3 cấp độ:

```mermaid
flowchart TD
    subgraph L1 ["🟢 Cấp 1: Thao Tác Cơ Bản & Quan Hệ 1 - 1"]
        B1["Bài 1.1: Tạo User 1 (An) + Profile (Lưu bằng 1 lệnh save nhờ CascadeType.ALL)"]
        B2["Bài 1.2: Truy vấn User 1 theo ID qua Optional, in Họ tên - Địa chỉ"]
        B3["Bài 1.3: Derived query findByEmail('an.nguyen@gmail.com')"]
        B1 --> B2 --> B3
    end

    subgraph L2 ["🟡 Cấp 2: Quan Hệ 1 - N & Cơ Chế Dirty Checking"]
        B4["Bài 2.1: Thêm 2 đơn hàng cho An (user1.addOrder) và tạo mới User 2 (Bình)"]
        B5["Bài 2.2: Derived query tìm đơn hàng theo trạng thái: findByStatus('COMPLETED')"]
        B6["Bài 2.3: Update thông tin An và đổi đơn ORD-102 (Hibernate tự phát hiện Dirty Checking)"]
        B4 --> B5 --> B6
    end

    subgraph L3 ["🔴 Cấp 3: Kỹ Thuật Nâng Cao"]
        B7["Bài 3.1: Tối ưu tránh N+1 Query bằng JOIN FETCH (Chỉ 1 câu SELECT)"]
        B8["Bài 3.2: Thống kê tính tổng chi tiêu bằng hàm SUM trong JPQL (1.450.000 VNĐ)"]
        B9["Bài 3.3: Xóa phần tử mồ côi (orphanRemoval = true) — Hủy đơn ORD-101"]
        B10["Bài 3.4: Xóa tài khoản kéo theo (Cascade Delete) — Xóa User 2 kéo theo xóa sạch Order/Profile"]
        B7 --> B8 --> B9 --> B10
    end

    L1 --> L2 --> L3
```

---

## 🌐 3. Cách Mở H2 Console Để Xem CSDL Trực Quan

Trong khi ứng dụng đang chạy:
1. Mở trình duyệt web truy cập: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
2. Điền thông tin kết nối (như trong `application.properties`):
   * **Driver Class:** `org.h2.Driver`
   * **JDBC URL:** `jdbc:h2:mem:hsf_db`
   * **User Name:** `sa`
   * **Password:** *(để trống)*
3. Bấm **Connect**: Bạn sẽ thấy danh sách 3 bảng `USERS`, `USER_PROFILES`, `ORDERS` bên trái. Có thể gõ lệnh `SELECT * FROM USERS;` để tra cứu trực tiếp.

---

## 📦 4. Chi Tiết Các File Trong Mã Nguồn

| Đường Dẫn Tệp | Vai Trò & Điểm Nhấn Sư Phạm |
| :--- | :--- |
| `src/main/java/com/hsf/jpademo/entity/User.java` | Thực thể trung tâm. Sử dụng `mappedBy = "user"`, `cascade = CascadeType.ALL`, `orphanRemoval = true`. Chứa 3 helper methods quan trọng: `setProfile`, `addOrder`, `removeOrder` để đồng bộ 2 chiều trong bộ nhớ RAM. |
| `src/main/java/com/hsf/jpademo/entity/UserProfile.java` | Owning side của quan hệ 1 - 1. Giữ khóa ngoại `@JoinColumn(name = "user_id", unique = true)`. |
| `src/main/java/com/hsf/jpademo/entity/Order.java` | Owning side của quan hệ Many - 1. Giữ khóa ngoại `@JoinColumn(name = "user_id", nullable = false)`. Tiền tệ dùng `BigDecimal`. |
| `src/main/java/com/hsf/jpademo/repository/UserRepository.java` | Chứa `findByEmail` (Derived query) và `findUserFullDetailsById` (Dùng `LEFT JOIN FETCH` diệt tận gốc lỗi N+1). |
| `src/main/java/com/hsf/jpademo/repository/OrderRepository.java` | Chứa `findByStatus` và `calculateTotalSpentByUserAndStatus` (Hàm tổng hợp `SUM` trong JPQL). |
| `src/main/java/com/hsf/jpademo/service/StudentLabService.java` | Triển khai logic nghiệp vụ 10 bài tập với `@Transactional`. |
| `src/main/java/com/hsf/jpademo/runner/DataInitializerAndDemoRunner.java` | Tự động chạy toàn bộ kịch bản và in banner kết quả ra console. |

---
*Điều hướng nhanh: [[00. Lộ Trình Nghiên Cứu & Giảng Dạy HSF302|Trang Chủ Lộ Trình]] | [[Giai Thich Chi Tiet Bai Tap Mau JPA (User-Profile-Order)|Lý Thuyết Chi Tiết Bài Tập JPA]]*
