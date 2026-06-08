package service;

import dao.MonHocDao;
import model.MonHoc;
import until.ApiClient;

import java.util.ArrayList;
import java.util.List;

public class MonHocService {

    private final MonHocDao dao;

    public MonHocService() {
        this.dao = new MonHocDao();
    }

    public void ensureServerRunning() {
        if (!ApiClient.isServerRunning()) {
            throw new RuntimeException("❌ Không thể kết nối đến API Server!");
        }
    }

    public List<MonHoc> getAll() {
        ensureServerRunning();
        return dao.getAllMonHoc();
    }

    public MonHoc getById(String maMH) {
        ensureServerRunning();
        return dao.getMonHocById(maMH);
    }

    public boolean add(MonHoc mh) {
        ensureServerRunning();
        validateRequired(mh.getMaMH(), mh.getTenMH());
        if (dao.kiemTraMonHocTonTai(mh.getMaMH())) {
            throw new RuntimeException("Mã môn học '" + mh.getMaMH() + "' đã tồn tại!");
        }
        return dao.themMonHoc(mh);
    }

    public boolean update(MonHoc mh) {
        ensureServerRunning();
        validateRequired(mh.getMaMH(), mh.getTenMH());
        return dao.capNhatMonHoc(mh);
    }

    public boolean delete(String maMH) {
        ensureServerRunning();
        return dao.xoaMonHoc(maMH);
    }

    public List<MonHoc> getByKhoa(String maKhoa) {
        ensureServerRunning();
        return dao.getMonHocByKhoa(maKhoa);
    }

    private void validateRequired(String... fields) {
        for (String f : fields) {
            if (f == null || f.trim().isEmpty()) {
                throw new RuntimeException("Vui lòng nhập đầy đủ thông tin bắt buộc!");
            }
        }
    }
}
