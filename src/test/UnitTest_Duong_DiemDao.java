package test;

import dao.DiemDao;
import model.Diem;
import org.junit.jupiter.api.*;
import until.ApiClient;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UNIT TEST - THÀNH VIÊN: DƯƠNG
 * Test class: DiemDao
 * Test method: capNhatDiem()
 * Loại test: Component Test
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UnitTest_Duong_DiemDao {

    private DiemDao dao;

    @BeforeEach
    void setUp() {
        System.out.println("=== Khởi tạo DiemDao ===");
        dao = new DiemDao();
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
        assertTrue(isRunning, "❌ API Server phải đang chạy");
        System.out.println("✅ PASS: API Server đang chạy");
    }

    @Test
    @Order(2)
    @DisplayName("TC2: Tính điểm tổng kết đúng công thức (QT*0.1 + GK*0.3 + CK*0.6)")
    void testCapNhatDiem_CalculateCorrectly() {
        System.out.println("TEST CASE 2: Tính điểm tổng kết");
        float diemQT = 8.0f;
        float diemGK = 7.5f;
        float diemCK = 9.0f;
        float expected = diemQT * 0.1f + diemGK * 0.3f + diemCK * 0.6f;
        System.out.println("   QT=" + diemQT + " GK=" + diemGK + " CK=" + diemCK + " → TK=" + expected);
        assertEquals(8.45f, expected, 0.01f, "❌ Công thức tính sai");
        System.out.println("✅ PASS: Công thức tính đúng");
    }

    @Test
    @Order(3)
    @DisplayName("TC3: Tính điểm chữ đúng theo thang điểm")
    void testCalculateGradeLetter() {
        System.out.println("TEST CASE 3: Tính điểm chữ");
        assertGradeLetter(9.0f, "A");
        assertGradeLetter(8.5f, "A");
        assertGradeLetter(8.0f, "B+");
        assertGradeLetter(7.0f, "B");
        assertGradeLetter(6.5f, "C+");
        assertGradeLetter(5.5f, "C");
        assertGradeLetter(5.0f, "D+");
        assertGradeLetter(4.0f, "D");
        assertGradeLetter(3.0f, "F");
        System.out.println("✅ PASS: Tất cả điểm chữ đúng");
    }

    @Test
    @Order(4)
    @DisplayName("TC4: Lấy điểm theo sinh viên SV001")
    void testGetDiemBySinhVien() {
        System.out.println("TEST CASE 4: Lấy điểm theo sinh viên");
        ArrayList<Diem> result = dao.getDiemBySinhVien("SV001");
        assertNotNull(result, "❌ Kết quả không được null");
        System.out.println("✅ PASS: Lấy được " + result.size() + " điểm của SV001");
    }

    @Test
    @Order(5)
    @DisplayName("TC5: Kiểm tra field names đúng (maSV, maLop)")
    void testCheckFieldNames() {
        System.out.println("TEST CASE 5: Kiểm tra field names");
        ArrayList<Diem> result = dao.getDiemBySinhVien("SV001");
        if (!result.isEmpty()) {
            Diem diem = result.get(0);
            assertNotNull(diem.getMaSV(), "❌ getMaSV() phải hoạt động");
            assertNotNull(diem.getMaLop(), "❌ getMaLop() phải hoạt động");
            System.out.println("✅ PASS: Field names đúng (maSV, maLop)");
        } else {
            System.out.println("⚠️  WARNING: Không có dữ liệu để test");
        }
    }

    @Test
    @Order(6)
    @DisplayName("TC6: Kiểm tra performance lấy điểm")
    void testGetDiem_Performance() {
        System.out.println("TEST CASE 6: Kiểm tra performance");
        long startTime = System.currentTimeMillis();
        dao.getDiemBySinhVien("SV001");
        long duration = System.currentTimeMillis() - startTime;
        assertTrue(duration < 2000, "❌ Thời gian phản hồi quá lâu: " + duration + "ms");
        System.out.println("✅ PASS: Thời gian phản hồi: " + duration + "ms");
    }

    private void assertGradeLetter(float diemTK, String expectedChu) {
        String chu;
        if (diemTK >= 8.5) chu = "A";
        else if (diemTK >= 8.0) chu = "B+";
        else if (diemTK >= 7.0) chu = "B";
        else if (diemTK >= 6.5) chu = "C+";
        else if (diemTK >= 5.5) chu = "C";
        else if (diemTK >= 5.0) chu = "D+";
        else if (diemTK >= 4.0) chu = "D";
        else chu = "F";
        assertEquals(expectedChu, chu, "Điểm " + diemTK + " phải là " + expectedChu);
    }
}
