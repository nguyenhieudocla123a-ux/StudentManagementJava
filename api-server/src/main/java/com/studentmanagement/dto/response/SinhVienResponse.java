package com.studentmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SinhVienResponse {
    
    private String maSV;
    private String hoTen;
    private String tenDangNhap;
    private String email;
    private String soDienThoai;
    private String maKhoa;
    private LocalDate ngaySinh;
    private String gioiTinh;
    private String diaChi;
}
