package service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.TaiKhoan;
import until.ApiClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Service xử lý Tài khoản - thay thế TaiKhoanDao
 */
public class TaiKhoanService {
    
    /**
     * Lấy tất cả tài khoản
     * Note: Backend có thể chưa có endpoint này, cần implement
     */
    public List<TaiKhoan> getAllTaiKhoan() {
        try {
            // TODO: Cần tạo endpoint GET /accounts ở backend
            String response = ApiClient.get("/accounts");
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            
            if (json.get("success").getAsBoolean()) {
                JsonArray dataArray = json.getAsJsonArray("data");
                List<TaiKhoan> list = new ArrayList<>();
                for (JsonElement element : dataArray) {
                    list.add(parseTaiKhoan(element.getAsJsonObject()));
                }
                return list;
            }
        } catch (Exception e) {
            System.err.println("Lỗi getAllTaiKhoan: " + e.getMessage());
        }
        return new ArrayList<>();
    }
    
    /**
     * Lấy tài khoản theo username
     */
    public TaiKhoan getTaiKhoanById(String tenDangNhap) {
        try {
            String response = ApiClient.get("/accounts/" + tenDangNhap);
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            
            if (json.get("success").getAsBoolean()) {
                return parseTaiKhoan(json.getAsJsonObject("data"));
            }
        } catch (Exception e) {
            System.err.println("Lỗi getTaiKhoanById: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Tạo tài khoản mới
     */
    public boolean themTaiKhoan(TaiKhoan tk) {
        try {
            String jsonBody = toJson(tk);
            String response = ApiClient.post("/accounts", jsonBody);
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            return json.get("success").getAsBoolean();
        } catch (Exception e) {
            System.err.println("Lỗi themTaiKhoan: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Cập nhật tài khoản
     */
    public boolean capNhatTaiKhoan(TaiKhoan tk) {
        try {
            String jsonBody = toJson(tk);
            String response = ApiClient.put("/accounts/" + tk.getTenDangNhap(), jsonBody);
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            return json.get("success").getAsBoolean();
        } catch (Exception e) {
            System.err.println("Lỗi capNhatTaiKhoan: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Xóa tài khoản
     */
    public boolean xoaTaiKhoan(String tenDangNhap) {
        try {
            String response = ApiClient.delete("/accounts/" + tenDangNhap);
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            return json.get("success").getAsBoolean();
        } catch (Exception e) {
            System.err.println("Lỗi xoaTaiKhoan: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Kiểm tra tên đăng nhập đã tồn tại chưa
     */
    public boolean kiemTraTenDangNhapTonTai(String username) {
        try {
            String response = ApiClient.get("/accounts/" + username);
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            // Nếu tìm thấy tài khoản -> tồn tại
            return json.get("success").getAsBoolean();
        } catch (Exception e) {
            // Lỗi hoặc không tìm thấy -> không tồn tại
            return false;
        }
    }
    
    private TaiKhoan parseTaiKhoan(JsonObject json) {
        TaiKhoan tk = new TaiKhoan();
        tk.setTenDangNhap(getStringOrNull(json, "tenDangNhap"));
        tk.setMatKhau(getStringOrNull(json, "matKhau"));
        tk.setLoaiNguoiDung(getStringOrNull(json, "loaiNguoiDung"));
        tk.setOnlineStatus(getStringOrNull(json, "onlineStatus"));
        return tk;
    }
    
    private String toJson(TaiKhoan tk) {
        return String.format(
            "{\"tenDangNhap\":\"%s\",\"matKhau\":\"%s\",\"loaiNguoiDung\":\"%s\"}",
            tk.getTenDangNhap(), tk.getMatKhau(), tk.getLoaiNguoiDung()
        );
    }
    
    private String getStringOrNull(JsonObject json, String key) {
        if (json.has(key) && !json.get(key).isJsonNull()) {
            return json.get(key).getAsString();
        }
        return null;
    }
}
