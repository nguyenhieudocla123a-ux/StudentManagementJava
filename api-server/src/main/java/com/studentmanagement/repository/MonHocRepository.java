package com.studentmanagement.repository;

import com.studentmanagement.entity.MonHoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MonHocRepository extends JpaRepository<MonHoc, String> {
    List<MonHoc> findByTenMHContaining(String tenMH);  // ✅ Sửa từ findByTenMonHocContaining
}
