package com.studentmanagement.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiemCreateRequest {
    
    @NotBlank(message = "Mã sinh viên không được để trống")
    private String maSV;
    
    @NotBlank(message = "Mã lớp học phần không được để trống")
    private String maLop;
    
    @DecimalMin(value = "0.0", message = "Điểm quá trình phải >= 0")
    @DecimalMax(value = "10.0", message = "Điểm quá trình phải <= 10")
    private Double diemQuaTrinh;
    
    @DecimalMin(value = "0.0", message = "Điểm giữa kỳ phải >= 0")
    @DecimalMax(value = "10.0", message = "Điểm giữa kỳ phải <= 10")
    private Double diemGiuaKy;
    
    @DecimalMin(value = "0.0", message = "Điểm cuối kỳ phải >= 0")
    @DecimalMax(value = "10.0", message = "Điểm cuối kỳ phải <= 10")
    private Double diemCuoiKy;
}
