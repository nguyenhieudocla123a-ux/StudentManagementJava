package until;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import dao.SinhvienDao;
import dao.DiemDao;
import model.SinhVien;
import model.Diem;

import java.util.Properties;
import java.util.List;

public class Mail {

    private static final String username = "nguyenhieudocla1234a@gmail.com";
    private static final String password = "eosl thrm fitd weqr"; // app password
    
    public static void send(String to, String messageText) throws Exception {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(username));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        msg.setSubject("Thông báo");
        msg.setText(messageText);
        Transport.send(msg);
        System.out.println("Gửi mail thành công tới: " + to);
    }

    /**
     * Gửi email với subject tùy chỉnh
     */
    public static void sendWithSubject(String to, String subject, String messageText) throws Exception {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(username));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        msg.setSubject(subject);
        msg.setText(messageText);
        Transport.send(msg);
        System.out.println("Gửi mail thành công tới: " + to);
    }

    /**
     * Gửi email kết quả học tập cho một sinh viên
     */
    public static void sendResultToStudent(String maSV, String maLop, String tenMonHoc, 
                                         double diemQT, double diemGK, double diemCK, 
                                         double diemTK, String diemChu, String xepLoai) throws Exception {
        SinhvienDao svDao = new SinhvienDao();
        SinhVien sv = svDao.getSinhVienById(maSV);
        
        if (sv == null || sv.getEmail() == null || sv.getEmail().trim().isEmpty()) {
            throw new Exception("Không tìm thấy email của sinh viên " + maSV);
        }

        String subject = "KẾT QUẢ HỌC TẬP - " + tenMonHoc;
        String content = createResultEmailContent(sv.getHoTen(), maSV, maLop, tenMonHoc, 
                                                diemQT, diemGK, diemCK, diemTK, diemChu, xepLoai);
        
        sendWithSubject(sv.getEmail(), subject, content);
    }

    /**
     * Gửi email kết quả cho tất cả sinh viên trong lớp
     */
    public static int sendResultToAllStudentsInClass(String maLop) {
        DiemDao diemDao = new DiemDao();
        SinhvienDao svDao = new SinhvienDao();
        int successCount = 0;
        int totalCount = 0;

        try {
            List<Diem> danhSachDiem = diemDao.getDiemByMaLop(maLop);
            totalCount = danhSachDiem.size();

            for (Diem diem : danhSachDiem) {
                try {
                    SinhVien sv = svDao.getSinhVienById(diem.getMaSV());
                    if (sv != null && sv.getEmail() != null && !sv.getEmail().trim().isEmpty()) {
                        
                        String tenMonHoc = diemDao.getTenMonHocByMaLop(maLop);
                        
                        sendResultToStudent(diem.getMaSV(), maLop, tenMonHoc,
                                          diem.getDiemQuaTrinh(), diem.getDiemGiuaKy(), 
                                          diem.getDiemCuoiKy(), diem.getDiemTongKet(),
                                          diem.getDiemChu(), diem.getXepLoai());
                        
                        successCount++;
                        
                        // Delay nhỏ để tránh spam
                        Thread.sleep(1000);
                    } else {
                        System.out.println("Sinh viên " + diem.getMaSV() + " không có email");
                    }
                } catch (Exception e) {
                    System.err.println("Lỗi gửi email cho sinh viên " + diem.getMaSV() + ": " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy danh sách điểm: " + e.getMessage());
        }

        System.out.println("Đã gửi thành công " + successCount + "/" + totalCount + " email");
        return successCount;
    }

    /**
     * Tạo nội dung email kết quả học tập
     */
    private static String createResultEmailContent(String hoTen, String maSV, String maLop, 
                                                 String tenMonHoc, double diemQT, double diemGK, 
                                                 double diemCK, double diemTK, String diemChu, String xepLoai) {
        StringBuilder content = new StringBuilder();
        content.append("Kính gửi sinh viên ").append(hoTen).append(",\n\n");
        content.append("Trường gửi đến bạn kết quả học tập môn học như sau:\n\n");
        content.append("═══════════════════════════════════════\n");
        content.append("📚 THÔNG TIN MÔN HỌC\n");
        content.append("═══════════════════════════════════════\n");
        content.append("• Mã sinh viên: ").append(maSV).append("\n");
        content.append("• Họ tên: ").append(hoTen).append("\n");
        content.append("• Mã lớp: ").append(maLop).append("\n");
        content.append("• Tên môn học: ").append(tenMonHoc).append("\n\n");
        
        content.append("═══════════════════════════════════════\n");
        content.append("📊 KẾT QUẢ HỌC TẬP\n");
        content.append("═══════════════════════════════════════\n");
        content.append("• Điểm quá trình: ").append(String.format("%.1f", diemQT)).append("\n");
        content.append("• Điểm giữa kỳ: ").append(String.format("%.1f", diemGK)).append("\n");
        content.append("• Điểm cuối kỳ: ").append(String.format("%.1f", diemCK)).append("\n");
        content.append("• Điểm tổng kết: ").append(String.format("%.1f", diemTK)).append("\n");
        content.append("• Điểm chữ: ").append(diemChu).append("\n");
        content.append("• Xếp loại: ").append(xepLoai).append("\n\n");
        
        // Thêm lời khuyên dựa trên kết quả
        if (diemTK >= 8.5) {
            content.append("🎉 Chúc mừng! Bạn đã đạt kết quả xuất sắc. Hãy tiếp tục duy trì!\n\n");
        } else if (diemTK >= 7.0) {
            content.append("👏 Kết quả tốt! Bạn có thể cải thiện thêm ở các môn tiếp theo.\n\n");
        } else if (diemTK >= 5.5) {
            content.append("📚 Bạn cần cố gắng hơn ở các môn học tiếp theo.\n\n");
        } else {
            content.append("⚠️ Kết quả chưa đạt yêu cầu. Vui lòng liên hệ giảng viên để được hỗ trợ.\n\n");
        }
        
        content.append("═══════════════════════════════════════\n");
        content.append("Nếu có thắc mắc, vui lòng liên hệ:\n");
        content.append("📧 Email: phongdaotao@truong.edu.vn\n");
        content.append("📞 Điện thoại: (024) 1234-5678\n\n");
        content.append("Trân trọng,\n");
        content.append("Phòng Đào tạo\n");
        content.append("Trường Đại học ABC");
        
        return content.toString();
    }
}