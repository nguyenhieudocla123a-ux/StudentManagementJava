package com.studentmanagement.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaiKhoanCreateRequest {
    
    @NotBlank(message = "Tên đăng nhập không được để trống")
    private String tenDangNhap;
    
    @NotBlank(message = "Mật khẩu không được để trống")
    private String matKhau;
    
    @NotBlank(message = "Loại người dùng không được để trống")
    private String loaiNguoiDung;
}
