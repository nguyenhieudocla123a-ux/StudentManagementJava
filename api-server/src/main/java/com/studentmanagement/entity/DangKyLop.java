package com.studentmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.persistence.PrePersist;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@Entity
@Table(name = "dangky_lop")
@IdClass(DangKyLop.DangKyLopId.class)
public class DangKyLop {
    
    @Id
    @Column(name = "ma_sv")
    private String maSV;
    
    @Id
    @Column(name = "ma_lop")
    private String maLop;
    
    @Column(name = "ngay_dang_ky")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime ngayDangKy;
    
    @PrePersist
    protected void onCreate() {
        if (this.ngayDangKy == null) {
            this.ngayDangKy = LocalDateTime.now();
        }
    }
    
    // Composite Key Class
    @Data
    public static class DangKyLopId implements Serializable {
        private String maSV;
        private String maLop;
    }
}
