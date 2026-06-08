package com.studentmanagement.service.impl;

import com.studentmanagement.dto.request.DiemCreateRequest;
import com.studentmanagement.dto.response.DiemResponse;
import com.studentmanagement.entity.Diem;
import com.studentmanagement.entity.LopHocPhan;
import com.studentmanagement.entity.SinhVien;
import com.studentmanagement.exception.ResourceNotFoundException;
import com.studentmanagement.repository.DiemRepository;
import com.studentmanagement.repository.LopHocPhanRepository;
import com.studentmanagement.repository.SinhVienRepository;
import com.studentmanagement.service.DiemService;
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
public class DiemServiceImpl implements DiemService {
    
    private final DiemRepository diemRepository;
    private final SinhVienRepository sinhVienRepository;
    private final LopHocPhanRepository lopHocPhanRepository;

    @Override
    @Transactional(readOnly = true)
    public List<DiemResponse> getAllDiem() {
        log.info("Lấy tất cả điểm");
        return diemRepository.findAll()
            .stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DiemResponse> getAllDiemPaged(Pageable pageable) {
        log.info("Lấy điểm phân trang: page={}, size={}", 
            pageable.getPageNumber(), pageable.getPageSize());
        return diemRepository.findAll(pageable)
            .map(this::convertToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public DiemResponse getDiemById(Integer id) {
        log.info("Lấy điểm theo ID: {}", id);
        Diem diem = diemRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Điểm", "id", id));
        return convertToResponse(diem);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiemResponse> getDiemBySinhVien(String maSV) {
        log.info("Lấy điểm của sinh viên: {}", maSV);
        return diemRepository.findByMaSV(maSV)
            .stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiemResponse> getDiemByLopHocPhan(String maLop) {
        log.info("Lấy điểm của lớp học phần: {}", maLop);
        return diemRepository.findByMaLop(maLop)
            .stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    @Override
    public DiemResponse createDiem(DiemCreateRequest request) {
        log.info("Tạo điểm mới: maSV={}, maLop={}", request.getMaSV(), request.getMaLop());
        
        // 1. Validate sinh viên tồn tại
        SinhVien sinhVien = sinhVienRepository.findById(request.getMaSV())
            .orElseThrow(() -> new ResourceNotFoundException("Sinh viên", "maSV", request.getMaSV()));
        
        // 2. Validate lớp học phần tồn tại
        LopHocPhan lopHocPhan = lopHocPhanRepository.findById(request.getMaLop())
            .orElseThrow(() -> new ResourceNotFoundException("Lớp học phần", "maLop", request.getMaLop()));
        
        // 3. Validate không trùng điểm
        if (diemRepository.existsByMaSVAndMaLop(request.getMaSV(), request.getMaLop())) {
            throw new IllegalArgumentException("Điểm của sinh viên " + request.getMaSV() + 
                " trong lớp " + request.getMaLop() + " đã tồn tại");
        }
        
        // 4. Validate điểm trong khoảng [0, 10]
        validateScore(request.getDiemQuaTrinh(), "Điểm quá trình");
        validateScore(request.getDiemGiuaKy(), "Điểm giữa kỳ");
        validateScore(request.getDiemCuoiKy(), "Điểm cuối kỳ");
        
        // 5. Tạo entity
        Diem diem = new Diem();
        diem.setMaSV(request.getMaSV());
        diem.setMaLop(request.getMaLop());
        diem.setDiemQuaTrinh(request.getDiemQuaTrinh());
        diem.setDiemGiuaKy(request.getDiemGiuaKy());
        diem.setDiemCuoiKy(request.getDiemCuoiKy());
        
        // 6. Tính điểm tổng kết, điểm chữ, xếp loại
        calculateGrades(diem);
        
        // 7. Lưu vào database
        Diem saved = diemRepository.save(diem);
        log.info("Tạo điểm thành công: id={}", saved.getId());
        
        return convertToResponse(saved);
    }

    @Override
    public DiemResponse updateDiem(Integer id, DiemCreateRequest request) {
        log.info("Cập nhật điểm: id={}", id);
        
        // 1. Tìm điểm cần cập nhật
        Diem diem = diemRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Điểm", "id", id));
        
        // 2. Validate điểm trong khoảng [0, 10]
        validateScore(request.getDiemQuaTrinh(), "Điểm quá trình");
        validateScore(request.getDiemGiuaKy(), "Điểm giữa kỳ");
        validateScore(request.getDiemCuoiKy(), "Điểm cuối kỳ");
        
        // 3. Cập nhật thông tin
        diem.setDiemQuaTrinh(request.getDiemQuaTrinh());
        diem.setDiemGiuaKy(request.getDiemGiuaKy());
        diem.setDiemCuoiKy(request.getDiemCuoiKy());
        
        // 4. Tính lại điểm tổng kết, điểm chữ, xếp loại
        calculateGrades(diem);
        
        // 5. Lưu vào database
        Diem updated = diemRepository.save(diem);
        log.info("Cập nhật điểm thành công: id={}", id);
        
        return convertToResponse(updated);
    }

    @Override
    public void deleteDiem(Integer id) {
        log.info("Xóa điểm: id={}", id);
        
        // 1. Kiểm tra điểm có tồn tại không
        if (!diemRepository.existsById(id)) {
            throw new ResourceNotFoundException("Điểm", "id", id);
        }
        
        // 2. Xóa điểm
        diemRepository.deleteById(id);
        log.info("Xóa điểm thành công: id={}", id);
    }

    /**
     * Validate điểm trong khoảng [0, 10]
     */
    private void validateScore(Double score, String scoreName) {
        if (score != null && (score < 0 || score > 10)) {
            throw new IllegalArgumentException(scoreName + " phải trong khoảng [0, 10]");
        }
    }

    /**
     * Tính điểm tổng kết, điểm chữ, xếp loại
     * Công thức: Điểm tổng kết = (Điểm QT + Điểm GK + Điểm CK * 2) / 4
     */
    private void calculateGrades(Diem diem) {
        Double diemQT = diem.getDiemQuaTrinh() != null ? diem.getDiemQuaTrinh() : 0.0;
        Double diemGK = diem.getDiemGiuaKy() != null ? diem.getDiemGiuaKy() : 0.0;
        Double diemCK = diem.getDiemCuoiKy() != null ? diem.getDiemCuoiKy() : 0.0;
        
        // Tính điểm tổng kết (làm tròn 2 chữ số thập phân)
        double diemTK = (diemQT + diemGK + diemCK * 2) / 4.0;
        diem.setDiemTongKet(Math.round(diemTK * 100.0) / 100.0);
        
        // Tính điểm chữ và xếp loại
        if (diemTK >= 9.0) {
            diem.setDiemChu("A+");
            diem.setXepLoai("Xuất sắc");
        } else if (diemTK >= 8.5) {
            diem.setDiemChu("A");
            diem.setXepLoai("Giỏi");
        } else if (diemTK >= 8.0) {
            diem.setDiemChu("B+");
            diem.setXepLoai("Khá");
        } else if (diemTK >= 7.0) {
            diem.setDiemChu("B");
            diem.setXepLoai("Khá");
        } else if (diemTK >= 6.5) {
            diem.setDiemChu("C+");
            diem.setXepLoai("Trung bình");
        } else if (diemTK >= 5.5) {
            diem.setDiemChu("C");
            diem.setXepLoai("Trung bình");
        } else if (diemTK >= 5.0) {
            diem.setDiemChu("D+");
            diem.setXepLoai("Trung bình yếu");
        } else if (diemTK >= 4.0) {
            diem.setDiemChu("D");
            diem.setXepLoai("Yếu");
        } else {
            diem.setDiemChu("F");
            diem.setXepLoai("Kém");
        }
    }

    /**
     * Chuyển đổi entity sang response DTO
     */
    private DiemResponse convertToResponse(Diem diem) {
        return new DiemResponse(
            diem.getId(),
            diem.getMaSV(),
            diem.getMaLop(),
            diem.getDiemQuaTrinh(),
            diem.getDiemGiuaKy(),
            diem.getDiemCuoiKy(),
            diem.getDiemTongKet(),
            diem.getDiemChu(),
            diem.getXepLoai()
        );
    }
}
