package com.studentmanagement.service.impl;

import com.studentmanagement.dto.request.DangKyLopCreateRequest;
import com.studentmanagement.dto.response.DangKyLopResponse;
import com.studentmanagement.entity.DangKyLop;
import com.studentmanagement.entity.LopHocPhan;
import com.studentmanagement.entity.SinhVien;
import com.studentmanagement.exception.BadRequestException;
import com.studentmanagement.exception.ResourceNotFoundException;
import com.studentmanagement.repository.DangKyLopRepository;
import com.studentmanagement.repository.LopHocPhanRepository;
import com.studentmanagement.repository.SinhVienRepository;
import com.studentmanagement.service.DangKyLopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DangKyLopServiceImpl implements DangKyLopService {

    private final DangKyLopRepository dangKyLopRepository;
    private final SinhVienRepository sinhVienRepository;
    private final LopHocPhanRepository lopHocPhanRepository;

    @Override
    @Transactional(readOnly = true)
    public List<DangKyLopResponse> getAllDangKyLop() {
        log.info("Lấy tất cả đăng ký lớp");
        return dangKyLopRepository.findAll()
            .stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DangKyLopResponse> getAllDangKyLopPaged(Pageable pageable) {
        log.info("Lấy đăng ký lớp phân trang: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        return dangKyLopRepository.findAll(pageable)
            .map(this::convertToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public DangKyLopResponse getDangKyLopById(String id) {
        log.info("Lấy đăng ký lớp: {}", id);
        DangKyLop.DangKyLopId dangKyLopId = new DangKyLop.DangKyLopId();
        dangKyLopId.setMaSV(id);
        return dangKyLopRepository.findById(dangKyLopId)
            .map(this::convertToResponse)
            .orElseThrow(() -> new ResourceNotFoundException("Đăng ký lớp", "mã", id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DangKyLopResponse> getDangKyLopBySinhVien(String maSV) {
        log.info("Lấy đăng ký lớp của sinh viên: {}", maSV);
        return dangKyLopRepository.findByMaSV(maSV)
            .stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DangKyLopResponse> getDangKyLopByLopHocPhan(String maLopHP) {
        log.info("Lấy đăng ký lớp của lớp học phần: {}", maLopHP);
        return dangKyLopRepository.findByMaLop(maLopHP)
            .stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    @Override
    public DangKyLopResponse createDangKyLop(DangKyLopCreateRequest request) {
        log.info("Tạo đăng ký lớp mới: {} - {}", request.getMaSV(), request.getMaLop());

        SinhVien sinhVien = sinhVienRepository.findById(request.getMaSV())
            .orElseThrow(() -> new ResourceNotFoundException("Sinh viên", "mã", request.getMaSV()));

        LopHocPhan lopHocPhan = lopHocPhanRepository.findById(request.getMaLop())
            .orElseThrow(() -> new ResourceNotFoundException("Lớp học phần", "mã", request.getMaLop()));

        if (lopHocPhan.getSiSoHienTai() >= lopHocPhan.getSiSoToiDa()) {
            throw new BadRequestException("Lớp học phần đã đủ sĩ số tối đa");
        }

        DangKyLop.DangKyLopId id = new DangKyLop.DangKyLopId();
        id.setMaSV(request.getMaSV());
        id.setMaLop(request.getMaLop());

        if (dangKyLopRepository.existsById(id)) {
            throw new BadRequestException("Sinh viên đã đăng ký lớp học phần này");
        }

        DangKyLop dangKyLop = new DangKyLop();
        dangKyLop.setMaSV(request.getMaSV());
        dangKyLop.setMaLop(request.getMaLop());

        DangKyLop saved = dangKyLopRepository.save(dangKyLop);

        lopHocPhan.setSiSoHienTai(lopHocPhan.getSiSoHienTai() + 1);
        lopHocPhanRepository.save(lopHocPhan);

        log.info("Tạo đăng ký lớp thành công: {} - {}", saved.getMaSV(), saved.getMaLop());

        return convertToResponse(saved);
    }

    @Override
    public DangKyLopResponse updateDangKyLop(String id, DangKyLopCreateRequest request) {
        log.info("Cập nhật đăng ký lớp: {}", id);

        DangKyLop.DangKyLopId dangKyLopId = new DangKyLop.DangKyLopId();
        dangKyLopId.setMaSV(id);
        dangKyLopId.setMaLop(request.getMaLop());

        DangKyLop dangKyLop = dangKyLopRepository.findById(dangKyLopId)
            .orElseThrow(() -> new ResourceNotFoundException("Đăng ký lớp", "mã", id));

        dangKyLop.setMaLop(request.getMaLop());

        DangKyLop updated = dangKyLopRepository.save(dangKyLop);
        log.info("Cập nhật đăng ký lớp thành công");

        return convertToResponse(updated);
    }

    @Override
    public void deleteDangKyLop(String id) {
        log.info("Xóa đăng ký lớp: {}", id);

        DangKyLop.DangKyLopId dangKyLopId = new DangKyLop.DangKyLopId();
        dangKyLopId.setMaSV(id);

        DangKyLop dangKyLop = dangKyLopRepository.findById(dangKyLopId)
            .orElseThrow(() -> new ResourceNotFoundException("Đăng ký lớp", "mã", id));

        LopHocPhan lopHocPhan = lopHocPhanRepository.findById(dangKyLop.getMaLop())
            .orElse(null);

        dangKyLopRepository.delete(dangKyLop);

        if (lopHocPhan != null && lopHocPhan.getSiSoHienTai() > 0) {
            lopHocPhan.setSiSoHienTai(lopHocPhan.getSiSoHienTai() - 1);
            lopHocPhanRepository.save(lopHocPhan);
        }

        log.info("Xóa đăng ký lớp thành công");
    }

    private DangKyLopResponse convertToResponse(DangKyLop dangKyLop) {
        String ngayDangKy = "";
        if (dangKyLop.getNgayDangKy() != null) {
            ngayDangKy = dangKyLop.getNgayDangKy().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        return new DangKyLopResponse(
            dangKyLop.getMaSV(),
            dangKyLop.getMaLop(),
            ngayDangKy
        );
    }
}
