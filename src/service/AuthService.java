package service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.TaiKhoan;
import until.ApiClient;

/**
 * Service xử lý Authentication - thay thế TaiKhoanDao cho login/logout
 */
public class AuthService {
    
    private String lastErrorMessage = "";
    
    /**
     * Đăng nhập - thay thế TaiKhoanDao.kiemTraDangNhap()
     * Parse đầy đủ các trường maSV/maGV/maQTV/hoTen từ response backend
     */
    public TaiKhoan kiemTraDangNhap(String username, String password) {
        try {
            String jsonBody = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);
            String response = ApiClient.post("/auth/login", jsonBody);
            
            JsonObject json = JsonParser.parseString(response).getAsJsonObject();
            if (json.get("success").getAsBoolean()) {
                String token = json.get("token").getAsString();
                ApiClient.setJwtToken(token);
                
                TaiKhoan tk = new TaiKhoan();
                tk.setTenDangNhap(json.get("tenDangNhap").getAsString());
                tk.setLoaiNguoiDung(json.get("loaiNguoiDung").getAsString());
                tk.setOnlineStatus(json.get("onlineStatus").getAsString());
                tk.setMatKhau(password); // Giữ password trong session
                
                // Parse thông tin bổ sung theo loại người dùng
                if (json.has("hoTen") && !json.get("hoTen").isJsonNull()) {
                    tk.setHoTen(json.get("hoTen").getAsString());
                }
                if (json.has("maSV") && !json.get("maSV").isJsonNull()) {
                    tk.setMaSV(json.get("maSV").getAsString());
                }
                if (json.has("maGV") && !json.get("maGV").isJsonNull()) {
                    tk.setMaGV(json.get("maGV").getAsString());
                }
                if (json.has("maQTV") && !json.get("maQTV").isJsonNull()) {
                    tk.setMaQTV(json.get("maQTV").getAsString());
                }
                
                lastErrorMessage = "";
                System.out.println("✅ Login thành công: " + username 
                    + " | Role: " + tk.getLoaiNguoiDung()
                    + " | HoTen: " + tk.getHoTen());
                return tk;
            } else {
                lastErrorMessage = json.has("message") 
                    ? json.get("message").getAsString() 
                    : "Tên đăng nhập hoặc mật khẩu không đúng";
                return null;
            }
        } catch (Exception e) {
            lastErrorMessage = "Không thể kết nối đến server: " + e.getMessage();
            System.err.println("❌ Login error: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Lấy thông báo lỗi cuối cùng
     */
    public String getLastErrorMessage() {
        return lastErrorMessage;
    }
    
    /**
     * Kiểm tra tài khoản có đang online không
     * Note: API hiện tại chưa có endpoint này, trả về false tạm thời
     */
    public boolean kiemTraTaiKhoanDangOnline(String username) {
        // TODO: Có thể implement endpoint GET /auth/status/{username} ở backend
        return false;
    }
    
    /**
     * Cập nhật trạng thái online
     * Note: Đã được xử lý trong API login
     */
    public void capNhatTrangThaiOnline(String username) {
        // Đã được xử lý tự động trong API login
    }
    
    /**
     * Cập nhật trạng thái offline - gọi khi người dùng đăng xuất
     * @return true nếu thành công, false nếu có lỗi
     */
    public boolean capNhatTrangThaiOffline(String username) {
        try {
            String jsonBody = String.format("{\"username\":\"%s\"}", username);
            String response = ApiClient.post("/auth/logout", jsonBody);
            if (response != null && !response.isEmpty()) {
                JsonObject json = JsonParser.parseString(response).getAsJsonObject();
                boolean success = json.has("success") && json.get("success").getAsBoolean();
                if (success) {
                    System.out.println("✅ Đăng xuất thành công: " + username);
                } else {
                    System.err.println("⚠️ Server trả lỗi khi logout: " + username);
                }
                return success;
            }
            return false;
        } catch (Exception e) {
            System.err.println("❌ Lỗi cập nhật trạng thái offline: " + e.getMessage());
            return false;
        }
    }
}
