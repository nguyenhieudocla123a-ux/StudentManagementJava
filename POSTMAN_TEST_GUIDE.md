# 📋 Hướng Dẫn Test API với Postman

## 🚀 API Production URL
```
https://studentmanagementappbackend-3.onrender.com/api
```

## 📁 Import vào Postman

### Bước 1: Import Collection
1. Mở **Postman**
2. Click **Import**
3. Chọn file: `StudentManagement-API-Render.postman_collection.json`

### Bước 2: Import Environment
1. Trong Postman, chuyển đến tab **Environments**
2. Click **Import**
3. Chọn file: `Render-Production.postman_environment.json`
4. Chọn environment **"Render Production"** ở góc trên phải

## 🔐 Authentication Flow

### 1. Login đầu tiên
**Endpoint:** `POST /auth/login`

**Body:**
```json
{
  "username": "admin",
  "password": "admin123"
}
```

**Kết quả mong đợi:**
```json
{
  "success": true,
  "message": "Đăng nhập thành công",
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "tenDangNhap": "admin",
  "loaiNguoiDung": "Admin",
  "onlineStatus": "Online"
}
```

> ⚠️ **Lưu ý:** JWT token sẽ tự động được lưu vào biến `jwt_token` để sử dụng cho các request tiếp theo.

### 2. Sử dụng JWT Token
Tất cả các endpoint khác yêu cầu **Authorization header:**
```
Authorization: Bearer {{jwt_token}}
```

## 📊 Test Cases

### ✅ Authentication Tests
1. **Login thành công** - admin/admin123
2. **Login thất bại** - sai username/password
3. **Register tài khoản mới**
4. **Logout**

### ✅ Student Management Tests (Cần JWT)
1. **Get All Students** - `GET /students`
2. **Get Students Paged** - `GET /students/paged?page=0&size=10`
3. **Search Students** - `GET /students/search?keyword=Nguyen`
4. **Get Student by ID** - `GET /students/SV001`
5. **Create Student** - `POST /students` (Admin only)
6. **Update Student** - `PUT /students/{id}` (Admin only)
7. **Delete Student** - `DELETE /students/{id}` (Admin only)

### ✅ Account Management Tests (Admin only)
1. **Get All Accounts** - `GET /accounts`
2. **Get Account by Username** - `GET /accounts/admin`
3. **Update Account** - `PUT /accounts/{username}`
4. **Delete Account** - `DELETE /accounts/{username}`

## 🎯 Quy trình Test Đề xuất

### 1. Test cơ bản (5 phút)
1. **Login** với admin/admin123
2. **Get All Students** 
3. **Get All Accounts**
4. **Logout**

### 2. Test đầy đủ (15 phút)
1. Chạy tất cả requests trong collection theo thứ tự
2. Kiểm tra response times (≤ 30 giây do free tier)
3. Verify response structure và status codes
4. Test error cases (invalid data, unauthorized access)

## ⏱️ Performance Notes

**Render Free Tier có những đặc điểm:**
- **Cold Start:** 60-120 giây cho lần đầu tiên sau thời gian ngủ
- **Auto-Sleep:** Sau 15 phút không hoạt động
- **Response Time:** 2-10 giây bình thường, 30+ giây khi cold start

## 🐛 Troubleshooting

### Lỗi thường gặp:

#### 1. Timeout (30+ giây)
**Nguyên nhân:** Cold start  
**Giải pháp:** Đợi và thử lại

#### 2. 401 Unauthorized
**Nguyên nhân:** JWT token expired hoặc sai  
**Giải pháp:** Login lại để lấy token mới

#### 3. 403 Forbidden
**Nguyên nhân:** Không đủ quyền (cần Admin role)  
**Giải pháp:** Đảm bảo đăng nhập bằng admin account

#### 4. 404 Not Found
**Nguyên nhân:** Endpoint sai hoặc resource không tồn tại  
**Giải pháp:** Kiểm tra URL và method

## 🔍 API Endpoints Summary

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| POST | `/auth/login` | ❌ | Đăng nhập |
| POST | `/auth/register` | ❌ | Đăng ký |
| POST | `/auth/logout` | ✅ | Đăng xuất |
| GET | `/students` | ✅ | Danh sách sinh viên |
| GET | `/students/paged` | ✅ | Sinh viên phân trang |
| GET | `/students/{id}` | ✅ | Chi tiết sinh viên |
| GET | `/students/search` | ✅ | Tìm kiếm sinh viên |
| POST | `/students` | 👑 | Tạo sinh viên (Admin) |
| PUT | `/students/{id}` | 👑 | Sửa sinh viên (Admin) |
| DELETE | `/students/{id}` | 👑 | Xóa sinh viên (Admin) |
| GET | `/accounts` | 👑 | Danh sách tài khoản (Admin) |
| GET | `/accounts/{username}` | 👑 | Chi tiết tài khoản (Admin) |
| PUT | `/accounts/{username}` | 👑 | Sửa tài khoản (Admin) |
| DELETE | `/accounts/{username}` | 👑 | Xóa tài khoản (Admin) |

**Legend:**
- ❌ Public (no auth required)
- ✅ JWT required (any role)
- 👑 Admin only

## 🎉 Quick Test

Muốn test nhanh? Chạy lệnh này trong terminal:

```bash
curl -X POST https://studentmanagementappbackend-3.onrender.com/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "admin123"}'
```

Hoặc sử dụng test script:
```bash
node test-api-render.js
```

## 📞 Support

Nếu gặp vấn đề:
1. Kiểm tra logs trong Postman Console
2. Verify JWT token có hợp lệ không
3. Đảm bảo đang sử dụng đúng environment
4. Check server logs trên Render dashboard