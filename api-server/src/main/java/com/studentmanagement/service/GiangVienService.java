package com.studentmanagement.service;

import com.studentmanagement.dto.request.GiangVienCreateRequest;
import com.studentmanagement.dto.response.GiangVienResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GiangVienService {
    
    /**
     * Lấy tất cả giảng viên
     */
    List<GiangVienResponse> getAllGiangVien();
    
    /**
     * Lấy giảng viên phân trang
     */
    Page<GiangVienResponse> getAllGiangVienPaged(Pageable pageable);
    
    /**
     * Lấy giảng viên theo mã
     */
    GiangVienResponse getGiangVienById(String maGV);
    
    /**
     * Tìm kiếm giảng viên theo họ tên
     */
    List<GiangVienResponse> searchByHoTen(String keyword);
    
    /**
     * Tạo giảng viên mới
     */
    GiangVienResponse createGiangVien(GiangVienCreateRequest request);
    
    /**
     * Cập nhật giảng viên
     */
    GiangVienResponse updateGiangVien(String maGV, GiangVienCreateRequest request);
    
    /**
     * Xóa giảng viên
     */
    void deleteGiangVien(String maGV);
}
