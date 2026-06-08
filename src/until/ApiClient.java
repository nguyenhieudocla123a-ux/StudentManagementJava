package until;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * HTTP Client để gọi REST API
 * Sử dụng HttpURLConnection - không cần thêm thư viện ngoài
 */
public class ApiClient {
        private static final String BASE_URL = AppConfig.getApiUrl();
    private static final java.nio.file.Path TOKEN_PATH = java.nio.file.Paths.get(System.getProperty("user.home"), ".duanjava_token");
    private static String jwtToken = null;

    static {
        // Load token from file if exists
        try {
            if (java.nio.file.Files.exists(TOKEN_PATH)) {
                jwtToken = java.nio.file.Files.readString(TOKEN_PATH).trim();
            }
        } catch (Exception e) {
            // ignore – token will be null
        }
    }

    public static void setJwtToken(String token) {
        jwtToken = token;
        // Persist token to file
        try {
            java.nio.file.Files.writeString(TOKEN_PATH, token, java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception e) {
            // ignore persistence errors
        }
    }

    public static String getJwtToken() {
        return jwtToken;
    }

    public static boolean hasToken() {
        return jwtToken != null && !jwtToken.isEmpty();
    }

    public static void clearJwtToken() {
        jwtToken = null;
        try {
            java.nio.file.Files.deleteIfExists(TOKEN_PATH);
        } catch (Exception e) {
            // ignore
        }
    }
    
    /**
     * Thêm header Authorization nếu có token
     */
    private static void setAuthHeader(HttpURLConnection conn) {
        if (jwtToken != null && !jwtToken.isEmpty()) {
            conn.setRequestProperty("Authorization", "Bearer " + jwtToken);
        }
    }
    
    /**
     * Gửi GET request
     * @param endpoint Đường dẫn API (vd: "/api/students", "/api/teachers/GV001")
     * @return JSON response dạng String
     */
    public static String get(String endpoint) throws IOException {
        URL url = new URL(BASE_URL + endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        setAuthHeader(conn);
        
        return readResponse(conn);
    }
    
    /**
     * Gửi POST request
     * @param endpoint Đường dẫn API
     * @param jsonBody Dữ liệu JSON dạng String
     * @return JSON response dạng String
     */
    public static String post(String endpoint, String jsonBody) throws IOException {
        URL url = new URL(BASE_URL + endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        setAuthHeader(conn);
        conn.setDoOutput(true);
        
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        
        return readResponse(conn);
    }
    
    /**
     * Gửi PUT request
     * @param endpoint Đường dẫn API
     * @param jsonBody Dữ liệu JSON dạng String
     * @return JSON response dạng String
     */
    public static String put(String endpoint, String jsonBody) throws IOException {
        URL url = new URL(BASE_URL + endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        setAuthHeader(conn);
        conn.setDoOutput(true);
        
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        
        return readResponse(conn);
    }
    
    /**
     * Gửi DELETE request
     * @param endpoint Đường dẫn API
     * @return JSON response dạng String
     */
    public static String delete(String endpoint) throws IOException {
        URL url = new URL(BASE_URL + endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("DELETE");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        setAuthHeader(conn);
        
        return readResponse(conn);
    }
    
    /**
     * Đọc response từ connection
     */
    private static String readResponse(HttpURLConnection conn) throws IOException {
        int responseCode = conn.getResponseCode();
        InputStream is = (responseCode < 400) ? conn.getInputStream() : conn.getErrorStream();
        
        // errorStream có thể null nếu server không trả về body (vd: 404 không có body)
        if (is == null) {
            String errorMsg = "{\"success\":false,\"message\":\"HTTP " + responseCode + "\"}";
            System.err.println("❌ Response is null. Code: " + responseCode);
            return errorMsg;
        }
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            String result = response.toString();
            
            // DEBUG: In ra response để xem
            System.out.println("📥 HTTP " + responseCode + " Response: " + result);
            
            return result;
        }
    }
    
    /**
     * Kiểm tra server có đang chạy không
     */
    public static boolean isServerRunning() {
        try {
            get("/api/students");
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    /**
     * Lấy BASE_URL
     */
    public static String getBaseUrl() {
        return BASE_URL;
    }
    
    /**
     * Test connection
     */
    public static void main(String[] args) {
        System.out.println("Testing API connection...");
        
        if (isServerRunning()) {
            System.out.println("✓ Server is running!");
            
            try {
                String response = get("/api/students");
                System.out.println("Response: " + response);
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }
        } else {
            System.err.println("✗ Server is not running!");
            System.err.println("Please start the API server first:");
            System.err.println("  cd api-server");
            System.err.println("  start-server.bat");
        }
    }
}
