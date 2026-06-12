package com.studentmanagement.controller;

import com.studentmanagement.dto.request.MonHocCreateRequest;
import com.studentmanagement.dto.response.ApiResponse;
import com.studentmanagement.dto.response.MonHocResponse;
import com.studentmanagement.service.MonHocService;
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
@RequestMapping("/subjects")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@PreAuthorize("hasAnyRole('Admin', 'GiangVien', 'SinhVien')")
public class MonHocController {
    
    private final MonHocService monHocService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<MonHocResponse>>> getAllSubjects() {
        log.info("GET /subjects - Lấy tất cả môn học");
        List<MonHocResponse> subjects = monHocService.getAllMonHoc();
        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách môn học thành công", subjects));
    }

    /**
     * Lấy môn học phân trang
     * GET /subjects/paged?page=0&size=10
     */
    @GetMapping("/paged")
    public ResponseEntity<ApiResponse<Page<MonHocResponse>>> getSubjectsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("GET /subjects/paged - page={}, size={}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<MonHocResponse> subjects = monHocService.getAllMonHocPaged(pageable);
        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách môn học (phân trang) thành công", subjects));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MonHocResponse>> getSubjectById(@PathVariable String id) {
        log.info("GET /subjects/{} - Lấy môn học theo mã", id);
        MonHocResponse subject = monHocService.getMonHocById(id);
        return ResponseEntity.ok(ApiResponse.success("Lấy môn học thành công", subject));
    }

    /**
     * Tìm kiếm môn học theo tên
     * GET /subjects/search?keyword=Toán
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<MonHocResponse>>> searchSubjects(@RequestParam String keyword) {
        log.info("GET /subjects/search - keyword={}", keyword);
        List<MonHocResponse> subjects = monHocService.searchByTenMonHoc(keyword);
        return ResponseEntity.ok(ApiResponse.success("Tìm kiếm môn học thành công", subjects));
    }

    /**
     * Lấy môn học theo khoa
     * GET /subjects/by-khoa/{maKhoa}
     */
    @GetMapping("/by-khoa/{maKhoa}")
    public ResponseEntity<ApiResponse<List<MonHocResponse>>> getSubjectsByKhoa(@PathVariable String maKhoa) {
        log.info("GET /subjects/by-khoa/{} - Lấy môn học theo khoa", maKhoa);
        List<MonHocResponse> subjects = monHocService.getMonHocByKhoa(maKhoa);
        return ResponseEntity.ok(ApiResponse.success("Lấy môn học theo khoa thành công", subjects));
    }
    
    @PostMapping
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse<MonHocResponse>> createSubject(
            @Valid @RequestBody MonHocCreateRequest request) {
        log.info("POST /subjects - Tạo môn học mới: {}", request.getMaMH());
        MonHocResponse subject = monHocService.createMonHoc(request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Tạo môn học thành công", subject));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse<MonHocResponse>> updateSubject(
            @PathVariable String id,
            @Valid @RequestBody MonHocCreateRequest request) {
        log.info("PUT /subjects/{} - Cập nhật môn học", id);
        MonHocResponse subject = monHocService.updateMonHoc(id, request);
        return ResponseEntity.ok(ApiResponse.success("Cập nhật môn học thành công", subject));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse<?>> deleteSubject(@PathVariable String id) {
        log.info("DELETE /subjects/{} - Xóa môn học", id);
        monHocService.deleteMonHoc(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa môn học thành công", null));
    }
}
