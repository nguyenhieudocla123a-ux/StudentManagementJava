-- Script tạo tài khoản test cho unit test
-- Chạy script này TRƯỚC KHI chạy unit test

-- Mật khẩu "123456" đã hash SHA-256
-- 8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92

-- Xóa tài khoản test cũ (nếu có)
DELETE FROM taikhoan WHERE ten_dang_nhap IN ('svtest999', 'svtest997', 'svtest998');

-- Tạo tài khoản test cho sinh viên
INSERT INTO taikhoan (ten_dang_nhap, mat_khau, loai_nguoi_dung) VALUES
('svtest999', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'SinhVien'),
('svtest997', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'SinhVien'),
('svtest998', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'SinhVien');

-- Kiểm tra kết quả
SELECT * FROM taikhoan WHERE ten_dang_nhap LIKE 'svtest%';
