package service;

import dao.DiemDao;
import model.Diem;
import until.ApiClient;

import java.util.ArrayList;
import java.util.List;

public class DiemService {

    private final DiemDao dao;

    public DiemService() {
        this.dao = new DiemDao();
    }

    public void ensureServerRunning() {
        if (!ApiClient.isServerRunning()) {
            throw new RuntimeException("❌ Không thể kết nối đến API Server!");
        }
    }

    public ArrayList<Diem> getBySinhVien(String maSV) {
        ensureServerRunning();
        return dao.getDiemBySinhVien(maSV);
    }

    public ArrayList<Diem> getByLop(String maLop) {
        ensureServerRunning();
        return dao.getDiemByLop(maLop);
    }

    public boolean nhapDiem(Diem d) {
        ensureServerRunning();
        validateDiem(d);
        if (d.getDiemQuaTrinh() == null && d.getDiemGiuaKy() == null && d.getDiemCuoiKy() == null) {
            throw new RuntimeException("Vui lòng nhập ít nhất một điểm!");
        }
        return dao.nhapDiem(d);
    }

    public boolean capNhatDiem(Diem d) {
        ensureServerRunning();
        validateDiem(d);
        return dao.capNhatDiem(d);
    }

    public boolean xoaDiem(String maSV) {
        ensureServerRunning();
        return dao.xoaDiem(maSV);
    }

    public Diem getBySinhVienAndLop(String maSV, String maLop) {
        return dao.getDiemByMaSVAndMaLop(maSV, maLop);
    }

    public ArrayList<Diem> getByMonHoc(String maMon) {
        return dao.getDiemByMonHoc(maMon);
    }

    public ArrayList<Diem> getBySinhVienTheoKyNam(String maSV, String hocKy, String namHoc) {
        return dao.getDiemBySinhVienTheoKyNam(maSV, hocKy, namHoc);
    }

    public ArrayList<String> getHocKyBySinhVien(String maSV) {
        return dao.getHocKyBySinhVien(maSV);
    }

    public ArrayList<String> getNamHocBySinhVien(String maSV) {
        return dao.getNamHocBySinhVien(maSV);
    }

    public float tinhDiemTK(float diemQT, float diemGK, float diemCK) {
        return diemQT * 0.2f + diemGK * 0.3f + diemCK * 0.5f;
    }

    public String tinhDiemChu(float diem) {
        if (diem >= 8.5) return "A";
        else if (diem >= 8.0) return "B+";
        else if (diem >= 7.0) return "B";
        else if (diem >= 6.5) return "C+";
        else if (diem >= 5.5) return "C";
        else if (diem >= 5.0) return "D+";
        else if (diem >= 4.0) return "D";
        else return "F";
    }

    public String tinhXepLoai(float diem) {
        if (diem >= 8.5) return "Giỏi";
        else if (diem >= 7.0) return "Khá";
        else if (diem >= 5.5) return "Trung bình";
        else if (diem >= 4.0) return "Yếu";
        else return "Kém";
    }

    public double tinhGPA(List<Diem> listDiem) {
        if (listDiem == null || listDiem.isEmpty()) return 0.0;
        double tongDiem = 0;
        int count = 0;
        for (Diem d : listDiem) {
            if (d.getDiemTongKet() != null) {
                tongDiem += d.getDiemTongKet();
                count++;
            }
        }
        return count == 0 ? 0.0 : tongDiem / count;
    }

    public boolean kiemTraDiemHopLe(Float diemQT, Float diemGK, Float diemCK) {
        return kiemTraDiem(diemQT) && kiemTraDiem(diemGK) && kiemTraDiem(diemCK);
    }

    private boolean kiemTraDiem(Float diem) {
        return diem == null || (diem >= 0 && diem <= 10);
    }

    private void validateDiem(Diem d) {
        if (!kiemTraDiemHopLe(d.getDiemQuaTrinh(), d.getDiemGiuaKy(), d.getDiemCuoiKy())) {
            throw new RuntimeException("Điểm phải nằm trong khoảng 0-10!");
        }
    }
}
