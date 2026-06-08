package model;

public class MonHoc {
    private String maMH;
    private String tenMH;
    private int soTinChi;
    private String moTa;
    private String maKhoa;

    // --- Constructor mặc định ---
    public MonHoc() {
    }

    // --- Constructor có tham số ---
    public MonHoc(String maMH, String tenMH, int soTinChi, String moTa, String maKhoa) {
        this.maMH = maMH;
        this.tenMH = tenMH;
        this.soTinChi = soTinChi;
        this.moTa = moTa;
        this.maKhoa = maKhoa;
    }

    // --- Getter & Setter ---
    public String getMaMH() {
        return maMH;
    }

    public void setMaMH(String maMH) {
        this.maMH = maMH;
    }

    public String getTenMH() {
        return tenMH;
    }

    public void setTenMH(String tenMH) {
        this.tenMH = tenMH;
    }

    public int getSoTinChi() {
        return soTinChi;
    }

    public void setSoTinChi(int soTinChi) {
        this.soTinChi = soTinChi;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getMaKhoa() {
        return maKhoa;
    }

    public void setMaKhoa(String maKhoa) {
        this.maKhoa = maKhoa;
    }


}
