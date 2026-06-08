package com.studentmanagement.service.impl;

import com.studentmanagement.dto.request.GiangVienCreateRequest;
import com.studentmanagement.dto.response.GiangVienResponse;
import com.studentmanagement.entity.GiangVien;
import com.studentmanagement.entity.TaiKhoan;
import com.studentmanagement.exception.BadRequestException;
import com.studentmanagement.exception.ResourceNotFoundException;
import com.studentmanagement.repository.GiangVienRepository;
import com.studentmanagement.repository.TaiKhoanRepository;
import com.studentmanagement.service.GiangVienService;
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
public class GiangVienServiceImpl implements GiangVienService {
    
    private final GiangVienRepository giangVienRepository;
    private final TaiKhoanRepository taiKhoanRepository;

    @Override
    @Transactional(readOnly = true)
    public List<GiangVienResponse> getAllGiangVien() {
        log.info("Lấy tất cả giảng viên");
        return giangVienRepository.findAll()
            .stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GiangVienResponse> getAllGiangVienPaged(Pageable pageable) {
        log.info("Lấy giảng viên phân trang: page={}, size={}", 
            pageable.getPageNumber(), pageable.getPageSize());
        return giangVienRepository.findAll(pageable)
            .map(this::convertToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public GiangVienResponse getGiangVienById(String maGV) {
        log.info("Lấy giảng viên: {}", maGV);
        GiangVien giangVien = giangVienRepository.findById(maGV)
            .orElseThrow(() -> new ResourceNotFoundException("Giảng viên", "mã", maGV));
        return convertToResponse(giangVien);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GiangVienResponse> searchByHoTen(String keyword) {
        log.info("Tìm kiếm giảng viên: {}", keyword);
        return giangVienRepository.findByHoTenContaining(keyword)
            .stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    @Override
    public GiangVienResponse createGiangVien(GiangVienCreateRequest request) {
        log.info("Tạo giảng viên mới: {}", request.getMaGV());
        
        // Kiểm tra mã giảng viên đã tồn tại
        if (giangVienRepository.existsById(request.getMaGV())) {
            throw new BadRequestException("Mã giảng viên '" + request.getMaGV() + "' đã tồn tại");
        }
        
        // Kiểm tra tài khoản tồn tại
        TaiKhoan taiKhoan = taiKhoanRepository.findById(request.getTenDangNhap())
            .orElseThrow(() -> new ResourceNotFoundException("Tài khoản", "tên đăng nhập", request.getTenDangNhap()));
        
        // Kiểm tra role là GiangVien
        if (!"GiangVien".equals(taiKhoan.getLoaiNguoiDung())) {
            throw new BadRequestException("Tài khoản không phải là tài khoản giảng viên. Role: " + taiKhoan.getLoaiNguoiDung());
        }
        
        // Kiểm tra tài khoản chưa được liên kết
        if (giangVienRepository.existsByTenDangNhap(request.getTenDangNhap())) {
            throw new BadRequestException("Tài khoản này đã được liên kết với giảng viên khác");
        }
        
        // Tạo giảng viên mới
        GiangVien giangVien = new GiangVien();
        giangVien.setMaGV(request.getMaGV());
        giangVien.setHoTen(request.getHoTen());
        giangVien.setTenDangNhap(request.getTenDangNhap());
        giangVien.setEmail(request.getEmail());
        giangVien.setSoDienThoai(request.getSoDienThoai());
        giangVien.setMaKhoa(request.getMaKhoa());
        
        GiangVien saved = giangVienRepository.save(giangVien);
        log.info("Tạo giảng viên thành công: {}", saved.getMaGV());
        
        return convertToResponse(saved);
    }

    @Override
    public GiangVienResponse updateGiangVien(String maGV, GiangVienCreateRequest request) {
        log.info("Cập nhật giảng viên: {}", maGV);
        
        GiangVien giangVien = giangVienRepository.findById(maGV)
            .orElseThrow(() -> new ResourceNotFoundException("Giảng viên", "mã", maGV));
        
        // Cập nhật thông tin
        giangVien.setHoTen(request.getHoTen());
        giangVien.setEmail(request.getEmail());
        giangVien.setSoDienThoai(request.getSoDienThoai());
        giangVien.setMaKhoa(request.getMaKhoa());
        
        GiangVien updated = giangVienRepository.save(giangVien);
        log.info("Cập nhật giảng viên thành công: {}", updated.getMaGV());
        
        return convertToResponse(updated);
    }

    @Override
    public void deleteGiangVien(String maGV) {
        log.info("Xóa giảng viên: {}", maGV);
        
        GiangVien giangVien = giangVienRepository.findById(maGV)
            .orElseThrow(() -> new ResourceNotFoundException("Giảng viên", "mã", maGV));
        
        giangVienRepository.delete(giangVien);
        log.info("Xóa giảng viên thành công: {}", maGV);
    }

    /**
     * Chuyển đổi entity sang response DTO
     */
    private GiangVienResponse convertToResponse(GiangVien giangVien) {
        return new GiangVienResponse(
            giangVien.getMaGV(),
            giangVien.getHoTen(),
            giangVien.getTenDangNhap(),
            giangVien.getEmail(),
            giangVien.getSoDienThoai(),
            giangVien.getMaKhoa()
        );
    }
}
