package com.studentmanagement.repository;

import com.studentmanagement.entity.SinhVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SinhVienRepository extends JpaRepository<SinhVien, String> {
    List<SinhVien> findByHoTenContaining(String hoTen);
    List<SinhVien> findByMaKhoa(String maKhoa);
    boolean existsByTenDangNhap(String tenDangNhap);
    Optional<SinhVien> findByTenDangNhap(String tenDangNhap);
}
