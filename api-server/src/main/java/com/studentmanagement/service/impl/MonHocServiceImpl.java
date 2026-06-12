package com.studentmanagement.service.impl;

import com.studentmanagement.dto.request.MonHocCreateRequest;
import com.studentmanagement.dto.response.MonHocResponse;
import com.studentmanagement.entity.MonHoc;
import com.studentmanagement.exception.BadRequestException;
import com.studentmanagement.exception.ResourceNotFoundException;
import com.studentmanagement.mapper.MonHocMapper;
import com.studentmanagement.repository.MonHocRepository;
import com.studentmanagement.service.MonHocService;
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
public class MonHocServiceImpl implements MonHocService {

    private final MonHocRepository monHocRepository;
    private final MonHocMapper monHocMapper;

    @Override
    @Transactional(readOnly = true)
    public List<MonHocResponse> getAllMonHoc() {
        log.info("Lấy tất cả môn học");
        return monHocRepository.findAll()
            .stream()
            .map(monHocMapper::toResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MonHocResponse> getAllMonHocPaged(Pageable pageable) {
        log.info("Lấy môn học phân trang: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        return monHocRepository.findAll(pageable)
            .map(this::convertToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public MonHocResponse getMonHocById(String maMH) {
        log.info("Lấy môn học: {}", maMH);
        MonHoc monHoc = monHocRepository.findById(maMH)
            .orElseThrow(() -> new ResourceNotFoundException("Môn học", "mã", maMH));
        return convertToResponse(monHoc);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MonHocResponse> searchByTenMonHoc(String tenMonHoc) {
        log.info("Tìm kiếm môn học: {}", tenMonHoc);
        return monHocRepository.findByTenMHContaining(tenMonHoc)
            .stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MonHocResponse> getMonHocByKhoa(String maKhoa) {
        log.info("Lấy môn học theo khoa: {}", maKhoa);
        return monHocRepository.findAll()
            .stream()
            .filter(mh -> maKhoa.equals(mh.getMaKhoa()))
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    @Override
    public MonHocResponse createMonHoc(MonHocCreateRequest request) {
        log.info("Tạo môn học mới: {}", request.getMaMH());

        if (monHocRepository.existsById(request.getMaMH())) {
            throw new BadRequestException("Mã môn học '" + request.getMaMH() + "' đã tồn tại");
        }

        MonHoc monHoc = new MonHoc();
        monHoc.setMaMH(request.getMaMH());
        monHoc.setTenMH(request.getTenMH());
        monHoc.setSoTinChi(request.getSoTinChi());
        monHoc.setMoTa(request.getMoTa());
        monHoc.setMaKhoa(request.getMaKhoa());

        MonHoc saved = monHocRepository.save(monHoc);
        log.info("Tạo môn học thành công: {}", saved.getMaMH());

        return convertToResponse(saved);
    }

    @Override
    public MonHocResponse updateMonHoc(String maMH, MonHocCreateRequest request) {
        log.info("Cập nhật môn học: {}", maMH);

        MonHoc monHoc = monHocRepository.findById(maMH)
            .orElseThrow(() -> new ResourceNotFoundException("Môn học", "mã", maMH));

        monHoc.setTenMH(request.getTenMH());
        monHoc.setSoTinChi(request.getSoTinChi());
        monHoc.setMoTa(request.getMoTa());
        monHoc.setMaKhoa(request.getMaKhoa());

        MonHoc updated = monHocRepository.save(monHoc);
        log.info("Cập nhật môn học thành công: {}", updated.getMaMH());

        return convertToResponse(updated);
    }

    @Override
    public void deleteMonHoc(String maMH) {
        log.info("Xóa môn học: {}", maMH);

        MonHoc monHoc = monHocRepository.findById(maMH)
            .orElseThrow(() -> new ResourceNotFoundException("Môn học", "mã", maMH));

        monHocRepository.delete(monHoc);
        log.info("Xóa môn học thành công: {}", maMH);
    }

    private MonHocResponse convertToResponse(MonHoc monHoc) {
        return new MonHocResponse(
            monHoc.getMaMH(),
            monHoc.getTenMH(),
            monHoc.getSoTinChi(),
            monHoc.getMoTa(),
            monHoc.getMaKhoa()
        );
    }
}
