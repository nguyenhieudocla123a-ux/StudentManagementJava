package com.studentmanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "DIEM")
public class Diem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "ma_sv")
    private String maSV;  // ✅ Sửa từ maSinhVien → maSV
    
    @Column(name = "ma_lop")
    private String maLop;  // ✅ Sửa từ maLopHocPhan → maLop
    
    @Column(name = "diem_qua_trinh")
    private Double diemQuaTrinh;
    
    @Column(name = "diem_giua_ky")
    private Double diemGiuaKy;
    
    @Column(name = "diem_cuoi_ky")
    private Double diemCuoiKy;
    
    @Column(name = "diem_tong_ket")
    private Double diemTongKet;
    
    @Column(name = "diem_chu")
    private String diemChu;
    
    @Column(name = "xep_loai")
    private String xepLoai;
    
    @ManyToOne
    @JoinColumn(name = "ma_sv", insertable = false, updatable = false)
    private SinhVien sinhVien;
    
    @ManyToOne
    @JoinColumn(name = "ma_lop", insertable = false, updatable = false)
    private LopHocPhan lopHocPhan;
}
