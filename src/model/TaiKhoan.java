package model;

public class TaiKhoan {
    private String tenDangNhap;
    private String matKhau;
    private String loaiNguoiDung; // "SinhVien", "GiangVien", "Admin"
    private String onlineStatus;  // "Online", "Offline"
    // Các trường bổ sung từ API login (tuỳ loại người dùng)
    private String maSV;   // chỉ khi loaiNguoiDung = "SinhVien"
    private String maGV;   // chỉ khi loaiNguoiDung = "GiangVien"
    private String maQTV;  // chỉ khi loaiNguoiDung = "Admin"
    private String hoTen;

    // Constructor mặc định
    public TaiKhoan() {
    }

    // Constructor có tham số cơ bản
    public TaiKhoan(String tenDangNhap, String matKhau, String loaiNguoiDung, String onlineStatus) {
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.loaiNguoiDung = loaiNguoiDung;
        this.onlineStatus = onlineStatus;
    }

    // GETTER và SETTER
    public String getTenDangNhap() { return tenDangNhap; }
    public void setTenDangNhap(String tenDangNhap) { this.tenDangNhap = tenDangNhap; }

    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }

    public String getLoaiNguoiDung() { return loaiNguoiDung; }
    public void setLoaiNguoiDung(String loaiNguoiDung) { this.loaiNguoiDung = loaiNguoiDung; }

    public String getOnlineStatus() { return onlineStatus; }
    public void setOnlineStatus(String onlineStatus) { this.onlineStatus = onlineStatus; }

    public String getMaSV() { return maSV; }
    public void setMaSV(String maSV) { this.maSV = maSV; }

    public String getMaGV() { return maGV; }
    public void setMaGV(String maGV) { this.maGV = maGV; }

    public String getMaQTV() { return maQTV; }
    public void setMaQTV(String maQTV) { this.maQTV = maQTV; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    @Override
    public String toString() {
        return "TaiKhoan{" +
                "tenDangNhap='" + tenDangNhap + '\'' +
                ", loaiNguoiDung='" + loaiNguoiDung + '\'' +
                ", hoTen='" + hoTen + '\'' +
                ", onlineStatus='" + onlineStatus + '\'' +
                '}';
    }
}