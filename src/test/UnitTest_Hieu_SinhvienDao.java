package test;

import dao.SinhvienDao;
import model.SinhVien;
import org.junit.jupiter.api.*;
import until.ApiClient;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UNIT TEST - THÀNH VIÊN: HIẾU
 * Test class: SinhvienDao
 * Test method: themSinhVien()
 * Loại test: Component Test
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UnitTest_Hieu_SinhvienDao {

    private SinhvienDao dao;
    private static final String TEST_MA_SV = "SV999";
    private static final String TEST_TEN_DANG_NHAP = "sv999test";

    @BeforeEach
    void setUp() {
        System.out.println("=== Khởi tạo SinhvienDao ===");
        dao = new SinhvienDao();
        
        // Tạo tài khoản test trước (giả lập admin đã tạo tài khoản)
        createTestAccount(TEST_TEN_DANG_NHAP);
        
        // Xóa sinh viên test cũ (nếu có) nhưng GIỮ LẠI tài khoản
        try { dao.xoaSinhVien(TEST_MA_SV); } catch (Exception e) { }
    }

    @AfterEach
    void tearDown() {
        // Xóa sinh viên test nhưng GIỮ LẠI tài khoản để test khác dùng
        try { dao.xoaSinhVien(TEST_MA_SV); System.out.println("🧹 Đã xóa dữ liệu test"); } catch (Exception e) { }
        System.out.println("=== Kết thúc test ===\n");
    }
    
    // Helper method: Tạo tài khoản test (giả lập admin tạo tài khoản trước)
    private void createTestAccount(String tenDangNhap) {
        try {
            String jsonBody = String.format(
                "{\"tenDangNhap\":\"%s\",\"matKhau\":\"123456\",\"loaiNguoiDung\":\"SinhVien\"}",
                tenDangNhap
            );
            ApiClient.post("/auth/register", jsonBody);
            System.out.println("✓ Đã tạo tài khoản test: " + tenDangNhap);
        } catch (Exception e) {
            // Tài khoản đã tồn tại, bỏ qua
            System.out.println("ℹ Tài khoản test đã tồn tại: " + tenDangNhap);
        }
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
    @DisplayName("TC2: Thêm sinh viên mới thành công")
    void testThemSinhVien_Success() {
        System.out.println("TEST CASE 2: Thêm sinh viên mới");
        SinhVien sv = createTestSinhVien();
        boolean result = dao.themSinhVien(sv);
        assertTrue(result, "❌ Thêm sinh viên phải thành công");
        System.out.println("✅ PASS: Thêm sinh viên thành công - Mã: " + sv.getMaSV());
    }

    @Test
    @Order(3)
    @DisplayName("TC3: Verify sinh viên tồn tại trong database")
    void testThemSinhVien_VerifyInDatabase() {
        System.out.println("TEST CASE 3: Verify trong database");
        dao.themSinhVien(createTestSinhVien());
        SinhVien saved = dao.getSinhVienById(TEST_MA_SV);
        assertNotNull(saved, "❌ Sinh viên phải tồn tại trong database");
        assertEquals(TEST_MA_SV, saved.getMaSV(), "❌ Mã SV không khớp");
        assertEquals("Nguyễn Văn Test", saved.getHoTen(), "❌ Họ tên không khớp");
        assertEquals("CNTT", saved.getMaKhoa(), "❌ Mã khoa không khớp");
        System.out.println("✅ PASS: Dữ liệu trong database đúng");
    }

    @Test
    @Order(4)
    @DisplayName("TC4: Thêm sinh viên trùng mã phải thất bại")
    void testThemSinhVien_DuplicateKey_ShouldFail() {
        System.out.println("TEST CASE 4: Test thêm mã trùng");
        dao.themSinhVien(createTestSinhVien());
        SinhVien sv2 = createTestSinhVien();
        sv2.setHoTen("Người khác");
        assertThrows(RuntimeException.class, () -> dao.themSinhVien(sv2), "❌ Phải throw exception khi thêm mã trùng");
        System.out.println("✅ PASS: Exception được throw khi thêm mã trùng");
    }

    @Test
    @Order(5)
    @DisplayName("TC5: Kiểm tra field names đã sửa đúng (maSV)")
    void testThemSinhVien_CheckFieldNames() {
        System.out.println("TEST CASE 5: Kiểm tra field names");
        dao.themSinhVien(createTestSinhVien());
        SinhVien saved = dao.getSinhVienById(TEST_MA_SV);
        assertNotNull(saved);
        String maSV = saved.getMaSV();
        assertNotNull(maSV, "❌ getMaSV() phải trả về giá trị");
        assertEquals(TEST_MA_SV, maSV, "❌ Mã SV không khớp");
        System.out.println("✅ PASS: Field names đúng (maSV)");
    }

    @Test
    @Order(6)
    @DisplayName("TC6: Kiểm tra performance thêm sinh viên")
    void testThemSinhVien_Performance() {
        System.out.println("TEST CASE 6: Kiểm tra performance");
        long startTime = System.currentTimeMillis();
        dao.themSinhVien(createTestSinhVien());
        long duration = System.currentTimeMillis() - startTime;
        assertTrue(duration < 2000, "❌ Thời gian phản hồi quá lâu: " + duration + "ms");
        System.out.println("✅ PASS: Thời gian phản hồi: " + duration + "ms");
    }

    @Test
    @Order(7)
    @DisplayName("TC7: Thêm nhiều sinh viên liên tiếp")
    void testThemSinhVien_Multiple() {
        System.out.println("TEST CASE 7: Thêm nhiều sinh viên");
        String[] maSVs = {"SV997", "SV998", "SV999"};
        String[] tenDangNhaps = {"sv997test", "sv998test", "sv999test"};
        
        try {
            // Tạo tài khoản test trước
            for (String tenDangNhap : tenDangNhaps) {
                createTestAccount(tenDangNhap);
            }
            
            // Thêm sinh viên
            for (int i = 0; i < maSVs.length; i++) {
                SinhVien sv = new SinhVien();
                sv.setMaSV(maSVs[i]);
                sv.setHoTen("Test " + maSVs[i]);
                sv.setTenDangNhap(tenDangNhaps[i]);
                sv.setGioiTinh("Nam");
                sv.setNgaySinh("2000-01-01");
                sv.setEmail(tenDangNhaps[i] + "@student.edu");
                sv.setSoDienThoai("0123456789");
                sv.setDiaChi("Test Address");
                sv.setMaKhoa("CNTT");
                assertTrue(dao.themSinhVien(sv), "Thêm " + maSVs[i] + " thất bại");
            }
            System.out.println("✅ PASS: Thêm được 3 sinh viên liên tiếp");
        } finally {
            // Xóa sinh viên nhưng GIỮ LẠI tài khoản
            for (String maSV : maSVs) {
                try { dao.xoaSinhVien(maSV); } catch (Exception e) { }
            }
        }
    }

    private SinhVien createTestSinhVien() {
        SinhVien sv = new SinhVien();
        sv.setMaSV(TEST_MA_SV);
        sv.setHoTen("Nguyễn Văn Test");
        sv.setTenDangNhap(TEST_TEN_DANG_NHAP); // Tên đăng nhập: svtest999
        sv.setGioiTinh("Nam");
        sv.setNgaySinh("2000-01-01");
        sv.setEmail("test@student.edu");
        sv.setSoDienThoai("0123456789");
        sv.setDiaChi("123 Test Street");
        sv.setMaKhoa("CNTT");
        return sv;
    }
}
