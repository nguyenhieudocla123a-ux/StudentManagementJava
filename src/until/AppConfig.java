package until;

/**
 * Cấu hình ứng dụng
 * MODE: ONLINE_ONLY - Bắt buộc phải có API Server
 */
public class AppConfig {
    
    // Chế độ hoạt động
    public static final String MODE = "ONLINE_ONLY"; // ONLINE_ONLY hoặc HYBRID
    
    // API Server URL
    // Development: http://localhost:8080/api
    // Production: https://studentmanagementappbackend-2.onrender.com/api
    public static final String API_BASE_URL = System.getenv("API_URL") != null 
        ? System.getenv("API_URL") 
        : "https://studentmanagementappbackend-2.onrender.com/api";
    
    // Timeout settings
    public static final int CONNECTION_TIMEOUT = 10000; // 10 seconds
    public static final int READ_TIMEOUT = 30000; // 30 seconds
    
    // Retry settings
    public static final int MAX_RETRY = 3;
    public static final int RETRY_DELAY = 2000; // 2 seconds
    
    /**
     * Kiểm tra có phải ONLINE_ONLY mode không
     */
    public static boolean isOnlineOnly() {
        return "ONLINE_ONLY".equals(MODE);
    }
    
    /**
     * Kiểm tra có phải HYBRID mode không
     */
    public static boolean isHybrid() {
        return "HYBRID".equals(MODE);
    }
    
    /**
     * Lấy API URL
     */
    public static String getApiUrl() {
        return API_BASE_URL;
    }
}
