package com.studentmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuanTriVienResponse {

    private String maQTV;
    private String hoTen;
    private String tenDangNhap;
    private String email;
    private String soDienThoai;
}
