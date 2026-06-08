# Student Management API Server

REST API Server cho hệ thống quản lý điểm sinh viên.

## Công nghệ

- **Spring Boot 3.2.0**
- **Java 17**
- **SQL Server**
- **Spring Data JPA**
- **Lombok**

## Cấu hình

Database: SQL Server
- Host: localhost:1433
- Database: QuanLyDiemSinhVien
- Username: sa
- Password: 123456789

## Chạy server

```bash
cd api-server
mvn spring-boot:run
```

Server sẽ chạy tại: `http://localhost:8080/api`

## API Endpoints

### Authentication
- `POST /api/auth/login` - Đăng nhập

### Khoa (Departments)
- `GET /api/khoa` - Lấy danh sách khoa
- `GET /api/khoa/{id}` - Lấy thông tin khoa
- `POST /api/khoa` - Thêm khoa mới
- `PUT /api/khoa/{id}` - Cập nhật khoa
- `DELETE /api/khoa/{id}` - Xóa khoa

### Students (Sinh viên)
- `GET /api/students` - Lấy danh sách sinh viên
- `GET /api/students/{id}` - Lấy thông tin sinh viên
- `GET /api/students/search?keyword=` - Tìm kiếm sinh viên
- `POST /api/students` - Thêm sinh viên mới
- `PUT /api/students/{id}` - Cập nhật sinh viên
- `DELETE /api/students/{id}` - Xóa sinh viên

### Teachers (Giảng viên)
- `GET /api/teachers` - Lấy danh sách giảng viên
- `GET /api/teachers/{id}` - Lấy thông tin giảng viên
- `GET /api/teachers/search?keyword=` - Tìm kiếm giảng viên
- `POST /api/teachers` - Thêm giảng viên mới
- `PUT /api/teachers/{id}` - Cập nhật giảng viên
- `DELETE /api/teachers/{id}` - Xóa giảng viên

### Subjects (Môn học)
- `GET /api/subjects` - Lấy danh sách môn học
- `GET /api/subjects/{id}` - Lấy thông tin môn học
- `GET /api/subjects/search?keyword=` - Tìm kiếm môn học
- `POST /api/subjects` - Thêm môn học mới
- `PUT /api/subjects/{id}` - Cập nhật môn học
- `DELETE /api/subjects/{id}` - Xóa môn học

### Classes (Lớp học phần)
- `GET /api/classes` - Lấy danh sách lớp học phần
- `GET /api/classes/{id}` - Lấy thông tin lớp học phần
- `GET /api/classes/teacher/{teacherId}` - Lấy lớp theo giảng viên
- `GET /api/classes/subject/{subjectId}` - Lấy lớp theo môn học
- `GET /api/classes/semester?hocKy=&namHoc=` - Lấy lớp theo học kỳ
- `POST /api/classes` - Thêm lớp học phần mới
- `PUT /api/classes/{id}` - Cập nhật lớp học phần
- `DELETE /api/classes/{id}` - Xóa lớp học phần

### Grades (Điểm)
- `GET /api/grades` - Lấy danh sách điểm
- `GET /api/grades/{id}` - Lấy thông tin điểm
- `GET /api/grades/student/{studentId}` - Lấy điểm theo sinh viên
- `GET /api/grades/class/{classId}` - Lấy điểm theo lớp
- `POST /api/grades` - Thêm điểm mới
- `PUT /api/grades/{id}` - Cập nhật điểm
- `DELETE /api/grades/{id}` - Xóa điểm

### Registrations (Đăng ký lớp)
- `GET /api/registrations` - Lấy danh sách đăng ký
- `GET /api/registrations/{id}` - Lấy thông tin đăng ký
- `GET /api/registrations/student/{studentId}` - Lấy đăng ký theo sinh viên
- `GET /api/registrations/class/{classId}` - Lấy đăng ký theo lớp
- `POST /api/registrations` - Đăng ký lớp học phần
- `DELETE /api/registrations/{id}` - Hủy đăng ký

## Ví dụ sử dụng

### Đăng nhập
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

### Lấy danh sách sinh viên
```bash
curl http://localhost:8080/api/students
```

### Thêm sinh viên mới
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{
    "maSinhVien":"SV006",
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
  -d '{
    "maSinhVien":"SV001",
    "maLopHocPhan":"LHP001"
  }'
```

## Cấu trúc project

```
api-server/
├── src/main/java/com/studentmanagement/
│   ├── entity/          # JPA Entities
│   ├── repository/      # Spring Data Repositories
│   ├── controller/      # REST Controllers
│   └── ApiServerApplication.java
├── src/main/resources/
│   └── application.yml  # Configuration
├── pom.xml
└── README.md
```

## Lưu ý

- Tất cả API đều hỗ trợ CORS (`@CrossOrigin(origins = "*")`)
- Điểm tổng kết được tính tự động: `0.1*CC + 0.3*GK + 0.6*CK`
- Đăng ký lớp tự động set ngày đăng ký và trạng thái
- API trả về JSON với format: `{success, message, data}`
