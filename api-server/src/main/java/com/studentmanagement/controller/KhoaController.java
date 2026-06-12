package com.studentmanagement.controller;

import com.studentmanagement.dto.request.KhoaCreateRequest;
import com.studentmanagement.dto.response.ApiResponse;
import com.studentmanagement.dto.response.KhoaResponse;
import com.studentmanagement.service.KhoaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/khoa")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@PreAuthorize("hasAnyRole('Admin', 'GiangVien', 'SinhVien')")
public class KhoaController {

    private final KhoaService khoaService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<KhoaResponse>>> getAllKhoa() {
        log.info("GET /khoa - Lấy tất cả khoa");
        List<KhoaResponse> khoas = khoaService.getAllKhoa();
        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách khoa thành công", khoas));
    }

    /**
     * Lấy khoa phân trang
     * GET /khoa/paged?page=0&size=10
     */
    @GetMapping("/paged")
    public ResponseEntity<ApiResponse<Page<KhoaResponse>>> getKhoaPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("GET /khoa/paged - page={}, size={}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<KhoaResponse> khoas = khoaService.getAllKhoaPaged(pageable);
        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách khoa (phân trang) thành công", khoas));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<KhoaResponse>> getKhoaById(@PathVariable String id) {
        log.info("GET /khoa/{} - Lấy khoa theo mã", id);
        KhoaResponse khoa = khoaService.getKhoaById(id);
        return ResponseEntity.ok(ApiResponse.success("Lấy khoa thành công", khoa));
    }

    /**
     * Tìm kiếm khoa theo tên
     * GET /khoa/search?keyword=Công nghệ
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<KhoaResponse>>> searchKhoa(
            @RequestParam String keyword) {
        log.info("GET /khoa/search - keyword={}", keyword);
        List<KhoaResponse> khoas = khoaService.searchByTenKhoa(keyword);
        return ResponseEntity.ok(ApiResponse.success("Tìm kiếm khoa thành công", khoas));
    }

    @PostMapping
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse<KhoaResponse>> createKhoa(
            @Valid @RequestBody KhoaCreateRequest request) {
        log.info("POST /khoa - Tạo khoa mới: {}", request.getMaKhoa());
        KhoaResponse khoa = khoaService.createKhoa(request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Tạo khoa thành công", khoa));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse<KhoaResponse>> updateKhoa(
            @PathVariable String id,
            @Valid @RequestBody KhoaCreateRequest request) {
        log.info("PUT /khoa/{} - Cập nhật khoa", id);
        KhoaResponse khoa = khoaService.updateKhoa(id, request);
        return ResponseEntity.ok(ApiResponse.success("Cập nhật khoa thành công", khoa));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse<?>> deleteKhoa(@PathVariable String id) {
        log.info("DELETE /khoa/{} - Xóa khoa", id);
        khoaService.deleteKhoa(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa khoa thành công", null));
    }
}
