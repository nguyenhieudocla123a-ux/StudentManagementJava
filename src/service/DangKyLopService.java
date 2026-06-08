package service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.DangKiLop;
import until.ApiClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Service xử lý Đăng ký lớp - thay thế DangKiLopDao
 */
public class DangKyLopService {
    
    private String lastErrorMessage = "";
    
    public List<DangKiLop> getAllDangKy() {
        try {
            String response = ApiClient.get("/registrations");
            JsonArray dataArray = JsonParser.parseString(response).getAsJsonArray();
            List<DangKiLop> list = new ArrayList<>();
            for (JsonElement element : dataArray) {
                list.add(parseDangKiLop(element.getAsJsonObject()));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    
    public List<DangKiLop> getLopDaDangKy(String maSV) {
        try {
            String response = ApiClient.get("/registrations/student/" + maSV);
            JsonArray dataArray = JsonParser.parseString(response).getAsJsonArray();
            List<DangKiLop> list = new ArrayList<>();
            for (JsonElement element : dataArray) {
                list.add(parseDangKiLop(element.getAsJsonObject()));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    
    public List<DangKiLop> getSVDaDangKy(String maLop) {
        try {
            String response = ApiClient.get("/registrations/class/" + maLop);
            JsonArray dataArray = JsonParser.parseString(response).getAsJsonArray();
            List<DangKiLop> list = new ArrayList<>();
            for (JsonElement element : dataArray) {
                list.add(parseDangKiLop(element.getAsJsonObject()));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    
    public boolean kiemTraDaDangKy(String maSV, String maLop) {
        try {
            String response = ApiClient.get("/registrations/" + maSV + "/" + maLop);
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            return json.has("maSV") && !json.get("maSV").isJsonNull();
        } catch (Exception e) {
            return false;
        }
    }
    
    public int demSoLuongDangKy(String maLop) {
        List<DangKiLop> list = getSVDaDangKy(maLop);
        return list.size();
    }
    
    public boolean dangKyLop(DangKiLop dkl) {
        try {
            String jsonBody = toJson(dkl);
            String response = ApiClient.post("/registrations", jsonBody);
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            
            if (json.get("success").getAsBoolean()) {
                lastErrorMessage = "";
                return true;
            } else {
                lastErrorMessage = json.get("message").getAsString();
                return false;
            }
        } catch (Exception e) {
            lastErrorMessage = "Lỗi kết nối: " + e.getMessage();
            return false;
        }
    }
    
    public boolean huyDangKy(String maSV, String maLop) {
        try {
            String response = ApiClient.delete("/registrations/" + maSV + "/" + maLop);
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            
            if (json.get("success").getAsBoolean()) {
                lastErrorMessage = "";
                return true;
            } else {
                lastErrorMessage = json.get("message").getAsString();
                return false;
            }
        } catch (Exception e) {
            lastErrorMessage = "Lỗi kết nối: " + e.getMessage();
            return false;
        }
    }
    
    public String getLastErrorMessage() {
        return lastErrorMessage;
    }
    
    /**
     * Kiểm tra sinh viên có đang đăng ký lớp nào không
     */
    public boolean kiemTraSinhVienCoDangKy(String maSV) {
        List<DangKiLop> list = getLopDaDangKy(maSV);
        return !list.isEmpty();
    }
    
    private DangKiLop parseDangKiLop(JsonObject json) {
        DangKiLop dkl = new DangKiLop();
        dkl.setMaSV(getStringOrNull(json, "maSV"));
        dkl.setMaLop(getStringOrNull(json, "maLop"));
        dkl.setNgayDangKy(getStringOrNull(json, "ngayDangKy"));
        return dkl;
    }
    
    private String toJson(DangKiLop dkl) {
        return String.format("{\"maSinhVien\":\"%s\",\"maLopHocPhan\":\"%s\"}", 
            dkl.getMaSV(), dkl.getMaLop());
    }
    
    private String getStringOrNull(JsonObject json, String key) {
        if (json.has(key) && !json.get(key).isJsonNull()) {
            return json.get(key).getAsString();
        }
        return null;
    }
}
