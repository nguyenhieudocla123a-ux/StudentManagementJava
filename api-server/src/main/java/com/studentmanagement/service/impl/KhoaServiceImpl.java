package com.studentmanagement.service.impl;

import com.studentmanagement.dto.request.KhoaCreateRequest;
import com.studentmanagement.dto.response.KhoaResponse;
import com.studentmanagement.entity.Khoa;
import com.studentmanagement.exception.BadRequestException;
import com.studentmanagement.exception.ResourceNotFoundException;
import com.studentmanagement.mapper.KhoaMapper;
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
    private final KhoaMapper khoaMapper;

    @Override
    @Transactional(readOnly = true)
    public List<KhoaResponse> getAllKhoa() {
        log.info("Lấy tất cả khoa");
        return khoaRepository.findAll()
            .stream()
            .map(khoaMapper::toResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<KhoaResponse> getAllKhoaPaged(Pageable pageable) {
        log.info("Lấy khoa phân trang: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        return khoaRepository.findAll(pageable)
            .map(khoaMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public KhoaResponse getKhoaById(String maKhoa) {
        log.info("Lấy khoa: {}", maKhoa);
        Khoa khoa = khoaRepository.findById(maKhoa)
            .orElseThrow(() -> new ResourceNotFoundException("Khoa", "mã", maKhoa));
        return khoaMapper.toResponse(khoa);
    }

    @Override
    @Transactional(readOnly = true)
    public List<KhoaResponse> searchByTenKhoa(String tenKhoa) {
        log.info("Tìm kiếm khoa: {}", tenKhoa);
        return khoaRepository.findByTenKhoa(tenKhoa)
            .stream()
            .map(khoaMapper::toResponse)
            .collect(Collectors.toList());
    }

    @Override
    public KhoaResponse createKhoa(KhoaCreateRequest request) {
        log.info("Tạo khoa mới: {}", request.getMaKhoa());

        if (khoaRepository.existsById(request.getMaKhoa())) {
            throw new BadRequestException("Mã khoa '" + request.getMaKhoa() + "' đã tồn tại");
        }

        if (khoaRepository.findByTenKhoa(request.getTenKhoa()).isPresent()) {
            throw new BadRequestException("Tên khoa '" + request.getTenKhoa() + "' đã tồn tại");
        }

        Khoa khoa = khoaMapper.toEntity(request);
        Khoa saved = khoaRepository.save(khoa);
        log.info("Tạo khoa thành công: {}", saved.getMaKhoa());

        return khoaMapper.toResponse(saved);
    }

    @Override
    public KhoaResponse updateKhoa(String maKhoa, KhoaCreateRequest request) {
        log.info("Cập nhật khoa: {}", maKhoa);

        Khoa khoa = khoaRepository.findById(maKhoa)
            .orElseThrow(() -> new ResourceNotFoundException("Khoa", "mã", maKhoa));

        khoaMapper.updateEntityFromRequest(request, khoa);
        Khoa updated = khoaRepository.save(khoa);
        log.info("Cập nhật khoa thành công: {}", updated.getMaKhoa());

        return khoaMapper.toResponse(updated);
    }

    @Override
    public void deleteKhoa(String maKhoa) {
        log.info("Xóa khoa: {}", maKhoa);

        Khoa khoa = khoaRepository.findById(maKhoa)
            .orElseThrow(() -> new ResourceNotFoundException("Khoa", "mã", maKhoa));

        khoaRepository.delete(khoa);
        log.info("Xóa khoa thành công: {}", maKhoa);
    }
}
