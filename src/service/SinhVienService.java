package service;

import dao.SinhvienDao;
import model.SinhVien;
import until.ApiClient;

import java.util.ArrayList;
import java.util.List;

public class SinhVienService {

    private final SinhvienDao dao;

    public SinhVienService() {
        this.dao = new SinhvienDao();
    }

    public void ensureServerRunning() {
        if (!ApiClient.isServerRunning()) {
            throw new RuntimeException("❌ Không thể kết nối đến API Server!");
        }
    }

    public ArrayList<SinhVien> getAll() {
        ensureServerRunning();
        return dao.getAllSinhVien();
    }

    public SinhVien getById(String maSV) {
        ensureServerRunning();
        return dao.getSinhVienById(maSV);
    }

    public boolean add(SinhVien sv) {
        ensureServerRunning();
        validateRequired(sv.getMaSV(), sv.getHoTen());
        validateEmail(sv.getEmail());
        if (dao.getSinhVienById(sv.getMaSV()) != null) {
            throw new RuntimeException("Mã sinh viên '" + sv.getMaSV() + "' đã tồn tại!");
        }
        return dao.themSinhVien(sv);
    }

    public boolean update(SinhVien sv) {
        ensureServerRunning();
        validateRequired(sv.getMaSV(), sv.getHoTen());
        validateEmail(sv.getEmail());
        return dao.capNhatSinhVien(sv);
    }

    public boolean delete(String maSV) {
        ensureServerRunning();
        return dao.xoaSinhVien(maSV);
    }

    public List<SinhVien> getByKhoa(String maKhoa) {
        List<SinhVien> all = getAll();
        List<SinhVien> result = new ArrayList<>();
        for (SinhVien sv : all) {
            if (maKhoa.equalsIgnoreCase(sv.getMaKhoa())) {
                result.add(sv);
            }
        }
        return result;
    }

    public int countTotal() {
        try {
            return getAll().size();
        } catch (Exception e) {
            return 0;
        }
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
