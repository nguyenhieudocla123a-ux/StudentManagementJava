# 3.3.4.2 Kiểm thử tương thích chức năng Nhập điểm

## Bảng 3.12 Kiểm thử tương thích chức năng Nhập điểm

| Mã TC | Tên TC | Mục đích | Các bước thực hiện | Đầu vào | Kết quả mong đợi | Tình trạng test | | | Ghi chú / Lý do |
|-------|--------|----------|-------------------|---------|------------------|-----------------|---|---|-----------------|
| | | | | | | **Đạt** | **Không đạt** | **Chưa test** | *(Có thể ghi nhận lý do vì sao Không Test / Chưa Test)* |
| | | | | | **TEST TƯƠNG THÍCH** | | | | |
| **TC01** | Chạy trên Windows 10 và Windows 11 | Đảm bảo ứng dụng chạy bình thường | 1. Cài đặt Java JDK 17<br>2. Cài đặt PostgreSQL<br>3. Khởi động API Server<br>4. Mở ứng dụng Desktop<br>5. Thực hiện nhập điểm | - Windows 10 Pro 64-bit<br>- Windows 11 Pro 64-bit<br>- Java 17<br>- PostgreSQL 14+ | Hoạt động bình thường | X | | | Ứng dụng Java Swing tương thích đa nền tảng Windows |
| **TC02** | Chạy trên màn hình độ phân giải khác nhau | Đảm bảo giao diện hiển thị đúng | 1. Mở ứng dụng trên màn hình 1920x1080<br>2. Mở ứng dụng trên màn hình 1366x768<br>3. Mở ứng dụng trên màn hình 2560x1440<br>4. Kiểm tra form nhập điểm | - Màn hình 1920x1080<br>- Màn hình 1366x768<br>- Màn hình 2560x1440 | Giao diện hiển thị đầy đủ, không bị cắt | X | | | Form nhập điểm responsive với các độ phân giải phổ biến |
| **TC03** | Scale and layout thành 125% hoặc 150% | Đảm bảo ứng dụng chạy bình thường | 1. Vào Settings > Display<br>2. Đặt Scale thành 125%<br>3. Khởi động lại ứng dụng<br>4. Thực hiện nhập điểm<br>5. Lặp lại với Scale 150% | - Windows Display Settings<br>- Scale: 125%, 150%<br>- Nhập điểm cho SV001 | Hoạt động bình thường | X | | | Tùy chỉnh |
| **TC04** | Tương thích với PostgreSQL 14, 15, 16 | Đảm bảo kết nối database | 1. Cài đặt PostgreSQL 14<br>2. Tạo database và chạy migration<br>3. Kết nối và nhập điểm<br>4. Lặp lại với PostgreSQL 15, 16 | - PostgreSQL 14.x<br>- PostgreSQL 15.x<br>- PostgreSQL 16.x<br>- Connection string hợp lệ | Kết nối thành công, nhập điểm OK | X | | | API sử dụng JDBC driver tương thích |
| **TC05** | Tương thích với Java 17 và Java 21 | Đảm bảo ứng dụng chạy trên các phiên bản Java | 1. Cài đặt Java 17<br>2. Biên dịch và chạy ứng dụng<br>3. Thực hiện nhập điểm<br>4. Lặp lại với Java 21 | - Java JDK 17<br>- Java JDK 21<br>- Source code ứng dụng | Biên dịch và chạy thành công | X | | | Ứng dụng target Java 17, tương thích ngược với Java 21 |
| **TC06** | Nhập điểm đồng thời từ nhiều client | Đảm bảo xử lý concurrent requests | 1. Mở 3 ứng dụng Desktop khác nhau<br>2. Đăng nhập 3 tài khoản giảng viên<br>3. Cùng lúc nhập điểm cho các sinh viên khác nhau<br>4. Kiểm tra dữ liệu trong database | - 3 client Desktop<br>- 3 tài khoản GV: GV001, GV002, GV003<br>- Nhập điểm cho SV001, SV002, SV003 | Tất cả điểm được lưu đúng, không bị conflict | X | | | API Server xử lý concurrent với Spring Boot transaction |
| **TC07** | Tương thích với trình duyệt (nếu có Web UI) | Kiểm tra Web UI trên các trình duyệt | 1. Mở Chrome<br>2. Truy cập http://localhost:8080<br>3. Thực hiện nhập điểm qua API<br>4. Lặp lại với Firefox, Edge | - Chrome 120+<br>- Firefox 120+<br>- Edge 120+<br>- API endpoint /grades | API hoạt động bình thường trên mọi trình duyệt | X | | | RESTful API không phụ thuộc trình duyệt |
| **TC08** | Tương thích với các locale khác nhau | Kiểm tra hiển thị tiếng Việt có dấu | 1. Đặt Windows locale thành Vietnamese<br>2. Khởi động ứng dụng<br>3. Nhập điểm với tên có dấu<br>4. Kiểm tra hiển thị | - Windows locale: Vietnamese<br>- Tên sinh viên: "Nguyễn Văn Á"<br>- Xếp loại: "Xuất sắc" | Hiển thị đúng tiếng Việt có dấu | X | | | Database sử dụng UTF-8 encoding |
| **TC09** | Kiểm tra với network latency cao | Đảm bảo ứng dụng xử lý timeout | 1. Sử dụng công cụ giả lập network delay (300ms)<br>2. Thực hiện nhập điểm<br>3. Kiểm tra response time | - Network latency: 300ms<br>- Nhập điểm cho SV001<br>- Timeout setting: 5000ms | Nhập điểm thành công, có thông báo loading | X | | | API Client có timeout handling |
| **TC10** | Tương thích với antivirus software | Đảm bảo ứng dụng không bị block | 1. Cài đặt Windows Defender<br>2. Cài đặt Kaspersky/Avast<br>3. Khởi động ứng dụng<br>4. Thực hiện nhập điểm | - Windows Defender enabled<br>- Antivirus: Kaspersky/Avast<br>- Port 8080 mở | Ứng dụng chạy bình thường, không bị block | X | | | Cần whitelist port 8080 nếu firewall chặn |

---

## Ghi chú:

### Môi trường test:
- **OS:** Windows 10 Pro 64-bit, Windows 11 Pro 64-bit
- **Java:** JDK 17.0.9, JDK 21.0.1
- **Database:** PostgreSQL 14.10, 15.5, 16.1
- **IDE:** IntelliJ IDEA 2024.2
- **API Server:** Spring Boot 3.2.0
- **Desktop App:** Java Swing

### Kết quả tổng hợp:
- **Tổng số test case:** 10
- **Đạt:** 10 ✅
- **Không đạt:** 0
- **Chưa test:** 0

### Các vấn đề phát hiện:
- Không có vấn đề tương thích nghiêm trọng
- Ứng dụng hoạt động ổn định trên các môi trường khác nhau

### Khuyến nghị:
1. Nên test thêm trên Windows Server 2019/2022 nếu triển khai production
2. Cân nhắc thêm test với Docker container
3. Test với PostgreSQL cluster (High Availability)
4. Thêm monitoring cho concurrent access
