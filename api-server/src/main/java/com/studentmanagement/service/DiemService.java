package com.studentmanagement.service;

import com.studentmanagement.dto.request.DiemCreateRequest;
import com.studentmanagement.dto.response.DiemResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DiemService {
    
    /**
     * Lấy tất cả điểm
     */
    List<DiemResponse> getAllDiem();
    
    /**
     * Lấy điểm phân trang
     */
    Page<DiemResponse> getAllDiemPaged(Pageable pageable);
    
    /**
     * Lấy điểm theo ID
     */
    DiemResponse getDiemById(Integer id);
    
    /**
     * Lấy điểm của sinh viên
     */
    List<DiemResponse> getDiemBySinhVien(String maSV);
    
    /**
     * Lấy điểm của lớp học phần
     */
    List<DiemResponse> getDiemByLopHocPhan(String maLop);
    
    /**
     * Thêm điểm mới
     */
    DiemResponse createDiem(DiemCreateRequest request);
    
    /**
     * Cập nhật điểm
     */
    DiemResponse updateDiem(Integer id, DiemCreateRequest request);
    
    /**
     * Xóa điểm
     */
    void deleteDiem(Integer id);
}
