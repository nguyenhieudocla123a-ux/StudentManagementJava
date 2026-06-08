package service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Khoa;
import until.ApiClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Service xử lý Khoa - thay thế KhoaDao
 */
public class KhoaService {
    
    public List<Khoa> getAllKhoa() {
        try {
            String response = ApiClient.get("/khoa");
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            
            if (json.get("success").getAsBoolean()) {
                JsonArray dataArray = json.getAsJsonArray("data");
                List<Khoa> list = new ArrayList<>();
                for (JsonElement element : dataArray) {
                    list.add(parseKhoa(element.getAsJsonObject()));
                }
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    
    public Khoa getKhoaById(String maKhoa) {
        try {
            String response = ApiClient.get("/khoa/" + maKhoa);
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            
            if (json.get("success").getAsBoolean()) {
                return parseKhoa(json.getAsJsonObject("data"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean themKhoa(Khoa khoa) {
        try {
            String jsonBody = toJson(khoa);
            String response = ApiClient.post("/khoa", jsonBody);
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            return json.get("success").getAsBoolean();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean capNhatKhoa(Khoa khoa) {
        try {
            String jsonBody = toJson(khoa);
            String response = ApiClient.put("/khoa/" + khoa.getMaKhoa(), jsonBody);
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            return json.get("success").getAsBoolean();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean xoaKhoa(String maKhoa) {
        try {
            String response = ApiClient.delete("/khoa/" + maKhoa);
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            return json.get("success").getAsBoolean();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private Khoa parseKhoa(JsonObject json) {
        Khoa khoa = new Khoa();
        khoa.setMaKhoa(getStringOrNull(json, "maKhoa"));
        khoa.setTenKhoa(getStringOrNull(json, "tenKhoa"));
        return khoa;
    }
    
    private String toJson(Khoa khoa) {
        return String.format("{\"maKhoa\":\"%s\",\"tenKhoa\":\"%s\"}", 
            khoa.getMaKhoa(), khoa.getTenKhoa());
    }
    
    private String getStringOrNull(JsonObject json, String key) {
        if (json.has(key) && !json.get(key).isJsonNull()) {
            return json.get(key).getAsString();
        }
        return null;
    }
}
