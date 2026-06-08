package test;

import until.ApiClient;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

/**
 * Demo gọi API với Admin token
 */
public class TestAdminApi {
    
    public static void main(String[] args) {
        // Token từ login
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiQWRtaW4iLCJzdWIiOiJhZG1pbiIsImlhdCI6MTc4MDY0OTQxNiwiZXhwIjoxNzgwNzM1ODE2fQ.UOxGdH7HcrABM44haT8n5lGrBpxYa_d_1X5mbDy8buc";
        
        System.out.println("🔐 Admin API Test");
        System.out.println("════════════════════════════════════════\n");
        
        // Lưu token
        ApiClient.setJwtToken(token);
        System.out.println("✓ Token saved: " + token.substring(0, 30) + "...\n");
        
        // Test 1: Lấy danh sách sinh viên
        testGetStudents();
        
        // Test 2: Lấy danh sách giáo viên
        testGetTeachers();
        
        // Test 3: Lấy danh sách khoa
        testGetKhoa();
        
        // Test 4: Lấy danh sách môn học
        testGetSubjects();
        
        // Test 5: Lấy danh sách lớp HP
        testGetClasses();
    }
    
    private static void testGetStudents() {
        System.out.println("📌 1. GET /students - Danh sách sinh viên");
        try {
            String response = ApiClient.get("/students");
            JsonParser parser = new JsonParser();
            JsonArray array = parser.parse(response).getAsJsonArray();
            System.out.println("✅ Thành công! Có " + array.size() + " sinh viên");
            if (array.size() > 0) {
                JsonObject sv = array.get(0).getAsJsonObject();
                System.out.println("   Sinh viên đầu tiên: " + sv.get("hoTen"));
            }
        } catch (Exception e) {
            System.out.println("❌ Lỗi: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testGetTeachers() {
        System.out.println("📌 2. GET /teachers - Danh sách giáo viên");
        try {
            String response = ApiClient.get("/teachers");
            JsonParser parser = new JsonParser();
            JsonArray array = parser.parse(response).getAsJsonArray();
            System.out.println("✅ Thành công! Có " + array.size() + " giáo viên");
            if (array.size() > 0) {
                JsonObject gv = array.get(0).getAsJsonObject();
                System.out.println("   Giáo viên đầu tiên: " + gv.get("hoTen"));
            }
        } catch (Exception e) {
            System.out.println("❌ Lỗi: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testGetKhoa() {
        System.out.println("📌 3. GET /khoa - Danh sách khoa");
        try {
            String response = ApiClient.get("/khoa");
            JsonParser parser = new JsonParser();
            JsonObject obj = parser.parse(response).getAsJsonObject();
            if (obj.has("data")) {
                JsonArray array = obj.getAsJsonArray("data");
                System.out.println("✅ Thành công! Có " + array.size() + " khoa");
                if (array.size() > 0) {
                    JsonObject khoa = array.get(0).getAsJsonObject();
                    System.out.println("   Khoa đầu tiên: " + khoa.get("tenKhoa"));
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Lỗi: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testGetSubjects() {
        System.out.println("📌 4. GET /subjects - Danh sách môn học");
        try {
            String response = ApiClient.get("/subjects");
            JsonParser parser = new JsonParser();
            JsonArray array = parser.parse(response).getAsJsonArray();
            System.out.println("✅ Thành công! Có " + array.size() + " môn học");
            if (array.size() > 0) {
                JsonObject mh = array.get(0).getAsJsonObject();
                System.out.println("   Môn học đầu tiên: " + mh.get("tenMH"));
            }
        } catch (Exception e) {
            System.out.println("❌ Lỗi: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testGetClasses() {
        System.out.println("📌 5. GET /classes - Danh sách lớp HP");
        try {
            String response = ApiClient.get("/classes");
            JsonParser parser = new JsonParser();
            JsonArray array = parser.parse(response).getAsJsonArray();
            System.out.println("✅ Thành công! Có " + array.size() + " lớp HP");
            if (array.size() > 0) {
                JsonObject lop = array.get(0).getAsJsonObject();
                System.out.println("   Lớp HP đầu tiên: " + lop.get("maLop"));
            }
        } catch (Exception e) {
            System.out.println("❌ Lỗi: " + e.getMessage());
        }
        System.out.println();
    }
}
