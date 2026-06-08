package com.studentmanagement.service;

import com.studentmanagement.dto.request.DangKyLopCreateRequest;
import com.studentmanagement.dto.response.DangKyLopResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface cho DangKyLop (Class Registration)
 */
public interface DangKyLopService {
    
    /**
     * Lấy tất cả đăng ký lớp
     */
    List<DangKyLopResponse> getAllDangKyLop();
    
    /**
     * Lấy tất cả đăng ký lớp phân trang
     */
    Page<DangKyLopResponse> getAllDangKyLopPaged(Pageable pageable);
    
    /**
     * Lấy đăng ký lớp theo mã
     */
    DangKyLopResponse getDangKyLopById(String id);
    
    /**
     * Lấy đăng ký lớp của sinh viên
     */
    List<DangKyLopResponse> getDangKyLopBySinhVien(String maSV);
    
    /**
     * Lấy đăng ký lớp của lớp học phần
     */
    List<DangKyLopResponse> getDangKyLopByLopHocPhan(String maLopHP);
    
    /**
     * Tạo đăng ký lớp mới
     */
    DangKyLopResponse createDangKyLop(DangKyLopCreateRequest request);
    
    /**
     * Cập nhật đăng ký lớp
     */
    DangKyLopResponse updateDangKyLop(String id, DangKyLopCreateRequest request);
    
    /**
     * Xóa đăng ký lớp
     */
    void deleteDangKyLop(String id);
}
