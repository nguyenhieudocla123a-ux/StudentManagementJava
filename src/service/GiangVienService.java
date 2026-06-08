package service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.GiangVien;
import until.ApiClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Service xử lý Giảng viên - thay thế GiangvienDao
 */
public class GiangVienService {
    
    /**
     * Lấy tất cả giảng viên
     */
    public List<GiangVien> getAllGiangVien() {
        try {
            String response = ApiClient.get("/teachers");
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            
            if (json.get("success").getAsBoolean()) {
                JsonArray dataArray = json.getAsJsonArray("data");
                List<GiangVien> list = new ArrayList<>();
                for (JsonElement element : dataArray) {
                    list.add(parseGiangVien(element.getAsJsonObject()));
                }
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    
    /**
     * Lấy giảng viên theo mã
     */
    public GiangVien getGiangVienById(String maGV) {
        try {
            String response = ApiClient.get("/teachers/" + maGV);
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            
            if (json.get("success").getAsBoolean()) {
                return parseGiangVien(json.getAsJsonObject("data"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Tìm kiếm giảng viên
     */
    public List<GiangVien> searchByHoTen(String keyword) {
        try {
            String response = ApiClient.get("/teachers/search?keyword=" + keyword);
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            
            if (json.get("success").getAsBoolean()) {
                JsonArray dataArray = json.getAsJsonArray("data");
                List<GiangVien> list = new ArrayList<>();
                for (JsonElement element : dataArray) {
                    list.add(parseGiangVien(element.getAsJsonObject()));
                }
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    
    /**
     * Thêm giảng viên mới
     */
    public boolean themGiangVien(GiangVien gv) {
        try {
            String jsonBody = toJson(gv);
            String response = ApiClient.post("/teachers", jsonBody);
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            return json.get("success").getAsBoolean();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Cập nhật giảng viên
     */
    public boolean capNhatGiangVien(GiangVien gv) {
        try {
            String jsonBody = toJson(gv);
            String response = ApiClient.put("/teachers/" + gv.getMaGV(), jsonBody);
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            return json.get("success").getAsBoolean();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Xóa giảng viên
     */
    public boolean xoaGiangVien(String maGV) {
        try {
            String response = ApiClient.delete("/teachers/" + maGV);
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            return json.get("success").getAsBoolean();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Parse JSON sang GiangVien object
     */
    private GiangVien parseGiangVien(JsonObject json) {
        GiangVien gv = new GiangVien();
        gv.setMaGV(getStringOrNull(json, "maGV"));
        gv.setHoTen(getStringOrNull(json, "hoTen"));
        gv.setEmail(getStringOrNull(json, "email"));
        gv.setSoDienThoai(getStringOrNull(json, "sdt"));
        gv.setKhoa(getStringOrNull(json, "maKhoa"));
        gv.setTenDangNhap(getStringOrNull(json, "tenDangNhap"));
        return gv;
    }
    
    /**
     * Convert GiangVien sang JSON string
     */
    private String toJson(GiangVien gv) {
        return String.format(
            "{\"maGV\":\"%s\",\"hoTen\":\"%s\",\"email\":\"%s\",\"sdt\":\"%s\",\"maKhoa\":\"%s\",\"tenDangNhap\":\"%s\"}",
            gv.getMaGV(), gv.getHoTen(), gv.getEmail(), gv.getSoDienThoai(), gv.getMaKhoa(), gv.getTenDangNhap()
        );
    }
    
    private String getStringOrNull(JsonObject json, String key) {
        if (json.has(key) && !json.get(key).isJsonNull()) {
            return json.get(key).getAsString();
        }
        return null;
    }
}
