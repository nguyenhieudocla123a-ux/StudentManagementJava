# 🎓 HỆ THỐNG QUẢN LÝ ĐIỂM SINH VIÊN

Hệ thống quản lý điểm sinh viên với kiến trúc Client-Server, sử dụng REST API.

---

## 📋 TỔNG QUAN

- **Desktop App:** Java Swing
- **API Server:** Spring Boot 3.2.0
- **Database:** SQL Server (local) / PostgreSQL (cloud)
- **Kiến trúc:** Full Online Mode - 100% qua REST API

---

## 🌐 PRODUCTION API

**API đã được deploy lên Render.com:**
```
https://studentmanagementappbackend-2.onrender.com/api
```

**Test API:**
```bash
# Health check
curl https://studentmanagementappbackend-2.onrender.com/api/khoa

# Login
curl -X POST https://studentmanagementappbackend-2.onrender.com/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"123456"}'
```

---

## 🚀 CÁCH SỬ DỤNG

### 1. Chạy với Production API (Khuyến nghị)

**Desktop App đã được cấu hình sẵn để kết nối Production API!**

- Chạy từ IDE hoặc JAR file
- Đăng nhập: `admin` / `123456`
- Không cần start local server!

### 2. Chạy Local (Development)

**Bước 1: Start API Server**
```bash
cd api-server
mvn spring-boot:run
```

**Bước 2: Đổi URL trong `src/until/AppConfig.java`**
```java
public static final String API_BASE_URL = "http://localhost:8080/api";
```

**Bước 3: Run Desktop App**
- Chạy từ IDE hoặc JAR file
- Đăng nhập: `admin` / `123456`

---

## 📚 TÀI LIỆU

### Hướng dẫn sử dụng:
- **`QUICK_START.md`** - Bắt đầu nhanh
- **`HUONG_DAN_API_SERVER.md`** - API Server guide
- **`HUONG_DAN_POSTMAN.md`** - Test API với Postman

### Hướng dẫn deploy:
- **`api-server/DEPLOY_RENDER.md`** - Deploy Render (đã deploy ✅)
- **`DEPLOY_RAILWAY_TOM_TAT.md`** - Deploy Railway
- **`HUONG_DAN_DEPLOY_CHI_TIET.md`** - Hướng dẫn chi tiết

### Postman Testing:
- **`StudentManagement-API.postman_collection.json`** - API collection
- **`Postman-Environment-Production.json`** - Production environment
- **`Postman-Environment-Local.json`** - Local environment

### Trạng thái dự án:
- **`CONVERSION_COMPLETE.md`** - Trạng thái chuyển đổi
- **`TICH_HOP_HOAN_TAT.md`** - Tích hợp API hoàn tất
- **`StudentManagement-Local.postman_environment.json`** - Postman environment

### Database:
- **`QLDIEMSV.sql`** - SQL Server schema
- **`migration-postgres.sql`** - PostgreSQL schema (cho cloud)

---

## 🎯 TÍNH NĂNG

### ✅ Đã hoàn thành:
- [x] Đăng nhập (Admin, Giảng viên, Sinh viên)
- [x] Quản lý sinh viên (CRUD)
- [x] Quản lý giảng viên (CRUD)
- [x] Quản lý môn học (CRUD)
- [x] Quản lý lớp học phần (CRUD)
- [x] Quản lý điểm (CRUD)
- [x] Đăng ký lớp học phần
- [x] Quản lý khoa (CRUD)
- [x] REST API Server
- [x] Full Online Mode

---

## 🏗️ KIẾN TRÚC

```
┌─────────────────────────────────────────┐
│  Desktop App (Java Swing)               │
│  - Login.java                           │
│  - AdFrame.java (Admin)                 │
│  - GVFrame.java (Giảng viên)            │
│  - SVFrame.java (Sinh viên)             │
└────────────────┬────────────────────────┘
                 │ HTTP/REST API
                 ↓
┌─────────────────────────────────────────┐
│  API Server (Spring Boot)               │
│  http://localhost:8080/api              │
│                                         │
│  8 Controllers:                         │
│  - AuthController                       │
│  - SinhVienController                   │
│  - GiangVienController                  │
│  - MonHocController                     │
│  - LopHocPhanController                 │
│  - DiemController                       │
│  - DangKyLopController                  │
│  - KhoaController                       │
└────────────────┬────────────────────────┘
                 │ JDBC
                 ↓
┌─────────────────────────────────────────┐
│  Database                               │
│  - SQL Server (local)                   │
│  - PostgreSQL (cloud)                   │
└─────────────────────────────────────────┘
```

---

## 📦 CẤU TRÚC DỰ ÁN

```
student-management/
├── api-server/                 # Spring Boot API Server
│   ├── src/main/java/
│   │   └── com/studentmanagement/
│   │       ├── controller/     # 8 REST Controllers
│   │       ├── entity/         # 8 JPA Entities
│   │       └── repository/     # 8 JPA Repositories
│   ├── src/main/resources/
│   │   ├── application.yml     # Config local
│   │   └── application-prod.yml # Config production
│   └── pom.xml
│
├── src/
│   ├── dao/                    # 8 DAO classes (API integration)
│   ├── model/                  # 8 Model classes
│   ├── until/                  # Utilities
│   │   ├── ApiClient.java      # HTTP client
│   │   ├── JsonParser.java     # JSON parser
│   │   └── AppConfig.java      # Config
│   └── View/                   # GUI (Swing)
│
├── QUICK_START.md              # Bắt đầu nhanh
├── DEPLOY_RAILWAY_TOM_TAT.md   # Deploy Railway
├── HUONG_DAN_DEPLOY_CHI_TIET.md # Deploy chi tiết
├── CONVERSION_COMPLETE.md      # Trạng thái dự án
├── migration-postgres.sql      # PostgreSQL schema
└── README.md                   # File này
```

---

## 🔧 YÊU CẦU HỆ THỐNG

### Development:
- Java 17+
- Maven 3.6+
- SQL Server 2019+ (local)
- IDE: IntelliJ IDEA / Eclipse

### Production:
- Railway/Render/AWS account
- PostgreSQL (provided by cloud)
- GitHub account

---

## 🎓 TÀI KHOẢN MẶC ĐỊNH

| Username | Password | Loại |
|----------|----------|------|
| admin | 123456 | Admin |
| SV001 | 123456 | Sinh viên |
| GV001 | 123456 | Giảng viên |

⚠️ **Nhớ đổi password sau khi deploy!**

---

## 📞 HỖ TRỢ

### Gặp vấn đề?
1. Xem `QUICK_START.md` - Troubleshooting
2. Check console log
3. Test API với Postman

### Muốn deploy?
1. Xem `DEPLOY_RAILWAY_TOM_TAT.md`
2. Follow từng bước
3. Chỉ mất 10-15 phút!

---

## 📊 API ENDPOINTS

### Authentication
- `POST /api/auth/login` - Đăng nhập

### Sinh viên
- `GET /api/students` - Lấy tất cả
- `POST /api/students` - Thêm mới
- `PUT /api/students/{id}` - Cập nhật
- `DELETE /api/students/{id}` - Xóa

### Giảng viên
- `GET /api/teachers` - Lấy tất cả
- `POST /api/teachers` - Thêm mới
- `PUT /api/teachers/{id}` - Cập nhật
- `DELETE /api/teachers/{id}` - Xóa

### Môn học
- `GET /api/subjects` - Lấy tất cả
- `POST /api/subjects` - Thêm mới
- `PUT /api/subjects/{id}` - Cập nhật
- `DELETE /api/subjects/{id}` - Xóa

### Lớp học phần
- `GET /api/classes` - Lấy tất cả

### Điểm
- `GET /api/grades/student/{maSV}` - Lấy theo sinh viên
- `GET /api/grades/class/{maLop}` - Lấy theo lớp
- `POST /api/grades` - Thêm điểm
- `PUT /api/grades/{id}` - Cập nhật
- `DELETE /api/grades/{id}` - Xóa

### Đăng ký lớp
- `GET /api/registrations` - Lấy tất cả
- `GET /api/registrations/student/{maSV}` - Theo sinh viên
- `GET /api/registrations/class/{maLop}` - Theo lớp
- `POST /api/registrations` - Đăng ký
- `DELETE /api/registrations/{id}` - Hủy đăng ký

### Khoa
- `GET /api/khoa` - Lấy tất cả
- `POST /api/khoa` - Thêm mới
- `PUT /api/khoa/{id}` - Cập nhật
- `DELETE /api/khoa/{id}` - Xóa

---

## 🎉 HOÀN TẤT!

Dự án đã sẵn sàng sử dụng và deploy lên cloud!

**Bước tiếp theo:**
1. Xem `QUICK_START.md` để chạy local
2. Xem `DEPLOY_RAILWAY_TOM_TAT.md` để deploy cloud
3. Enjoy! 🚀
