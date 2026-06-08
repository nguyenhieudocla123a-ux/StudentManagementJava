package com.studentmanagement.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonHocCreateRequest {

    @NotBlank(message = "Mã môn học không được để trống")
    private String maMH;

    @NotBlank(message = "Tên môn học không được để trống")
    private String tenMH;

    @Min(value = 1, message = "Số tín chỉ phải >= 1")
    private Integer soTinChi;

    private String moTa;

    @NotBlank(message = "Mã khoa không được để trống")
    private String maKhoa;
}
