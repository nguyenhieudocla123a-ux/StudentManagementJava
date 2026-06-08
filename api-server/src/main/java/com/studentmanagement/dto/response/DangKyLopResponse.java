package com.studentmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DangKyLopResponse {

    private String maSV;
    private String maLop;
    private String ngayDangKy;
}
