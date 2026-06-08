# 🚀 Hướng dẫn Deploy lên Render.com

## Bước 1: Tạo PostgreSQL Database trên Render

1. Truy cập https://render.com và đăng nhập
2. Click **"New +"** → chọn **"PostgreSQL"**
3. Cấu hình database:
   - **Name**: `student-management-db`
   - **Database**: `studentdb`
   - **User**: `studentdb_user` (tự động tạo)
   - **Region**: `Singapore` hoặc `Frankfurt` (gần VN)
   - **Instance Type**: `Free` (cho test) hoặc `Starter` (production)
4. Click **"Create Database"**
5. **LƯU LẠI** thông tin sau (trong tab "Info"):
   - `Internal Database URL` (dùng để connect từ web service)
   - `External Database URL` (dùng để connect từ local)
   - `Username` và `Password`

---

## Bước 2: Tạo Database Schema trên PostgreSQL

### Cách 1: Dùng psql (command line)
```bash
# Install PostgreSQL client nếu chưa có
# Windows: Download từ postgresql.org
# Mac: brew install postgresql
# Linux: sudo apt install postgresql-client

# Connect vào database (dùng External Database URL)
psql "postgresql://studentdb_user:YOUR_PASSWORD@HOST:PORT/studentdb"

# Chạy các lệnh SQL từ file migration-postgres.sql
\i migration-postgres.sql
```

### Cách 2: Dùng pgAdmin (GUI)
1. Download pgAdmin: https://www.pgadmin.org/download/
2. Kết nối database bằng thông tin từ Render
3. Mở Query Tool và chạy nội dung file `migration-postgres.sql`

### Cách 3: Dùng Web Shell của Render
1. Vào database trong Render dashboard
2. Tab "Shell" → Connect
3. Copy nội dung file SQL và paste vào
4. Chạy từng khối lệnh (CREATE TABLE, INSERT data test)

---

## Bước 3: Deploy Web Service

1. Trong Render Dashboard, click **"New +"** → **"Web Service"**
2. **Connect Repository:**
   - Chọn **"Build and deploy from a Git repository"**
   - Click **"Connect GitHub"** → Authorize
   - Chọn repo: `StudentManagementAppBackend`
3. **Cấu hình Web Service:**
   ```
   Name: student-management-api
   Region: Singapore (hoặc Frankfurt)
   Branch: main
   Root Directory: (để trống - vì code ở root)
   Runtime: Java
   Build Command: ./build.sh
   Start Command: ./start.sh
   ```
4. **Instance Type:**
   - Free (512 MB RAM, auto-sleep sau 15 phút không dùng)
   - Starter $7/tháng (512 MB RAM, không sleep)

---

## Bước 4: Cấu hình Environment Variables

Trong tab **"Environment"**, thêm các biến môi trường:

### Required Variables:
```
SPRING_PROFILES_ACTIVE=prod
DATABASE_URL=<Internal_Database_URL_from_Step_1>
DB_USERNAME=studentdb_user
DB_PASSWORD=<Password_from_Step_1>
PORT=8080
```

### Optional Variables (tuning performance):
```
JAVA_TOOL_OPTIONS=-Xmx512m -Xms256m
MAVEN_OPTS=-Xmx512m
```

**Ví dụ DATABASE_URL:**
```
postgresql://studentdb_user:abc123xyz@dpg-xxxxx-a.singapore-postgres.render.com/studentdb
```

---

## Bước 5: Deploy!

1. Click **"Create Web Service"**
2. Render sẽ tự động:
   - Clone repo từ GitHub
   - Chạy `mvn clean install`
   - Build JAR file
   - Start Spring Boot app
3. Xem logs trong tab **"Logs"** để theo dõi quá trình deploy
4. Khi thấy `Tomcat started on port 8080`, app đã sẵn sàng!

---

## Bước 6: Kiểm tra API

URL của bạn sẽ có dạng:
```
https://student-management-api-xxxx.onrender.com
```

### Test với curl:
```bash
# Health check
curl https://student-management-api-xxxx.onrender.com/api/auth/login

# Login test
curl -X POST https://student-management-api-xxxx.onrender.com/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"123456"}'
```

### Test với Postman:
1. Import collection từ `StudentManagement-API.postman_collection.json`
2. Tạo Environment mới tên "Render Production"
3. Thêm variable:
   ```
   baseUrl = https://student-management-api-xxxx.onrender.com/api
   ```
4. Chạy tests!

---

## 🔧 Troubleshooting

### Lỗi: "Application failed to start"
**Nguyên nhân:** Không kết nối được database

**Giải pháp:**
1. Kiểm tra `DATABASE_URL` đúng format chưa
2. Kiểm tra database đã chạy chưa (trong Render dashboard)
3. Xem logs chi tiết: Tab "Logs" → tìm exception

### Lỗi: "Out of memory"
**Nguyên nhân:** Free tier chỉ có 512 MB RAM

**Giải pháp:**
```bash
# Thêm vào Environment Variables:
JAVA_TOOL_OPTIONS=-Xmx400m -Xms200m
```

### Lỗi: "Table doesn't exist"
**Nguyên nhân:** Chưa chạy migration SQL

**Giải pháp:**
- Vào database Shell trong Render
- Chạy lại file `migration-postgres.sql`

### App sleep sau 15 phút (Free tier)
**Nguyên nhân:** Đây là giới hạn của Free plan

**Giải pháp:**
- Upgrade lên Starter ($7/tháng)
- Hoặc dùng uptime monitor (cron-job.org) để ping app 10 phút/lần

---

## 📊 Giám sát & Bảo trì

### Xem Logs:
```
Render Dashboard → Your Service → Logs
```

### Restart Service:
```
Render Dashboard → Your Service → Manual Deploy → Deploy latest commit
```

### Rollback:
```
Render Dashboard → Your Service → Events → Deploy earlier version
```

### Metrics:
```
Render Dashboard → Your Service → Metrics
```
- CPU usage
- Memory usage
- Response time
- HTTP status codes

---

## 🎯 Checklist Deploy

- [ ] Database PostgreSQL đã tạo và chạy
- [ ] Schema đã được tạo (migration-postgres.sql)
- [ ] Web Service đã được tạo và connect đúng repo
- [ ] Environment variables đã được set đầy đủ
- [ ] Build thành công (xem logs)
- [ ] App start thành công (xem logs "Tomcat started")
- [ ] Test API login thành công
- [ ] Update baseUrl trong Desktop App (nếu có)

---

## 💰 Chi phí ước tính

### Free Tier:
- PostgreSQL: Free (1 GB storage, limited connections)
- Web Service: Free (auto-sleep sau 15 phút)
- **Tổng: $0/tháng** ✅

### Production Tier:
- PostgreSQL Starter: $7/tháng (10 GB storage)
- Web Service Starter: $7/tháng (no sleep)
- **Tổng: $14/tháng**

---

## 📚 Tài liệu tham khảo

- Render Docs: https://render.com/docs
- Spring Boot on Render: https://render.com/docs/deploy-spring-boot
- PostgreSQL on Render: https://render.com/docs/databases

---

## 🔐 Bảo mật

**⚠️ LƯU Ý:** Sau khi deploy, nhớ:
1. Đổi password cho tài khoản `admin`
2. Thay đổi `JWT_SECRET` trong code (hiện đang hard-code)
3. Không commit file chứa password/secret vào Git
4. Dùng Environment Variables cho tất cả thông tin nhạy cảm

---

**Chúc bạn deploy thành công! 🚀**
