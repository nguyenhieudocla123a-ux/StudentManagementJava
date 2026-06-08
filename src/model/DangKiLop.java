package model;

public class DangKiLop {
    private String maSV;
    private String maLop;
    private String ngayDangKy;

    // Constructor mặc định
    public DangKiLop() {
    }

    // Constructor với tham số
    public DangKiLop(String maSV, String maLop) {
        this.maSV = maSV;
        this.maLop = maLop;
    }

    // Getter và Setter
    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getNgayDangKy() {
        return ngayDangKy;
    }

    public void setNgayDangKy(String ngayDangKy) {
        this.ngayDangKy = ngayDangKy;
    }

    @Override
    public String toString() {
        return "DangKiLop{" +
                "maSV='" + maSV + '\'' +
                ", maLop='" + maLop + '\'' +
                '}';
    }

    // Phương thức equals và hashCode để sử dụng trong Collections
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DangKiLop dangKiLop = (DangKiLop) o;
        return maSV.equals(dangKiLop.maSV) && maLop.equals(dangKiLop.maLop);
    }

    @Override
    public int hashCode() {
        return maSV.hashCode() + maLop.hashCode();
    }
}