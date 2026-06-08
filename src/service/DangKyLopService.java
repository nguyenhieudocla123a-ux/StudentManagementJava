package service;

import dao.DangKiLopDao;
import model.DangKiLop;
import until.ApiClient;

import java.util.ArrayList;
import java.util.List;

public class DangKyLopService {

    private final DangKiLopDao dao;

    public DangKyLopService() {
        this.dao = new DangKiLopDao();
    }

    public void ensureServerRunning() {
        if (!ApiClient.isServerRunning()) {
            throw new RuntimeException("❌ Không thể kết nối đến API Server!");
        }
    }

    public List<DangKiLop> getAll() {
        ensureServerRunning();
        return dao.getAllDangKy();
    }

    public List<DangKiLop> getChiTietDangKy() {
        return dao.getChiTietDangKy();
    }

    public boolean dangKy(String maSV, String maLop) {
        ensureServerRunning();
        return dao.dangKyLop(new DangKiLop(maSV, maLop));
    }

    public boolean huyDangKy(String maSV, String maLop) {
        ensureServerRunning();
        return dao.huyDangKy(maSV, maLop);
    }

    public boolean isDaDangKy(String maSV, String maLop) {
        ensureServerRunning();
        return dao.kiemTraDaDangKy(maSV, maLop);
    }

    public List<String> getLopDaDangKy(String maSV) {
        ensureServerRunning();
        return dao.getLopDaDangKy(maSV);
    }

    public List<String> getSVDaDangKy(String maLop) {
        ensureServerRunning();
        return dao.getSVDaDangKy(maLop);
    }

    public int getSoLuongDangKy(String maLop) {
        ensureServerRunning();
        return dao.demSoLuongDangKy(maLop);
    }

    public boolean xoaTatCaDangKy(String maSV) {
        ensureServerRunning();
        return dao.xoaTatCaDangKy(maSV);
    }

    public boolean isSinhVienDaDangKy(String maSV) {
        ensureServerRunning();
        return dao.kiemTraSinhVienDaDangKy(maSV);
    }

    public String getLastError() {
        return dao.getLastErrorMessage();
    }
}
