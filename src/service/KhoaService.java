package service;

import dao.KhoaDao;
import model.Khoa;
import until.ApiClient;

import java.util.ArrayList;
import java.util.List;

public class KhoaService {

    private final KhoaDao dao;

    public KhoaService() {
        this.dao = new KhoaDao();
    }

    public void ensureServerRunning() {
        if (!ApiClient.isServerRunning()) {
            throw new RuntimeException("❌ Không thể kết nối đến API Server!");
        }
    }

    public List<Khoa> getAll() {
        ensureServerRunning();
        return dao.getAllKhoa();
    }

    public Khoa getById(String maKhoa) {
        ensureServerRunning();
        return dao.getKhoaById(maKhoa);
    }

    public boolean add(Khoa khoa) {
        ensureServerRunning();
        validateRequired(khoa.getMaKhoa(), khoa.getTenKhoa());
        if (dao.kiemTraKhoaTonTai(khoa.getMaKhoa())) {
            throw new RuntimeException("Mã khoa '" + khoa.getMaKhoa() + "' đã tồn tại!");
        }
        return dao.themKhoa(khoa);
    }

    public boolean update(Khoa khoa) {
        ensureServerRunning();
        validateRequired(khoa.getMaKhoa(), khoa.getTenKhoa());
        return dao.capNhatKhoa(khoa);
    }

    public boolean delete(String maKhoa) {
        ensureServerRunning();
        return dao.xoaKhoa(maKhoa);
    }

    public boolean isKhoaCoTheXoa(String maKhoa) {
        ensureServerRunning();
        return dao.kiemTraKhoaCoTheXoa(maKhoa);
    }

    private void validateRequired(String... fields) {
        for (String f : fields) {
            if (f == null || f.trim().isEmpty()) {
                throw new RuntimeException("Vui lòng nhập đầy đủ thông tin bắt buộc!");
            }
        }
    }
}
