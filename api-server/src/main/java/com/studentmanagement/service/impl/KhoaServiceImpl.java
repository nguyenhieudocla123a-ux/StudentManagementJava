package com.studentmanagement.service.impl;

import com.studentmanagement.dto.request.KhoaCreateRequest;
import com.studentmanagement.dto.response.KhoaResponse;
import com.studentmanagement.entity.Khoa;
import com.studentmanagement.exception.BadRequestException;
import com.studentmanagement.exception.ResourceNotFoundException;
import com.studentmanagement.repository.KhoaRepository;
import com.studentmanagement.service.KhoaService;
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
public class KhoaServiceImpl implements KhoaService {

    private final KhoaRepository khoaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<KhoaResponse> getAllKhoa() {
        log.info("Lấy tất cả khoa");
        return khoaRepository.findAll()
            .stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<KhoaResponse> getAllKhoaPaged(Pageable pageable) {
        log.info("Lấy khoa phân trang: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        return khoaRepository.findAll(pageable)
            .map(this::convertToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public KhoaResponse getKhoaById(String maKhoa) {
        log.info("Lấy khoa: {}", maKhoa);
        Khoa khoa = khoaRepository.findById(maKhoa)
            .orElseThrow(() -> new ResourceNotFoundException("Khoa", "mã", maKhoa));
        return convertToResponse(khoa);
    }

    @Override
    @Transactional(readOnly = true)
    public List<KhoaResponse> searchByTenKhoa(String tenKhoa) {
        log.info("Tìm kiếm khoa: {}", tenKhoa);
        return khoaRepository.findByTenKhoa(tenKhoa)
            .stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    @Override
    public KhoaResponse createKhoa(KhoaCreateRequest request) {
        log.info("Tạo khoa mới: {}", request.getMaKhoa());

        if (khoaRepository.existsById(request.getMaKhoa())) {
            throw new BadRequestException("Mã khoa '" + request.getMaKhoa() + "' đã tồn tại");
        }

        if (khoaRepository.findByTenKhoa(request.getTenKhoa()) != null) {
            throw new BadRequestException("Tên khoa '" + request.getTenKhoa() + "' đã tồn tại");
        }

        Khoa khoa = new Khoa();
        khoa.setMaKhoa(request.getMaKhoa());
        khoa.setTenKhoa(request.getTenKhoa());

        Khoa saved = khoaRepository.save(khoa);
        log.info("Tạo khoa thành công: {}", saved.getMaKhoa());

        return convertToResponse(saved);
    }

    @Override
    public KhoaResponse updateKhoa(String maKhoa, KhoaCreateRequest request) {
        log.info("Cập nhật khoa: {}", maKhoa);

        Khoa khoa = khoaRepository.findById(maKhoa)
            .orElseThrow(() -> new ResourceNotFoundException("Khoa", "mã", maKhoa));

        khoa.setTenKhoa(request.getTenKhoa());

        Khoa updated = khoaRepository.save(khoa);
        log.info("Cập nhật khoa thành công: {}", updated.getMaKhoa());

        return convertToResponse(updated);
    }

    @Override
    public void deleteKhoa(String maKhoa) {
        log.info("Xóa khoa: {}", maKhoa);

        Khoa khoa = khoaRepository.findById(maKhoa)
            .orElseThrow(() -> new ResourceNotFoundException("Khoa", "mã", maKhoa));

        khoaRepository.delete(khoa);
        log.info("Xóa khoa thành công: {}", maKhoa);
    }

    private KhoaResponse convertToResponse(Khoa khoa) {
        return new KhoaResponse(
            khoa.getMaKhoa(),
            khoa.getTenKhoa()
        );
    }
}
