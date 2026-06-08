package com.studentmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DangKyLopCreateRequest {

    @NotBlank(message = "Mã sinh viên không được để trống")
    private String maSV;

    @NotBlank(message = "Mã lớp học phần không được để trống")
    private String maLop;
}
