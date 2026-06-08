package com.studentmanagement.repository;

import com.studentmanagement.entity.GiangVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface GiangVienRepository extends JpaRepository<GiangVien, String> {
    List<GiangVien> findByHoTenContaining(String hoTen);
    List<GiangVien> findByMaKhoa(String maKhoa);
    boolean existsByTenDangNhap(String tenDangNhap);
    Optional<GiangVien> findByTenDangNhap(String tenDangNhap);
}
