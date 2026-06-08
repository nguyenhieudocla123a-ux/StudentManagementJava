package com.studentmanagement.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuanTriVienCreateRequest {

    @NotBlank(message = "Mã quản trị viên không được để trống")
    private String maQTV;

    @NotBlank(message = "Họ tên không được để trống")
    private String hoTen;

    @Email(message = "Email không hợp lệ")
    private String email;

    @Pattern(regexp = "^[0-9]{10,11}$", message = "Số điện thoại phải có 10-11 chữ số")
    private String soDienThoai;

    @NotBlank(message = "Tên đăng nhập không được để trống")
    private String tenDangNhap;
}
