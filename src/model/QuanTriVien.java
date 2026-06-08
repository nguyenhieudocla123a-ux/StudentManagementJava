package model;

public class QuanTriVien {
    private String maQTV;
    private String hoTen;
    private String email;
    private String soDienThoai;
    private String tenDangNhap;

    // Constructor mặc định
    public QuanTriVien() {}

    // Constructor đầy đủ
    public QuanTriVien(String maQTV, String hoTen, String email, String soDienThoai, String tenDangNhap) {
        this.maQTV = maQTV;
        this.hoTen = hoTen;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.tenDangNhap = tenDangNhap;
    }

    // GETTER và SETTER
    public String getMaQTV() { return maQTV; }
    public void setMaQTV(String maQTV) { this.maQTV = maQTV; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    public String getTenDangNhap() { return tenDangNhap; }
    public void setTenDangNhap(String tenDangNhap) { this.tenDangNhap = tenDangNhap; }

    @Override
    public String toString() {
        return "QuanTriVien{maQTV='" + maQTV + "', hoTen='" + hoTen + "'}";
    }
}
