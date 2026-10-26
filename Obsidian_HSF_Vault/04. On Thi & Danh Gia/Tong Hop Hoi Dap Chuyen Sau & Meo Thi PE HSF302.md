---
title: "Tổng Hợp Hỏi Đáp Chuyên Sâu & Mẹo Thi Thực Chiến PE HSF302"
tags:
  - project/hsf302
  - category/on-thi-pe
  - spring-boot
  - jpa
  - thymeleaf
created: 2026-10-12
updated: 2026-10-26
status: active
priority: high
related:
  - "[[00. Lộ Trình Nghiên Cứu & Giảng Dạy HSF302]]"
  - "[[Chien Luoc On Thi Practical Exam PE]]"
  - "[[De Thi Mau PE - AMG Artifact Management System]]"
---

# Tổng Hợp Hỏi Đáp Chuyên Sâu & Mẹo Thi Thực Chiến PE HSF302

> [!TIP] Điều Hướng Hồ Sơ
> - 🧭 **Về Trang Tổng Quan**: [[00. Lộ Trình Nghiên Cứu & Giảng Dạy HSF302]]
> - 📂 **Cùng Chuyên Mục**: [[Chien Luoc On Thi Practical Exam PE]] | [[De Thi Mau PE - AMG Artifact Management System]]

---

## Bản Chất `@Repository` & Cơ Chế Của `JpaRepository`

### Tại sao không gắn `@Repository` trên Interface kế thừa `JpaRepository` mà code vẫn chạy?
- **Nhiệm vụ của `@Repository`**:
  - Đánh dấu class thuộc tầng Data Access (DAO) để Spring IoC Container tạo Bean.
  - Tự động dịch các lỗi JDBC/SQL (`SQLException`) sang hệ thống ngoại lệ chuẩn của Spring (`DataAccessException`).
- **Lý do không cần viết trên Interface `extends JpaRepository`**:
  - Spring Boot tự động bật `@EnableJpaRepositories` để quét mọi Interface kế thừa `JpaRepository` và tạo Dynamic Proxy.
  - Class cài đặt ngầm bên dưới của Spring là **`SimpleJpaRepository`** đã được gắn sẵn `@Repository` và `@Transactional(readOnly = true)` trong mã nguồn gốc của Spring.
- **Khi nào bắt buộc phải gắn `@Repository`?** Khi tự viết class DAO thủ công bằng `EntityManager` hoặc `JdbcTemplate` mà không kế thừa `JpaRepository`.

---

## Công Thức "Lắp Ghép Lego" Trong `JpaRepository` (Derived Query Methods)

> [!IMPORTANT] Quy tắc bắt buộc
> Mọi hàm tự chế phải được **khai báo tên hàm trong Interface Repository trước**, sau đó bên Service mới gọi được. (Mẹo: Gõ tên hàm bên Service trước rồi bấm `Alt + Enter` $\rightarrow$ *Create method in Repository* để IntelliJ tự bắn sang Repo).

$$\text{[Tiền tố]} + \text{By} + \text{[Tên Thuộc Tính]} + \text{[Điều Kiện]} + \text{OrderBy} + \text{[Tên Thuộc Tính]} + \text{Asc/Desc}$$

| Từ khóa ghép tên hàm | Tương đương SQL / JPQL | Ví dụ thực chiến trong bài thi PE |
| :--- | :--- | :--- |
| `findBy...` / `findAllBy...` | `SELECT * FROM ... WHERE` | `findAllByOrderByInstallationDateDesc()` |
| `existsBy...` | `SELECT 1 ...` (Trả về `boolean`) | `existsByDeviceCode(String deviceCode)` *(Check trùng mã)* |
| `countBy...` | `SELECT COUNT(*)` | `countByStatus(String status)` |
| `Containing` | `LIKE '%...%'` | `findByDeviceNameContaining(String keyword)` |
| `ContainingIgnoreCase` | `LOWER(...) LIKE LOWER('%...%')` | `findByDeviceNameContainingIgnoreCaseOrderByInstallationDateDesc(String name)` |
| `StartingWith` / `EndingWith` | `LIKE '...%'` / `LIKE '%...'` | `findByDeviceCodeStartingWith(String prefix)` |
| `GreaterThan` / `LessThan` | `>` / `<` | `findByPriceGreaterThan(BigDecimal price)` |
| `Between` | `BETWEEN ? AND ?` | `findByInstallationDateBetween(LocalDate start, LocalDate end)` |

### Xử lý khi IntelliJ không tự gợi ý (Suggest) tên hàm trong Repository
- Gõ kiểu trả về trước (ví dụ `List<Device> findBy`) rồi nhấn `Ctrl + Space`.
- Đảm bảo Entity đã viết đầy đủ Getter/Setter và không có lỗi biên dịch.
- Kiểm tra menu `File` xem có đang bật nhầm `Power Save Mode` không.
- Bấm nút **Reload All Maven Projects** ở thanh Maven bên phải.

---

## Giải Mã `CONCAT`, `LIKE` Và `@Param` Trong `@Query` (JPQL)

```java
@Query("SELECT d FROM Device d WHERE LOWER(d.deviceName) LIKE LOWER(CONCAT('%', :name, '%')) ORDER BY d.installationDate DESC")
List<Device> searchByDeviceName(@Param("name") String keyword);
```

- **`CONCAT('%', :name, '%')`**: Hàm nối chuỗi. Ghép `'%'` + từ khóa + `'%'` thành `'%bulb%'`.
  - *Lưu ý:* Không được viết `LIKE '%:name%'` vì Hibernate sẽ hiểu nhầm `:name` là chuỗi chữ thường chứ không phải biến truyền vào.
- **So sánh các vị trí dấu `%`**:
  - `CONCAT('%', :name, '%')`: **Contains** (Chứa ở bất kỳ đâu: đầu, giữa, cuối) $\rightarrow$ **Dùng cho bài thi PE**.
  - `CONCAT(:name, '%')`: **Starts With** (Bắt đầu bằng).
  - `CONCAT('%', :name)`: **Ends With** (Kết thúc bằng).
  - `CONCAT(:name)`: **Exact Match** (Giống hệt dấu `=`).
- **`@Param("name")`**: Ánh xạ biến `keyword` của hàm Java vào đúng vị trí `:name` trong câu JPQL (bắt buộc khi trình biên dịch Java xóa tên biến thành `arg0`).

---

## Dependency Injection (DI), Constructor Injection & Strategy Pattern

### Vì sao khai báo Repository ở cấp Class và dùng Constructor Injection?
- Interface Repository không thể `new` thủ công trong hàm mà phải để ở cấp Class để Spring IoC Container tiêm (Inject) Bean vào và dùng chung cho mọi hàm trong Service.
- **Ưu điểm của Constructor Injection (`private final`) so với `@Autowired` trên biến**:
  - Dùng được từ khóa **`final`** (Bất biến - Immutability, không bao giờ bị gán lại thành `null`).
  - Phát hiện thiếu phụ thuộc ngay lúc biên dịch/khởi động thay vì bị `NullPointerException` lúc đang chạy.
  - Dễ dàng truyền Mock Object khi viết Unit Test.

### Thực tế vì sao phải tách Interface `PaymentService` và nhiều Class `Impl` thay vì gộp chung 1 file?
1. **Độ phức tạp thực tế**: Mỗi cổng thanh toán (VNPay, MoMo, Stripe) dài 500–1.000 dòng code với cấu hình, thuật toán băm (`HMAC-SHA512`, `RSA`) và webhook riêng. Gộp chung sẽ tạo ra "God Class" hàng nghìn dòng.
2. **Nguyên lý SOLID (Single Responsibility & Open/Closed)**: Khi thêm cổng ZaloPay mới, chỉ cần tạo class `ZaloPayServiceImpl` mới, **tuyệt đối không chạm vào file `VnPayServiceImpl` đang chạy kiếm tiền ổn định** (tránh sửa thằng mới làm chết oan thằng cũ).
3. **Tránh đụng độ Git (Merge Conflict)**: Nhiều thành viên trong nhóm code các cổng thanh toán độc lập trên các file riêng.

### Cách gọi động đúng Class `Impl` bằng `Map<String, PaymentService>` (Không cần `if-else`)

```java
@Service("VNPAY")
public class VnPayServiceImpl implements PaymentService { ... }

@Service("MOMO")
public class MomoServiceImpl implements PaymentService { ... }

@RestController
public class CheckoutController {
    // Spring tự động gom mọi Impl vào Map với Key là tên trong @Service("...")
    private final Map<String, PaymentService> paymentMap;

    public CheckoutController(Map<String, PaymentService> paymentMap) {
        this.paymentMap = paymentMap;
    }

    @PostMapping("/pay")
    public String checkout(@RequestParam String type, @RequestParam double amount) {
        PaymentService service = paymentMap.get(type.toUpperCase());
        return service.pay(amount);
    }
}
```

---

## Quy Trình 30 Giây Tạo DTO Từ Entity & Mẹo IntelliJ

### Tạo DTO thần tốc khi đề thi cấm dùng Lombok
1. **Copy thuộc tính (5s)**: Copy toàn bộ `private ...` từ Entity sang DTO (đổi `Category` thành `CategoryDTO`).
2. **Phím tắt `Alt + Insert` (10s)**:
   - Sinh Constructor rỗng (**Select None**) $\rightarrow$ Nhớ khởi tạo `this.category = new CategoryDTO();` bên trong để tránh lỗi `NullPointerException` khi Thymeleaf bind form.
   - Sinh Constructor đầy đủ (**Select All**).
   - Sinh toàn bộ **Getter and Setter** (`Ctrl + A` $\rightarrow$ Enter).
3. **Gắn Validation theo bảng Screen Definition của đề (10s)**:
   - `@NotBlank`, `@Pattern(regexp = "^[A-Z]{3}\\d{3}$")`, `@Size(max = 200)`
   - `@Positive`, `@Min(1)`, `@Max(60)`
   - `@Future` (ngày tương lai) hoặc `@PastOrPresent` (ngày quá khứ/hiện tại) + `@DateTimeFormat(pattern = "yyyy-MM-dd")`.
4. **Viết 2 hàm chuyển đổi `toEntity()` và `fromEntity()` (5s)**.

### Các mẹo phím tắt IntelliJ quan trọng
- **Mở lại tùy chọn mở 2 cửa sổ Project cùng lúc**: `Ctrl + Alt + S` (Settings) $\rightarrow$ **Appearance & Behavior** $\rightarrow$ **System Settings** $\rightarrow$ Mục **Open project in** chọn **Ask** hoặc **New window**.
- **Rút tự động Interface từ Class `Impl` có sẵn**: Đặt chuột vào tên class `DeviceServiceImpl` $\rightarrow$ Chuột phải chọn **Refactor** $\rightarrow$ **Extract Interface...** $\rightarrow$ Đặt tên `DeviceService` và xóa đuôi `.impl` ở ô Package.
- **Tối ưu và xóa các dòng `import` thừa**: Bấm **`Ctrl + Alt + O`**.
