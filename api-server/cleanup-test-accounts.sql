-- Script để xóa các tài khoản test còn sót lại
-- Chạy script này trước khi chạy unit test

-- Xóa tài khoản test của sinh viên
DELETE FROM taikhoan WHERE ten_dang_nhap IN ('svtest999', 'svtest997', 'svtest998', 'SV999', 'SV997', 'SV998');

-- Xóa sinh viên test (nếu có)
DELETE FROM sinhvien WHERE ma_sv IN ('SVTEST999', 'SVTEST997', 'SVTEST998', 'SV999', 'SV997', 'SV998');

-- Kiểm tra kết quả
SELECT * FROM taikhoan WHERE ten_dang_nhap LIKE '%test%' OR ten_dang_nhap LIKE 'SV99%';
SELECT * FROM sinhvien WHERE ma_sv LIKE '%TEST%' OR ma_sv LIKE 'SV99%';
