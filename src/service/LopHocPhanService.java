package service;

import dao.LopHocPhanDao;
import model.LopHocPhan;
import until.ApiClient;

import java.util.ArrayList;
import java.util.List;

public class LopHocPhanService {

    private final LopHocPhanDao dao;

    public LopHocPhanService() {
        this.dao = new LopHocPhanDao();
    }

    public void ensureServerRunning() {
        if (!ApiClient.isServerRunning()) {
            throw new RuntimeException("❌ Không thể kết nối đến API Server!");
        }
    }

    public ArrayList<LopHocPhan> getAll() {
        ensureServerRunning();
        return dao.getAllLopHocPhan();
    }

    public List<LopHocPhan> getByMonHoc(String maMH) {
        ensureServerRunning();
        return dao.getLopByMonHoc(maMH);
    }

    public List<LopHocPhan> getByGiangVien(String maGV) {
        ensureServerRunning();
        return dao.getLopByGiangVien(maGV);
    }

    public LopHocPhan getByMaLop(String maLop) {
        ensureServerRunning();
        return dao.getLopHocPhanByMaLop(maLop);
    }

    public boolean add(LopHocPhan lhp) {
        ensureServerRunning();
        validateRequired(lhp.getMaLop(), lhp.getMaMH(), lhp.getMaGV(), lhp.getHocKy(), lhp.getNamHoc());
        if (dao.kiemTraLopHocPhanTonTai(lhp.getMaLop())) {
            throw new RuntimeException("Mã lớp '" + lhp.getMaLop() + "' đã tồn tại!");
        }
        return dao.themLopHocPhan(lhp);
    }

    public boolean update(LopHocPhan lhp) {
        ensureServerRunning();
        validateRequired(lhp.getMaLop(), lhp.getMaMH(), lhp.getMaGV(), lhp.getHocKy(), lhp.getNamHoc());
        return dao.capNhatLopHocPhan(lhp);
    }

    public boolean delete(String maLop) {
        ensureServerRunning();
        return dao.xoaLopHocPhan(maLop);
    }

    public boolean capNhatTrangThai(String maLop, String trangThai) {
        ensureServerRunning();
        return dao.capNhatTrangThai(maLop, trangThai);
    }

    public List<LopHocPhan> getDangMo() {
        return dao.getLopHocPhanDangMo();
    }

    public boolean isDangMo(String maLop) {
        LopHocPhan lhp = getByMaLop(maLop);
        return lhp != null && lhp.isDangMo();
    }

    private void validateRequired(String... fields) {
        for (String f : fields) {
            if (f == null || f.trim().isEmpty()) {
                throw new RuntimeException("Vui lòng nhập đầy đủ thông tin bắt buộc!");
            }
        }
    }
}
