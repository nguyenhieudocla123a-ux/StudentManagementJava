package model;

public class TaiKhoan {
    private String tenDangNhap;
    private String matKhau;
    private String loaiNguoiDung; // "SinhVien", "GiangVien", "Admin"
    private String onlineStatus;  // "Online", "Offline"

    // Constructor mặc định
    public TaiKhoan() {
    }

    // Constructor có tham số
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

    @Override
    public String toString() {
        return "TaiKhoan{" +
                "tenDangNhap='" + tenDangNhap + '\'' +
                ", loaiNguoiDung='" + loaiNguoiDung + '\'' +
                ", onlineStatus='" + onlineStatus + '\'' +
                '}';
    }
}