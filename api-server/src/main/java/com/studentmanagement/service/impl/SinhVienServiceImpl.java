package com.studentmanagement.service.impl;

import com.studentmanagement.dto.request.SinhVienCreateRequest;
import com.studentmanagement.dto.response.SinhVienResponse;
import com.studentmanagement.entity.SinhVien;
import com.studentmanagement.entity.TaiKhoan;
import com.studentmanagement.exception.BadRequestException;
import com.studentmanagement.exception.ResourceNotFoundException;
import com.studentmanagement.repository.SinhVienRepository;
import com.studentmanagement.repository.TaiKhoanRepository;
import com.studentmanagement.service.SinhVienService;
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
public class SinhVienServiceImpl implements SinhVienService {
    
    private final SinhVienRepository sinhVienRepository;
    private final TaiKhoanRepository taiKhoanRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SinhVienResponse> getAllSinhVien() {
        log.info("Lấy tất cả sinh viên");
        return sinhVienRepository.findAll()
            .stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SinhVienResponse> getAllSinhVienPaged(Pageable pageable) {
        log.info("Lấy sinh viên phân trang: page={}, size={}", 
            pageable.getPageNumber(), pageable.getPageSize());
        return sinhVienRepository.findAll(pageable)
            .map(this::convertToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public SinhVienResponse getSinhVienById(String maSV) {
        log.info("Lấy sinh viên: {}", maSV);
        SinhVien sinhVien = sinhVienRepository.findById(maSV)
            .orElseThrow(() -> new ResourceNotFoundException("Sinh viên", "mã", maSV));
        return convertToResponse(sinhVien);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SinhVienResponse> searchByHoTen(String keyword) {
        log.info("Tìm kiếm sinh viên: {}", keyword);
        return sinhVienRepository.findByHoTenContaining(keyword)
            .stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    @Override
    public SinhVienResponse createSinhVien(SinhVienCreateRequest request) {
        log.info("Tạo sinh viên mới: {}", request.getMaSV());
        
        // Kiểm tra mã sinh viên đã tồn tại
        if (sinhVienRepository.existsById(request.getMaSV())) {
            throw new BadRequestException("Mã sinh viên '" + request.getMaSV() + "' đã tồn tại");
        }
        
        // Kiểm tra tài khoản tồn tại
        TaiKhoan taiKhoan = taiKhoanRepository.findById(request.getTenDangNhap())
            .orElseThrow(() -> new ResourceNotFoundException("Tài khoản", "tên đăng nhập", request.getTenDangNhap()));
        
        // Kiểm tra role là SinhVien
        if (!"SinhVien".equals(taiKhoan.getLoaiNguoiDung())) {
            throw new BadRequestException("Tài khoản không phải là tài khoản sinh viên. Role: " + taiKhoan.getLoaiNguoiDung());
        }
        
        // Kiểm tra tài khoản chưa được liên kết
        if (sinhVienRepository.existsByTenDangNhap(request.getTenDangNhap())) {
            throw new BadRequestException("Tài khoản này đã được liên kết với sinh viên khác");
        }
        
        // Tạo sinh viên mới
        SinhVien sinhVien = new SinhVien();
        sinhVien.setMaSV(request.getMaSV());
        sinhVien.setHoTen(request.getHoTen());
        sinhVien.setTenDangNhap(request.getTenDangNhap());
        sinhVien.setEmail(request.getEmail());
        sinhVien.setSoDienThoai(request.getSoDienThoai());
        sinhVien.setMaKhoa(request.getMaKhoa());
        sinhVien.setNgaySinh(request.getNgaySinh());
        sinhVien.setGioiTinh(request.getGioiTinh());
        sinhVien.setDiaChi(request.getDiaChi());
        
        SinhVien saved = sinhVienRepository.save(sinhVien);
        log.info("Tạo sinh viên thành công: {}", saved.getMaSV());
        
        return convertToResponse(saved);
    }

    @Override
    public SinhVienResponse updateSinhVien(String maSV, SinhVienCreateRequest request) {
        log.info("Cập nhật sinh viên: {}", maSV);
        
        SinhVien sinhVien = sinhVienRepository.findById(maSV)
            .orElseThrow(() -> new ResourceNotFoundException("Sinh viên", "mã", maSV));
        
        // Cập nhật thông tin
        sinhVien.setHoTen(request.getHoTen());
        sinhVien.setEmail(request.getEmail());
        sinhVien.setSoDienThoai(request.getSoDienThoai());
        sinhVien.setMaKhoa(request.getMaKhoa());
        sinhVien.setNgaySinh(request.getNgaySinh());
        sinhVien.setGioiTinh(request.getGioiTinh());
        sinhVien.setDiaChi(request.getDiaChi());
        
        SinhVien updated = sinhVienRepository.save(sinhVien);
        log.info("Cập nhật sinh viên thành công: {}", updated.getMaSV());
        
        return convertToResponse(updated);
    }

    @Override
    public void deleteSinhVien(String maSV) {
        log.info("Xóa sinh viên: {}", maSV);
        
        SinhVien sinhVien = sinhVienRepository.findById(maSV)
            .orElseThrow(() -> new ResourceNotFoundException("Sinh viên", "mã", maSV));
        
        sinhVienRepository.delete(sinhVien);
        log.info("Xóa sinh viên thành công: {}", maSV);
    }

    /**
     * Chuyển đổi entity sang response DTO
     */
    private SinhVienResponse convertToResponse(SinhVien sinhVien) {
        return new SinhVienResponse(
            sinhVien.getMaSV(),
            sinhVien.getHoTen(),
            sinhVien.getTenDangNhap(),
            sinhVien.getEmail(),
            sinhVien.getSoDienThoai(),
            sinhVien.getMaKhoa(),
            sinhVien.getNgaySinh(),
            sinhVien.getGioiTinh(),
            sinhVien.getDiaChi()
        );
    }
}
