

-- Tạo mới database
CREATE DATABASE QuanLyDiemSinhVien;

USE QuanLyDiemSinhVien;


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
update TAIKHOAN
set online_status='Offline'
INSERT INTO TAIKHOAN (ten_dang_nhap, mat_khau, loai_nguoi_dung, online_status) VALUES
-- Admin
('admin', 'admin123', 'Admin', 'Offline'),
-- Giảng viên
('GV001', '123456', 'GiangVien', 'Offline'),
('GV002', '123456', 'GiangVien', 'Offline'),
('GV003', '123456', 'GiangVien', 'Offline'),
('GV004', '123456', 'GiangVien', 'Offline'),
('GV005', '123456', 'GiangVien', 'Offline'),
---Sinh viên 
('SV001', '123456', 'SinhVien', 'Offline'),
('SV002', '123456', 'SinhVien', 'Offline'),
('SV003', '123456', 'SinhVien', 'Offline'),
('SV004', '123456', 'SinhVien', 'Offline'),
('SV005', '123456', 'SinhVien', 'Offline'),

('SV006', '123456', 'SinhVien', 'Offline'),
('SV007', '123456', 'SinhVien', 'Offline'),
('SV008', '123456', 'SinhVien', 'Offline'),
('SV009', '123456', 'SinhVien', 'Offline'),
('SV010', '123456', 'SinhVien', 'Offline'),
('SV011', '123456', 'SinhVien', 'Offline'),
('SV012', '123456', 'SinhVien', 'Offline'),
('SV013', '123456', 'SinhVien', 'Offline'),
('SV014', '123456', 'SinhVien', 'Offline'),
('SV015', '123456', 'SinhVien', 'Offline'),
('SV016', '123456', 'SinhVien', 'Offline'),
('SV017', '123456', 'SinhVien', 'Offline'),
('SV018', '123456', 'SinhVien', 'Offline'),
('SV019', '123456', 'SinhVien', 'Offline'),
('SV020', '123456', 'SinhVien', 'Offline'),
('SV021', '123456', 'SinhVien', 'Offline'),
('SV022', '123456', 'SinhVien', 'Offline'),
('SV023', '123456', 'SinhVien', 'Offline'),
('SV024', '123456', 'SinhVien', 'Offline'),
('SV025', '123456', 'SinhVien', 'Offline'),
('SV026', '123456', 'SinhVien', 'Offline'),
('SV027', '123456', 'SinhVien', 'Offline'),
('SV028', '123456', 'SinhVien', 'Offline'),
('SV029', '123456', 'SinhVien', 'Offline'),
('SV030', '123456', 'SinhVien', 'Offline'),
('SV031', '123456', 'SinhVien', 'Offline'),
('SV032', '123456', 'SinhVien', 'Offline'),
('SV033', '123456', 'SinhVien', 'Offline'),
('SV034', '123456', 'SinhVien', 'Offline'),
('SV035', '123456', 'SinhVien', 'Offline'),
('SV036', '123456', 'SinhVien', 'Offline'),
('SV037', '123456', 'SinhVien', 'Offline'),
('SV038', '123456', 'SinhVien', 'Offline'),
('SV039', '123456', 'SinhVien', 'Offline'),
('SV040', '123456', 'SinhVien', 'Offline'),
('SV041', '123456', 'SinhVien', 'Offline'),
('SV042', '123456', 'SinhVien', 'Offline'),
('SV043', '123456', 'SinhVien', 'Offline'),
('SV044', '123456', 'SinhVien', 'Offline'),
('SV045', '123456', 'SinhVien', 'Offline'),
('SV046', '123456', 'SinhVien', 'Offline'),
('SV047', '123456', 'SinhVien', 'Offline'),
('SV048', '123456', 'SinhVien', 'Offline'),
('SV049', '123456', 'SinhVien', 'Offline'),
('SV050', '123456', 'SinhVien', 'Offline'),
('SV051', '123456', 'SinhVien', 'Offline'),
('SV052', '123456', 'SinhVien', 'Offline'),
('SV053', '123456', 'SinhVien', 'Offline'),
('SV054', '123456', 'SinhVien', 'Offline'),
('SV055', '123456', 'SinhVien', 'Offline'),
('SV056', '123456', 'SinhVien', 'Offline'),
('SV057', '123456', 'SinhVien', 'Offline'),
('SV058', '123456', 'SinhVien', 'Offline'),
('SV059', '123456', 'SinhVien', 'Offline'),
('SV060', '123456', 'SinhVien', 'Offline'),
('SV061', '123456', 'SinhVien', 'Offline'),
('SV062', '123456', 'SinhVien', 'Offline'),
('SV063', '123456', 'SinhVien', 'Offline'),
('SV064', '123456', 'SinhVien', 'Offline'),
('SV065', '123456', 'SinhVien', 'Offline'),
('SV066', '123456', 'SinhVien', 'Offline'),
('SV067', '123456', 'SinhVien', 'Offline'),
('SV068', '123456', 'SinhVien', 'Offline'),
('SV069', '123456', 'SinhVien', 'Offline'),
('SV070', '123456', 'SinhVien', 'Offline'),
('SV071', '123456', 'SinhVien', 'Offline'),
('SV072', '123456', 'SinhVien', 'Offline'),
('SV073', '123456', 'SinhVien', 'Offline'),
('SV074', '123456', 'SinhVien', 'Offline'),
('SV075', '123456', 'SinhVien', 'Offline'),
('SV076', '123456', 'SinhVien', 'Offline'),
('SV077', '123456', 'SinhVien', 'Offline'),
('SV078', '123456', 'SinhVien', 'Offline'),
('SV079', '123456', 'SinhVien', 'Offline'),
('SV080', '123456', 'SinhVien', 'Offline'),
('SV081', '123456', 'SinhVien', 'Offline'),
('SV082', '123456', 'SinhVien', 'Offline'),
('SV083', '123456', 'SinhVien', 'Offline'),
('SV084', '123456', 'SinhVien', 'Offline'),
('SV085', '123456', 'SinhVien', 'Offline'),
('SV086', '123456', 'SinhVien', 'Offline'),
('SV087', '123456', 'SinhVien', 'Offline'),
('SV088', '123456', 'SinhVien', 'Offline'),
('SV089', '123456', 'SinhVien', 'Offline'),
('SV090', '123456', 'SinhVien', 'Offline'),
('SV091', '123456', 'SinhVien', 'Offline'),
('SV092', '123456', 'SinhVien', 'Offline'),
('SV093', '123456', 'SinhVien', 'Offline'),
('SV094', '123456', 'SinhVien', 'Offline'),
('SV095', '123456', 'SinhVien', 'Offline'),
('SV096', '123456', 'SinhVien', 'Offline'),
('SV097', '123456', 'SinhVien', 'Offline'),
('SV098', '123456', 'SinhVien', 'Offline'),
('SV099', '123456', 'SinhVien', 'Offline'),
('SV100', '123456', 'SinhVien', 'Offline');




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
Update SINHVIEN
set email='bacxnt2005@gmail.com';
INSERT INTO SINHVIEN (ma_sv, ho_ten, gioi_tinh, ngay_sinh, email, so_dien_thoai, dia_chi, ma_khoa, ten_dang_nhap) VALUES
('SV001', N'Nguyễn Văn An', N'Nam', '2003-01-01', 'sv001@example.com', '0912345001', N'Hà Nội', 'CNTT', 'SV001'),
('SV002', N'Trần Thị Bích', N'Nữ', '2003-01-02', 'sv002@example.com', '0912345002', N'Hà Nội', 'CNTT', 'SV002'),
('SV003', N'Lê Văn Bình', N'Nam', '2003-01-03', 'sv003@example.com', '0912345003', N'Hà Nội', 'KT', 'SV003'),
('SV004', N'Phạm Thị Chi', N'Nữ', '2003-01-04', 'sv004@example.com', '0912345004', N'Hà Nội', 'NN', 'SV004'),
('SV005', N'Hoàng Văn Dũng', N'Nam', '2003-01-05', 'sv005@example.com', '0912345005', N'Hà Nội', 'CK', 'SV005'),
('SV006', N'Nguyễn Thị Em', N'Nữ', '2003-01-06', 'sv006@example.com', '0912345006', N'Hà Nội', 'XD', 'SV006'),
('SV007', N'Trần Văn Hoàng', N'Nam', '2003-01-07', 'sv007@example.com', '0912345007', N'Hà Nội', 'CNTT', 'SV007'),
('SV008', N'Lê Thị Giang', N'Nữ', '2003-01-08', 'sv008@example.com', '0912345008', N'Hà Nội', 'KT', 'SV008'),
('SV009', N'Phạm Văn Hùng', N'Nam', '2003-01-09', 'sv009@example.com', '0912345009', N'Hà Nội', 'NN', 'SV009'),
('SV010', N'Hoàng Thị Hạnh', N'Nữ', '2003-01-10', 'sv010@example.com', '0912345010', N'Hà Nội', 'CK', 'SV010'),
('SV011', N'Nguyễn Văn Khoa', N'Nam', '2003-01-11', 'sv011@example.com', '0912345011', N'Hà Nội', 'XD', 'SV011'),
('SV012', N'Trần Thị Lan', N'Nữ', '2003-01-12', 'sv012@example.com', '0912345012', N'Hà Nội', 'CNTT', 'SV012'),
('SV013', N'Lê Văn Minh', N'Nam', '2003-01-13', 'sv013@example.com', '0912345013', N'Hà Nội', 'KT', 'SV013'),
('SV014', N'Phạm Thị Ngọc', N'Nữ', '2003-01-14', 'sv014@example.com', '0912345014', N'Hà Nội', 'NN', 'SV014'),
('SV015', N'Hoàng Văn Phúc', N'Nam', '2003-01-15', 'sv015@example.com', '0912345015', N'Hà Nội', 'CK', 'SV015'),
('SV016', N'Nguyễn Thị Quỳnh', N'Nữ', '2003-01-16', 'sv016@example.com', '0912345016', N'Hà Nội', 'XD', 'SV016'),
('SV017', N'Trần Văn Sơn', N'Nam', '2003-01-17', 'sv017@example.com', '0912345017', N'Hà Nội', 'CNTT', 'SV017'),
('SV018', N'Lê Thị Thanh', N'Nữ', '2003-01-18', 'sv018@example.com', '0912345018', N'Hà Nội', 'KT', 'SV018'),
('SV019', N'Phạm Văn Tuấn', N'Nam', '2003-01-19', 'sv019@example.com', '0912345019', N'Hà Nội', 'NN', 'SV019'),
('SV020', N'Hoàng Thị Vân', N'Nữ', '2003-01-20', 'sv020@example.com', '0912345020', N'Hà Nội', 'CK', 'SV020'),
('SV021', N'Nguyễn Văn Hải', N'Nam', '2003-01-21', 'sv021@example.com', '0912345021', N'Hà Nội', 'XD', 'SV021'),
('SV022', N'Trần Thị Hoa', N'Nữ', '2003-01-22', 'sv022@example.com', '0912345022', N'Hà Nội', 'CNTT', 'SV022'),
('SV023', N'Lê Văn Nam', N'Nam', '2003-01-23', 'sv023@example.com', '0912345023', N'Hà Nội', 'KT', 'SV023'),
('SV024', N'Phạm Thị Phương', N'Nữ', '2003-01-24', 'sv024@example.com', '0912345024', N'Hà Nội', 'NN', 'SV024'),
('SV025', N'Hoàng Văn Long', N'Nam', '2003-01-25', 'sv025@example.com', '0912345025', N'Hà Nội', 'CK', 'SV025'),
('SV026', N'Nguyễn Thị Mai', N'Nữ', '2003-01-26', 'sv026@example.com', '0912345026', N'Hà Nội', 'XD', 'SV026'),
('SV027', N'Trần Văn Phát', N'Nam', '2003-01-27', 'sv027@example.com', '0912345027', N'Hà Nội', 'CNTT', 'SV027'),
('SV028', N'Lê Thị Hồng', N'Nữ', '2003-01-28', 'sv028@example.com', '0912345028', N'Hà Nội', 'KT', 'SV028'),
('SV029', N'Phạm Văn Thắng', N'Nam', '2003-01-29', 'sv029@example.com', '0912345029', N'Hà Nội', 'NN', 'SV029'),
('SV030', N'Hoàng Thị Lan', N'Nữ', '2003-01-30', 'sv030@example.com', '0912345030', N'Hà Nội', 'CK', 'SV030'),
('SV031', N'Nguyễn Văn Phú', N'Nam', '2003-02-01', 'sv031@example.com', '0912345031', N'Hà Nội', 'XD', 'SV031'),
('SV032', N'Trần Thị Linh', N'Nữ', '2003-02-02', 'sv032@example.com', '0912345032', N'Hà Nội', 'CNTT', 'SV032'),
('SV033', N'Lê Văn Quang', N'Nam', '2003-02-03', 'sv033@example.com', '0912345033', N'Hà Nội', 'KT', 'SV033'),
('SV034', N'Phạm Thị Hạnh', N'Nữ', '2003-02-04', 'sv034@example.com', '0912345034', N'Hà Nội', 'NN', 'SV034'),
('SV035', N'Hoàng Văn Sơn', N'Nam', '2003-02-05', 'sv035@example.com', '0912345035', N'Hà Nội', 'CK', 'SV035'),
('SV036', N'Nguyễn Thị Thu', N'Nữ', '2003-02-06', 'sv036@example.com', '0912345036', N'Hà Nội', 'XD', 'SV036'),
('SV037', N'Trần Văn Duy', N'Nam', '2003-02-07', 'sv037@example.com', '0912345037', N'Hà Nội', 'CNTT', 'SV037'),
('SV038', N'Lê Thị Mai', N'Nữ', '2003-02-08', 'sv038@example.com', '0912345038', N'Hà Nội', 'KT', 'SV038'),
('SV039', N'Phạm Văn Khải', N'Nam', '2003-02-09', 'sv039@example.com', '0912345039', N'Hà Nội', 'NN', 'SV039'),
('SV040', N'Hoàng Thị Nhung', N'Nữ', '2003-02-10', 'sv040@example.com', '0912345040', N'Hà Nội', 'CK', 'SV040'),
('SV041', N'Nguyễn Văn Phước', N'Nam', '2003-02-11', 'sv041@example.com', '0912345041', N'Hà Nội', 'XD', 'SV041'),
('SV042', N'Trần Thị Hương', N'Nữ', '2003-02-12', 'sv042@example.com', '0912345042', N'Hà Nội', 'CNTT', 'SV042'),
('SV043', N'Lê Văn Hải', N'Nam', '2003-02-13', 'sv043@example.com', '0912345043', N'Hà Nội', 'KT', 'SV043'),
('SV044', N'Phạm Thị Hằng', N'Nữ', '2003-02-14', 'sv044@example.com', '0912345044', N'Hà Nội', 'NN', 'SV044'),
('SV045', N'Hoàng Văn Duy', N'Nam', '2003-02-15', 'sv045@example.com', '0912345045', N'Hà Nội', 'CK', 'SV045'),
('SV046', N'Nguyễn Thị Trang', N'Nữ', '2003-02-16', 'sv046@example.com', '0912345046', N'Hà Nội', 'XD', 'SV046'),
('SV047', N'Trần Văn Nam', N'Nam', '2003-02-17', 'sv047@example.com', '0912345047', N'Hà Nội', 'CNTT', 'SV047'),
('SV048', N'Lê Thị Phương', N'Nữ', '2003-02-18', 'sv048@example.com', '0912345048', N'Hà Nội', 'KT', 'SV048'),
('SV049', N'Phạm Văn Thành', N'Nam', '2003-02-19', 'sv049@example.com', '0912345049', N'Hà Nội', 'NN', 'SV049'),
('SV050', N'Hoàng Thị Yến', N'Nữ', '2003-02-20', 'sv050@example.com', '0912345050', N'Hà Nội', 'CK', 'SV050'),
('SV051', N'Nguyễn Văn Bảo', N'Nam', '2003-02-21', 'sv051@example.com', '0912345051', N'Hà Nội', 'XD', 'SV051'),
('SV052', N'Trần Thị Thảo', N'Nữ', '2003-02-22', 'sv052@example.com', '0912345052', N'Hà Nội', 'CNTT', 'SV052'),
('SV053', N'Lê Văn Dương', N'Nam', '2003-02-23', 'sv053@example.com', '0912345053', N'Hà Nội', 'KT', 'SV053'),
('SV054', N'Phạm Thị Thuý', N'Nữ', '2003-02-24', 'sv054@example.com', '0912345054', N'Hà Nội', 'NN', 'SV054'),
('SV055', N'Hoàng Văn Toàn', N'Nam', '2003-02-25', 'sv055@example.com', '0912345055', N'Hà Nội', 'CK', 'SV055'),
('SV056', N'Nguyễn Thị Hồng', N'Nữ', '2003-02-26', 'sv056@example.com', '0912345056', N'Hà Nội', 'XD', 'SV056'),
('SV057', N'Trần Văn Khánh', N'Nam', '2003-02-27', 'sv057@example.com', '0912345057', N'Hà Nội', 'CNTT', 'SV057'),
('SV058', N'Lê Thị Kiều', N'Nữ', '2003-02-28', 'sv058@example.com', '0912345058', N'Hà Nội', 'KT', 'SV058'),
('SV059', N'Phạm Văn Lâm', N'Nam', '2003-03-01', 'sv059@example.com', '0912345059', N'Hà Nội', 'NN', 'SV059'),
('SV060', N'Hoàng Thị Thanh', N'Nữ', '2003-03-02', 'sv060@example.com', '0912345060', N'Hà Nội', 'CK', 'SV060'),
('SV061', N'Nguyễn Văn Đức', N'Nam', '2003-03-03', 'sv061@example.com', '0912345061', N'Hà Nội', 'XD', 'SV061'),
('SV062', N'Trần Thị Dung', N'Nữ', '2003-03-04', 'sv062@example.com', '0912345062', N'Hà Nội', 'CNTT', 'SV062'),
('SV063', N'Lê Văn Cường', N'Nam', '2003-03-05', 'sv063@example.com', '0912345063', N'Hà Nội', 'KT', 'SV063'),
('SV064', N'Phạm Thị Lan', N'Nữ', '2003-03-06', 'sv064@example.com', '0912345064', N'Hà Nội', 'NN', 'SV064'),
('SV065', N'Hoàng Văn Nam', N'Nam', '2003-03-07', 'sv065@example.com', '0912345065', N'Hà Nội', 'CK', 'SV065'),
('SV066', N'Nguyễn Thị Ngân', N'Nữ', '2003-03-08', 'sv066@example.com', '0912345066', N'Hà Nội', 'XD', 'SV066'),
('SV067', N'Trần Văn Quý', N'Nam', '2003-03-09', 'sv067@example.com', '0912345067', N'Hà Nội', 'CNTT', 'SV067'),
('SV068', N'Lê Thị Diệp', N'Nữ', '2003-03-10', 'sv068@example.com', '0912345068', N'Hà Nội', 'KT', 'SV068'),
('SV069', N'Phạm Văn Phú', N'Nam', '2003-03-11', 'sv069@example.com', '0912345069', N'Hà Nội', 'NN', 'SV069'),
('SV070', N'Hoàng Thị Mai', N'Nữ', '2003-03-12', 'sv070@example.com', '0912345070', N'Hà Nội', 'CK', 'SV070'),
('SV071', N'Nguyễn Văn Tùng', N'Nam', '2003-03-13', 'sv071@example.com', '0912345071', N'Hà Nội', 'XD', 'SV071'),
('SV072', N'Trần Thị Như', N'Nữ', '2003-03-14', 'sv072@example.com', '0912345072', N'Hà Nội', 'CNTT', 'SV072'),
('SV073', N'Lê Văn Hòa', N'Nam', '2003-03-15', 'sv073@example.com', '0912345073', N'Hà Nội', 'KT', 'SV073'),
('SV074', N'Phạm Thị Hiền', N'Nữ', '2003-03-16', 'sv074@example.com', '0912345074', N'Hà Nội', 'NN', 'SV074'),
('SV075', N'Hoàng Văn Phước', N'Nam', '2003-03-17', 'sv075@example.com', '0912345075', N'Hà Nội', 'CK', 'SV075'),
('SV076', N'Nguyễn Thị Hằng', N'Nữ', '2003-03-18', 'sv076@example.com', '0912345076', N'Hà Nội', 'XD', 'SV076'),
('SV077', N'Trần Văn Kiên', N'Nam', '2003-03-19', 'sv077@example.com', '0912345077', N'Hà Nội', 'CNTT', 'SV077'),
('SV078', N'Lê Thị Ngọc', N'Nữ', '2003-03-20', 'sv078@example.com', '0912345078', N'Hà Nội', 'KT', 'SV078'),
('SV079', N'Phạm Văn Sơn', N'Nam', '2003-03-21', 'sv079@example.com', '0912345079', N'Hà Nội', 'NN', 'SV079'),
('SV080', N'Hoàng Thị Hòa', N'Nữ', '2003-03-22', 'sv080@example.com', '0912345080', N'Hà Nội', 'CK', 'SV080'),
('SV081', N'Nguyễn Văn Lộc', N'Nam', '2003-03-23', 'sv081@example.com', '0912345081', N'Hà Nội', 'XD', 'SV081'),
('SV082', N'Trần Thị Thanh', N'Nữ', '2003-03-24', 'sv082@example.com', '0912345082', N'Hà Nội', 'CNTT', 'SV082'),
('SV083', N'Lê Văn Sơn', N'Nam', '2003-03-25', 'sv083@example.com', '0912345083', N'Hà Nội', 'KT', 'SV083'),
('SV084', N'Phạm Thị Dung', N'Nữ', '2003-03-26', 'sv084@example.com', '0912345084', N'Hà Nội', 'NN', 'SV084'),
('SV085', N'Hoàng Văn Hải', N'Nam', '2003-03-27', 'sv085@example.com', '0912345085', N'Hà Nội', 'CK', 'SV085'),
('SV086', N'Nguyễn Thị Loan', N'Nữ', '2003-03-28', 'sv086@example.com', '0912345086', N'Hà Nội', 'XD', 'SV086'),
('SV087', N'Trần Văn Vinh', N'Nam', '2003-03-29', 'sv087@example.com', '0912345087', N'Hà Nội', 'CNTT', 'SV087'),
('SV088', N'Lê Thị Hạnh', N'Nữ', '2003-03-30', 'sv088@example.com', '0912345088', N'Hà Nội', 'KT', 'SV088'),
('SV089', N'Phạm Văn Phong', N'Nam', '2003-03-31', 'sv089@example.com', '0912345089', N'Hà Nội', 'NN', 'SV089'),
('SV090', N'Hoàng Thị Thu', N'Nữ', '2003-04-01', 'sv090@example.com', '0912345090', N'Hà Nội', 'CK', 'SV090'),
('SV091', N'Nguyễn Văn Tài', N'Nam', '2003-04-02', 'sv091@example.com', '0912345091', N'Hà Nội', 'XD', 'SV091'),
('SV092', N'Trần Thị Ngân', N'Nữ', '2003-04-03', 'sv092@example.com', '0912345092', N'Hà Nội', 'CNTT', 'SV092'),
('SV093', N'Lê Văn Lợi', N'Nam', '2003-04-04', 'sv093@example.com', '0912345093', N'Hà Nội', 'KT', 'SV093'),
('SV094', N'Phạm Thị Hương', N'Nữ', '2003-04-05', 'sv094@example.com', '0912345094', N'Hà Nội', 'NN', 'SV094'),
('SV095', N'Hoàng Văn Khang', N'Nam', '2003-04-06', 'sv095@example.com', '0912345095', N'Hà Nội', 'CK', 'SV095'),
('SV096', N'Nguyễn Thị Kim', N'Nữ', '2003-04-07', 'sv096@example.com', '0912345096', N'Hà Nội', 'XD', 'SV096'),
('SV097', N'Trần Văn Hoà', N'Nam', '2003-04-08', 'sv097@example.com', '0912345097', N'Hà Nội', 'CNTT', 'SV097'),
('SV098', N'Lê Thị Lan', N'Nữ', '2003-04-09', 'sv098@example.com', '0912345098', N'Hà Nội', 'KT', 'SV098'),
('SV099', N'Phạm Văn Trường', N'Nam', '2003-04-10', 'sv099@example.com', '0912345099', N'Hà Nội', 'NN', 'SV099'),
('SV100', N'Hoàng Thị Thanh', N'Nữ', '2003-04-11', 'sv100@example.com', '0912345100', N'Hà Nội', 'CK', 'SV100');


update SINHVIEN
set email='nguyenhieudocla123a@gmail.com'
update TAIKHOAN
set online_status='offline'
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
('GV003', N'ThS Lê Văn Minh', 'gv003@school.edu', '0987654323', 'KT', 'GV003'),
('GV004', N'TS Phạm Thị Hương', 'gv004@school.edu', '0987654324', 'NN', 'GV004'),
('GV005', N'ThS Hoàng Văn Tú', 'gv005@school.edu', '0987654325', 'CK', 'GV005');



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
('JAVA', N'Lập trình Java', 3, N'Môn học lập trình Java cơ bản và nâng cao', 'CNTT'),
('SQL', N'Cơ sở dữ liệu', 3, N'Môn học về SQL và quản trị database', 'CNTT'),
('WEB', N'Lập trình Web', 3, N'Môn học lập trình web với HTML, CSS, JavaScript', 'CNTT'),
('KTE', N'Kinh tế học', 2, N'Môn học kinh tế căn bản', 'KT'),
('TA', N'Tiếng Anh', 2, N'Môn học tiếng Anh giao tiếp', 'NN'),
('FAN', N'Tiếng Pháp', 2, N'Môn học tiếng Pháp căn bản', 'NN'),
('CAD', N'Vẽ kỹ thuật', 3, N'Môn học vẽ kỹ thuật AutoCAD', 'CK');

INSERT INTO MONHOC (ma_mh, ten_mh, so_tin_chi, ma_khoa, mo_ta) VALUES
-- ===== Công Nghệ Thông Tin =====
('CNTT101', N'Toán cao cấp A1', 3, 'CNTT', N'Cung cấp kiến thức toán học nền tảng bao gồm hàm số, đạo hàm, tích phân và ứng dụng trong kỹ thuật.'),
('CNTT102', N'Toán rời rạc', 3, 'CNTT', N'Giới thiệu logic toán, tập hợp, quan hệ, đồ thị và ứng dụng trong khoa học máy tính.'),
('CNTT103', N'Lập trình C++', 3, 'CNTT', N'Học về cú pháp C++, cấu trúc điều khiển, hàm, mảng, con trỏ và lập trình hướng đối tượng.'),
('CNTT104', N'Cấu trúc dữ liệu & Giải thuật', 4, 'CNTT', N'Trình bày các cấu trúc dữ liệu như stack, queue, list, tree, graph và các giải thuật tìm kiếm, sắp xếp.'),
('CNTT105', N'Cơ sở dữ liệu', 3, 'CNTT', N'Học mô hình dữ liệu quan hệ, SQL, thiết kế cơ sở dữ liệu và tối ưu truy vấn.'),
('CNTT106', N'Mạng máy tính', 3, 'CNTT', N'Giới thiệu mô hình OSI, TCP/IP, định tuyến, switching và cấu hình mạng cơ bản.'),
('CNTT107', N'Hệ điều hành', 3, 'CNTT', N'Nghiên cứu quản lý tiến trình, bộ nhớ, file system, lập lịch CPU và giao tiếp hệ thống.'),
('CNTT108', N'Lập trình hướng đối tượng Java', 3, 'CNTT', N'Tập trung vào class, object, kế thừa, đa hình, interface và xử lý ngoại lệ trong Java.'),
('CNTT109', N'Phân tích thiết kế hệ thống', 3, 'CNTT', N'Hệ thống hóa quy trình phân tích yêu cầu, thiết kế hệ thống thông tin theo mô hình UML.'),

-- ===== Kinh Tế =====
('KT101', N'Kinh tế vi mô', 3, 'KT', N'Nghiên cứu hành vi của cá nhân, doanh nghiệp và cách thị trường vận hành.'),
('KT102', N'Kinh tế vĩ mô', 3, 'KT', N'Phân tích tăng trưởng kinh tế, lạm phát, thất nghiệp và chính sách tài khóa - tiền tệ.'),
('KT103', N'Nguyên lý kế toán', 3, 'KT', N'Cung cấp kiến thức nền về tài sản, công nợ, vốn, báo cáo tài chính và quy trình kế toán.'),
('KT104', N'Tài chính doanh nghiệp', 3, 'KT', N'Phân tích tài chính, quản trị vốn, đầu tư và chiến lược tài chính cho doanh nghiệp.'),
('KT105', N'Quản trị học', 3, 'KT', N'Tổng quan về quản lý, lập kế hoạch, lãnh đạo và kiểm soát trong tổ chức.'),

-- ===== Ngoại Ngữ =====
('NN101', N'English 1', 3, 'NN', N'Học các kỹ năng nghe, nói, đọc, viết cơ bản cho trình độ A2.'),
('NN102', N'English 2', 3, 'NN', N'Phát triển kỹ năng tiếng Anh lên mức B1, tập trung nghe – nói.'),
('NN103', N'Ngữ pháp tiếng Anh', 3, 'NN', N'Nghiên cứu về cấu trúc câu, thì, mệnh đề quan hệ, câu điều kiện và ngữ pháp nâng cao.'),
('NN104', N'Phiên dịch 1', 3, 'NN', N'Kỹ năng phiên dịch cơ bản Anh – Việt và Việt – Anh.'),
('NN105', N'Biên dịch 1', 3, 'NN', N'Kỹ năng biên dịch tài liệu chuyên ngành và văn bản hành chính.'),

-- ===== Cơ Khí =====
('CK101', N'Vẽ kỹ thuật', 3, 'CK', N'Cung cấp kiến thức đọc bản vẽ kỹ thuật, hình chiếu, tiêu chuẩn kỹ thuật.'),
('CK102', N'Cơ lý thuyết', 4, 'CK', N'Nghiên cứu tĩnh học, động học và động lực học vật rắn.'),
('CK103', N'Sức bền vật liệu', 3, 'CK', N'Phân tích ứng suất, biến dạng, mô đun đàn hồi, thanh chịu kéo - nén - uốn.'),
('CK104', N'Công nghệ chế tạo máy', 3, 'CK', N'Giới thiệu các công nghệ gia công cơ khí như tiện, phay, mài, đúc, hàn.'),
('CK105', N'Nhiệt động lực học', 3, 'CK', N'Nghiên cứu tính chất khí, nhiệt, chu trình nhiệt và ứng dụng kỹ thuật.'),

-- ===== Xây Dựng =====
('XD101', N'Vật liệu xây dựng', 3, 'XD', N'Phân tích tính chất cơ học, hóa học của bê tông, thép, gạch, vật liệu mới.'),
('XD102', N'Trắc địa đại cương', 3, 'XD', N'Nghiên cứu đo đạc địa hình, bản đồ, sai số và thiết bị trắc địa.'),
('XD103', N'Cơ học kết cấu', 4, 'XD', N'Phân tích nội lực, chuyển vị và kiểm tra độ bền kết cấu công trình.'),
('XD104', N'Kết cấu bê tông cốt thép', 4, 'XD', N'Thiết kế và tính toán cấu kiện bê tông cốt thép theo tiêu chuẩn Việt Nam.'),
('XD105', N'Địa chất công trình', 3, 'XD', N'Nghiên cứu tính chất địa chất, nền móng và phương pháp khảo sát địa chất.');

-- Tạo proc để lấy tất cả các sinh viên của Khoa
create proc proc1 @khoa varchar(10) 
as 
select ma_sv , ho_ten
from SINHVIEN
where ma_khoa=@khoa


/* ==================== BẢNG LỚP HỌC PHẦN ==================== */
CREATE TABLE LOPHOCPHAN (
    ma_lop VARCHAR(20) PRIMARY KEY,
    ma_mh VARCHAR(20) NOT NULL,
    ma_gv VARCHAR(20),
    hoc_ky VARCHAR(10) NOT NULL,
    nam_hoc VARCHAR(10) NOT NULL,
    si_so_toi_da INT,
    FOREIGN KEY (ma_mh) REFERENCES MONHOC(ma_mh),
    FOREIGN KEY (ma_gv) REFERENCES GIANGVIEN(ma_gv)
);
ALTER TABLE LOPHOCPHAN
ADD si_so_hien_tai INT,
    CONSTRAINT chk_siso CHECK (si_so_hien_tai <= si_so_toi_da);
	ALTER TABLE LOPHOCPHAN
ADD trang_thai VARCHAR(10) CHECK (trang_thai IN ('mo', 'dong')) DEFAULT 'dong';
UPDATE LOPHOCPHAN
SET trang_thai = 'dong';

Update LOPHOCPHAN
set si_so_hien_tai=0
where si_so_hien_tai is  NULL 

update LOPHOCPHAN
set trang_thai='mo';

INSERT INTO LOPHOCPHAN (ma_lop, ma_mh, ma_gv, hoc_ky, nam_hoc, si_so_toi_da) VALUES
('JAVA001', 'JAVA', 'GV001', 'HK1', '2024-2025', 50),
('JAVA002', 'JAVA', 'GV002', 'HK1', '2024-2025', 45),
('SQL001', 'SQL', 'GV001', 'HK1', '2024-2025', 40),
('WEB001', 'WEB', 'GV002', 'HK1', '2024-2025', 35),
('KTE001', 'KTE', 'GV003', 'HK1', '2024-2025', 60),
('TA001', 'TA', 'GV004', 'HK1', '2024-2025', 30),
('FAN001', 'FAN', 'GV004', 'HK1', '2024-2025', 25),
('CAD001', 'CAD', 'GV005', 'HK1', '2024-2025', 40);

-- ===== Lớp học phần Công Nghệ Thông Tin năm học 2023-2024 =====
INSERT INTO LOPHOCPHAN (ma_lop, ma_mh, ma_gv, hoc_ky, nam_hoc, si_so_toi_da) VALUES
('CNTT101-01', 'CNTT101', 'GV001', 'HK1', '2023-2024', 60),
('CNTT101-02', 'CNTT101', 'GV002', 'HK1', '2023-2024', 60),
('CNTT102-01', 'CNTT102', 'GV003', 'HK1', '2023-2024', 60),
('CNTT102-02', 'CNTT102', 'GV004', 'HK1', '2023-2024', 60),
('CNTT103-01', 'CNTT103', 'GV005', 'HK2', '2023-2024', 60),
('CNTT103-02', 'CNTT103', 'GV001', 'HK2', '2023-2024', 60),
('CNTT104-01', 'CNTT104', 'GV002', 'HK2', '2023-2024', 60),
('CNTT104-02', 'CNTT104', 'GV003', 'HK2', '2023-2024', 60),
('CNTT105-01', 'CNTT105', 'GV004', 'HK1', '2023-2024', 60),
('CNTT105-02', 'CNTT105', 'GV005', 'HK1', '2023-2024', 60);
-- ===== Lớp học phần Kinh Tế năm học 2023-2024 =====
INSERT INTO LOPHOCPHAN (ma_lop, ma_mh, ma_gv, hoc_ky, nam_hoc, si_so_toi_da) VALUES
('KT101-01', 'KT101', 'GV001', 'HK1', '2023-2024', 60),
('KT101-02', 'KT101', 'GV002', 'HK1', '2023-2024', 60),
('KT102-01', 'KT102', 'GV003', 'HK2', '2023-2024', 60),
('KT102-02', 'KT102', 'GV004', 'HK2', '2023-2024', 60),
('KT103-01', 'KT103', 'GV005', 'HK1', '2023-2024', 60),
('KT103-02', 'KT103', 'GV001', 'HK1', '2023-2024', 60),
('KT104-01', 'KT104', 'GV002', 'HK2', '2023-2024', 60),
('KT104-02', 'KT104', 'GV003', 'HK2', '2023-2024', 60),
('KT105-01', 'KT105', 'GV004', 'HK1', '2023-2024', 60),
('KT105-02', 'KT105', 'GV005', 'HK1', '2023-2024', 60);

-- ===== Lớp học phần Ngoại Ngữ năm học 2023-2024 =====
INSERT INTO LOPHOCPHAN (ma_lop, ma_mh, ma_gv, hoc_ky, nam_hoc, si_so_toi_da) VALUES
('NN101-01', 'NN101', 'GV001', 'HK1', '2023-2024', 50),
('NN101-02', 'NN101', 'GV002', 'HK1', '2023-2024', 50),
('NN102-01', 'NN102', 'GV003', 'HK2', '2023-2024', 50),
('NN102-02', 'NN102', 'GV004', 'HK2', '2023-2024', 50),
('NN103-01', 'NN103', 'GV005', 'HK1', '2023-2024', 50),
('NN103-02', 'NN103', 'GV001', 'HK1', '2023-2024', 50),
('NN104-01', 'NN104', 'GV002', 'HK2', '2023-2024', 50),
('NN104-02', 'NN104', 'GV003', 'HK2', '2023-2024', 50),
('NN105-01', 'NN105', 'GV004', 'HK1', '2023-2024', 50),
('NN105-02', 'NN105', 'GV005', 'HK1', '2023-2024', 50);

-- ===== Lớp học phần Cơ Khí năm học 2023-2024 =====
INSERT INTO LOPHOCPHAN (ma_lop, ma_mh, ma_gv, hoc_ky, nam_hoc, si_so_toi_da) VALUES
('CK101-01', 'CK101', 'GV001', 'HK1', '2023-2024', 40),
('CK101-02', 'CK101', 'GV002', 'HK1', '2023-2024', 40),
('CK102-01', 'CK102', 'GV003', 'HK2', '2023-2024', 40),
('CK102-02', 'CK102', 'GV004', 'HK2', '2023-2024', 40),
('CK103-01', 'CK103', 'GV005', 'HK1', '2023-2024', 40),
('CK103-02', 'CK103', 'GV001', 'HK1', '2023-2024', 40),
('CK104-01', 'CK104', 'GV002', 'HK2', '2023-2024', 40),
('CK104-02', 'CK104', 'GV003', 'HK2', '2023-2024', 40),
('CK105-01', 'CK105', 'GV004', 'HK1', '2023-2024', 40),
('CK105-02', 'CK105', 'GV005', 'HK1', '2023-2024', 40);

-- ===== Lớp học phần Xây Dựng năm học 2023-2024 =====
INSERT INTO LOPHOCPHAN (ma_lop, ma_mh, ma_gv, hoc_ky, nam_hoc, si_so_toi_da) VALUES
('XD101-01', 'XD101', 'GV001', 'HK1', '2023-2024', 50),
('XD101-02', 'XD101', 'GV002', 'HK1', '2023-2024', 50),
('XD102-01', 'XD102', 'GV003', 'HK2', '2023-2024', 50),
('XD102-02', 'XD102', 'GV004', 'HK2', '2023-2024', 50),
('XD103-01', 'XD103', 'GV005', 'HK1', '2023-2024', 50),
('XD103-02', 'XD103', 'GV001', 'HK1', '2023-2024', 50),
('XD104-01', 'XD104', 'GV002', 'HK2', '2023-2024', 50),
('XD104-02', 'XD104', 'GV003', 'HK2', '2023-2024', 50),
('XD105-01', 'XD105', 'GV004', 'HK1', '2023-2024', 50),
('XD105-02', 'XD105', 'GV005', 'HK1', '2023-2024', 50);


/* ==================== BẢNG ĐIỂM ==================== */
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
    FOREIGN KEY (ma_sv) REFERENCES SINHVIEN(ma_sv),
    FOREIGN KEY (ma_lop) REFERENCES LOPHOCPHAN(ma_lop)
);
--//=========Bảng Khoa==============

Create table KHOA(
      ma_khoa varchar(20) primary key NOT NULL,
	  ten_khoa nvarchar(200) NOT NULL
)
CREATE TABLE DANGKY_LOP (
    ma_sv VARCHAR(20),
    ma_lop VARCHAR(20),
    PRIMARY KEY(ma_sv, ma_lop),
    FOREIGN KEY(ma_sv) REFERENCES SINHVIEN(ma_sv),
    FOREIGN KEY(ma_lop) REFERENCES LOPHOCPHAN(ma_lop)
);

INSERT INTO DANGKY_LOP (ma_sv, ma_lop) VALUES
('SV001', 'JAVA001'),
('SV001', 'SQL001'),
('SV002', 'JAVA001'),
('SV002', 'WEB001'),
('SV003', 'KTE001'),
('SV003', 'TA001'),
('SV004', 'JAVA002'),
('SV004', 'FAN001'),
('SV005', 'CAD001'),
('SV005', 'TA001'),
('SV006', 'WEB001'),
('SV006', 'SQL001');

INSERT INTO DANGKY_LOP (ma_sv, ma_lop) VALUES
-- Sinh viên CNTT (SV001, SV002, SV007, SV012, SV017, SV022, SV027, SV032, SV037, SV042, SV047, SV052, SV057, SV062, SV067, SV072, SV077, SV082, SV087, SV092, SV097)
-- Đăng ký môn CNTT
('SV001', 'CNTT101-01'), ('SV001', 'CNTT102-01'), ('SV001', 'CNTT103-01'), ('SV001', 'CNTT104-01'), ('SV001', 'CNTT105-01'),
('SV002', 'CNTT101-01'), ('SV002', 'CNTT102-01'), ('SV002', 'CNTT103-01'), ('SV002', 'CNTT104-01'), ('SV002', 'CNTT105-01'),
('SV007', 'CNTT101-01'), ('SV007', 'CNTT102-01'), ('SV007', 'CNTT103-01'), ('SV007', 'CNTT104-01'), ('SV007', 'CNTT105-01'),
('SV012', 'CNTT101-01'), ('SV012', 'CNTT102-01'), ('SV012', 'CNTT103-01'), ('SV012', 'CNTT104-01'), ('SV012', 'CNTT105-01'),
('SV017', 'CNTT101-01'), ('SV017', 'CNTT102-01'), ('SV017', 'CNTT103-01'), ('SV017', 'CNTT104-01'), ('SV017', 'CNTT105-01'),
('SV022', 'CNTT101-01'), ('SV022', 'CNTT102-01'), ('SV022', 'CNTT103-01'), ('SV022', 'CNTT104-01'), ('SV022', 'CNTT105-01'),
('SV027', 'CNTT101-01'), ('SV027', 'CNTT102-01'), ('SV027', 'CNTT103-01'), ('SV027', 'CNTT104-01'), ('SV027', 'CNTT105-01'),
('SV032', 'CNTT101-01'), ('SV032', 'CNTT102-01'), ('SV032', 'CNTT103-01'), ('SV032', 'CNTT104-01'), ('SV032', 'CNTT105-01'),
('SV037', 'CNTT101-01'), ('SV037', 'CNTT102-01'), ('SV037', 'CNTT103-01'), ('SV037', 'CNTT104-01'), ('SV037', 'CNTT105-01'),
('SV042', 'CNTT101-01'), ('SV042', 'CNTT102-01'), ('SV042', 'CNTT103-01'), ('SV042', 'CNTT104-01'), ('SV042', 'CNTT105-01'),
('SV047', 'CNTT101-01'), ('SV047', 'CNTT102-01'), ('SV047', 'CNTT103-01'), ('SV047', 'CNTT104-01'), ('SV047', 'CNTT105-01'),
('SV052', 'CNTT101-01'), ('SV052', 'CNTT102-01'), ('SV052', 'CNTT103-01'), ('SV052', 'CNTT104-01'), ('SV052', 'CNTT105-01'),
('SV057', 'CNTT101-01'), ('SV057', 'CNTT102-01'), ('SV057', 'CNTT103-01'), ('SV057', 'CNTT104-01'), ('SV057', 'CNTT105-01'),
('SV062', 'CNTT101-01'), ('SV062', 'CNTT102-01'), ('SV062', 'CNTT103-01'), ('SV062', 'CNTT104-01'), ('SV062', 'CNTT105-01'),
('SV067', 'CNTT101-01'), ('SV067', 'CNTT102-01'), ('SV067', 'CNTT103-01'), ('SV067', 'CNTT104-01'), ('SV067', 'CNTT105-01'),
('SV072', 'CNTT101-01'), ('SV072', 'CNTT102-01'), ('SV072', 'CNTT103-01'), ('SV072', 'CNTT104-01'), ('SV072', 'CNTT105-01'),
('SV077', 'CNTT101-01'), ('SV077', 'CNTT102-01'), ('SV077', 'CNTT103-01'), ('SV077', 'CNTT104-01'), ('SV077', 'CNTT105-01'),
('SV082', 'CNTT101-01'), ('SV082', 'CNTT102-01'), ('SV082', 'CNTT103-01'), ('SV082', 'CNTT104-01'), ('SV082', 'CNTT105-01'),
('SV087', 'CNTT101-01'), ('SV087', 'CNTT102-01'), ('SV087', 'CNTT103-01'), ('SV087', 'CNTT104-01'), ('SV087', 'CNTT105-01'),
('SV092', 'CNTT101-01'), ('SV092', 'CNTT102-01'), ('SV092', 'CNTT103-01'), ('SV092', 'CNTT104-01'), ('SV092', 'CNTT105-01'),
('SV097', 'CNTT101-01'), ('SV097', 'CNTT102-01'), ('SV097', 'CNTT103-01'), ('SV097', 'CNTT104-01'), ('SV097', 'CNTT105-01'),

-- Sinh viên KT (SV003, SV008, SV013, SV018, SV023, SV028, SV033, SV038, SV043, SV048, SV053, SV058, SV063, SV068, SV073, SV078, SV083, SV088, SV093, SV098)
-- Đăng ký môn KT
('SV003', 'KT101-01'), ('SV003', 'KT102-01'), ('SV003', 'KT103-01'), ('SV003', 'KT104-01'), ('SV003', 'KT105-01'),
('SV008', 'KT101-01'), ('SV008', 'KT102-01'), ('SV008', 'KT103-01'), ('SV008', 'KT104-01'), ('SV008', 'KT105-01'),
('SV013', 'KT101-01'), ('SV013', 'KT102-01'), ('SV013', 'KT103-01'), ('SV013', 'KT104-01'), ('SV013', 'KT105-01'),
('SV018', 'KT101-01'), ('SV018', 'KT102-01'), ('SV018', 'KT103-01'), ('SV018', 'KT104-01'), ('SV018', 'KT105-01'),
('SV023', 'KT101-01'), ('SV023', 'KT102-01'), ('SV023', 'KT103-01'), ('SV023', 'KT104-01'), ('SV023', 'KT105-01'),
('SV028', 'KT101-01'), ('SV028', 'KT102-01'), ('SV028', 'KT103-01'), ('SV028', 'KT104-01'), ('SV028', 'KT105-01'),
('SV033', 'KT101-01'), ('SV033', 'KT102-01'), ('SV033', 'KT103-01'), ('SV033', 'KT104-01'), ('SV033', 'KT105-01'),
('SV038', 'KT101-01'), ('SV038', 'KT102-01'), ('SV038', 'KT103-01'), ('SV038', 'KT104-01'), ('SV038', 'KT105-01'),
('SV043', 'KT101-01'), ('SV043', 'KT102-01'), ('SV043', 'KT103-01'), ('SV043', 'KT104-01'), ('SV043', 'KT105-01'),
('SV048', 'KT101-01'), ('SV048', 'KT102-01'), ('SV048', 'KT103-01'), ('SV048', 'KT104-01'), ('SV048', 'KT105-01'),
('SV053', 'KT101-01'), ('SV053', 'KT102-01'), ('SV053', 'KT103-01'), ('SV053', 'KT104-01'), ('SV053', 'KT105-01'),
('SV058', 'KT101-01'), ('SV058', 'KT102-01'), ('SV058', 'KT103-01'), ('SV058', 'KT104-01'), ('SV058', 'KT105-01'),
('SV063', 'KT101-01'), ('SV063', 'KT102-01'), ('SV063', 'KT103-01'), ('SV063', 'KT104-01'), ('SV063', 'KT105-01'),
('SV068', 'KT101-01'), ('SV068', 'KT102-01'), ('SV068', 'KT103-01'), ('SV068', 'KT104-01'), ('SV068', 'KT105-01'),
('SV073', 'KT101-01'), ('SV073', 'KT102-01'), ('SV073', 'KT103-01'), ('SV073', 'KT104-01'), ('SV073', 'KT105-01'),
('SV078', 'KT101-01'), ('SV078', 'KT102-01'), ('SV078', 'KT103-01'), ('SV078', 'KT104-01'), ('SV078', 'KT105-01'),
('SV083', 'KT101-01'), ('SV083', 'KT102-01'), ('SV083', 'KT103-01'), ('SV083', 'KT104-01'), ('SV083', 'KT105-01'),
('SV088', 'KT101-01'), ('SV088', 'KT102-01'), ('SV088', 'KT103-01'), ('SV088', 'KT104-01'), ('SV088', 'KT105-01'),
('SV093', 'KT101-01'), ('SV093', 'KT102-01'), ('SV093', 'KT103-01'), ('SV093', 'KT104-01'), ('SV093', 'KT105-01'),
('SV098', 'KT101-01'), ('SV098', 'KT102-01'), ('SV098', 'KT103-01'), ('SV098', 'KT104-01'), ('SV098', 'KT105-01'),

-- Sinh viên NN (SV004, SV009, SV014, SV019, SV024, SV029, SV034, SV039, SV044, SV049, SV054, SV059, SV064, SV069, SV074, SV079, SV084, SV089, SV094, SV099)
-- Đăng ký môn NN
('SV004', 'NN101-01'), ('SV004', 'NN102-01'), ('SV004', 'NN103-01'), ('SV004', 'NN104-01'), ('SV004', 'NN105-01'),
('SV009', 'NN101-01'), ('SV009', 'NN102-01'), ('SV009', 'NN103-01'), ('SV009', 'NN104-01'), ('SV009', 'NN105-01'),
('SV014', 'NN101-01'), ('SV014', 'NN102-01'), ('SV014', 'NN103-01'), ('SV014', 'NN104-01'), ('SV014', 'NN105-01'),
('SV019', 'NN101-01'), ('SV019', 'NN102-01'), ('SV019', 'NN103-01'), ('SV019', 'NN104-01'), ('SV019', 'NN105-01'),
('SV024', 'NN101-01'), ('SV024', 'NN102-01'), ('SV024', 'NN103-01'), ('SV024', 'NN104-01'), ('SV024', 'NN105-01'),
('SV029', 'NN101-01'), ('SV029', 'NN102-01'), ('SV029', 'NN103-01'), ('SV029', 'NN104-01'), ('SV029', 'NN105-01'),
('SV034', 'NN101-01'), ('SV034', 'NN102-01'), ('SV034', 'NN103-01'), ('SV034', 'NN104-01'), ('SV034', 'NN105-01'),
('SV039', 'NN101-01'), ('SV039', 'NN102-01'), ('SV039', 'NN103-01'), ('SV039', 'NN104-01'), ('SV039', 'NN105-01'),
('SV044', 'NN101-01'), ('SV044', 'NN102-01'), ('SV044', 'NN103-01'), ('SV044', 'NN104-01'), ('SV044', 'NN105-01'),
('SV049', 'NN101-01'), ('SV049', 'NN102-01'), ('SV049', 'NN103-01'), ('SV049', 'NN104-01'), ('SV049', 'NN105-01'),
('SV054', 'NN101-01'), ('SV054', 'NN102-01'), ('SV054', 'NN103-01'), ('SV054', 'NN104-01'), ('SV054', 'NN105-01'),
('SV059', 'NN101-01'), ('SV059', 'NN102-01'), ('SV059', 'NN103-01'), ('SV059', 'NN104-01'), ('SV059', 'NN105-01'),
('SV064', 'NN101-01'), ('SV064', 'NN102-01'), ('SV064', 'NN103-01'), ('SV064', 'NN104-01'), ('SV064', 'NN105-01'),
('SV069', 'NN101-01'), ('SV069', 'NN102-01'), ('SV069', 'NN103-01'), ('SV069', 'NN104-01'), ('SV069', 'NN105-01'),
('SV074', 'NN101-01'), ('SV074', 'NN102-01'), ('SV074', 'NN103-01'), ('SV074', 'NN104-01'), ('SV074', 'NN105-01'),
('SV079', 'NN101-01'), ('SV079', 'NN102-01'), ('SV079', 'NN103-01'), ('SV079', 'NN104-01'), ('SV079', 'NN105-01'),
('SV084', 'NN101-01'), ('SV084', 'NN102-01'), ('SV084', 'NN103-01'), ('SV084', 'NN104-01'), ('SV084', 'NN105-01'),
('SV089', 'NN101-01'), ('SV089', 'NN102-01'), ('SV089', 'NN103-01'), ('SV089', 'NN104-01'), ('SV089', 'NN105-01'),
('SV094', 'NN101-01'), ('SV094', 'NN102-01'), ('SV094', 'NN103-01'), ('SV094', 'NN104-01'), ('SV094', 'NN105-01'),
('SV099', 'NN101-01'), ('SV099', 'NN102-01'), ('SV099', 'NN103-01'), ('SV099', 'NN104-01'), ('SV099', 'NN105-01'),

-- Sinh viên CK (SV005, SV010, SV015, SV020, SV025, SV030, SV035, SV040, SV045, SV050, SV055, SV060, SV065, SV070, SV075, SV080, SV085, SV090, SV095, SV100)
-- Đăng ký môn CK
('SV005', 'CK101-01'), ('SV005', 'CK102-01'), ('SV005', 'CK103-01'), ('SV005', 'CK104-01'), ('SV005', 'CK105-01'),
('SV010', 'CK101-01'), ('SV010', 'CK102-01'), ('SV010', 'CK103-01'), ('SV010', 'CK104-01'), ('SV010', 'CK105-01'),
('SV015', 'CK101-01'), ('SV015', 'CK102-01'), ('SV015', 'CK103-01'), ('SV015', 'CK104-01'), ('SV015', 'CK105-01'),
('SV020', 'CK101-01'), ('SV020', 'CK102-01'), ('SV020', 'CK103-01'), ('SV020', 'CK104-01'), ('SV020', 'CK105-01'),
('SV025', 'CK101-01'), ('SV025', 'CK102-01'), ('SV025', 'CK103-01'), ('SV025', 'CK104-01'), ('SV025', 'CK105-01'),
('SV030', 'CK101-01'), ('SV030', 'CK102-01'), ('SV030', 'CK103-01'), ('SV030', 'CK104-01'), ('SV030', 'CK105-01'),
('SV035', 'CK101-01'), ('SV035', 'CK102-01'), ('SV035', 'CK103-01'), ('SV035', 'CK104-01'), ('SV035', 'CK105-01'),
('SV040', 'CK101-01'), ('SV040', 'CK102-01'), ('SV040', 'CK103-01'), ('SV040', 'CK104-01'), ('SV040', 'CK105-01'),
('SV045', 'CK101-01'), ('SV045', 'CK102-01'), ('SV045', 'CK103-01'), ('SV045', 'CK104-01'), ('SV045', 'CK105-01'),
('SV050', 'CK101-01'), ('SV050', 'CK102-01'), ('SV050', 'CK103-01'), ('SV050', 'CK104-01'), ('SV050', 'CK105-01'),
('SV055', 'CK101-01'), ('SV055', 'CK102-01'), ('SV055', 'CK103-01'), ('SV055', 'CK104-01'), ('SV055', 'CK105-01'),
('SV060', 'CK101-01'), ('SV060', 'CK102-01'), ('SV060', 'CK103-01'), ('SV060', 'CK104-01'), ('SV060', 'CK105-01'),
('SV065', 'CK101-01'), ('SV065', 'CK102-01'), ('SV065', 'CK103-01'), ('SV065', 'CK104-01'), ('SV065', 'CK105-01'),
('SV070', 'CK101-01'), ('SV070', 'CK102-01'), ('SV070', 'CK103-01'), ('SV070', 'CK104-01'), ('SV070', 'CK105-01'),
('SV075', 'CK101-01'), ('SV075', 'CK102-01'), ('SV075', 'CK103-01'), ('SV075', 'CK104-01'), ('SV075', 'CK105-01'),
('SV080', 'CK101-01'), ('SV080', 'CK102-01'), ('SV080', 'CK103-01'), ('SV080', 'CK104-01'), ('SV080', 'CK105-01'),
('SV085', 'CK101-01'), ('SV085', 'CK102-01'), ('SV085', 'CK103-01'), ('SV085', 'CK104-01'), ('SV085', 'CK105-01'),
('SV090', 'CK101-01'), ('SV090', 'CK102-01'), ('SV090', 'CK103-01'), ('SV090', 'CK104-01'), ('SV090', 'CK105-01'),
('SV095', 'CK101-01'), ('SV095', 'CK102-01'), ('SV095', 'CK103-01'), ('SV095', 'CK104-01'), ('SV095', 'CK105-01'),
('SV100', 'CK101-01'), ('SV100', 'CK102-01'), ('SV100', 'CK103-01'), ('SV100', 'CK104-01'), ('SV100', 'CK105-01'),

-- Sinh viên XD (SV006, SV011, SV016, SV021, SV026, SV031, SV036, SV041, SV046, SV051, SV056, SV061, SV066, SV071, SV076, SV081, SV086, SV091, SV096)
-- Đăng ký môn XD
('SV006', 'XD101-01'), ('SV006', 'XD102-01'), ('SV006', 'XD103-01'), ('SV006', 'XD104-01'), ('SV006', 'XD105-01'),
('SV011', 'XD101-01'), ('SV011', 'XD102-01'), ('SV011', 'XD103-01'), ('SV011', 'XD104-01'), ('SV011', 'XD105-01'),
('SV016', 'XD101-01'), ('SV016', 'XD102-01'), ('SV016', 'XD103-01'), ('SV016', 'XD104-01'), ('SV016', 'XD105-01'),
('SV021', 'XD101-01'), ('SV021', 'XD102-01'), ('SV021', 'XD103-01'), ('SV021', 'XD104-01'), ('SV021', 'XD105-01'),
('SV026', 'XD101-01'), ('SV026', 'XD102-01'), ('SV026', 'XD103-01'), ('SV026', 'XD104-01'), ('SV026', 'XD105-01'),
('SV031', 'XD101-01'), ('SV031', 'XD102-01'), ('SV031', 'XD103-01'), ('SV031', 'XD104-01'), ('SV031', 'XD105-01'),
('SV036', 'XD101-01'), ('SV036', 'XD102-01'), ('SV036', 'XD103-01'), ('SV036', 'XD104-01'), ('SV036', 'XD105-01'),
('SV041', 'XD101-01'), ('SV041', 'XD102-01'), ('SV041', 'XD103-01'), ('SV041', 'XD104-01'), ('SV041', 'XD105-01'),
('SV046', 'XD101-01'), ('SV046', 'XD102-01'), ('SV046', 'XD103-01'), ('SV046', 'XD104-01'), ('SV046', 'XD105-01'),
('SV051', 'XD101-01'), ('SV051', 'XD102-01'), ('SV051', 'XD103-01'), ('SV051', 'XD104-01'), ('SV051', 'XD105-01'),
('SV056', 'XD101-01'), ('SV056', 'XD102-01'), ('SV056', 'XD103-01'), ('SV056', 'XD104-01'), ('SV056', 'XD105-01'),
('SV061', 'XD101-01'), ('SV061', 'XD102-01'), ('SV061', 'XD103-01'), ('SV061', 'XD104-01'), ('SV061', 'XD105-01'),
('SV066', 'XD101-01'), ('SV066', 'XD102-01'), ('SV066', 'XD103-01'), ('SV066', 'XD104-01'), ('SV066', 'XD105-01'),
('SV071', 'XD101-01'), ('SV071', 'XD102-01'), ('SV071', 'XD103-01'), ('SV071', 'XD104-01'), ('SV071', 'XD105-01'),
('SV076', 'XD101-01'), ('SV076', 'XD102-01'), ('SV076', 'XD103-01'), ('SV076', 'XD104-01'), ('SV076', 'XD105-01'),
('SV081', 'XD101-01'), ('SV081', 'XD102-01'), ('SV081', 'XD103-01'), ('SV081', 'XD104-01'), ('SV081', 'XD105-01'),
('SV086', 'XD101-01'), ('SV086', 'XD102-01'), ('SV086', 'XD103-01'), ('SV086', 'XD104-01'), ('SV086', 'XD105-01'),
('SV091', 'XD101-01'), ('SV091', 'XD102-01'), ('SV091', 'XD103-01'), ('SV091', 'XD104-01'), ('SV091', 'XD105-01'),
('SV096', 'XD101-01'), ('SV096', 'XD102-01'), ('SV096', 'XD103-01'), ('SV096', 'XD104-01'), ('SV096', 'XD105-01');




--Tang sĩ số nếu có sinh viên đăng kí lớp học phần 
CREATE TRIGGER trg_tang_siso
ON DANGKY_LOP
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;
    
    -- Cập nhật sĩ số hiện tại dựa trên số lượng thực tế trong bảng DANGKY_LOP
    UPDATE LOPHOCPHAN
    SET si_so_hien_tai = (
        SELECT COUNT(*)
        FROM DANGKY_LOP
        WHERE DANGKY_LOP.ma_lop = LOPHOCPHAN.ma_lop
    )
    WHERE ma_lop IN (
        SELECT DISTINCT ma_lop 
        FROM inserted
    );
END;

--Giảm sĩ số nếu có sinh viên huỷ đăng kí 
CREATE TRIGGER trg_giam_siso
ON DANGKY_LOP
AFTER DELETE
AS
BEGIN
    SET NOCOUNT ON;
    
    -- Cập nhật sĩ số hiện tại dựa trên số lượng thực tế trong bảng DANGKY_LOP
    UPDATE LOPHOCPHAN
    SET si_so_hien_tai = (
        SELECT COUNT(*)
        FROM DANGKY_LOP
        WHERE DANGKY_LOP.ma_lop = LOPHOCPHAN.ma_lop
    )
    WHERE ma_lop IN (
        SELECT DISTINCT ma_lop 
        FROM deleted
    );
END;
UPDATE LOPHOCPHAN
SET si_so_hien_tai = (
    SELECT COUNT(*)
    FROM DANGKY_LOP
    WHERE DANGKY_LOP.ma_lop = LOPHOCPHAN.ma_lop
);


INSERT INTO DIEM (ma_sv, ma_lop, diem_qua_trinh, diem_giua_ky, diem_cuoi_ky, diem_tong_ket, diem_chu, xep_loai) VALUES
-- Sinh viên SV001 (5 môn CNTT)
('SV001', 'CNTT101-01', 8.5, 7.5, 9.0, 8.4, 'B', N'Giỏi'),
('SV001', 'CNTT102-01', 9.0, 8.0, 8.5, 8.5, 'A', N'Xuất sắc'),
('SV001', 'CNTT103-01', 7.5, 8.5, 7.0, 7.6, 'B', N'Khá'),
('SV001', 'CNTT104-01', 8.0, 7.0, 8.5, 8.0, 'B', N'Giỏi'),
('SV001', 'CNTT105-01', 9.0, 9.0, 8.0, 8.6, 'A', N'Xuất sắc'),

-- Sinh viên SV002 (5 môn CNTT)
('SV002', 'CNTT101-01', 7.0, 6.5, 7.5, 7.1, 'B', N'Khá'),
('SV002', 'CNTT102-01', 8.0, 7.0, 8.0, 7.7, 'B', N'Khá'),
('SV002', 'CNTT103-01', 6.5, 7.5, 6.0, 6.6, 'C', N'Trung bình'),
('SV002', 'CNTT104-01', 7.5, 6.0, 7.0, 6.9, 'C', N'Trung bình'),
('SV002', 'CNTT105-01', 8.5, 8.0, 7.5, 7.9, 'B', N'Khá'),

-- Sinh viên SV003 (5 môn KT)
('SV003', 'KT101-01', 8.0, 7.0, 8.5, 8.0, 'B', N'Giỏi'),
('SV003', 'KT102-01', 7.5, 8.0, 7.0, 7.4, 'B', N'Khá'),
('SV003', 'KT103-01', 9.0, 8.5, 9.5, 9.1, 'A', N'Xuất sắc'),
('SV003', 'KT104-01', 6.5, 7.0, 6.0, 6.4, 'C', N'Trung bình'),
('SV003', 'KT105-01', 8.5, 7.5, 8.0, 8.0, 'B', N'Giỏi'),

-- Sinh viên SV004 (5 môn NN)
('SV004', 'NN101-01', 9.0, 8.5, 9.0, 8.9, 'A', N'Xuất sắc'),
('SV004', 'NN102-01', 8.0, 7.5, 8.5, 8.1, 'B', N'Giỏi'),
('SV004', 'NN103-01', 7.5, 8.0, 7.0, 7.4, 'B', N'Khá'),
('SV004', 'NN104-01', 8.5, 7.0, 8.0, 7.9, 'B', N'Khá'),
('SV004', 'NN105-01', 9.0, 9.5, 8.5, 8.9, 'A', N'Xuất sắc'),

-- Sinh viên SV005 (5 môn CK)
('SV005', 'CK101-01', 8.5, 7.5, 9.0, 8.4, 'B', N'Giỏi'),
('SV005', 'CK102-01', 7.0, 8.0, 6.5, 7.1, 'B', N'Khá'),
('SV005', 'CK103-01', 9.0, 8.5, 9.5, 9.1, 'A', N'Xuất sắc'),
('SV005', 'CK104-01', 6.5, 7.0, 6.0, 6.4, 'C', N'Trung bình'),
('SV005', 'CK105-01', 8.0, 7.0, 8.5, 8.0, 'B', N'Giỏi'),

-- Sinh viên SV006 (5 môn XD)
('SV006', 'XD101-01', 8.0, 7.5, 8.5, 8.1, 'B', N'Giỏi'),
('SV006', 'XD102-01', 7.0, 8.0, 6.5, 7.1, 'B', N'Khá'),
('SV006', 'XD103-01', 9.0, 8.5, 9.5, 9.1, 'A', N'Xuất sắc'),
('SV006', 'XD104-01', 6.5, 7.0, 6.0, 6.4, 'C', N'Trung bình'),
('SV006', 'XD105-01', 8.5, 7.0, 8.0, 7.9, 'B', N'Khá'),

-- Sinh viên SV007 (5 môn CNTT)
('SV007', 'CNTT101-01', 9.5, 9.0, 9.5, 9.3, 'A', N'Xuất sắc'),
('SV007', 'CNTT102-01', 8.5, 9.0, 8.0, 8.4, 'B', N'Giỏi'),
('SV007', 'CNTT103-01', 7.0, 8.0, 7.5, 7.6, 'B', N'Khá'),
('SV007', 'CNTT104-01', 9.0, 8.5, 9.0, 8.9, 'A', N'Xuất sắc'),
('SV007', 'CNTT105-01', 8.0, 7.5, 8.5, 8.1, 'B', N'Giỏi'),

-- Sinh viên SV008 (5 môn KT)
('SV008', 'KT101-01', 7.0, 6.5, 7.5, 7.1, 'B', N'Khá'),
('SV008', 'KT102-01', 8.0, 7.0, 8.0, 7.7, 'B', N'Khá'),
('SV008', 'KT103-01', 6.5, 7.5, 6.0, 6.6, 'C', N'Trung bình'),
('SV008', 'KT104-01', 7.5, 6.0, 7.0, 6.9, 'C', N'Trung bình'),
('SV008', 'KT105-01', 8.5, 8.0, 7.5, 7.9, 'B', N'Khá'),

-- Sinh viên SV009 (5 môn NN)
('SV009', 'NN101-01', 7.0, 6.5, 7.5, 7.1, 'B', N'Khá'),
('SV009', 'NN102-01', 6.5, 7.0, 6.0, 6.4, 'C', N'Trung bình'),
('SV009', 'NN103-01', 8.0, 7.5, 8.0, 7.9, 'B', N'Khá'),
('SV009', 'NN104-01', 7.5, 6.0, 7.0, 6.9, 'C', N'Trung bình'),
('SV009', 'NN105-01', 8.0, 7.0, 8.5, 8.0, 'B', N'Giỏi'),

-- Sinh viên SV010 (5 môn CK)
('SV010', 'CK101-01', 7.5, 6.0, 7.0, 6.9, 'C', N'Trung bình'),
('SV010', 'CK102-01', 8.5, 8.0, 7.5, 7.9, 'B', N'Khá'),
('SV010', 'CK103-01', 6.0, 7.0, 5.5, 6.1, 'C', N'Trung bình'),
('SV010', 'CK104-01', 9.0, 8.5, 9.0, 8.9, 'A', N'Xuất sắc'),
('SV010', 'CK105-01', 7.0, 6.5, 7.5, 7.1, 'B', N'Khá');


update LOPHOCPHAN
set si_so_hien_tai=10
-- Thêm cột ngay_dang_ky với giá trị mặc định là thời gian hiện tại
ALTER TABLE DANGKY_LOP 
ADD ngay_dang_ky DATETIME DEFAULT CURRENT_TIMESTAMP;

-- Đảm bảo không thể có 2 dòng điểm cho cùng 1 sinh viên trong 1 lớp
ALTER TABLE DIEM 
ADD CONSTRAINT UQ_Diem_SV_Lop UNIQUE(ma_sv, ma_lop);

-- Thêm thời gian mở/đóng đăng ký và ngày bắt đầu/kết thúc học
ALTER TABLE LOPHOCPHAN
ADD thoi_gian_mo_dang_ky DATETIME,
    thoi_gian_dong_dang_ky DATETIME,
    ngay_bat_dau_hoc DATE,
    ngay_ket_thuc_hoc DATE;

-- Xem tất cả constraints của bảng SINHVIEN
SELECT 
    f.name AS ConstraintName,
    COL_NAME(fc.parent_object_id, fc.parent_column_id) AS ColumnName,
    OBJECT_NAME(f.referenced_object_id) AS ReferencedTable
FROM 
    sys.foreign_keys AS f
INNER JOIN 
    sys.foreign_key_columns AS fc ON f.object_id = fc.constraint_object_id
WHERE 
    OBJECT_NAME(f.parent_object_id) = 'SINHVIEN';

ALTER TABLE sinhvien 
DROP CONSTRAINT FK6omik97f3m8glj6xnp9nfs2j1;