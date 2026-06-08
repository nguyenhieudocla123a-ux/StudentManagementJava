package com.studentmanagement.repository;

import com.studentmanagement.entity.LopHocPhan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LopHocPhanRepository extends JpaRepository<LopHocPhan, String> {
    List<LopHocPhan> findByMaGV(String maGV);  // ✅ Sửa từ findByMaGiangVien
    List<LopHocPhan> findByMaMH(String maMH);  // ✅ Sửa từ findByMaMonHoc
    List<LopHocPhan> findByHocKyAndNamHoc(String hocKy, String namHoc);
}
