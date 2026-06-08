# Hướng dẫn sử dụng CSV Test Data

## Tổng quan
Các file CSV này chứa test data cho Postman Collection để test API Student Management System.

## Các file CSV

### 1. testdata_sinhvien.csv
Test data cho API Sinh Viên (`/api/students`)

**Cấu trúc:**
```csv
tc_id,mo_ta,ma_sv,ho_ten,ten_dang_nhap,gioi_tinh,ngay_sinh,email,so_dien_thoai,dia_chi,ma_khoa,expected_status,expected_success
```

**Các cột:**
- `tc_id`: Mã test case (TC_SV_01, TC_SV_02, ...)
- `mo_ta`: Mô tả test case
- `ma_sv`: Mã sinh viên (bắt buộc)
- `ho_ten`: Họ tên sinh viên (bắt buộc)
- `ten_dang_nhap`: Tên đăng nhập (bắt buộc) - **TRƯỜNG MỚI**
- `gioi_tinh`: Giới tính (Nam/Nữ)
- `ngay_sinh`: Ngày sinh (yyyy-MM-dd)
- `email`: Email
- `so_dien_thoai`: Số điện thoại
- `dia_chi`: Địa chỉ
- `ma_khoa`: Mã khoa (CNTT, KT, QTKD, NN)
- `expected_status`: HTTP status code mong đợi (200, 400, 404)
- `expected_success`: Kết quả mong đợi (true/false)

**Test cases:**
- TC_SV_01: Thêm sinh viên hợp lệ ✅
- TC_SV_02: Mã sinh viên đã tồn tại ❌
- TC_SV_03: Thiếu tên đăng nhập ❌
- TC_SV_04: Tên đăng nhập đã tồn tại ❌
- TC_SV_05: Email không hợp lệ ❌
- TC_SV_06: Ngày sinh sai định dạng ❌
- TC_SV_07: Thiếu thông tin bắt buộc ❌
- TC_SV_08: Cập nhật sinh viên hợp lệ ✅
- TC_SV_09: Lấy sinh viên theo mã ✅
- TC_SV_10: Lấy sinh viên không tồn tại ❌
- TC_SV_11: Xóa sinh viên ✅
- TC_SV_12: Xóa sinh viên không tồn tại ❌

### 2. testdata_giangvien.csv
Test data cho API Giảng Viên (`/api/teachers`)

**Cấu trúc:**
```csv
tc_id,mo_ta,ma_gv,ho_ten,ten_dang_nhap,email,so_dien_thoai,ma_khoa,expected_status,expected_success
```

**Các cột:**
- `tc_id`: Mã test case (TC_GV_01, TC_GV_02, ...)
- `mo_ta`: Mô tả test case
- `ma_gv`: Mã giảng viên (bắt buộc)
- `ho_ten`: Họ tên giảng viên (bắt buộc)
- `ten_dang_nhap`: Tên đăng nhập (bắt buộc) - **TRƯỜNG MỚI**
- `email`: Email
- `so_dien_thoai`: Số điện thoại
- `ma_khoa`: Mã khoa (CNTT, KT, QTKD, NN)
- `expected_status`: HTTP status code mong đợi (200, 400, 404)
- `expected_success`: Kết quả mong đợi (true/false)

**Test cases:**
- TC_GV_01: Thêm giảng viên hợp lệ ✅
- TC_GV_02: Mã giảng viên đã tồn tại ❌
- TC_GV_03: Thiếu tên đăng nhập ❌
- TC_GV_04: Tên đăng nhập đã tồn tại ❌
- TC_GV_05: Email không hợp lệ ❌
- TC_GV_06: Thiếu thông tin bắt buộc ❌
- TC_GV_07: Cập nhật giảng viên hợp lệ ✅
- TC_GV_08: Lấy giảng viên theo mã ✅
- TC_GV_09: Lấy giảng viên không tồn tại ❌
- TC_GV_10: Xóa giảng viên ✅
- TC_GV_11: Xóa giảng viên không tồn tại ❌
- TC_GV_12: Tìm kiếm giảng viên theo tên ✅

### 3. testdata_dangnhap.csv
Test data cho API Đăng nhập (`/api/auth/login`)

**Không thay đổi** - File này test chức năng đăng nhập, không liên quan đến việc tạo sinh viên/giảng viên.

## Thay đổi quan trọng

### ⚠️ Trường mới: `ten_dang_nhap`

Từ phiên bản mới, khi tạo sinh viên hoặc giảng viên, **BẮT BUỘC** phải cung cấp `ten_dang_nhap`.

**Trước đây:**
- Hệ thống tự động dùng mã sinh viên/giảng viên làm username
- Ví dụ: `SV001` → username = `SV001`

**Bây giờ:**
- Admin phải chỉ định username riêng
- Ví dụ: `SV001` → username = `nguyenvana` (do admin nhập)

**Validation:**
- `ten_dang_nhap` không được để trống
- `ten_dang_nhap` không được trùng với username đã tồn tại
- Mật khẩu mặc định vẫn là `123456`

## Cách sử dụng trong Postman

### 1. Import CSV vào Postman Collection Runner

1. Mở Postman
2. Chọn Collection "StudentManagement-AutoTest"
3. Click "Run" để mở Collection Runner
4. Click "Select File" ở phần "Data"
5. Chọn file CSV tương ứng:
   - `testdata_sinhvien.csv` cho test Sinh Viên
   - `testdata_giangvien.csv` cho test Giảng Viên
   - `testdata_dangnhap.csv` cho test Đăng Nhập

### 2. Sử dụng biến trong Request

**Ví dụ POST /api/students:**
```json
{
  "maSV": "{{ma_sv}}",
  "hoTen": "{{ho_ten}}",
  "tenDangNhap": "{{ten_dang_nhap}}",
  "gioiTinh": "{{gioi_tinh}}",
  "ngaySinh": "{{ngay_sinh}}",
  "email": "{{email}}",
  "soDienThoai": "{{so_dien_thoai}}",
  "diaChi": "{{dia_chi}}",
  "maKhoa": "{{ma_khoa}}"
}
```

**Ví dụ POST /api/teachers:**
```json
{
  "maGV": "{{ma_gv}}",
  "hoTen": "{{ho_ten}}",
  "tenDangNhap": "{{ten_dang_nhap}}",
  "email": "{{email}}",
  "soDienThoai": "{{so_dien_thoai}}",
  "maKhoa": "{{ma_khoa}}"
}
```

### 3. Kiểm tra kết quả

Trong Tests tab của Postman request:
```javascript
pm.test("Status code is " + pm.iterationData.get("expected_status"), function () {
    pm.response.to.have.status(pm.iterationData.get("expected_status"));
});

pm.test("Success is " + pm.iterationData.get("expected_success"), function () {
    var jsonData = pm.response.json();
    pm.expect(jsonData.success).to.eql(pm.iterationData.get("expected_success") === "true");
});
```

## Lưu ý

1. **Thứ tự chạy test:** Nên chạy theo thứ tự:
   - Thêm mới (TC_XX_01)
   - Lấy thông tin (TC_XX_08, TC_XX_09)
   - Cập nhật (TC_XX_07, TC_XX_08)
   - Xóa (TC_XX_11, TC_XX_12)

2. **Dọn dẹp data:** Sau khi test xong, nên xóa các bản ghi test để tránh conflict lần chạy sau.

3. **Mật khẩu mặc định:** Tất cả tài khoản được tạo đều có mật khẩu mặc định là `123456`.

4. **Tên đăng nhập:** Nên đặt tên đăng nhập có ý nghĩa, dễ nhớ, không trùng lặp.

## Ví dụ test case thành công

### Thêm sinh viên mới
```csv
TC_SV_01,Them sinh vien hop le,SV101,Nguyen Van Test,nguyenvantest,Nam,2000-01-15,test01@edu.vn,0901234567,Ha Noi,CNTT,200,true
```

**Request:**
```json
POST /api/students
{
  "maSV": "SV101",
  "hoTen": "Nguyen Van Test",
  "tenDangNhap": "nguyenvantest",
  "gioiTinh": "Nam",
  "ngaySinh": "2000-01-15",
  "email": "test01@edu.vn",
  "soDienThoai": "0901234567",
  "diaChi": "Ha Noi",
  "maKhoa": "CNTT"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Thêm sinh viên thành công (tài khoản: nguyenvantest, mật khẩu: 123456)",
  "data": {
    "maSV": "SV101",
    "hoTen": "Nguyen Van Test",
    "tenDangNhap": "nguyenvantest",
    ...
  }
}
```

## Hỗ trợ

Nếu có vấn đề với test data, vui lòng kiểm tra:
1. API Server đang chạy (`http://localhost:8080/api`)
2. Database có dữ liệu khởi tạo
3. Format CSV đúng (UTF-8, comma-separated)
4. Các trường bắt buộc không để trống
