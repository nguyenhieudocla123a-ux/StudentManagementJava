package service;

import dao.GiangvienDao;
import model.GiangVien;
import until.ApiClient;

import java.util.ArrayList;
import java.util.List;

public class GiangVienService {

    private final GiangvienDao dao;

    public GiangVienService() {
        this.dao = new GiangvienDao();
    }

    public void ensureServerRunning() {
        if (!ApiClient.isServerRunning()) {
            throw new RuntimeException("❌ Không thể kết nối đến API Server!");
        }
    }

    public ArrayList<GiangVien> getAll() {
        ensureServerRunning();
        return dao.getAllGiangVien();
    }

    public GiangVien getById(String maGV) {
        ensureServerRunning();
        return dao.getGiangVienById(maGV);
    }

    public boolean add(GiangVien gv) {
        ensureServerRunning();
        validateRequired(gv.getMaGV(), gv.getHoTen(), gv.getTenDangNhap());
        validateEmail(gv.getEmail());
        if (dao.getGiangVienById(gv.getMaGV()) != null) {
            throw new RuntimeException("Mã giảng viên '" + gv.getMaGV() + "' đã tồn tại!");
        }
        return dao.themGiangVien(gv);
    }

    public boolean update(GiangVien gv) {
        ensureServerRunning();
        validateRequired(gv.getMaGV(), gv.getHoTen(), gv.getTenDangNhap());
        validateEmail(gv.getEmail());
        return dao.capNhatGiangVien(gv);
    }

    public boolean delete(String maGV) {
        ensureServerRunning();
        return dao.xoaGiangVien(maGV);
    }

    public List<GiangVien> getByKhoa(String maKhoa) {
        List<GiangVien> all = getAll();
        List<GiangVien> result = new ArrayList<>();
        for (GiangVien gv : all) {
            if (maKhoa.equalsIgnoreCase(gv.getKhoa())) {
                result.add(gv);
            }
        }
        return result;
    }

    private void validateRequired(String... fields) {
        for (String f : fields) {
            if (f == null || f.trim().isEmpty()) {
                throw new RuntimeException("Vui lòng nhập đầy đủ thông tin bắt buộc!");
            }
        }
    }

    private void validateEmail(String email) {
        if (email != null && !email.trim().isEmpty() && !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new RuntimeException("Email không hợp lệ!");
        }
    }
}
