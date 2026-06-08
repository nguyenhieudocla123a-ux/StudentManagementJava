package com.studentmanagement.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LopHocPhanCreateRequest {

    @NotBlank(message = "Mã lớp học phần không được để trống")
    private String maLop;

    @NotBlank(message = "Mã môn học không được để trống")
    private String maMH;

    @NotBlank(message = "Mã giảng viên không được để trống")
    private String maGV;

    @NotBlank(message = "Học kỳ không được để trống")
    private String hocKy;

    @NotBlank(message = "Năm học không được để trống")
    private String namHoc;

    @Min(value = 1, message = "Sĩ số tối đa phải >= 1")
    private Integer siSoToiDa;

    private String trangThai;
}
