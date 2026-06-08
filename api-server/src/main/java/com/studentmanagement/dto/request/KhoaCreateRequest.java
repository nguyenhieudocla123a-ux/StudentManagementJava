package com.studentmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KhoaCreateRequest {

    @NotBlank(message = "Mã khoa không được để trống")
    private String maKhoa;

    @NotBlank(message = "Tên khoa không được để trống")
    private String tenKhoa;
}
