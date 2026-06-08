package com.studentmanagement.controller;

import com.studentmanagement.dto.request.DiemCreateRequest;
import com.studentmanagement.dto.response.ApiResponse;
import com.studentmanagement.dto.response.DiemResponse;
import com.studentmanagement.service.DiemService;
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
@RequestMapping("/grades")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@PreAuthorize("hasAnyRole('Admin', 'GiangVien', 'SinhVien')")
public class DiemController {
    
    private final DiemService diemService;
    
    /**
     * Lấy tất cả điểm
     * GET /api/grades
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<DiemResponse>>> getAllGrades() {
        log.info("GET /grades - Lấy tất cả điểm");
        List<DiemResponse> grades = diemService.getAllDiem();
        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách điểm thành công", grades));
    }
    
    /**
     * Lấy điểm phân trang
     * GET /api/grades/paged?page=0&size=10
     */
    @GetMapping("/paged")
    public ResponseEntity<ApiResponse<Page<DiemResponse>>> getGradesPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("GET /grades/paged - page={}, size={}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<DiemResponse> grades = diemService.getAllDiemPaged(pageable);
        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách điểm (phân trang) thành công", grades));
    }
    
    /**
     * Lấy điểm theo ID
     * GET /api/grades/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DiemResponse>> getGradeById(@PathVariable Integer id) {
        log.info("GET /grades/{} - Lấy điểm theo ID", id);
        DiemResponse grade = diemService.getDiemById(id);
        return ResponseEntity.ok(ApiResponse.success("Lấy điểm thành công", grade));
    }
    
    /**
     * Lấy điểm của sinh viên
     * GET /api/grades/student/{studentId}
     */
    @GetMapping("/student/{studentId}")
    public ResponseEntity<ApiResponse<List<DiemResponse>>> getGradesByStudent(@PathVariable String studentId) {
        log.info("GET /grades/student/{} - Lấy điểm của sinh viên", studentId);
        List<DiemResponse> grades = diemService.getDiemBySinhVien(studentId);
        return ResponseEntity.ok(ApiResponse.success("Lấy điểm của sinh viên thành công", grades));
    }
    
    /**
     * Lấy điểm của lớp học phần
     * GET /api/grades/class/{classId}
     */
    @GetMapping("/class/{classId}")
    public ResponseEntity<ApiResponse<List<DiemResponse>>> getGradesByClass(@PathVariable String classId) {
        log.info("GET /grades/class/{} - Lấy điểm của lớp học phần", classId);
        List<DiemResponse> grades = diemService.getDiemByLopHocPhan(classId);
        return ResponseEntity.ok(ApiResponse.success("Lấy điểm của lớp học phần thành công", grades));
    }
    
    /**
     * Thêm điểm mới
     * POST /api/grades
     */
    @PostMapping
    @PreAuthorize("hasRole('GiangVien') or hasRole('Admin')")
    public ResponseEntity<ApiResponse<DiemResponse>> createGrade(@Valid @RequestBody DiemCreateRequest request) {
        log.info("POST /grades - Thêm điểm mới: maSV={}, maLop={}", request.getMaSV(), request.getMaLop());
        DiemResponse grade = diemService.createDiem(request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Thêm điểm thành công", grade));
    }
    
    /**
     * Cập nhật điểm
     * PUT /api/grades/{id}
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('GiangVien') or hasRole('Admin')")
    public ResponseEntity<ApiResponse<DiemResponse>> updateGrade(
            @PathVariable Integer id,
            @Valid @RequestBody DiemCreateRequest request) {
        log.info("PUT /grades/{} - Cập nhật điểm", id);
        DiemResponse grade = diemService.updateDiem(id, request);
        return ResponseEntity.ok(ApiResponse.success("Cập nhật điểm thành công", grade));
    }
    
    /**
     * Xóa điểm
     * DELETE /api/grades/{id}
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('GiangVien') or hasRole('Admin')")
    public ResponseEntity<ApiResponse<Void>> deleteGrade(@PathVariable Integer id) {
        log.info("DELETE /grades/{} - Xóa điểm", id);
        diemService.deleteDiem(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa điểm thành công", null));
    }
}
