package com.studentmanagement.controller;

import com.studentmanagement.dto.request.SinhVienCreateRequest;
import com.studentmanagement.dto.response.ApiResponse;
import com.studentmanagement.dto.response.SinhVienResponse;
import com.studentmanagement.service.SinhVienService;
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
@RequestMapping("/students")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@PreAuthorize("hasAnyRole('Admin', 'GiangVien', 'SinhVien')")
public class SinhVienController {
    
    private final SinhVienService sinhVienService;
    
    /**
     * Lấy tất cả sinh viên
     * GET /api/students
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<SinhVienResponse>>> getAllStudents() {
        log.info("GET /students - Lấy tất cả sinh viên");
        List<SinhVienResponse> students = sinhVienService.getAllSinhVien();
        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách sinh viên thành công", students));
    }
    
    /**
     * Lấy sinh viên phân trang
     * GET /api/students/paged?page=0&size=10
     */
    @GetMapping("/paged")
    public ResponseEntity<ApiResponse<Page<SinhVienResponse>>> getStudentsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("GET /students/paged - page={}, size={}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<SinhVienResponse> students = sinhVienService.getAllSinhVienPaged(pageable);
        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách sinh viên (phân trang) thành công", students));
    }
    
    /**
     * Lấy sinh viên theo mã
     * GET /api/students/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SinhVienResponse>> getStudentById(@PathVariable String id) {
        log.info("GET /students/{} - Lấy sinh viên theo mã", id);
        SinhVienResponse student = sinhVienService.getSinhVienById(id);
        return ResponseEntity.ok(ApiResponse.success("Lấy sinh viên thành công", student));
    }
    
    /**
     * Tìm kiếm sinh viên theo họ tên
     * GET /api/students/search?keyword=Nguyễn
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<SinhVienResponse>>> searchStudents(
            @RequestParam String keyword) {
        log.info("GET /students/search - keyword={}", keyword);
        List<SinhVienResponse> students = sinhVienService.searchByHoTen(keyword);
        return ResponseEntity.ok(ApiResponse.success("Tìm kiếm sinh viên thành công", students));
    }
    
    /**
     * Tạo sinh viên mới
     * POST /api/students
     */
    @PostMapping
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse<SinhVienResponse>> createStudent(
            @Valid @RequestBody SinhVienCreateRequest request) {
        log.info("POST /students - Tạo sinh viên mới: {}", request.getMaSV());
        SinhVienResponse student = sinhVienService.createSinhVien(request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Tạo sinh viên thành công", student));
    }
    
    /**
     * Cập nhật sinh viên
     * PUT /api/students/{id}
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse<SinhVienResponse>> updateStudent(
            @PathVariable String id,
            @Valid @RequestBody SinhVienCreateRequest request) {
        log.info("PUT /students/{} - Cập nhật sinh viên", id);
        SinhVienResponse student = sinhVienService.updateSinhVien(id, request);
        return ResponseEntity.ok(ApiResponse.success("Cập nhật sinh viên thành công", student));
    }
    
    /**
     * Xóa sinh viên
     * DELETE /api/students/{id}
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ApiResponse<?>> deleteStudent(@PathVariable String id) {
        log.info("DELETE /students/{} - Xóa sinh viên", id);
        sinhVienService.deleteSinhVien(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa sinh viên thành công", null));
    }
}
