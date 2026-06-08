package com.studentmanagement.controller;

import com.studentmanagement.dto.request.GiangVienCreateRequest;
import com.studentmanagement.dto.response.ApiResponse;
import com.studentmanagement.dto.response.GiangVienResponse;
import com.studentmanagement.service.GiangVienService;
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
@RequestMapping("/teachers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@PreAuthorize("hasAnyRole('Admin', 'GiangVien', 'SinhVien')")
public class GiangVienController {
    
    private final GiangVienService giangVienService;
    
    /**
     * Lấy tất cả giảng viên
     * GET /api/teachers
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<GiangVienResponse>>> getAllTeachers() {
        log.info("GET /teachers - Lấy tất cả giảng viên");
        List<GiangVienResponse> teachers = giangVienService.getAllGiangVien();
        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách giảng viên thành công", teachers));
    }
    
    /**
     * Lấy giảng viên phân trang
     * GET /api/teachers/paged?page=0&size=10
     */
    @GetMapping("/paged")
    public ResponseEntity<ApiResponse<Page<GiangVienResponse>>> getTeachersPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("GET /teachers/paged - page={}, size={}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<GiangVienResponse> teachers = giangVienService.getAllGiangVienPaged(pageable);
        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách giảng viên (phân trang) thành công", teachers));
    }
    
    /**
     * Lấy giảng viên theo mã
     * GET /api/teachers/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GiangVienResponse>> getTeacherById(@PathVariable String id) {
        log.info("GET /teachers/{} - Lấy giảng viên theo mã", id);
        GiangVienResponse teacher = giangVienService.getGiangVienById(id);
        return ResponseEntity.ok(ApiResponse.success("Lấy giảng viên thành công", teacher));
    }
    
    /**
     * Tìm kiếm giảng viên theo họ tên
     * GET /api/teachers/search?keyword=Nguyễn
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<GiangVienResponse>>> searchTeachers(
            @RequestParam String keyword) {
        log.info("GET /teachers/search - keyword={}", keyword);
        List<GiangVienResponse> teachers = giangVienService.searchByHoTen(keyword);
        return ResponseEntity.ok(ApiResponse.success("Tìm kiếm giảng viên thành công", teachers));
    }
    
    /**
     * Tạo giảng viên mới
     * POST /api/teachers
     */
    @PostMapping
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse<GiangVienResponse>> createTeacher(
            @Valid @RequestBody GiangVienCreateRequest request) {
        log.info("POST /teachers - Tạo giảng viên mới: {}", request.getMaGV());
        GiangVienResponse teacher = giangVienService.createGiangVien(request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Tạo giảng viên thành công", teacher));
    }
    
    /**
     * Cập nhật giảng viên
     * PUT /api/teachers/{id}
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse<GiangVienResponse>> updateTeacher(
            @PathVariable String id,
            @Valid @RequestBody GiangVienCreateRequest request) {
        log.info("PUT /teachers/{} - Cập nhật giảng viên", id);
        GiangVienResponse teacher = giangVienService.updateGiangVien(id, request);
        return ResponseEntity.ok(ApiResponse.success("Cập nhật giảng viên thành công", teacher));
    }
    
    /**
     * Xóa giảng viên
     * DELETE /api/teachers/{id}
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse<?>> deleteTeacher(@PathVariable String id) {
        log.info("DELETE /teachers/{} - Xóa giảng viên", id);
        giangVienService.deleteGiangVien(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa giảng viên thành công", null));
    }
}
