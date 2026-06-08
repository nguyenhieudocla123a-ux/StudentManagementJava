package model;

public class Diem {
    private int id;
    private String maSV;
    private String maLop;
    private Float diemQuaTrinh;
    private Float diemGiuaKy;
    private Float diemCuoiKy;
    private Float diemTongKet;
    private String diemChu;
    private String xepLoai;

    // Constructor mặc định
    public Diem() {
    }

    // Constructor có tham số
    public Diem(int id, String maSV, String maLop, Float diemQuaTrinh, Float diemGiuaKy,
                Float diemCuoiKy, Float diemTongKet, String diemChu, String xepLoai) {
        this.id = id;
        this.maSV = maSV;
        this.maLop = maLop;
        this.diemQuaTrinh = diemQuaTrinh;
        this.diemGiuaKy = diemGiuaKy;
        this.diemCuoiKy = diemCuoiKy;
        this.diemTongKet = diemTongKet;
        this.diemChu = diemChu;
        this.xepLoai = xepLoai;
    }

    // GETTER và SETTER
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMaSV() { return maSV; }
    public void setMaSV(String maSV) { this.maSV = maSV; }

    public String getMaLop() { return maLop; }
    public void setMaLop(String maLop) { this.maLop = maLop; }

    public Float getDiemQuaTrinh() { return diemQuaTrinh; }
    public void setDiemQuaTrinh(Float diemQuaTrinh) { this.diemQuaTrinh = diemQuaTrinh; }

    public Float getDiemGiuaKy() { return diemGiuaKy; }
    public void setDiemGiuaKy(Float diemGiuaKy) { this.diemGiuaKy = diemGiuaKy; }

    public Float getDiemCuoiKy() { return diemCuoiKy; }
    public void setDiemCuoiKy(Float diemCuoiKy) { this.diemCuoiKy = diemCuoiKy; }

    public Float getDiemTongKet() { return diemTongKet; }
    public void setDiemTongKet(Float diemTongKet) { this.diemTongKet = diemTongKet; }

    public String getDiemChu() { return diemChu; }
    public void setDiemChu(String diemChu) { this.diemChu = diemChu; }

    public String getXepLoai() { return xepLoai; }
    public void setXepLoai(String xepLoai) { this.xepLoai = xepLoai; }

    @Override
    public String toString() {
        return "Diem{" +
                "id=" + id +
                ", maSV='" + maSV + '\'' +
                ", maLop='" + maLop + '\'' +
                ", diemQuaTrinh=" + diemQuaTrinh +
                ", diemGiuaKy=" + diemGiuaKy +
                ", diemCuoiKy=" + diemCuoiKy +
                ", diemTongKet=" + diemTongKet +
                ", diemChu='" + diemChu + '\'' +
                ", xepLoai='" + xepLoai + '\'' +
                '}';
    }
}