package com.studentmanagement.service;

import com.studentmanagement.dto.request.LopHocPhanCreateRequest;
import com.studentmanagement.dto.response.LopHocPhanResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface cho LopHocPhan (Class/Section)
 */
public interface LopHocPhanService {
    
    /**
     * Lấy tất cả lớp học phần
     */
    List<LopHocPhanResponse> getAllLopHocPhan();
    
    /**
     * Lấy tất cả lớp học phần phân trang
     */
    Page<LopHocPhanResponse> getAllLopHocPhanPaged(Pageable pageable);
    
    /**
     * Lấy lớp học phần theo mã
     */
    LopHocPhanResponse getLopHocPhanById(String id);
    
    /**
     * Lấy lớp học phần của môn học
     */
    List<LopHocPhanResponse> getLopHocPhanByMonHoc(String maMonHoc);
    
    /**
     * Lấy lớp học phần của giáo viên
     */
    List<LopHocPhanResponse> getLopHocPhanByGiangVien(String maGV);
    
    /**
     * Tạo lớp học phần mới
     */
    LopHocPhanResponse createLopHocPhan(LopHocPhanCreateRequest request);
    
    /**
     * Cập nhật lớp học phần
     */
    LopHocPhanResponse updateLopHocPhan(String id, LopHocPhanCreateRequest request);
    
    /**
     * Xóa lớp học phần
     */
    void deleteLopHocPhan(String id);
}
