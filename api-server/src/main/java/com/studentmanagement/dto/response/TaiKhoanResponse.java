package com.studentmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaiKhoanResponse {
    
    private String tenDangNhap;
    private String loaiNguoiDung;
    private String onlineStatus;
}
