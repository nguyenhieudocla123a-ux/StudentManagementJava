# Student Management API Server

REST API Server cho hệ thống quản lý điểm sinh viên.

## Công nghệ

- **Spring Boot 3.2.0**
- **Java 17**
- **SQL Server / PostgreSQL**
- **Spring Data JPA**
- **Spring Security + JWT**
- **Lombok**

## Cấu hình

### Database (Development - SQL Server)
- Host: localhost:1433
- Database: QuanLyDiemSinhVien_V2
- Username: sa
- Password: 123456789

### Database (Production - PostgreSQL)
Configure via environment variables for cloud deployment

## Chạy server

```bash
mvn spring-boot:run
```

Server sẽ chạy tại: `http://localhost:8080/api`

## API Endpoints

### Authentication
- `POST /api/auth/login` - Đăng nhập (trả về JWT token)
- `POST /api/auth/register` - Đăng ký tài khoản
- `POST /api/auth/logout` - Đăng xuất

### Khoa (Departments)
- `GET /api/khoa` - Lấy danh sách khoa
- `GET /api/khoa/{id}` - Lấy thông tin khoa
- `POST /api/khoa` - Thêm khoa mới
- `PUT /api/khoa/{id}` - Cập nhật khoa
- `DELETE /api/khoa/{id}` - Xóa khoa

### Students (Sinh viên)
- `GET /api/students` - Lấy danh sách sinh viên
- `GET /api/students/paged?page=0&size=10` - Phân trang
- `GET /api/students/{id}` - Lấy thông tin sinh viên
- `GET /api/students/search?keyword=` - Tìm kiếm sinh viên
- `POST /api/students` - Thêm sinh viên mới (Admin only)
- `PUT /api/students/{id}` - Cập nhật sinh viên (Admin only)
- `DELETE /api/students/{id}` - Xóa sinh viên (Admin only)

### Teachers (Giảng viên)
- `GET /api/teachers` - Lấy danh sách giảng viên
- `GET /api/teachers/paged?page=0&size=10` - Phân trang
- `GET /api/teachers/{id}` - Lấy thông tin giảng viên
- `GET /api/teachers/search?keyword=` - Tìm kiếm giảng viên
- `POST /api/teachers` - Thêm giảng viên mới (Admin only)
- `PUT /api/teachers/{id}` - Cập nhật giảng viên (Admin only)
- `DELETE /api/teachers/{id}` - Xóa giảng viên (Admin only)

### Subjects (Môn học)
- `GET /api/subjects` - Lấy danh sách môn học
- `GET /api/subjects/{id}` - Lấy thông tin môn học
- `GET /api/subjects/search?keyword=` - Tìm kiếm môn học
- `POST /api/subjects` - Thêm môn học mới (Admin only)
- `PUT /api/subjects/{id}` - Cập nhật môn học (Admin only)
- `DELETE /api/subjects/{id}` - Xóa môn học (Admin only)

### Classes (Lớp học phần)
- `GET /api/classes` - Lấy danh sách lớp học phần
- `GET /api/classes/{id}` - Lấy thông tin lớp học phần
- `GET /api/classes/teacher/{teacherId}` - Lấy lớp theo giảng viên
- `GET /api/classes/subject/{subjectId}` - Lấy lớp theo môn học
- `GET /api/classes/semester?hocKy=&namHoc=` - Lấy lớp theo học kỳ
- `GET /api/classes/open` - Lấy lớp đang mở đăng ký
- `POST /api/classes` - Thêm lớp học phần mới (Admin only)
- `PUT /api/classes/{id}` - Cập nhật lớp học phần (Admin only)
- `PUT /api/classes/{id}/deadline` - Cập nhật deadline đăng ký
- `DELETE /api/classes/{id}` - Xóa lớp học phần (Admin only)

### Grades (Điểm)
- `GET /api/grades` - Lấy danh sách điểm
- `GET /api/grades/paged?page=0&size=10` - Phân trang
- `GET /api/grades/{id}` - Lấy thông tin điểm
- `GET /api/grades/student/{studentId}` - Lấy điểm theo sinh viên
- `GET /api/grades/class/{classId}` - Lấy điểm theo lớp
- `POST /api/grades` - Thêm điểm mới (GiangVien/Admin)
- `PUT /api/grades/{id}` - Cập nhật điểm (GiangVien/Admin)
- `DELETE /api/grades/{id}` - Xóa điểm (GiangVien/Admin)

### Registrations (Đăng ký lớp)
- `GET /api/registrations` - Lấy danh sách đăng ký
- `GET /api/registrations/student/{studentId}` - Lấy đăng ký theo sinh viên
- `GET /api/registrations/class/{classId}` - Lấy đăng ký theo lớp
- `POST /api/registrations` - Đăng ký lớp học phần (kiểm tra deadline, sĩ số)
- `DELETE /api/registrations/{maSV}/{maLop}` - Hủy đăng ký

## Ví dụ sử dụng

### Đăng nhập
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"123456"}'
```

Response:
```json
{
  "success": true,
  "message": "Đăng nhập thành công",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "userInfo": {
      "tenDangNhap": "admin",
      "loaiNguoiDung": "Admin",
      "hoTen": "Administrator"
    }
  }
}
```

### Lấy danh sách sinh viên (cần JWT token)
```bash
curl http://localhost:8080/api/students \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Thêm sinh viên mới
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "maSV":"SV006",
    "hoTen":"Nguyễn Văn A",
    "ngaySinh":"2000-01-01",
    "gioiTinh":"Nam",
    "diaChi":"Hà Nội",
    "soDienThoai":"0123456789",
    "email":"nva@email.com",
    "maKhoa":"CNTT",
    "tenDangNhap":"SV006"
  }'
```

### Đăng ký lớp học phần
```bash
curl -X POST http://localhost:8080/api/registrations \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "maSV":"SV001",
    "maLop":"LHP001"
  }'
```

## Cấu trúc project

```
api-server/
├── src/main/java/com/studentmanagement/
│   ├── entity/          # JPA Entities (9 entities)
│   ├── repository/      # Spring Data Repositories
│   ├── service/         # Business Logic Layer
│   ├── controller/      # REST Controllers (9 controllers)
│   ├── dto/             # Request/Response DTOs
│   ├── security/        # JWT + Spring Security
│   ├── exception/       # Global Exception Handler
│   └── ApiServerApplication.java
├── src/main/resources/
│   └── application.yml  # Configuration
├── pom.xml
└── README.md
```

## Tính năng Security

- **JWT Authentication** - Token hết hạn sau 24 giờ
- **Role-based Access Control** - Admin, GiangVien, SinhVien
- **BCrypt Password Encoding** - Mã hóa mật khẩu an toàn
- **CORS Enabled** - Hỗ trợ cross-origin requests
- **Method-level Security** - @PreAuthorize trên từng endpoint

## Business Logic đặc biệt

### Tính điểm tự động:
- **Điểm tổng kết** = (Điểm QT + Điểm GK + Điểm CK × 2) / 4
- **Điểm chữ** tự động (A+, A, B+, B, C+, C, D+, D, F)
- **Xếp loại** tự động (Xuất sắc, Giỏi, Khá, Trung bình, Yếu, Kém)

### Đăng ký lớp:
- Kiểm tra deadline (thoiGianMoDangKy, thoiGianDongDangKy)
- Kiểm tra sĩ số (siSoHienTai < siSoToiDa)
- Kiểm tra trạng thái lớp (phải là "mo")
- Tự động tăng/giảm sĩ số khi đăng ký/hủy

## Response Format

Tất cả API trả về format chuẩn:
```json
{
  "code": 200,
  "success": true,
  "message": "Thành công",
  "data": {...},
  "timestamp": "2024-01-01T10:00:00"
}
```

## Test Accounts

- **Admin**: `admin / 123456`
- **Student**: `SV001 / 123456`
- **Teacher**: `GV001 / 123456`

## Deploy

Xem hướng dẫn deploy lên Railway/Render tại repo chính.
