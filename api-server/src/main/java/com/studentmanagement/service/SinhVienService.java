package com.studentmanagement.service;

import com.studentmanagement.dto.request.SinhVienCreateRequest;
import com.studentmanagement.dto.response.SinhVienResponse;
import com.studentmanagement.entity.SinhVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SinhVienService {
    
    /**
     * Lấy tất cả sinh viên
     */
    List<SinhVienResponse> getAllSinhVien();
    
    /**
     * Lấy sinh viên phân trang
     */
    Page<SinhVienResponse> getAllSinhVienPaged(Pageable pageable);
    
    /**
     * Lấy sinh viên theo mã
     */
    SinhVienResponse getSinhVienById(String maSV);
    
    /**
     * Tìm kiếm sinh viên theo họ tên
     */
    List<SinhVienResponse> searchByHoTen(String keyword);
    
    /**
     * Tạo sinh viên mới
     */
    SinhVienResponse createSinhVien(SinhVienCreateRequest request);
    
    /**
     * Cập nhật sinh viên
     */
    SinhVienResponse updateSinhVien(String maSV, SinhVienCreateRequest request);
    
    /**
     * Xóa sinh viên
     */
    void deleteSinhVien(String maSV);
}
