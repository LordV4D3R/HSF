-- ===================================================================
-- SCRIPT TẠO DATABASE VÀ DỮ LIỆU MẪU CHO SQL SERVER (SSMS)
-- ===================================================================

-- 1. Tạo Database
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'ProductCatalogDB')
BEGIN
    CREATE DATABASE ProductCatalogDB;
END
GO

USE ProductCatalogDB;
GO

-- 2. Bảng Danh mục (categories)
IF OBJECT_ID('products', 'U') IS NOT NULL DROP TABLE products;
IF OBJECT_ID('categories', 'U') IS NOT NULL DROP TABLE categories;
GO

CREATE TABLE categories (
    id INT IDENTITY(1,1) PRIMARY KEY,
    category_code VARCHAR(20) NOT NULL UNIQUE,
    category_name NVARCHAR(100) NOT NULL,
    description NVARCHAR(255) NULL,
    is_active BIT DEFAULT 1 NOT NULL
);
GO

-- 3. Bảng Sản phẩm (products)
CREATE TABLE products (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    category_id INT NOT NULL,
    sku VARCHAR(30) NOT NULL UNIQUE,
    product_name NVARCHAR(150) NOT NULL,
    price DECIMAL(18, 2) NOT NULL,
    stock_quantity INT NOT NULL DEFAULT 0,
    description NVARCHAR(MAX) NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE', -- 'AVAILABLE', 'OUT_OF_STOCK', 'DISCONTINUED'
    created_at DATETIME2 DEFAULT GETDATE() NOT NULL,
    updated_at DATETIME2 NULL,
    CONSTRAINT FK_Products_Categories FOREIGN KEY (category_id) REFERENCES categories(id)
);
GO

-- 4. Dữ liệu mẫu (Seed Data)
INSERT INTO categories (category_code, category_name, description, is_active) VALUES
('CAT_PHONE', N'Điện thoại thông minh', N'Các dòng smartphone chính hãng', 1),
('CAT_LAPTOP', N'Laptop & Máy tính xách tay', N'Laptop văn phòng, gaming', 1),
('CAT_ACC', N'Phụ kiện công nghệ', N'Tai nghe, cáp sạc, ốp lưng', 1);

INSERT INTO products (category_id, sku, product_name, price, stock_quantity, status) VALUES
(1, 'IP15PM-256', N'iPhone 15 Pro Max 256GB', 29990000.00, 25, 'AVAILABLE'),
(1, 'SS-S24U-512', N'Samsung Galaxy S24 Ultra 512GB', 28500000.00, 18, 'AVAILABLE'),
(2, 'MBA-M3-16', N'MacBook Air M3 16GB 512GB', 31990000.00, 10, 'AVAILABLE'),
(2, 'DELL-XPS13', N'Dell XPS 13 9340', 34500000.00, 0, 'OUT_OF_STOCK'),
(3, 'AP-PRO-2', N'AirPods Pro Gen 2 Type-C', 5490000.00, 50, 'AVAILABLE');
GO
