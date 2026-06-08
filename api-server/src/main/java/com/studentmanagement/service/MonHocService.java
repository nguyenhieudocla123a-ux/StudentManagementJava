package com.studentmanagement.service;

import com.studentmanagement.dto.request.MonHocCreateRequest;
import com.studentmanagement.dto.response.MonHocResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface cho MonHoc (Subject/Course)
 */
public interface MonHocService {
    
    /**
     * Lấy tất cả môn học
     */
    List<MonHocResponse> getAllMonHoc();
    
    /**
     * Lấy tất cả môn học phân trang
     */
    Page<MonHocResponse> getAllMonHocPaged(Pageable pageable);
    
    /**
     * Lấy môn học theo mã
     */
    MonHocResponse getMonHocById(String id);
    
    /**
     * Tìm kiếm môn học theo tên
     */
    List<MonHocResponse> searchByTenMonHoc(String tenMonHoc);
    
    /**
     * Tìm môn học theo khoa
     */
    List<MonHocResponse> getMonHocByKhoa(String maKhoa);
    
    /**
     * Tạo môn học mới
     */
    MonHocResponse createMonHoc(MonHocCreateRequest request);
    
    /**
     * Cập nhật môn học
     */
    MonHocResponse updateMonHoc(String id, MonHocCreateRequest request);
    
    /**
     * Xóa môn học
     */
    void deleteMonHoc(String id);
}
