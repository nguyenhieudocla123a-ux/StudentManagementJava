package model;

public class GiangVien {
    private String maGV;
    private String hoTen;
    private String tenDangNhap; // ← THÊM TRƯỜNG NÀY
    private String email;
    private String soDienThoai;
    private String  makhoa;

    // Constructor mặc định
    public GiangVien() {
    }

    // Constructor đầy đủ tham số
    public GiangVien(String maGV, String hoTen, String email, String soDienThoai, String khoa) {
        this.maGV = maGV;
        this.hoTen = hoTen;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.makhoa= khoa;
    }

    // GETTER và SETTER
    public String getMaGV() {
        return maGV;
    }

    public void setMaGV(String maGV) {
        this.maGV = maGV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getKhoa() {
        return makhoa;
    }

    public void setKhoa(String khoa) {
        this.makhoa = khoa;
    }

    // Getter cho mã khoa (tiện ích)
    public String getMaKhoa() {
        return makhoa;
    }

    // ← THÊM GETTER/SETTER CHO TEN DANG NHAP
    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    // Phương thức so sánh
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GiangVien giangVien = (GiangVien) o;
        return maGV.equals(giangVien.maGV);
    }

    @Override
    public int hashCode() {
        return maGV.hashCode();
    }
}