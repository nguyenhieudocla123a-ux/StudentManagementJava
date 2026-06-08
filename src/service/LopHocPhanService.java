package service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.LopHocPhan;
import until.ApiClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Service xử lý Lớp học phần - thay thế LopHocPhanDao
 */
public class LopHocPhanService {
    
    public List<LopHocPhan> getAllLopHocPhan() {
        try {
            String response = ApiClient.get("/classes");
            JsonArray dataArray = JsonParser.parseString(response).getAsJsonArray();
            List<LopHocPhan> list = new ArrayList<>();
            for (JsonElement element : dataArray) {
                list.add(parseLopHocPhan(element.getAsJsonObject()));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    
    public LopHocPhan getLopHocPhanByMaLop(String maLop) {
        try {
            String response = ApiClient.get("/classes/" + maLop);
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            return parseLopHocPhan(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<LopHocPhan> getLopByGiangVien(String maGV) {
        try {
            String response = ApiClient.get("/classes/teacher/" + maGV);
            JsonArray dataArray = JsonParser.parseString(response).getAsJsonArray();
            List<LopHocPhan> list = new ArrayList<>();
            for (JsonElement element : dataArray) {
                list.add(parseLopHocPhan(element.getAsJsonObject()));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    
    public List<LopHocPhan> getLopDangMoDangKy() {
        try {
            String response = ApiClient.get("/classes/open");
            JsonArray dataArray = JsonParser.parseString(response).getAsJsonArray();
            List<LopHocPhan> list = new ArrayList<>();
            for (JsonElement element : dataArray) {
                list.add(parseLopHocPhan(element.getAsJsonObject()));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    
    public boolean themLopHocPhan(LopHocPhan lhp) {
        try {
            String jsonBody = toJson(lhp);
            String response = ApiClient.post("/classes", jsonBody);
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            return json.get("success").getAsBoolean();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean capNhatLopHocPhan(LopHocPhan lhp) {
        try {
            String jsonBody = toJson(lhp);
            String response = ApiClient.put("/classes/" + lhp.getMaLop(), jsonBody);
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            return json.get("success").getAsBoolean();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean xoaLopHocPhan(String maLop) {
        try {
            String response = ApiClient.delete("/classes/" + maLop);
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            return json.get("success").getAsBoolean();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean kiemTraGiangVienCoLopHocPhan(String maGV) {
        return !getLopByGiangVien(maGV).isEmpty();
    }

    public boolean kiemTraLopCoTheXoa(String maLop) {
        try {
            String response = ApiClient.get("/registrations/class/" + maLop);
            JsonArray dataArray = JsonParser.parseString(response).getAsJsonArray();
            return dataArray.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean capNhatTrangThai(String maLop, String trangThai) {
        try {
            String jsonBody = String.format("{\"trangThai\":\"%s\"}", trangThai);
            String response = ApiClient.put("/classes/" + maLop + "/deadline", jsonBody);
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            return json.has("success") && json.get("success").getAsBoolean();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private LopHocPhan parseLopHocPhan(JsonObject json) {
        LopHocPhan lhp = new LopHocPhan();
        lhp.setMaLop(getStringOrNull(json, "maLop"));
        lhp.setMaMH(getStringOrNull(json, "maMH"));
        lhp.setMaGV(getStringOrNull(json, "maGV"));
        lhp.setHocKy(getStringOrNull(json, "hocKy"));
        lhp.setNamHoc(getStringOrNull(json, "namHoc"));
        lhp.setTrangThai(getStringOrNull(json, "trangThai"));

        if (json.has("siSoToiDa") && !json.get("siSoToiDa").isJsonNull()) {
            lhp.setSiSoToiDa(json.get("siSoToiDa").getAsInt());
        }
        if (json.has("siSoHienTai") && !json.get("siSoHienTai").isJsonNull()) {
            lhp.setSiSoHienTai(json.get("siSoHienTai").getAsInt());
        }

        // Các trường thời gian (lưu dạng String)
        lhp.setThoiGianMoDangKy(getStringOrNull(json, "thoiGianMoDangKy"));
        lhp.setThoiGianDongDangKy(getStringOrNull(json, "thoiGianDongDangKy"));
        lhp.setNgayBatDauHoc(getStringOrNull(json, "ngayBatDauHoc"));
        lhp.setNgayKetThucHoc(getStringOrNull(json, "ngayKetThucHoc"));
        return lhp;
    }
    
    private String toJson(LopHocPhan lhp) {
        return String.format(
            "{\"maLop\":\"%s\",\"maMH\":\"%s\",\"maGV\":\"%s\",\"hocKy\":\"%s\",\"namHoc\":\"%s\",\"siSoToiDa\":%d,\"trangThai\":\"%s\"}",
            lhp.getMaLop() != null ? lhp.getMaLop() : "",
            lhp.getMaMH() != null ? lhp.getMaMH() : "",
            lhp.getMaGV() != null ? lhp.getMaGV() : "",
            lhp.getHocKy() != null ? lhp.getHocKy() : "",
            lhp.getNamHoc() != null ? lhp.getNamHoc() : "",
            lhp.getSiSoToiDa() != null ? lhp.getSiSoToiDa() : 0,
            lhp.getTrangThai() != null ? lhp.getTrangThai() : "mo"
        );
    }
    
    private String getStringOrNull(JsonObject json, String key) {
        if (json.has(key) && !json.get(key).isJsonNull()) {
            return json.get(key).getAsString();
        }
        return null;
    }
}
