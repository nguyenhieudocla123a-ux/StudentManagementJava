package com.studentmanagement.service.impl;

import com.studentmanagement.dto.request.QuanTriVienCreateRequest;
import com.studentmanagement.dto.response.QuanTriVienResponse;
import com.studentmanagement.entity.QuanTriVien;
import com.studentmanagement.entity.TaiKhoan;
import com.studentmanagement.exception.BadRequestException;
import com.studentmanagement.exception.ResourceNotFoundException;
import com.studentmanagement.repository.QuanTriVienRepository;
import com.studentmanagement.repository.TaiKhoanRepository;
import com.studentmanagement.service.QuanTriVienService;
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
public class QuanTriVienServiceImpl implements QuanTriVienService {

    private final QuanTriVienRepository quanTriVienRepository;
    private final TaiKhoanRepository taiKhoanRepository;

    @Override
    @Transactional(readOnly = true)
    public List<QuanTriVienResponse> getAllQuanTriVien() {
        log.info("Lấy tất cả quản trị viên");
        return quanTriVienRepository.findAll()
            .stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QuanTriVienResponse> getAllQuanTriVienPaged(Pageable pageable) {
        log.info("Lấy quản trị viên phân trang: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        return quanTriVienRepository.findAll(pageable)
            .map(this::convertToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public QuanTriVienResponse getQuanTriVienById(String maQTV) {
        log.info("Lấy quản trị viên: {}", maQTV);
        QuanTriVien quanTriVien = quanTriVienRepository.findById(maQTV)
            .orElseThrow(() -> new ResourceNotFoundException("Quản trị viên", "mã", maQTV));
        return convertToResponse(quanTriVien);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuanTriVienResponse> searchByHoTen(String keyword) {
        log.info("Tìm kiếm quản trị viên: {}", keyword);
        return quanTriVienRepository.findByHoTenContaining(keyword)
            .stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    @Override
    public QuanTriVienResponse createQuanTriVien(QuanTriVienCreateRequest request) {
        log.info("Tạo quản trị viên mới: {}", request.getMaQTV());

        if (quanTriVienRepository.existsById(request.getMaQTV())) {
            throw new BadRequestException("Mã quản trị viên '" + request.getMaQTV() + "' đã tồn tại");
        }

        TaiKhoan taiKhoan = taiKhoanRepository.findById(request.getTenDangNhap())
            .orElseThrow(() -> new ResourceNotFoundException("Tài khoản", "tên đăng nhập", request.getTenDangNhap()));

        if (!"QuanTriVien".equals(taiKhoan.getLoaiNguoiDung())) {
            throw new BadRequestException("Tài khoản không phải là quản trị viên. Role: " + taiKhoan.getLoaiNguoiDung());
        }

        QuanTriVien existing = quanTriVienRepository.findByTenDangNhap(request.getTenDangNhap()).orElse(null);
        if (existing != null && !existing.getMaQTV().equals(request.getMaQTV())) {
            throw new BadRequestException("Tài khoản này đã được liên kết với quản trị viên khác");
        }

        QuanTriVien quanTriVien = new QuanTriVien();
        quanTriVien.setMaQTV(request.getMaQTV());
        quanTriVien.setHoTen(request.getHoTen());
        quanTriVien.setTenDangNhap(request.getTenDangNhap());
        quanTriVien.setEmail(request.getEmail());
        quanTriVien.setSoDienThoai(request.getSoDienThoai());

        QuanTriVien saved = quanTriVienRepository.save(quanTriVien);
        log.info("Tạo quản trị viên thành công: {}", saved.getMaQTV());

        return convertToResponse(saved);
    }

    @Override
    public QuanTriVienResponse updateQuanTriVien(String maQTV, QuanTriVienCreateRequest request) {
        log.info("Cập nhật quản trị viên: {}", maQTV);

        QuanTriVien quanTriVien = quanTriVienRepository.findById(maQTV)
            .orElseThrow(() -> new ResourceNotFoundException("Quản trị viên", "mã", maQTV));

        quanTriVien.setHoTen(request.getHoTen());
        quanTriVien.setEmail(request.getEmail());
        quanTriVien.setSoDienThoai(request.getSoDienThoai());

        QuanTriVien updated = quanTriVienRepository.save(quanTriVien);
        log.info("Cập nhật quản trị viên thành công: {}", updated.getMaQTV());

        return convertToResponse(updated);
    }

    @Override
    public void deleteQuanTriVien(String maQTV) {
        log.info("Xóa quản trị viên: {}", maQTV);

        QuanTriVien quanTriVien = quanTriVienRepository.findById(maQTV)
            .orElseThrow(() -> new ResourceNotFoundException("Quản trị viên", "mã", maQTV));

        quanTriVienRepository.delete(quanTriVien);
        log.info("Xóa quản trị viên thành công: {}", maQTV);
    }

    private QuanTriVienResponse convertToResponse(QuanTriVien quanTriVien) {
        return new QuanTriVienResponse(
            quanTriVien.getMaQTV(),
            quanTriVien.getHoTen(),
            quanTriVien.getTenDangNhap(),
            quanTriVien.getEmail(),
            quanTriVien.getSoDienThoai()
        );
    }
}
