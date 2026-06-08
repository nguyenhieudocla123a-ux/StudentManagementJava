CREATE DATABASE QuanLyDiemSinhVien_V2;
GO
USE QuanLyDiemSinhVien_V2;
GO

/* ==================== BẢNG KHOA ==================== */
CREATE TABLE KHOA (
    ma_khoa VARCHAR(20) PRIMARY KEY,
    ten_khoa NVARCHAR(200) NOT NULL
);

INSERT INTO KHOA (ma_khoa, ten_khoa) VALUES	
('CNTT', N'Công nghệ Thông tin'),
('KT', N'Kinh tế'),
('NN', N'Ngoại ngữ'),
('CK', N'Cơ khí'),
('XD', N'Xây dựng');

/* ==================== BẢNG TÀI KHOẢN ==================== */
CREATE TABLE TAIKHOAN (
    ten_dang_nhap VARCHAR(50) PRIMARY KEY,
    mat_khau VARCHAR(255) NOT NULL,
    loai_nguoi_dung VARCHAR(20) CHECK (loai_nguoi_dung IN ('SinhVien','GiangVien','Admin')),
    online_status VARCHAR(10) DEFAULT 'Offline'
);

INSERT INTO TAIKHOAN (ten_dang_nhap, mat_khau, loai_nguoi_dung, online_status) VALUES
('admin', 'admin123', 'Admin', 'Offline'),
('GV001', '123456', 'GiangVien', 'Offline'),
('GV002', '123456', 'GiangVien', 'Offline'),
('GV003', '123456', 'GiangVien', 'Offline'),
('SV001', '123456', 'SinhVien', 'Offline'),
('SV002', '123456', 'SinhVien', 'Offline'),
('SV003', '123456', 'SinhVien', 'Offline');
-- (Mình đã rút gọn danh sách SV để file script gọn nhẹ hơn, nếu bạn cần full 100 SV, bạn có thể copy INSERT từ file cũ qua)

/* ==================== BẢNG SINH VIÊN ==================== */
CREATE TABLE SINHVIEN (
    ma_sv VARCHAR(20) PRIMARY KEY,
    ho_ten NVARCHAR(100) NOT NULL,
    gioi_tinh NVARCHAR(10) CHECK (gioi_tinh IN (N'Nam', N'Nữ')),
    ngay_sinh DATE,
    email VARCHAR(100),
    so_dien_thoai VARCHAR(15),
    dia_chi NVARCHAR(255),
    ma_khoa VARCHAR(20),
    ten_dang_nhap VARCHAR(50) UNIQUE,
    FOREIGN KEY (ma_khoa) REFERENCES KHOA(ma_khoa),
    FOREIGN KEY (ten_dang_nhap) REFERENCES TAIKHOAN(ten_dang_nhap)
);

INSERT INTO SINHVIEN (ma_sv, ho_ten, gioi_tinh, ngay_sinh, email, so_dien_thoai, dia_chi, ma_khoa, ten_dang_nhap) VALUES
('SV001', N'Nguyễn Văn An', N'Nam', '2003-01-01', 'bacxnt2005@gmail.com', '0912345001', N'Hà Nội', 'CNTT', 'SV001'),
('SV002', N'Trần Thị Bích', N'Nữ', '2003-01-02', 'nguyenhieudocla123a@gmail.com', '0912345002', N'Hà Nội', 'CNTT', 'SV002'),
('SV003', N'Lê Văn Bình', N'Nam', '2003-01-03', 'sv003@example.com', '0912345003', N'Hà Nội', 'KT', 'SV003');

/* ==================== BẢNG GIẢNG VIÊN ==================== */
CREATE TABLE GIANGVIEN (
    ma_gv VARCHAR(20) PRIMARY KEY,
    ho_ten NVARCHAR(100) NOT NULL,
    email VARCHAR(100),
    so_dien_thoai VARCHAR(15),
    ma_khoa VARCHAR(20),
    ten_dang_nhap VARCHAR(50) UNIQUE,
    FOREIGN KEY (ma_khoa) REFERENCES KHOA(ma_khoa),
    FOREIGN KEY (ten_dang_nhap) REFERENCES TAIKHOAN(ten_dang_nhap)
);

INSERT INTO GIANGVIEN (ma_gv, ho_ten, email, so_dien_thoai, ma_khoa, ten_dang_nhap) VALUES
('GV001', N'PGS.TS Nguyễn Văn Hùng', 'gv001@school.edu', '0987654321', 'CNTT', 'GV001'),
('GV002', N'TS Trần Thị Lan', 'gv002@school.edu', '0987654322', 'CNTT', 'GV002'),
('GV003', N'ThS Lê Văn Minh', 'gv003@school.edu', '0987654323', 'KT', 'GV003');

/* ==================== BẢNG QUẢN TRỊ VIÊN ==================== */
CREATE TABLE QUANTRIVIEN (
    ma_qtv VARCHAR(20) PRIMARY KEY,
    ho_ten NVARCHAR(100) NOT NULL,
    email VARCHAR(100),
    so_dien_thoai VARCHAR(15),
    ten_dang_nhap VARCHAR(50) UNIQUE,
    FOREIGN KEY (ten_dang_nhap) REFERENCES TAIKHOAN(ten_dang_nhap)
);

INSERT INTO QUANTRIVIEN (ma_qtv, ho_ten, email, so_dien_thoai, ten_dang_nhap) VALUES
('AD001', N'Quản trị viên Hệ thống', 'admin@school.edu', '0901234567', 'admin');

/* ==================== BẢNG MÔN HỌC ==================== */
CREATE TABLE MONHOC (
    ma_mh VARCHAR(20) PRIMARY KEY,
    ten_mh NVARCHAR(200) NOT NULL,
    so_tin_chi INT NOT NULL,
    mo_ta NVARCHAR(MAX),
    ma_khoa VARCHAR(20),
    FOREIGN KEY (ma_khoa) REFERENCES KHOA(ma_khoa)
);

INSERT INTO MONHOC (ma_mh, ten_mh, so_tin_chi, mo_ta, ma_khoa) VALUES
('JAVA', N'Lập trình Java', 3, N'Môn học lập trình Java cơ bản', 'CNTT'),
('SQL', N'Cơ sở dữ liệu', 3, N'Môn học về SQL', 'CNTT'),
('KTE', N'Kinh tế học', 2, N'Môn học kinh tế', 'KT');

/* ==================== BẢNG LỚP HỌC PHẦN ==================== */
-- ĐÃ CẬP NHẬT: Thêm các cột quản lý thời gian
CREATE TABLE LOPHOCPHAN (
    ma_lop VARCHAR(20) PRIMARY KEY,
    ma_mh VARCHAR(20) NOT NULL,
    ma_gv VARCHAR(20),
    hoc_ky VARCHAR(10) NOT NULL,
    nam_hoc VARCHAR(10) NOT NULL,
    si_so_toi_da INT,
    si_so_hien_tai INT DEFAULT 0,
    trang_thai VARCHAR(10) CHECK (trang_thai IN ('mo', 'dong')) DEFAULT 'dong',
    thoi_gian_mo_dang_ky DATETIME, 
    thoi_gian_dong_dang_ky DATETIME,
    ngay_bat_dau_hoc DATE,
    ngay_ket_thuc_hoc DATE,
    CONSTRAINT chk_siso CHECK (si_so_hien_tai <= si_so_toi_da),
    FOREIGN KEY (ma_mh) REFERENCES MONHOC(ma_mh),
    FOREIGN KEY (ma_gv) REFERENCES GIANGVIEN(ma_gv)
);

-- Dữ liệu test: Các lớp mở đăng ký, hết hạn, và chưa tới hạn
INSERT INTO LOPHOCPHAN (ma_lop, ma_mh, ma_gv, hoc_ky, nam_hoc, si_so_toi_da, trang_thai, thoi_gian_mo_dang_ky, thoi_gian_dong_dang_ky, ngay_bat_dau_hoc, ngay_ket_thuc_hoc) VALUES
-- Lớp Đang Mở Đăng ký
('JAVA001', 'JAVA', 'GV001', 'HK1', '2026-2027', 50, 'mo', '2026-06-01 00:00:00', '2026-06-30 23:59:59', '2026-07-05', '2026-10-05'),
-- Lớp Đã Hết Hạn Đăng ký
('SQL001', 'SQL', 'GV002', 'HK1', '2026-2027', 40, 'mo', '2026-05-01 00:00:00', '2026-05-15 23:59:59', '2026-06-01', '2026-09-01'),
-- Lớp Chưa Tới Hạn Đăng ký
('KTE001', 'KTE', 'GV003', 'HK1', '2026-2027', 60, 'dong', '2026-08-01 00:00:00', '2026-08-15 23:59:59', '2026-09-05', '2026-12-05');

/* ==================== BẢNG ĐĂNG KÝ LỚP ==================== */
-- ĐÃ CẬP NHẬT: Thêm thời gian thực ngay_dang_ky
CREATE TABLE DANGKY_LOP (
    ma_sv VARCHAR(20),
    ma_lop VARCHAR(20),
    ngay_dang_ky DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(ma_sv, ma_lop),
    FOREIGN KEY(ma_sv) REFERENCES SINHVIEN(ma_sv),
    FOREIGN KEY(ma_lop) REFERENCES LOPHOCPHAN(ma_lop)
);

INSERT INTO DANGKY_LOP (ma_sv, ma_lop, ngay_dang_ky) VALUES
('SV001', 'JAVA001', '2026-06-02 10:15:00'),
('SV002', 'JAVA001', '2026-06-03 08:30:00');

/* ==================== TRIGGERS CHO SĨ SỐ ==================== */

-- ĐÃ CẬP NHẬT: Thêm UQ_Diem_SV_Lop để tránh trùng lặp
CREATE TABLE DIEM (
    id INT IDENTITY(1,1) PRIMARY KEY,
    ma_sv VARCHAR(20) NOT NULL,
    ma_lop VARCHAR(20) NOT NULL,
    diem_qua_trinh FLOAT,
    diem_giua_ky FLOAT,
    diem_cuoi_ky FLOAT,
    diem_tong_ket FLOAT,
    diem_chu VARCHAR(5),
    xep_loai NVARCHAR(20),
    CONSTRAINT UQ_Diem_SV_Lop UNIQUE(ma_sv, ma_lop),
    FOREIGN KEY (ma_sv) REFERENCES SINHVIEN(ma_sv),
    FOREIGN KEY (ma_lop) REFERENCES LOPHOCPHAN(ma_lop)
);

INSERT INTO DIEM (ma_sv, ma_lop, diem_qua_trinh, diem_giua_ky, diem_cuoi_ky, diem_tong_ket, diem_chu, xep_loai) VALUES
('SV001', 'JAVA001', 8.5, 7.5, 9.0, 8.4, 'B', N'Giỏi'),
('SV002', 'JAVA001', 7.0, 6.5, 7.5, 7.1, 'B', N'Khá');
GO
