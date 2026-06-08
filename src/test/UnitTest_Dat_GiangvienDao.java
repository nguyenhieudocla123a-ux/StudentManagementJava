package test;

import dao.GiangvienDao;
import model.GiangVien;
import org.junit.jupiter.api.*;
import until.ApiClient;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UNIT TEST - THÀNH VIÊN: ĐẠT
 * Test class: GiangvienDao
 * Test method: getAllGiangVien()
 * Loại test: Component Test
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UnitTest_Dat_GiangvienDao {

    private GiangvienDao dao;

    @BeforeEach
    void setUp() {
        System.out.println("=== Khởi tạo GiangvienDao ===");
        dao = new GiangvienDao();
    }

    @AfterEach
    void tearDown() {
        System.out.println("=== Kết thúc test ===\n");
    }

    @Test
    @Order(1)
    @DisplayName("TC1: Kiểm tra API Server đang chạy")
    void testApiServerIsRunning() {
        System.out.println("TEST CASE 1: Kiểm tra API Server");
        boolean isRunning = ApiClient.isServerRunning();
        assertTrue(isRunning, "❌ API Server phải đang chạy tại http://localhost:8080");
        System.out.println("✅ PASS: API Server đang chạy");
    }

    @Test
    @Order(2)
    @DisplayName("TC2: getAllGiangVien() trả về danh sách không rỗng")
    void testGetAllGiangVien_ReturnsNonEmptyList() {
        System.out.println("TEST CASE 2: Lấy danh sách giảng viên");
        ArrayList<GiangVien> result = dao.getAllGiangVien();
        assertNotNull(result, "❌ Kết quả không được null");
        assertFalse(result.isEmpty(), "❌ Danh sách không được rỗng");
        assertTrue(result.size() >= 5, "❌ Phải có ít nhất 5 giảng viên");
        System.out.println("✅ PASS: Lấy được " + result.size() + " giảng viên");
    }

    @Test
    @Order(3)
    @DisplayName("TC3: Kiểm tra cấu trúc dữ liệu GiangVien")
    void testGetAllGiangVien_CheckDataStructure() {
        System.out.println("TEST CASE 3: Kiểm tra cấu trúc dữ liệu");
        ArrayList<GiangVien> result = dao.getAllGiangVien();
        assertFalse(result.isEmpty(), "Danh sách không được rỗng");
        GiangVien firstGV = result.get(0);
        assertNotNull(firstGV.getMaGV(), "❌ Mã GV không được null");
        assertNotNull(firstGV.getHoTen(), "❌ Họ tên không được null");
        assertNotNull(firstGV.getEmail(), "❌ Email không được null");
        assertNotNull(firstGV.getMaKhoa(), "❌ Mã khoa không được null");
        assertTrue(firstGV.getMaGV().startsWith("GV"), "❌ Mã GV phải bắt đầu bằng 'GV'");
        assertTrue(firstGV.getEmail().contains("@"), "❌ Email phải chứa ký tự @");
        System.out.println("✅ PASS: Cấu trúc dữ liệu đúng");
    }

    @Test
    @Order(4)
    @DisplayName("TC4: Kiểm tra field names đã được sửa đúng")
    void testGetAllGiangVien_CheckFieldNames() {
        System.out.println("TEST CASE 4: Kiểm tra field names");
        ArrayList<GiangVien> result = dao.getAllGiangVien();
        assertFalse(result.isEmpty());
        GiangVien gv = result.get(0);
        String maGV = gv.getMaGV();
        assertNotNull(maGV, "❌ getMaGV() phải trả về giá trị");
        assertTrue(maGV.matches("GV\\d{3}"), "❌ Mã GV phải có format GVxxx, nhưng là: " + maGV);
        System.out.println("✅ PASS: Field names đúng (maGV)");
    }

    @Test
    @Order(5)
    @DisplayName("TC5: Tất cả giảng viên có đầy đủ thông tin")
    void testGetAllGiangVien_AllHaveCompleteInfo() {
        System.out.println("TEST CASE 5: Kiểm tra tất cả giảng viên");
        ArrayList<GiangVien> result = dao.getAllGiangVien();
        int count = 0;
        for (GiangVien gv : result) {
            assertNotNull(gv.getMaGV(), "Giảng viên thứ " + count + " thiếu mã GV");
            assertNotNull(gv.getHoTen(), "Giảng viên thứ " + count + " thiếu họ tên");
            assertNotNull(gv.getEmail(), "Giảng viên thứ " + count + " thiếu email");
            assertFalse(gv.getMaGV().isEmpty(), "Mã GV không được rỗng");
            assertFalse(gv.getHoTen().isEmpty(), "Họ tên không được rỗng");
            count++;
        }
        System.out.println("✅ PASS: Tất cả " + count + " giảng viên có đầy đủ thông tin");
    }

    @Test
    @Order(6)
    @DisplayName("TC6: Kiểm tra performance")
    void testGetAllGiangVien_Performance() {
        System.out.println("TEST CASE 6: Kiểm tra performance");
        long startTime = System.currentTimeMillis();
        ArrayList<GiangVien> result = dao.getAllGiangVien();
        long duration = System.currentTimeMillis() - startTime;
        assertTrue(duration < 2000, "❌ Thời gian phản hồi quá lâu: " + duration + "ms");
        System.out.println("✅ PASS: Thời gian phản hồi: " + duration + "ms");
    }

    @Test
    @Order(7)
    @DisplayName("TC7: Kiểm tra dữ liệu mẫu GV001 tồn tại")
    void testGetAllGiangVien_SampleDataExists() {
        System.out.println("TEST CASE 7: Kiểm tra dữ liệu mẫu");
        ArrayList<GiangVien> result = dao.getAllGiangVien();
        boolean hasGV001 = result.stream().anyMatch(gv -> "GV001".equals(gv.getMaGV()));
        assertTrue(hasGV001, "❌ Phải có giảng viên mẫu GV001");
        GiangVien gv001 = result.stream().filter(gv -> "GV001".equals(gv.getMaGV())).findFirst().orElse(null);
        assertNotNull(gv001);
        assertNotNull(gv001.getMaKhoa(), "GV001 phải có mã khoa");
        System.out.println("✅ PASS: Dữ liệu mẫu tồn tại - GV001: " + gv001.getHoTen());
    }
}
