-- ============================================================================
-- HSF302 - PRACTICAL EXAM DATABASE SCRIPT (SDM: SMART DEVICE MANAGEMENT)
-- PaperNo: 1 (HSF302_SU26_PE1_432339)
-- Database: HSF302_2026_PE_SDM
-- ============================================================================

-- 1. TẠO CƠ SỞ DỮ LIỆU
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = N'HSF302_2026_PE_SDM')
BEGIN
    CREATE DATABASE HSF302_2026_PE_SDM;
    PRINT N'>> Database HSF302_2026_PE_SDM created successfully!';
END
GO

USE [HSF302_2026_PE_SDM]
GO

-- 2. XÓA BẢNG NẾU ĐÃ TỒN TẠI (Theo đúng thứ tự khóa ngoại)
IF OBJECT_ID('dbo.devices', 'U') IS NOT NULL DROP TABLE [dbo].[devices];
GO
IF OBJECT_ID('dbo.categories', 'U') IS NOT NULL DROP TABLE [dbo].[categories];
GO

-- 3. TẠO BẢNG CATEGORIES (Theo đúng đề thi)
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[categories](
	[category_id] [int] IDENTITY(1,1) NOT NULL,
	[category_name] [nvarchar](100) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[category_id] ASC
),
UNIQUE NONCLUSTERED 
(
	[category_name] ASC
)
) ON [PRIMARY]
GO

-- 4. TẠO BẢNG DEVICES (Theo đúng đề thi)
CREATE TABLE [dbo].[devices](
	[device_id] [int] IDENTITY(1,1) NOT NULL,
	[device_code] [varchar](6) NOT NULL,
	[device_name] [nvarchar](200) NOT NULL,
	[category_id] [int] NOT NULL,
	[price] [decimal](10) NOT NULL,
	[warranty_months] [int] NOT NULL,
	[installation_date] [date] NOT NULL,
	[status] [nvarchar](10) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[device_id] ASC
),
UNIQUE NONCLUSTERED 
(
	[device_code] ASC
)
) ON [PRIMARY]
GO

-- 5. THÊM RÀNG BUỘC KHÓA NGOẠI
ALTER TABLE [dbo].[devices]  WITH CHECK ADD  CONSTRAINT [FK_devices_categories] FOREIGN KEY([category_id])
REFERENCES [dbo].[categories] ([category_id])
GO

ALTER TABLE [dbo].[devices] CHECK CONSTRAINT [FK_devices_categories]
GO

-- ============================================================================
-- 6. CHÈN DỮ LIỆU MẪU (SEED DATA THEO ĐÚNG ĐỀ BÀI)
-- ============================================================================

-- 6.1. Danh mục Categories
SET IDENTITY_INSERT [dbo].[categories] ON;
INSERT INTO [dbo].[categories] ([category_id], [category_name]) VALUES
(1, N'Lighting'),
(2, N'Security'),
(3, N'Climate Control'),
(4, N'Entertainment'),
(5, N'Energy Management');
SET IDENTITY_INSERT [dbo].[categories] OFF;
GO

-- 6.2. Thiết bị Devices
SET IDENTITY_INSERT [dbo].[devices] ON;
INSERT INTO [dbo].[devices] ([device_id], [device_code], [device_name], [category_id], [price], [warranty_months], [installation_date], [status]) VALUES
(1, 'DVC101', N'Smart Bulb A1', 1, 500, 10, '2026-05-15', N'AC'),
(2, 'DVC202', N'Smart Lock X', 2, 350, 12, '2026-06-20', N'DR'),
(3, 'DVC303', N'Smart Thermostat Pro', 3, 220, 24, '2026-07-10', N'IN'),
(4, 'DVC104', N'Ambient Light Strip RGB', 1, 150, 6, '2026-04-01', N'AC'),
(5, 'DVC405', N'Smart Speaker Hub Mini', 4, 80, 12, '2026-08-15', N'DR'),
(6, 'DVC206', N'Outdoor Security Camera 4K', 2, 450, 18, '2026-03-25', N'AC');
SET IDENTITY_INSERT [dbo].[devices] OFF;
GO

-- ============================================================================
-- 7. TRUY VẤN KIỂM TRA
-- ============================================================================
SELECT 
    d.[device_id],
    d.[device_code],
    d.[device_name],
    c.[category_name],
    d.[price],
    d.[warranty_months],
    d.[installation_date],
    d.[status]
FROM [dbo].[devices] d
JOIN [dbo].[categories] c ON d.[category_id] = c.[category_id]
ORDER BY d.[installation_date] DESC;
GO
