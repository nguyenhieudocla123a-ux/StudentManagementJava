package com.studentmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiemResponse {
    
    private Integer id;
    private String maSV;
    private String maLop;
    private Double diemQuaTrinh;
    private Double diemGiuaKy;
    private Double diemCuoiKy;
    private Double diemTongKet;
    private String diemChu;
    private String xepLoai;
}
