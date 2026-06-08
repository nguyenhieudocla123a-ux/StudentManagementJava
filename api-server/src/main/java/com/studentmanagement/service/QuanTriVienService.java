package com.studentmanagement.service;

import com.studentmanagement.dto.request.QuanTriVienCreateRequest;
import com.studentmanagement.dto.response.QuanTriVienResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface cho QuanTriVien (Admin)
 */
public interface QuanTriVienService {
    
    /**
     * Lấy tất cả quản trị viên
     */
    List<QuanTriVienResponse> getAllQuanTriVien();
    
    /**
     * Lấy tất cả quản trị viên phân trang
     */
    Page<QuanTriVienResponse> getAllQuanTriVienPaged(Pageable pageable);
    
    /**
     * Lấy quản trị viên theo mã
     */
    QuanTriVienResponse getQuanTriVienById(String id);
    
    /**
     * Tìm kiếm quản trị viên theo tên
     */
    List<QuanTriVienResponse> searchByHoTen(String hoTen);
    
    /**
     * Tạo quản trị viên mới
     */
    QuanTriVienResponse createQuanTriVien(QuanTriVienCreateRequest request);
    
    /**
     * Cập nhật quản trị viên
     */
    QuanTriVienResponse updateQuanTriVien(String id, QuanTriVienCreateRequest request);
    
    /**
     * Xóa quản trị viên
     */
    void deleteQuanTriVien(String id);
}
