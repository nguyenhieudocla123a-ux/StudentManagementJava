package service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.MonHoc;
import until.ApiClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Service xử lý Môn học - thay thế MonHocDao
 */
public class MonHocService {
    
    /**
     * Lấy tất cả môn học
     */
    public List<MonHoc> getAllMonHoc() {
        try {
            String response = ApiClient.get("/subjects");
            JsonArray dataArray = JsonParser.parseString(response).getAsJsonArray();
            List<MonHoc> list = new ArrayList<>();
            for (JsonElement element : dataArray) {
                list.add(parseMonHoc(element.getAsJsonObject()));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    
    /**
     * Lấy môn học theo mã
     */
    public MonHoc getMonHocById(String maMH) {
        try {
            String response = ApiClient.get("/subjects/" + maMH);
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            return parseMonHoc(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Tìm kiếm môn học
     */
    public List<MonHoc> searchMonHoc(String keyword) {
        try {
            String response = ApiClient.get("/subjects/search?keyword=" + keyword);
            JsonArray dataArray = JsonParser.parseString(response).getAsJsonArray();
            List<MonHoc> list = new ArrayList<>();
            for (JsonElement element : dataArray) {
                list.add(parseMonHoc(element.getAsJsonObject()));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    
    /**
     * Thêm môn học mới
     */
    public boolean themMonHoc(MonHoc mh) {
        try {
            String jsonBody = toJson(mh);
            String response = ApiClient.post("/subjects", jsonBody);
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            return json.get("success").getAsBoolean();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Cập nhật môn học
     */
    public boolean capNhatMonHoc(MonHoc mh) {
        try {
            String jsonBody = toJson(mh);
            String response = ApiClient.put("/subjects/" + mh.getMaMH(), jsonBody);
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            return json.get("success").getAsBoolean();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Xóa môn học
     */
    public boolean xoaMonHoc(String maMH) {
        try {
            String response = ApiClient.delete("/subjects/" + maMH);
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            return json.get("success").getAsBoolean();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean kiemTraMonHocDuocSuDung(String maMH) {
        try {
            String response = ApiClient.get("/classes/subject/" + maMH);
            JsonArray dataArray = JsonParser.parseString(response).getAsJsonArray();
            return !dataArray.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Parse JSON sang MonHoc object
     */
    private MonHoc parseMonHoc(JsonObject json) {
        MonHoc mh = new MonHoc();
        mh.setMaMH(getStringOrNull(json, "maMH"));
        mh.setTenMH(getStringOrNull(json, "tenMH"));
        
        if (json.has("soTinChi") && !json.get("soTinChi").isJsonNull()) {
            mh.setSoTinChi(json.get("soTinChi").getAsInt());
        }
        
        mh.setMaKhoa(getStringOrNull(json, "maKhoa"));
        return mh;
    }
    
    /**
     * Convert MonHoc sang JSON string
     */
    private String toJson(MonHoc mh) {
        return String.format(
            "{\"maMH\":\"%s\",\"tenMH\":\"%s\",\"soTinChi\":%d,\"maKhoa\":\"%s\"}",
            mh.getMaMH(), mh.getTenMH(), mh.getSoTinChi(), mh.getMaKhoa()
        );
    }
    
    private String getStringOrNull(JsonObject json, String key) {
        if (json.has(key) && !json.get(key).isJsonNull()) {
            return json.get(key).getAsString();
        }
        return null;
    }
}
