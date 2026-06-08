package service;

import dao.TaiKhoanDao;
import model.TaiKhoan;
import until.ApiClient;
import until.JsonParser;

import java.util.List;

public class TaiKhoanService {

    private final TaiKhoanDao dao;

    public TaiKhoanService() {
        this.dao = new TaiKhoanDao();
    }

    public void ensureServerRunning() {
        if (!ApiClient.isServerRunning()) {
            throw new RuntimeException("❌ Không thể kết nối đến API Server!");
        }
    }

    public TaiKhoan login(String tenDangNhap, String matKhau) {
        ensureServerRunning();
        TaiKhoan tk = dao.kiemTraDangNhap(tenDangNhap, matKhau);
        if (tk != null) {
            if (dao.kiemTraTaiKhoanDangOnline(tenDangNhap)) {
                dao.capNhatTrangThaiOffline(tenDangNhap);
            }
            dao.capNhatTrangThaiOnline(tenDangNhap);
        }
        return tk;
    }

    public boolean logout(String tenDangNhap) {
        try {
            ensureServerRunning();
            dao.capNhatTrangThaiOffline(tenDangNhap);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<TaiKhoan> getAll() {
        ensureServerRunning();
        return dao.getAllTaiKhoan();
    }

    public boolean update(TaiKhoan tk) {
        ensureServerRunning();
        return dao.capNhatTaiKhoan(tk);
    }

    public boolean delete(String tenDangNhap) {
        ensureServerRunning();
        return dao.xoaTaiKhoan(tenDangNhap);
    }

    public boolean register(TaiKhoan tk) {
        ensureServerRunning();
        return dao.themTaiKhoan(tk);
    }

    public boolean isOnline(String tenDangNhap) {
        return dao.kiemTraTaiKhoanDangOnline(tenDangNhap);
    }

    public String getLastError() {
        return dao.getLastErrorMessage();
    }

    public String getEmailByUsername(String tenDangNhap) {
        return dao.getEmailByUsername(tenDangNhap);
    }
}
