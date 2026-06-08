package service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.SinhVien;
import until.ApiClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Service xử lý Sinh viên - thay thế SinhvienDao
 */
public class SinhVienService {
    
    /**
     * Lấy tất cả sinh viên
     */
    public List<SinhVien> getAllSinhVien() {
        try {
            String response = ApiClient.get("/students");
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            
            if (json.get("success").getAsBoolean()) {
                JsonArray dataArray = json.getAsJsonArray("data");
                List<SinhVien> list = new ArrayList<>();
                for (JsonElement element : dataArray) {
                    list.add(parseSinhVien(element.getAsJsonObject()));
                }
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    
    /**
     * Lấy sinh viên theo mã
     */
    public SinhVien getSinhVienById(String maSV) {
        try {
            String response = ApiClient.get("/students/" + maSV);
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            
            if (json.get("success").getAsBoolean()) {
                return parseSinhVien(json.getAsJsonObject("data"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Tìm kiếm sinh viên theo tên
     */
    public List<SinhVien> searchByHoTen(String keyword) {
        try {
            String response = ApiClient.get("/students/search?keyword=" + keyword);
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            
            if (json.get("success").getAsBoolean()) {
                JsonArray dataArray = json.getAsJsonArray("data");
                List<SinhVien> list = new ArrayList<>();
                for (JsonElement element : dataArray) {
                    list.add(parseSinhVien(element.getAsJsonObject()));
                }
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    
    /**
     * Tạo sinh viên mới
     */
    public boolean themSinhVien(SinhVien sv) {
        try {
            String jsonBody = toJson(sv);
            String response = ApiClient.post("/students", jsonBody);
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            return json.get("success").getAsBoolean();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Cập nhật sinh viên
     */
    public boolean capNhatSinhVien(SinhVien sv) {
        try {
            String jsonBody = toJson(sv);
            String response = ApiClient.put("/students/" + sv.getMaSV(), jsonBody);
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            return json.get("success").getAsBoolean();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Xóa sinh viên
     */
    public boolean xoaSinhVien(String maSV) {
        try {
            String response = ApiClient.delete("/students/" + maSV);
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            return json.get("success").getAsBoolean();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Parse JSON sang SinhVien object
     */
    private SinhVien parseSinhVien(JsonObject json) {
        SinhVien sv = new SinhVien();
        sv.setMaSV(getStringOrNull(json, "maSV"));
        sv.setHoTen(getStringOrNull(json, "hoTen"));
        sv.setNgaySinh(getStringOrNull(json, "ngaySinh"));
        sv.setGioiTinh(getStringOrNull(json, "gioiTinh"));
        sv.setDiaChi(getStringOrNull(json, "diaChi"));
        sv.setEmail(getStringOrNull(json, "email"));
        sv.setSoDienThoai(getStringOrNull(json, "soDienThoai"));
        sv.setMaKhoa(getStringOrNull(json, "maKhoa"));
        sv.setTenDangNhap(getStringOrNull(json, "tenDangNhap"));
        return sv;
    }
    
    /**
     * Convert SinhVien sang JSON string
     */
    private String toJson(SinhVien sv) {
        return String.format(
            "{\"maSV\":\"%s\",\"hoTen\":\"%s\",\"ngaySinh\":\"%s\",\"gioiTinh\":\"%s\",\"diaChi\":\"%s\",\"email\":\"%s\",\"soDienThoai\":\"%s\",\"maKhoa\":\"%s\",\"tenDangNhap\":\"%s\"}",
            sv.getMaSV(), sv.getHoTen(), sv.getNgaySinh(), sv.getGioiTinh(),
            sv.getDiaChi(), sv.getEmail(), sv.getSoDienThoai(), sv.getMaKhoa(), sv.getTenDangNhap()
        );
    }
    
    private String getStringOrNull(JsonObject json, String key) {
        if (json.has(key) && !json.get(key).isJsonNull()) {
            return json.get(key).getAsString();
        }
        return null;
    }
}
