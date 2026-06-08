package com.studentmanagement.service;

import com.studentmanagement.dto.request.TaiKhoanCreateRequest;
import com.studentmanagement.dto.response.TaiKhoanResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface cho TaiKhoan (Account)
 */
public interface TaiKhoanService {
    
    /**
     * Lấy tất cả tài khoản
     */
    List<TaiKhoanResponse> getAllTaiKhoan();
    
    /**
     * Lấy tất cả tài khoản phân trang
     */
    Page<TaiKhoanResponse> getAllTaiKhoanPaged(Pageable pageable);
    
    /**
     * Lấy tài khoản theo tên đăng nhập
     */
    TaiKhoanResponse getTaiKhoanById(String tenDangNhap);
    
    /**
     * Lấy tài khoản theo loại người dùng
     */
    List<TaiKhoanResponse> getTaiKhoanByLoai(String loaiNguoiDung);
    
    /**
     * Tạo tài khoản mới
     */
    TaiKhoanResponse createTaiKhoan(TaiKhoanCreateRequest request);
    
    /**
     * Cập nhật tài khoản
     */
    TaiKhoanResponse updateTaiKhoan(String tenDangNhap, TaiKhoanCreateRequest request);
    
    /**
     * Xóa tài khoản
     */
    void deleteTaiKhoan(String tenDangNhap);
    
    /**
     * Kiểm tra tài khoản có tồn tại không
     */
    boolean existsTaiKhoan(String tenDangNhap);
}
