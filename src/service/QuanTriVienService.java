package service;

import dao.QuanTriVienDao;
import model.QuanTriVien;
import until.ApiClient;

import java.util.ArrayList;
import java.util.List;

public class QuanTriVienService {

    private final QuanTriVienDao dao;

    public QuanTriVienService() {
        this.dao = new QuanTriVienDao();
    }

    public void ensureServerRunning() {
        if (!ApiClient.isServerRunning()) {
            throw new RuntimeException("❌ Không thể kết nối đến API Server!");
        }
    }

    public ArrayList<QuanTriVien> getAll() {
        ensureServerRunning();
        return dao.getAllQuanTriVien();
    }

    public QuanTriVien getByTaiKhoan(String tenDangNhap) {
        ensureServerRunning();
        return dao.getQuanTriVienByTaiKhoan(tenDangNhap);
    }

    public boolean add(QuanTriVien qtv) {
        ensureServerRunning();
        validateRequired(qtv.getMaQTV(), qtv.getHoTen(), qtv.getTenDangNhap());
        return dao.themQuanTriVien(qtv);
    }

    public boolean update(QuanTriVien qtv) {
        ensureServerRunning();
        validateRequired(qtv.getMaQTV(), qtv.getHoTen(), qtv.getTenDangNhap());
        return dao.capNhatQuanTriVien(qtv);
    }

    public boolean delete(String maQTV) {
        ensureServerRunning();
        return dao.xoaQuanTriVien(maQTV);
    }

    private void validateRequired(String... fields) {
        for (String f : fields) {
            if (f == null || f.trim().isEmpty()) {
                throw new RuntimeException("Vui lòng nhập đầy đủ thông tin bắt buộc!");
            }
        }
    }
}
