package service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Diem;
import model.LopHocPhan;
import until.ApiClient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Service xử lý Điểm - thay thế DiemDao
 */
public class DiemService {
    
    public List<Diem> getAllDiem() {
        try {
            String response = ApiClient.get("/grades");
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            
            if (json.get("success").getAsBoolean()) {
                JsonArray dataArray = json.getAsJsonArray("data");
                List<Diem> list = new ArrayList<>();
                for (JsonElement element : dataArray) {
                    list.add(parseDiem(element.getAsJsonObject()));
                }
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    
    public Diem getDiemById(int id) {
        try {
            String response = ApiClient.get("/grades/" + id);
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            
            if (json.get("success").getAsBoolean()) {
                return parseDiem(json.getAsJsonObject("data"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Diem> getDiemBySinhVien(String maSV) {
        try {
            String response = ApiClient.get("/grades/student/" + maSV);
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            
            if (json.get("success").getAsBoolean()) {
                JsonArray dataArray = json.getAsJsonArray("data");
                List<Diem> list = new ArrayList<>();
                for (JsonElement element : dataArray) {
                    list.add(parseDiem(element.getAsJsonObject()));
                }
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    
    public List<Diem> getDiemByLop(String maLop) {
        try {
            String response = ApiClient.get("/grades/class/" + maLop);
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            
            if (json.get("success").getAsBoolean()) {
                JsonArray dataArray = json.getAsJsonArray("data");
                List<Diem> list = new ArrayList<>();
                for (JsonElement element : dataArray) {
                    list.add(parseDiem(element.getAsJsonObject()));
                }
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    
    public Diem getDiemByMaSVAndMaLop(String maSV, String maLop) {
        List<Diem> allDiem = getDiemByLop(maLop);
        for (Diem d : allDiem) {
            if (d.getMaSV().equals(maSV)) {
                return d;
            }
        }
        return null;
    }
    
    /**
     * Lọc điểm theo học kỳ và năm học.
     * @param hocKy  "Tất cả" để bỏ qua lọc học kỳ
     * @param namHoc "Tất cả" để bỏ qua lọc năm học
     */
    public List<Diem> getDiemBySinhVienTheoKyNam(String maSV, String hocKy, String namHoc) {
        List<Diem> allDiem = getDiemBySinhVien(maSV);
        // Nếu cả hai đều "Tất cả" → trả về toàn bộ
        if ("Tất cả".equals(hocKy) && "Tất cả".equals(namHoc)) {
            return allDiem;
        }
        List<Diem> filtered = new ArrayList<>();
        LopHocPhanService lhpService = new LopHocPhanService();
        for (Diem d : allDiem) {
            LopHocPhan lhp = lhpService.getLopHocPhanByMaLop(d.getMaLop());
            if (lhp == null) continue;
            boolean hocKyMatch = "Tất cả".equals(hocKy) || hocKy.equals(lhp.getHocKy());
            boolean namHocMatch = "Tất cả".equals(namHoc) || namHoc.equals(lhp.getNamHoc());
            if (hocKyMatch && namHocMatch) {
                filtered.add(d);
            }
        }
        return filtered;
    }
    
    public List<Integer> getHocKyBySinhVien(String maSV) {
        List<Diem> allDiem = getDiemBySinhVien(maSV);
        Set<Integer> hocKySet = new HashSet<>();
        LopHocPhanService lhpService = new LopHocPhanService();
        for (Diem d : allDiem) {
            LopHocPhan lhp = lhpService.getLopHocPhanByMaLop(d.getMaLop());
            if (lhp != null && lhp.getHocKy() != null) {
                try {
                    hocKySet.add(Integer.parseInt(lhp.getHocKy()));
                } catch (NumberFormatException ignored) {}
            }
        }
        return new ArrayList<>(hocKySet);
    }
    
    public List<Integer> getNamHocBySinhVien(String maSV) {
        List<Diem> allDiem = getDiemBySinhVien(maSV);
        Set<Integer> namHocSet = new HashSet<>();
        LopHocPhanService lhpService = new LopHocPhanService();
        for (Diem d : allDiem) {
            LopHocPhan lhp = lhpService.getLopHocPhanByMaLop(d.getMaLop());
            if (lhp != null && lhp.getNamHoc() != null) {
                try {
                    namHocSet.add(Integer.parseInt(lhp.getNamHoc()));
                } catch (NumberFormatException ignored) {}
            }
        }
        return new ArrayList<>(namHocSet);
    }
    
    public boolean nhapDiem(Diem diem) {
        try {
            String jsonBody = toJson(diem);
            String response = ApiClient.post("/grades", jsonBody);
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            return json.get("success").getAsBoolean();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean capNhatDiem(Diem diem) {
        try {
            String jsonBody = toJson(diem);
            String response = ApiClient.put("/grades/" + diem.getId(), jsonBody);
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            return json.get("success").getAsBoolean();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean xoaDiem(int maDiem) {
        try {
            String response = ApiClient.delete("/grades/" + maDiem);
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            return json.get("success").getAsBoolean();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public String getTenMonHocByMaLop(String maLop) {
        // Lấy từ LopHocPhan và MonHoc
        LopHocPhanService lhpService = new LopHocPhanService();
        MonHocService mhService = new MonHocService();
        
        var lhp = lhpService.getLopHocPhanByMaLop(maLop);
        if (lhp != null) {
            var mh = mhService.getMonHocById(lhp.getMaMH());
            if (mh != null) {
                return mh.getTenMH();
            }
        }
        return "";
    }
    
    private Diem parseDiem(JsonObject json) {
        Diem diem = new Diem();
        
        if (json.has("id") && !json.get("id").isJsonNull()) {
            diem.setId(json.get("id").getAsInt());
        }
        
        diem.setMaSV(getStringOrNull(json, "maSV"));
        diem.setMaLop(getStringOrNull(json, "maLop"));
        
        if (json.has("diemQuaTrinh") && !json.get("diemQuaTrinh").isJsonNull()) {
            diem.setDiemQuaTrinh(json.get("diemQuaTrinh").getAsFloat());
        }
        if (json.has("diemGiuaKy") && !json.get("diemGiuaKy").isJsonNull()) {
            diem.setDiemGiuaKy(json.get("diemGiuaKy").getAsFloat());
        }
        if (json.has("diemCuoiKy") && !json.get("diemCuoiKy").isJsonNull()) {
            diem.setDiemCuoiKy(json.get("diemCuoiKy").getAsFloat());
        }
        if (json.has("diemTongKet") && !json.get("diemTongKet").isJsonNull()) {
            diem.setDiemTongKet(json.get("diemTongKet").getAsFloat());
        }
        diem.setDiemChu(getStringOrNull(json, "diemChu"));
        diem.setXepLoai(getStringOrNull(json, "xepLoai"));
        
        return diem;
    }
    
    private String toJson(Diem diem) {
        return String.format(
            "{\"maSV\":\"%s\",\"maLop\":\"%s\",\"diemQuaTrinh\":%.1f,\"diemGiuaKy\":%.1f,\"diemCuoiKy\":%.1f,\"diemTongKet\":%.1f,\"diemChu\":\"%s\",\"xepLoai\":\"%s\"}",
            diem.getMaSV(), diem.getMaLop(), 
            diem.getDiemQuaTrinh() != null ? diem.getDiemQuaTrinh() : 0.0, 
            diem.getDiemGiuaKy() != null ? diem.getDiemGiuaKy() : 0.0, 
            diem.getDiemCuoiKy() != null ? diem.getDiemCuoiKy() : 0.0, 
            diem.getDiemTongKet() != null ? diem.getDiemTongKet() : 0.0,
            diem.getDiemChu() != null ? diem.getDiemChu() : "",
            diem.getXepLoai() != null ? diem.getXepLoai() : ""
        );
    }
    
    private String getStringOrNull(JsonObject json, String key) {
        if (json.has(key) && !json.get(key).isJsonNull()) {
            return json.get(key).getAsString();
        }
        return null;
    }
}
