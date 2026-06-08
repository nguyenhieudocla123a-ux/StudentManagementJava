package com.studentmanagement.service.impl;

import com.studentmanagement.dto.request.TaiKhoanCreateRequest;
import com.studentmanagement.dto.response.TaiKhoanResponse;
import com.studentmanagement.entity.TaiKhoan;
import com.studentmanagement.exception.BadRequestException;
import com.studentmanagement.exception.ResourceNotFoundException;
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

    @Override
    @Transactional(readOnly = true)
    public List<TaiKhoanResponse> getAllTaiKhoan() {
        log.info("Lấy tất cả tài khoản");
        return taiKhoanRepository.findAll()
            .stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TaiKhoanResponse> getAllTaiKhoanPaged(Pageable pageable) {
        log.info("Lấy tài khoản phân trang: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        return taiKhoanRepository.findAll(pageable)
            .map(this::convertToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public TaiKhoanResponse getTaiKhoanById(String tenDangNhap) {
        log.info("Lấy tài khoản: {}", tenDangNhap);
        TaiKhoan taiKhoan = taiKhoanRepository.findById(tenDangNhap)
            .orElseThrow(() -> new ResourceNotFoundException("Tài khoản", "tên đăng nhập", tenDangNhap));
        return convertToResponse(taiKhoan);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaiKhoanResponse> getTaiKhoanByLoai(String loaiNguoiDung) {
        log.info("Lấy tài khoản theo loại: {}", loaiNguoiDung);
        return taiKhoanRepository.findAll()
            .stream()
            .filter(tk -> loaiNguoiDung.equals(tk.getLoaiNguoiDung()))
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    @Override
    public TaiKhoanResponse createTaiKhoan(TaiKhoanCreateRequest request) {
        log.info("Tạo tài khoản mới: {}", request.getTenDangNhap());

        if (taiKhoanRepository.existsById(request.getTenDangNhap())) {
            throw new BadRequestException("Tên đăng nhập '" + request.getTenDangNhap() + "' đã tồn tại");
        }

        TaiKhoan taiKhoan = new TaiKhoan();
        taiKhoan.setTenDangNhap(request.getTenDangNhap());
        taiKhoan.setMatKhau(request.getMatKhau());
        taiKhoan.setLoaiNguoiDung(request.getLoaiNguoiDung());
        taiKhoan.setOnlineStatus("Offline");

        TaiKhoan saved = taiKhoanRepository.save(taiKhoan);
        log.info("Tạo tài khoản thành công: {}", saved.getTenDangNhap());

        return convertToResponse(saved);
    }

    @Override
    public TaiKhoanResponse updateTaiKhoan(String tenDangNhap, TaiKhoanCreateRequest request) {
        log.info("Cập nhật tài khoản: {}", tenDangNhap);

        TaiKhoan taiKhoan = taiKhoanRepository.findById(tenDangNhap)
            .orElseThrow(() -> new ResourceNotFoundException("Tài khoản", "tên đăng nhập", tenDangNhap));

        taiKhoan.setMatKhau(request.getMatKhau());
        taiKhoan.setLoaiNguoiDung(request.getLoaiNguoiDung());

        TaiKhoan updated = taiKhoanRepository.save(taiKhoan);
        log.info("Cập nhật tài khoản thành công: {}", updated.getTenDangNhap());

        return convertToResponse(updated);
    }

    @Override
    public void deleteTaiKhoan(String tenDangNhap) {
        log.info("Xóa tài khoản: {}", tenDangNhap);

        TaiKhoan taiKhoan = taiKhoanRepository.findById(tenDangNhap)
            .orElseThrow(() -> new ResourceNotFoundException("Tài khoản", "tên đăng nhập", tenDangNhap));

        taiKhoanRepository.delete(taiKhoan);
        log.info("Xóa tài khoản thành công: {}", tenDangNhap);
    }

    @Override
    public boolean existsTaiKhoan(String tenDangNhap) {
        return taiKhoanRepository.existsById(tenDangNhap);
    }

    private TaiKhoanResponse convertToResponse(TaiKhoan taiKhoan) {
        return new TaiKhoanResponse(
            taiKhoan.getTenDangNhap(),
            taiKhoan.getLoaiNguoiDung(),
            taiKhoan.getOnlineStatus()
        );
    }
}
