---
title: "Giải Chi Tiết Đề Thi PE1 SU26: SDM - Smart Device Management"
tags:
  - project/hsf302
  - category/on-thi-pe
  - spring-mvc
  - thymeleaf
created: 2026-10-12
updated: 2026-10-26
status: done
priority: high
related:
  - "[[00. Lộ Trình Nghiên Cứu & Giảng Dạy HSF302]]"
  - "[[Tong Hop Hoi Dap Chuyen Sau & Meo Thi PE HSF302]]"
  - "[[De Thi Mau PE - AMG Artifact Management System]]"
---

# Giải Chi Tiết Đề Thi PE1 SU26: SDM - Smart Device Management

> [!TIP] Điều Hướng Hồ Sơ
> - 🧭 **Về Trang Tổng Quan**: [[00. Lộ Trình Nghiên Cứu & Giảng Dạy HSF302]]
> - 📂 **Cùng Chuyên Mục**: [[Tong Hop Hoi Dap Chuyen Sau & Meo Thi PE HSF302]] | [[De Thi Mau PE - AMG Artifact Management System]]

---

## Thông Tin Đề Thi & Quy Định Chấm Điểm

- **Đề gốc**: `De/HSF302_SU26_PE1_432339_PaperNo1`
- **Lời giải Full 10/10**: `Code_Demo/SE2033_SE193910_SDM_PE`
- **Database**: `HSF302_2026_PE_SDM` (SQL Server)
- **Các quy định bắt buộc (Vi phạm bị 0 điểm)**:
  - **Cấm dùng Lombok** và các thư viện ngoài luồng.
  - **Cấm dùng `alert()` của JavaScript** (phải dùng HTML Alert & Bootstrap Modal).
  - Cấu hình `spring.jpa.hibernate.ddl-auto=none`, `server.port=8080`, `server.servlet.context-path=/`.
  - Đúng cấu trúc package: `com.<studentId>.mvc.{entity, repository, service, service.impl, controller, dto}`.

---

## Tóm Tắt Nghiệp Vụ 4 Câu Hỏi

### Q1: List & Search (2.5 điểm)
- Hiển thị danh sách tại `/` và `/list`, sắp xếp theo `installationDate` giảm dần (`DESC`).
- Nếu danh sách trống, hiển thị `"No records found"`.
- Tìm kiếm gần đúng theo tên thiết bị (`ContainingIgnoreCase`) kết hợp sắp xếp `installationDate DESC`.
- Cột `Status` hiển thị chữ: `AC` $\rightarrow$ `Active`, `IN` $\rightarrow$ `Inactive`, `DR` $\rightarrow$ `Draft`.

### Q2: Delete & Confirmation Dialog (1.5 điểm)
- Bấm `Delete` mở popup xác nhận: `Are you sure you want to delete "[device name]"?`.
- Nếu chọn `Yes`:
  - Kiểm tra nếu `status == "AC"`: **Không xóa**, hiển thị lỗi `"Cannot delete an active device."`.
  - Ngược lại (`IN` hoặc `DR`): Xóa khỏi DB, hiển thị `"Deleted successfully"`.

### Q3: Add New Device & Validation (4.0 điểm)
- Dropdown `Category` phải load động từ bảng `categories` trong DB.
- **Luật Validation**:
  - `deviceCode`: Bắt buộc, 6 ký tự, Regex `^[A-Z]{3}\d{3}$` (VD: `DVC101`), không được trùng lặp (`existsByDeviceCode`).
  - `deviceName`: Bắt buộc, tối đa 200 ký tự, không có khoảng trắng ở 2 đầu (`^(?!\s)(?!.*\s$).*$`).
  - `price`: Bắt buộc, số dương `> 0` (`@Positive`).
  - `warrantyMonths`: Bắt buộc, số nguyên từ `1` đến `60` (`@Min(1)`, `@Max(60)`).
  - `installationDate`: Bắt buộc, **phải là ngày trong tương lai** (`@Future`).
- Lưu thành công: Hiện thông báo `"Created new device successfully"` ngay trên trang Add và **xóa trắng các ô nhập liệu (Clear fields)**.

### Q4: View Device Details (1.5 điểm)
- Xem chi tiết toàn bộ thông tin thiết bị tại `/view?id=...` và nút `Back` quay về `/list`.
