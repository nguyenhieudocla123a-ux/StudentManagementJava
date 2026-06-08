package com.studentmanagement.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SinhVienCreateRequest {
    
    @NotBlank(message = "Mã sinh viên không được để trống")
    private String maSV;
    
    @NotBlank(message = "Họ tên không được để trống")
    private String hoTen;
    
    @NotBlank(message = "Tên đăng nhập không được để trống")
    private String tenDangNhap;
    
    @Email(message = "Email không hợp lệ")
    private String email;
    
    @Pattern(regexp = "^[0-9]{10,11}$", message = "Số điện thoại phải có 10-11 chữ số")
    private String soDienThoai;
    
    @NotBlank(message = "Mã khoa không được để trống")
    private String maKhoa;
    
    private LocalDate ngaySinh;
    
    private String gioiTinh;
    
    private String diaChi;
}
