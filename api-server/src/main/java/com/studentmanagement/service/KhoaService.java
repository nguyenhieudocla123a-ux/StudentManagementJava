package com.studentmanagement.service;

import com.studentmanagement.dto.request.KhoaCreateRequest;
import com.studentmanagement.dto.response.KhoaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface cho Khoa (Department/Faculty)
 */
public interface KhoaService {
    
    /**
     * Lấy tất cả khoa
     */
    List<KhoaResponse> getAllKhoa();
    
    /**
     * Lấy tất cả khoa phân trang
     */
    Page<KhoaResponse> getAllKhoaPaged(Pageable pageable);
    
    /**
     * Lấy khoa theo mã
     */
    KhoaResponse getKhoaById(String id);
    
    /**
     * Tìm kiếm khoa theo tên
     */
    List<KhoaResponse> searchByTenKhoa(String tenKhoa);
    
    /**
     * Tạo khoa mới
     */
    KhoaResponse createKhoa(KhoaCreateRequest request);
    
    /**
     * Cập nhật khoa
     */
    KhoaResponse updateKhoa(String id, KhoaCreateRequest request);
    
    /**
     * Xóa khoa
     */
    void deleteKhoa(String id);
}
