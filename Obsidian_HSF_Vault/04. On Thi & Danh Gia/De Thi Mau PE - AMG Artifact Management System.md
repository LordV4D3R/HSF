# Đề Thi Mẫu PE: AMG - Artifact Management System (Hệ Thống Quản Lý Cổ Vật / Hiện Vật)

> **Mã nguồn:** `E:\Mindy\HSF\Code_Demo\Sinh_Code\HSF302_FE_AGM-main`  
> **Cơ sở dữ liệu:** Microsoft SQL Server (`HSF302_2026_PE_AMG`)  
> **Kiến trúc:** Spring Boot MVC + Spring Data JPA + Thymeleaf + Bootstrap 5  

---

## 1. Mô tả bài toán & Nghiệp vụ cốt lõi

Hệ thống quản lý cổ vật / bảo vật lịch sử trong bảo tàng hoặc bộ sưu tập văn hóa.

### Các thực thể chính:
1. **`Category` (Danh mục cổ vật):** Gốm sứ (`Ceramics`), Tượng (`Sculptures`), Tranh (`Paintings`), v.v.
2. **`Artifact` (Hiện vật / Bảo vật):**
   - `artifactCode`: Mã cổ vật (Bắt buộc theo định dạng: 3 chữ cái in hoa + 3 chữ số, Regex: `^[A-Z]{3}\d{3}$`, ví dụ `ART101`, `SCU201`).
   - `artifactName`: Tên cổ vật (Hỗ trợ Unicode tiếng Việt, tối đa 200 ký tự).
   - `category_id`: Khóa ngoại trỏ đến danh mục tương ứng.
   - `estimatedValue`: Giá trị ước tính (USD, số dương lớn hơn 0).
   - `ageYears`: Niên đại / Tuổi thọ tính theo năm (Số nguyên dương > 0).
   - `acquisitionDate`: Ngày tiếp nhận (Phải là ngày trong quá khứ hoặc hiện tại).
   - `status`: Trạng thái cổ vật gồm:
     - `'AC'` (**Active** - Đang trưng bày chính thức): **NGHIỆP VỤ BẮT BUỘC: KHÔNG ĐƯỢC PHÉP XÓA!**
     - `'IN'` (**Inactive** - Tạm ngưng trưng bày): Được phép xóa.
     - `'DR'` (**Draft** - Đang thẩm định sơ bộ): Được phép xóa.

---

## 2. Kịch bản Cơ sở dữ liệu SQL Server (Script DB + Seed Data)

File script đã được tạo sẵn tại:
- `E:\Mindy\HSF\Code_Demo\Sinh_Code\HSF302_FE_AGM-main\script_db.sql`
- `E:\Mindy\HSF\Code_Demo\Sinh_Code\HSF302_FE_AGM-main\src\main\resources\script_db.sql`

```sql
-- ============================================================================
-- HSF302 - PRACTICAL EXAM DATABASE SCRIPT (AMG: ARTIFACT MANAGEMENT SYSTEM)
-- Database: HSF302_2026_PE_AMG
-- ============================================================================

-- 1. TẠO DATABASE
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = N'HSF302_2026_PE_AMG')
BEGIN
    CREATE DATABASE HSF302_2026_PE_AMG;
END
GO

USE HSF302_2026_PE_AMG;
GO

-- 2. XÓA BẢNG NẾU ĐÃ TỒN TẠI
IF OBJECT_ID(N'dbo.artifacts', N'U') IS NOT NULL DROP TABLE dbo.artifacts;
IF OBJECT_ID(N'dbo.categories', N'U') IS NOT NULL DROP TABLE dbo.categories;
GO

-- 3. TẠO BẢNG CATEGORIES
CREATE TABLE categories (
    category_id INT IDENTITY(1,1) PRIMARY KEY,
    category_name NVARCHAR(100) NOT NULL UNIQUE
);
GO

-- 4. TẠO BẢNG ARTIFACTS
CREATE TABLE artifacts (
    artifact_id INT IDENTITY(1,1) PRIMARY KEY,
    artifact_code VARCHAR(6) NOT NULL UNIQUE,
    artifact_name NVARCHAR(200) NOT NULL,
    category_id INT NOT NULL,
    estimated_value DECIMAL(10, 2) NOT NULL CHECK (estimated_value > 0),
    age_years INT NOT NULL CHECK (age_years > 0),
    acquisition_date DATE NOT NULL,
    status NVARCHAR(10) NOT NULL CHECK (status IN ('AC', 'IN', 'DR')),
    CONSTRAINT FK_artifacts_categories FOREIGN KEY (category_id) REFERENCES categories(category_id)
);
GO

-- 5. CHÈN DỮ LIỆU MẪU (SEED DATA)
SET IDENTITY_INSERT categories ON;
INSERT INTO categories (category_id, category_name) VALUES
(1, N'Ceramics'),
(2, N'Sculptures'),
(3, N'Paintings'),
(4, N'Jewelry & Regalia'),
(5, N'Ancient Weapons');
SET IDENTITY_INSERT categories OFF;
GO

SET IDENTITY_INSERT artifacts ON;
INSERT INTO artifacts (artifact_id, artifact_code, artifact_name, category_id, estimated_value, age_years, acquisition_date, status) VALUES
(1, 'ART101', N'Ming Dynasty Blue and White Vase', 1, 50000.00, 580, '2023-05-15', 'AC'),
(2, 'ART102', N'Tang Dynasty Terracotta Horse', 1, 35000.00, 1250, '2022-11-20', 'AC'),
(3, 'ART103', N'Song Dynasty Celadon Bowl', 1, 12000.00, 850, '2024-01-10', 'IN'),
(4, 'SCU201', N'Bronze Statue of Standing Buddha', 2, 85000.00, 1400, '2021-09-05', 'AC'),
(5, 'SCU202', N'Greco-Buddhist Gandhara Stone Relief', 2, 45000.00, 1800, '2023-08-14', 'DR'),
(6, 'PNT301', N'Silk Scroll Landscape Autumn Mist', 3, 28000.00, 420, '2023-12-01', 'AC'),
(7, 'JWL401', N'Royal Gold Filigree Crown', 4, 150000.00, 600, '2022-03-18', 'AC'),
(8, 'WPN501', N'Damascus Steel Ceremonial Saber', 5, 22000.00, 350, '2024-04-12', 'IN');
SET IDENTITY_INSERT artifacts OFF;
GO
```

---

## 3. Các chức năng cần kiểm thử khi chạy ứng dụng

1. **Hiển thị danh sách (`/list` hoặc `/`):**
   - Sắp xếp giảm dần theo `acquisitionDate` (`ORDER BY a.acquisitionDate DESC`).
   - Cột `Status` hiển thị nhãn: `Active` nếu 'AC', `Inactive` nếu 'IN', `Draft` nếu 'DR'.
2. **Tìm kiếm theo tên (`searchName`):**
   - Tìm kiếm không phân biệt chữ hoa / thường (`searchByArtifactNameContainingIgnoreCaseOrderByAcquisitionDateDesc`).
3. **Thêm mới cổ vật (`/add`):**
   - Validate form với Jakarta Validation (`@Valid`, Regex mã cổ vật, giá trị > 0, ngày quá khứ/hiện tại).
   - Chọn Category từ dropdown.
4. **Xem chi tiết (`/view?id=...`):**
   - Hiển thị đầy đủ thông tin cổ vật.
5. **Xóa cổ vật (`/delete`):**
   - Nếu xóa bản ghi có status `'AC'` $\rightarrow$ Báo lỗi: *"Cannot delete an active artifact."*
   - Nếu xóa bản ghi có status `'IN'` hoặc `'DR'` $\rightarrow$ Xóa thành công và thông báo *"Deleted successfully"*.
