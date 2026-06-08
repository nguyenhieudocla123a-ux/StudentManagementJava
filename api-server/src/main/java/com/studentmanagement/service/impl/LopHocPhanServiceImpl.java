package com.studentmanagement.service.impl;

import com.studentmanagement.dto.request.LopHocPhanCreateRequest;
import com.studentmanagement.dto.response.LopHocPhanResponse;
import com.studentmanagement.entity.LopHocPhan;
import com.studentmanagement.exception.BadRequestException;
import com.studentmanagement.exception.ResourceNotFoundException;
import com.studentmanagement.repository.LopHocPhanRepository;
import com.studentmanagement.repository.MonHocRepository;
import com.studentmanagement.repository.GiangVienRepository;
import com.studentmanagement.service.LopHocPhanService;
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
public class LopHocPhanServiceImpl implements LopHocPhanService {

    private final LopHocPhanRepository lopHocPhanRepository;
    private final MonHocRepository monHocRepository;
    private final GiangVienRepository giangVienRepository;

    @Override
    @Transactional(readOnly = true)
    public List<LopHocPhanResponse> getAllLopHocPhan() {
        log.info("Lấy tất cả lớp học phần");
        return lopHocPhanRepository.findAll()
            .stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LopHocPhanResponse> getAllLopHocPhanPaged(Pageable pageable) {
        log.info("Lấy lớp học phần phân trang: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        return lopHocPhanRepository.findAll(pageable)
            .map(this::convertToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public LopHocPhanResponse getLopHocPhanById(String maLop) {
        log.info("Lấy lớp học phần: {}", maLop);
        LopHocPhan lopHocPhan = lopHocPhanRepository.findById(maLop)
            .orElseThrow(() -> new ResourceNotFoundException("Lớp học phần", "mã", maLop));
        return convertToResponse(lopHocPhan);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LopHocPhanResponse> getLopHocPhanByMonHoc(String maMonHoc) {
        log.info("Lấy lớp học phần theo môn học: {}", maMonHoc);
        return lopHocPhanRepository.findByMaMH(maMonHoc)
            .stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LopHocPhanResponse> getLopHocPhanByGiangVien(String maGV) {
        log.info("Lấy lớp học phần theo giảng viên: {}", maGV);
        return lopHocPhanRepository.findByMaGV(maGV)
            .stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    @Override
    public LopHocPhanResponse createLopHocPhan(LopHocPhanCreateRequest request) {
        log.info("Tạo lớp học phần mới: {}", request.getMaLop());

        if (lopHocPhanRepository.existsById(request.getMaLop())) {
            throw new BadRequestException("Mã lớp học phần '" + request.getMaLop() + "' đã tồn tại");
        }

        if (!monHocRepository.existsById(request.getMaMH())) {
            throw new ResourceNotFoundException("Môn học", "mã", request.getMaMH());
        }

        if (!giangVienRepository.existsById(request.getMaGV())) {
            throw new ResourceNotFoundException("Giảng viên", "mã", request.getMaGV());
        }

        LopHocPhan lopHocPhan = new LopHocPhan();
        lopHocPhan.setMaLop(request.getMaLop());
        lopHocPhan.setMaMH(request.getMaMH());
        lopHocPhan.setMaGV(request.getMaGV());
        lopHocPhan.setHocKy(request.getHocKy());
        lopHocPhan.setNamHoc(request.getNamHoc());
        lopHocPhan.setSiSoToiDa(request.getSiSoToiDa());
        lopHocPhan.setSiSoHienTai(0);
        lopHocPhan.setTrangThai(request.getTrangThai());

        LopHocPhan saved = lopHocPhanRepository.save(lopHocPhan);
        log.info("Tạo lớp học phần thành công: {}", saved.getMaLop());

        return convertToResponse(saved);
    }

    @Override
    public LopHocPhanResponse updateLopHocPhan(String maLop, LopHocPhanCreateRequest request) {
        log.info("Cập nhật lớp học phần: {}", maLop);

        LopHocPhan lopHocPhan = lopHocPhanRepository.findById(maLop)
            .orElseThrow(() -> new ResourceNotFoundException("Lớp học phần", "mã", maLop));

        if (!monHocRepository.existsById(request.getMaMH())) {
            throw new ResourceNotFoundException("Môn học", "mã", request.getMaMH());
        }

        if (!giangVienRepository.existsById(request.getMaGV())) {
            throw new ResourceNotFoundException("Giảng viên", "mã", request.getMaGV());
        }

        lopHocPhan.setMaMH(request.getMaMH());
        lopHocPhan.setMaGV(request.getMaGV());
        lopHocPhan.setHocKy(request.getHocKy());
        lopHocPhan.setNamHoc(request.getNamHoc());
        lopHocPhan.setSiSoToiDa(request.getSiSoToiDa());
        if (request.getTrangThai() != null) {
            lopHocPhan.setTrangThai(request.getTrangThai());
        }

        LopHocPhan updated = lopHocPhanRepository.save(lopHocPhan);
        log.info("Cập nhật lớp học phần thành công: {}", updated.getMaLop());

        return convertToResponse(updated);
    }

    @Override
    public void deleteLopHocPhan(String maLop) {
        log.info("Xóa lớp học phần: {}", maLop);

        LopHocPhan lopHocPhan = lopHocPhanRepository.findById(maLop)
            .orElseThrow(() -> new ResourceNotFoundException("Lớp học phần", "mã", maLop));

        lopHocPhanRepository.delete(lopHocPhan);
        log.info("Xóa lớp học phần thành công: {}", maLop);
    }

    private LopHocPhanResponse convertToResponse(LopHocPhan lopHocPhan) {
        return new LopHocPhanResponse(
            lopHocPhan.getMaLop(),
            lopHocPhan.getMaMH(),
            lopHocPhan.getMaGV(),
            lopHocPhan.getHocKy(),
            lopHocPhan.getNamHoc(),
            lopHocPhan.getSiSoToiDa(),
            lopHocPhan.getSiSoHienTai(),
            lopHocPhan.getTrangThai()
        );
    }
}
