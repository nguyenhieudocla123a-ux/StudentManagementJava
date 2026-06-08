package com.studentmanagement.repository;

import com.studentmanagement.entity.QuanTriVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuanTriVienRepository extends JpaRepository<QuanTriVien, String> {
    Optional<QuanTriVien> findByTenDangNhap(String tenDangNhap);
    List<QuanTriVien> findByHoTenContaining(String hoTen);
}
