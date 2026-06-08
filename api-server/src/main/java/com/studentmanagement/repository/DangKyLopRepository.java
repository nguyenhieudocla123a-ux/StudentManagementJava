package com.studentmanagement.repository;

import com.studentmanagement.entity.DangKyLop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DangKyLopRepository extends JpaRepository<DangKyLop, DangKyLop.DangKyLopId> {
    List<DangKyLop> findByMaSV(String maSV);  // ✅ Sửa từ findByMaSinhVien
    List<DangKyLop> findByMaLop(String maLop);  // ✅ Sửa từ findByMaLopHocPhan
    Optional<DangKyLop> findByMaSVAndMaLop(String maSV, String maLop);  // ✅ Sửa từ findByMaSinhVienAndMaLopHocPhan
}
