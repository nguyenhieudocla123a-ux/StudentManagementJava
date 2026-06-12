package com.studentmanagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@Entity
@Table(name = "lophocphan")
public class LopHocPhan {
    
    @Id
    @Column(name = "ma_lop")
    private String maLop;
    
    @Column(name = "ma_mh")
    private String maMH;
    
    @Column(name = "ma_gv")
    private String maGV;
    
    @Column(name = "hoc_ky")
    private String hocKy;
    
    @Column(name = "nam_hoc")
    private String namHoc;
    
    @Column(name = "si_so_toi_da")
    private Integer siSoToiDa;
    
    @Column(name = "si_so_hien_tai")
    private Integer siSoHienTai;
    
    @Column(name = "trang_thai")
    private String trangThai;
    
    @Column(name = "thoi_gian_mo_dang_ky")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime thoiGianMoDangKy;
    
    @Column(name = "thoi_gian_dong_dang_ky")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime thoiGianDongDangKy;
    
    @Column(name = "ngay_bat_dau_hoc")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate ngayBatDauHoc;
    
    @Column(name = "ngay_ket_thuc_hoc")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate ngayKetThucHoc;
}
