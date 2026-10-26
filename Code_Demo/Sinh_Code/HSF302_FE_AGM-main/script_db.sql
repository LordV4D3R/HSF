-- ============================================================================
-- HSF302 - PRACTICAL EXAM DATABASE SCRIPT (AMG: ARTIFACT MANAGEMENT SYSTEM)
-- Dự án: SE2033_SE193910_AMG_PE (HSF302_FE_AGM-main)
-- Khớp chính xác 100% với Entity Category, Artifact và application.properties
-- ============================================================================

-- 1. TẠO CƠ SỞ DỮ LIỆU
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = N'HSF302_2026_PE_AMG')
BEGIN
    CREATE DATABASE HSF302_2026_PE_AMG;
    PRINT N'>> Đã tạo database HSF302_2026_PE_AMG thành công!';
END
ELSE
BEGIN
    PRINT N'>> Database HSF302_2026_PE_AMG đã tồn tại.';
END
GO

USE HSF302_2026_PE_AMG;
GO

-- ============================================================================
-- 2. XÓA BẢNG NẾU ĐÃ TỒN TẠI (Theo đúng thứ tự khóa ngoại)
-- ============================================================================
IF OBJECT_ID(N'dbo.artifacts', N'U') IS NOT NULL
    DROP TABLE dbo.artifacts;
GO

IF OBJECT_ID(N'dbo.categories', N'U') IS NOT NULL
    DROP TABLE dbo.categories;
GO

-- ============================================================================
-- 3. TẠO BẢNG CATEGORIES (Danh mục hiện vật)
-- Entity: com.se193910.mvc.entity.Category
-- ============================================================================
CREATE TABLE categories (
    category_id INT IDENTITY(1,1) PRIMARY KEY,
    category_name NVARCHAR(100) NOT NULL UNIQUE
);
GO

-- ============================================================================
-- 4. TẠO BẢNG ARTIFACTS (Hiện vật lịch sử / cổ vật)
-- Entity: com.se193910.mvc.entity.Artifact
-- Ràng buộc Code: 3 chữ cái in hoa + 3 số (regex: ^[A-Z]{3}\d{3}$)
-- Ràng buộc Status: 'AC' (Active), 'IN' (Inactive), 'DR' (Draft)
-- Ghi chú nghiệp vụ: Hiện vật có status 'AC' KHÔNG ĐƯỢC PHÉP XÓA!
-- ============================================================================
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

-- ============================================================================
-- 5. CHÈN DỮ LIỆU MẪU (SEED DATA)
-- ============================================================================

-- 5.1. Dữ liệu danh mục Categories
SET IDENTITY_INSERT categories ON;
INSERT INTO categories (category_id, category_name) VALUES
(1, N'Ceramics'),
(2, N'Sculptures'),
(3, N'Paintings'),
(4, N'Jewelry & Regalia'),
(5, N'Ancient Weapons');
SET IDENTITY_INSERT categories OFF;
GO

-- 5.2. Dữ liệu hiện vật Artifacts
-- Gồm:
--   - Status 'AC' (Active): Khóa xóa, hiển thị nhãn xanh Active
--   - Status 'IN' (Inactive) & 'DR' (Draft): Cho phép test chức năng Delete trong bài PE
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

-- ============================================================================
-- 6. TRUY VẤN KIỂM TRA DỮ LIỆU
-- ============================================================================
PRINT N'------------------------------------------------------------';
PRINT N'DANH SÁCH BẢNG CATEGORIES:';
SELECT * FROM categories;

PRINT N'------------------------------------------------------------';
PRINT N'DANH SÁCH BẢNG ARTIFACTS (SẮP XẾP THEO NGÀY TIẾP NHẬN GIẢM DẦN):';
SELECT 
    a.artifact_id,
    a.artifact_code,
    a.artifact_name,
    c.category_name,
    a.estimated_value,
    a.age_years,
    a.acquisition_date,
    a.status,
    CASE a.status
        WHEN 'AC' THEN N'Active (Không được xóa)'
        WHEN 'IN' THEN N'Inactive (Được xóa)'
        WHEN 'DR' THEN N'Draft (Được xóa)'
        ELSE a.status
    END AS status_display
FROM artifacts a
JOIN categories c ON a.category_id = c.category_id
ORDER BY a.acquisition_date DESC;
GO
