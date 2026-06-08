package com.studentmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LopHocPhanResponse {

    private String maLop;
    private String maMH;
    private String maGV;
    private String hocKy;
    private String namHoc;
    private Integer siSoToiDa;
    private Integer siSoHienTai;
    private String trangThai;
}
