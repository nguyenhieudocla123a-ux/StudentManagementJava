package View;

import service.AuthService;
import model.TaiKhoan;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame implements ActionListener {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnExit;
    private TaiKhoan taiKhoan;
    private AuthService authService;

    private final Color PRIMARY_COLOR = new Color(25, 118, 210);
    private final Color ACCENT_COLOR = new Color(94, 53, 177);
    private final Color BG_GRADIENT_START = new Color(30, 136, 229);
    private final Color BG_GRADIENT_END = new Color(94, 53, 177);
    private final Color CARD_COLOR = Color.WHITE;
    private final Color TEXT_COLOR = new Color(33, 33, 33);
    private final Color BORDER_COLOR = new Color(224, 224, 224);
    private final Color SUCCESS_COLOR = new Color(56, 142, 60);
    private final Color DANGER_COLOR = new Color(211, 47, 47);
    private final Color INPUT_BG = new Color(250, 250, 250);

    public Login() {
        setTitle("Hệ thống Quản lý Tình hình Học tập của Sinh viên");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
    }
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JPanel leftPanel = createLeftPanel();
        JPanel rightPanel = createRightPanel();

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, BG_GRADIENT_START, 0, getHeight(), BG_GRADIENT_END);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        leftPanel.setPreferredSize(new Dimension(450, 600));
        leftPanel.setLayout(new GridBagLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);

        JLabel iconLabel = new JLabel("🎓");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 80));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        iconLabel.setForeground(Color.WHITE);

        JLabel titleLabel = new JLabel("HỆ THỐNG QUẢN LÝ");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("TÌNH HÌNH HỌC TẬP CỦA SINH VIÊN");
        subtitleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descLabel = new JLabel("Quản lý điểm, lớp học và sinh viên");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        descLabel.setForeground(new Color(255, 255, 255, 200));
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(iconLabel);
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(subtitleLabel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(descLabel);

        leftPanel.add(contentPanel);
        return leftPanel;
    }

    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(new EmptyBorder(40, 60, 40, 60));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setMaximumSize(new Dimension(400, 500));

        JLabel welcomeLabel = new JLabel("Đăng nhập");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        welcomeLabel.setForeground(TEXT_COLOR);
        welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subLabel = new JLabel("Vui lòng nhập thông tin để tiếp tục");
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subLabel.setForeground(new Color(117, 117, 117));
        subLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        formPanel.add(welcomeLabel);
        formPanel.add(Box.createVerticalStrut(8));
        formPanel.add(subLabel);
        formPanel.add(Box.createVerticalStrut(40));

        JLabel lblUser = new JLabel("Tên đăng nhập");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblUser.setForeground(TEXT_COLOR);
        lblUser.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtUsername.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 2),
                new EmptyBorder(14, 16, 14, 16)
        ));
        txtUsername.setBackground(INPUT_BG);
        txtUsername.setMaximumSize(new Dimension(400, 50));
        txtUsername.setAlignmentX(Component.LEFT_ALIGNMENT);

        formPanel.add(lblUser);
        formPanel.add(Box.createVerticalStrut(8));
        formPanel.add(txtUsername);
        formPanel.add(Box.createVerticalStrut(25));

        JLabel lblPass = new JLabel("Mật khẩu");
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblPass.setForeground(TEXT_COLOR);
        lblPass.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 2),
                new EmptyBorder(14, 16, 14, 16)
        ));
        txtPassword.setBackground(INPUT_BG);
        txtPassword.setMaximumSize(new Dimension(400, 50));
        txtPassword.setAlignmentX(Component.LEFT_ALIGNMENT);

        formPanel.add(lblPass);
        formPanel.add(Box.createVerticalStrut(8));
        formPanel.add(txtPassword);
        formPanel.add(Box.createVerticalStrut(40));

        btnLogin = createButton("Đăng nhập", PRIMARY_COLOR, 400, 50);
        btnLogin.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(btnLogin);

        formPanel.add(Box.createVerticalStrut(12));

        btnExit = createButton("Thoát", new Color(158, 158, 158), 400, 50);
        btnExit.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(btnExit);

        rightPanel.add(formPanel);
        return rightPanel;
    }

    private JButton createButton(String text, Color color, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setMaximumSize(new Dimension(width, height));
        button.setPreferredSize(new Dimension(width, height));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(this);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == btnLogin) {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu!",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Disable button để tránh click nhiều lần
            btnLogin.setEnabled(false);
            btnLogin.setText("Đang đăng nhập...");
            
            // Chạy trong background thread
            new Thread(() -> {
                try {
                    System.out.println("🔄 Bắt đầu đăng nhập: " + username);
                    
                    authService = new AuthService();
                    taiKhoan = authService.kiemTraDangNhap(username, password);
                    
                    // Quay về UI thread để hiển thị kết quả
                    SwingUtilities.invokeLater(() -> {
                        btnLogin.setEnabled(true);
                        btnLogin.setText("Đăng nhập");
                        
                        if (taiKhoan == null) {
                            String errorMsg = authService.getLastErrorMessage();
                            System.err.println("❌ Đăng nhập thất bại: " + errorMsg);
                            JOptionPane.showMessageDialog(this,
                                    (errorMsg != null && !errorMsg.isEmpty()) ? errorMsg : "Tên đăng nhập hoặc mật khẩu không chính xác!",
                                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                        } else {
                            System.out.println("✅ Đăng nhập thành công: " + username + " | Role: " + taiKhoan.getLoaiNguoiDung());
                            
                            JOptionPane.showMessageDialog(this,
                                    "Đăng nhập thành công!\nXin chào " + username,
                                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
                            
                            navigateToUserInterface();
                        }
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                    SwingUtilities.invokeLater(() -> {
                        btnLogin.setEnabled(true);
                        btnLogin.setText("Đăng nhập");
                        JOptionPane.showMessageDialog(this,
                                "Lỗi kết nối: " + ex.getMessage(),
                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                    });
                }
            }).start();

        } else if (src == btnExit) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc muốn thoát không?",
                    "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }



    private void navigateToUserInterface() {
        SwingUtilities.invokeLater(() -> {
            try {
                switch (taiKhoan.getLoaiNguoiDung()) {
                    case "Admin":
                        AdFrame adminFrame = new AdFrame(taiKhoan);
                        adminFrame.setVisible(true);
                        break;
                    case "GiangVien":
                        GVFrame giangVienFrame = new GVFrame(taiKhoan);
                        giangVienFrame.setVisible(true);
                        break;
                    case "SinhVien":
                        SVFrame sinhVienFrame = new SVFrame(taiKhoan);
                        sinhVienFrame.setVisible(true);
                        break;
                    default:
                        JOptionPane.showMessageDialog(this,
                                "Loại người dùng không hợp lệ!",
                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                }
                this.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Không thể mở giao diện: " + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            // Mở 3 frame login chồng lên nhau - kéo dần ra để demo
            
            // Login 1 - Admin (dưới cùng)
            Login login = new Login();
            login.setVisible(true);
        });
    }
}
