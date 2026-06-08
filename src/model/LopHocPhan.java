package model;

public class LopHocPhan {
    private String maLop;
    private String maMH;
    private String maGV;
    private String hocKy;
    private String namHoc;
    private Integer siSoToiDa;
    private Integer siSoHienTai;
    private String trangThai; // "Mở" hoặc "Đóng"
    
    // Thêm các trường thời gian
    private String thoiGianMoDangKy;
    private String thoiGianDongDangKy;
    private String ngayBatDauHoc;
    private String ngayKetThucHoc;

    // Constructor mặc định
    public LopHocPhan() {
    }

    // Constructor có tham số
    public LopHocPhan(String maLop, String maMH, String maGV, String hocKy, String namHoc, Integer siSoToiDa, Integer siSoHienTai) {
        this.maLop = maLop;
        this.maMH = maMH;
        this.maGV = maGV;
        this.hocKy = hocKy;
        this.namHoc = namHoc;
        this.siSoToiDa = siSoToiDa;
        this.siSoHienTai = 0;
        this.trangThai = "mo"; // Mặc định là mở
    }

    // Constructor đầy đủ với trạng thái
    public LopHocPhan(String maLop, String maMH, String maGV, String hocKy, String namHoc, Integer siSoToiDa, Integer siSoHienTai, String trangThai) {
        this.maLop = maLop;
        this.maMH = maMH;
        this.maGV = maGV;
        this.hocKy = hocKy;
        this.namHoc = namHoc;
        this.siSoToiDa = siSoToiDa;
        this.siSoHienTai = siSoHienTai;
        this.trangThai = trangThai;
    }

    // GETTER và SETTER
    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getMaMH() {
        return maMH;
    }

    public void setMaMH(String maMH) {
        this.maMH = maMH;
    }

    public String getMaGV() {
        return maGV;
    }

    public void setMaGV(String maGV) {
        this.maGV = maGV;
    }

    public String getHocKy() {
        return hocKy;
    }

    public void setHocKy(String hocKy) {
        this.hocKy = hocKy;
    }

    public String getNamHoc() {
        return namHoc;
    }

    public void setNamHoc(String namHoc) {
        this.namHoc = namHoc;
    }


    public void setSiSoToiDa(Integer siSoToiDa) {
        this.siSoToiDa = siSoToiDa;
    }
    public Integer getSiSoHienTai() {
        return siSoHienTai == null ? 0 : siSoHienTai;
    }
    public Integer getSiSoToiDa() {
        return siSoToiDa == null ? 0 : siSoToiDa;
    }


    public void setSiSoHienTai(Integer siSoHienTai) {
        this.siSoHienTai = siSoHienTai;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    // Kiểm tra lớp có đang mở không
    public boolean isDangMo() {
        return "mo".equalsIgnoreCase(trangThai);
    }
    
    public String getThoiGianMoDangKy() { return thoiGianMoDangKy; }
    public void setThoiGianMoDangKy(String thoiGianMoDangKy) { this.thoiGianMoDangKy = thoiGianMoDangKy; }
    
    public String getThoiGianDongDangKy() { return thoiGianDongDangKy; }
    public void setThoiGianDongDangKy(String thoiGianDongDangKy) { this.thoiGianDongDangKy = thoiGianDongDangKy; }
    
    public String getNgayBatDauHoc() { return ngayBatDauHoc; }
    public void setNgayBatDauHoc(String ngayBatDauHoc) { this.ngayBatDauHoc = ngayBatDauHoc; }
    
    public String getNgayKetThucHoc() { return ngayKetThucHoc; }
    public void setNgayKetThucHoc(String ngayKetThucHoc) { this.ngayKetThucHoc = ngayKetThucHoc; }

    // Lấy tên trạng thái hiển thị
    public String getTrangThaiHienThi() {
        return "mo".equalsIgnoreCase(trangThai) ? "Mở" : "Đóng";
    }

    @Override
    public String toString() {
        return "LopHocPhan{" +
                "maLop='" + maLop + '\'' +
                ", maMH='" + maMH + '\'' +
                ", maGV='" + maGV + '\'' +
                ", hocKy='" + hocKy + '\'' +
                ", namHoc='" + namHoc + '\'' +
                ", siSoToiDa=" + siSoToiDa +
                ", siSoHienTai=" + siSoHienTai +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}
