package com.studentmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonHocResponse {

    private String maMH;
    private String tenMH;
    private Integer soTinChi;
    private String moTa;
    private String maKhoa;
}
