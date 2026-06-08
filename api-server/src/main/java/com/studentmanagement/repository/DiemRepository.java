package com.studentmanagement.repository;

import com.studentmanagement.entity.Diem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiemRepository extends JpaRepository<Diem, Integer> {
    List<Diem> findByMaSV(String maSV);
    List<Diem> findByMaLop(String maLop);
    Optional<Diem> findByMaSVAndMaLop(String maSV, String maLop);
    
    // Kiểm tra trùng điểm
    boolean existsByMaSVAndMaLop(String maSV, String maLop);
    
    // Xóa điểm theo sinh viên
    void deleteByMaSV(String maSV);
    
    // Đếm số môn đã có điểm
    long countByMaSV(String maSV);
}
