package com.studentmanagement.service.impl;

import com.studentmanagement.dto.request.TaiKhoanCreateRequest;
import com.studentmanagement.dto.response.TaiKhoanResponse;
import com.studentmanagement.entity.TaiKhoan;
import com.studentmanagement.exception.BadRequestException;
import com.studentmanagement.exception.ResourceNotFoundException;
import com.studentmanagement.mapper.TaiKhoanMapper;
import com.studentmanagement.repository.TaiKhoanRepository;
import com.studentmanagement.service.TaiKhoanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TaiKhoanServiceImpl implements TaiKhoanService {

    private final TaiKhoanRepository taiKhoanRepository;
    private final TaiKhoanMapper taiKhoanMapper;

    @Override
    @Transactional(readOnly = true)
    public List<TaiKhoanResponse> getAllTaiKhoan() {
        log.info("Lấy tất cả tài khoản");
        return taiKhoanRepository.findAll()
            .stream()
            .map(taiKhoanMapper::toResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TaiKhoanResponse> getAllTaiKhoanPaged(Pageable pageable) {
        log.info("Lấy tài khoản phân trang: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        return taiKhoanRepository.findAll(pageable)
            .map(taiKhoanMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public TaiKhoanResponse getTaiKhoanById(String tenDangNhap) {
        log.info("Lấy tài khoản: {}", tenDangNhap);
        TaiKhoan taiKhoan = taiKhoanRepository.findById(tenDangNhap)
            .orElseThrow(() -> new ResourceNotFoundException("Tài khoản", "tên đăng nhập", tenDangNhap));
        return taiKhoanMapper.toResponse(taiKhoan);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaiKhoanResponse> getTaiKhoanByLoai(String loaiNguoiDung) {
        log.info("Lấy tài khoản theo loại: {}", loaiNguoiDung);
        return taiKhoanRepository.findAll()
            .stream()
            .filter(tk -> loaiNguoiDung.equals(tk.getLoaiNguoiDung()))
            .map(taiKhoanMapper::toResponse)
            .collect(Collectors.toList());
    }

    @Override
    public TaiKhoanResponse createTaiKhoan(TaiKhoanCreateRequest request) {
        log.info("Tạo tài khoản mới: {}", request.getTenDangNhap());

        // Validate input
        if (request.getTenDangNhap() == null || request.getTenDangNhap().trim().isEmpty()) {
            throw new BadRequestException("Tên đăng nhập không được để trống");
        }
        
        if (request.getMatKhau() == null || request.getMatKhau().trim().isEmpty()) {
            throw new BadRequestException("Mật khẩu không được để trống");
        }

        if (request.getLoaiNguoiDung() == null || request.getLoaiNguoiDung().trim().isEmpty()) {
            throw new BadRequestException("Loại người dùng không được để trống");
        }

        // Check if account exists
        if (taiKhoanRepository.existsById(request.getTenDangNhap().trim())) {
            throw new BadRequestException("Tên đăng nhập '" + request.getTenDangNhap() + "' đã tồn tại");
        }

        // Validate role
        String role = request.getLoaiNguoiDung().trim();
        if (!role.equals("Admin") && !role.equals("SinhVien") && !role.equals("GiangVien")) {
            throw new BadRequestException("Loại người dùng không hợp lệ. Chỉ chấp nhận: Admin, SinhVien, GiangVien");
        }

        try {
            TaiKhoan taiKhoan = taiKhoanMapper.toEntity(request);
            taiKhoan.setTenDangNhap(request.getTenDangNhap().trim());
            taiKhoan.setLoaiNguoiDung(role);
            
            TaiKhoan saved = taiKhoanRepository.save(taiKhoan);
            log.info("Tạo tài khoản thành công: {}", saved.getTenDangNhap());

            return taiKhoanMapper.toResponse(saved);
        } catch (Exception e) {
            log.error("Lỗi khi tạo tài khoản: {}", e.getMessage(), e);
            throw new BadRequestException("Không thể tạo tài khoản: " + e.getMessage());
        }
    }

    @Override
    public TaiKhoanResponse updateTaiKhoan(String tenDangNhap, TaiKhoanCreateRequest request) {
        log.info("Cập nhật tài khoản: {}", tenDangNhap);

        TaiKhoan taiKhoan = taiKhoanRepository.findById(tenDangNhap)
            .orElseThrow(() -> new ResourceNotFoundException("Tài khoản", "tên đăng nhập", tenDangNhap));

        try {
            taiKhoanMapper.updateEntityFromRequest(request, taiKhoan);
            TaiKhoan updated = taiKhoanRepository.save(taiKhoan);
            log.info("Cập nhật tài khoản thành công: {}", updated.getTenDangNhap());

            return taiKhoanMapper.toResponse(updated);
        } catch (Exception e) {
            log.error("Lỗi khi cập nhật tài khoản: {}", e.getMessage(), e);
            throw new BadRequestException("Không thể cập nhật tài khoản: " + e.getMessage());
        }
    }

    @Override
    public void deleteTaiKhoan(String tenDangNhap) {
        log.info("Xóa tài khoản: {}", tenDangNhap);

        TaiKhoan taiKhoan = taiKhoanRepository.findById(tenDangNhap)
            .orElseThrow(() -> new ResourceNotFoundException("Tài khoản", "tên đăng nhập", tenDangNhap));

        try {
            taiKhoanRepository.delete(taiKhoan);
            log.info("Xóa tài khoản thành công: {}", tenDangNhap);
        } catch (Exception e) {
            log.error("Lỗi khi xóa tài khoản: {}", e.getMessage(), e);
            throw new BadRequestException("Không thể xóa tài khoản: " + e.getMessage());
        }
    }

    @Override
    public boolean existsTaiKhoan(String tenDangNhap) {
        return taiKhoanRepository.existsById(tenDangNhap);
    }
}
