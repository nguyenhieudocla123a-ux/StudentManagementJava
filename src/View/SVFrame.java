package View;

import service.*;
import model.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SVFrame extends JFrame {

    private TaiKhoan taiKhoan;
    private SinhVien sinhVien;
    private JSplitPane mainSplitPane;

    // Main panels
    private JPanel pnSidebar, pnMainContent;

    // Tables
    private JTable tblLopCoTheDangKy, tblLopDaDangKy, tblDiem;

    // Services - thay thế DAOs
    private LopHocPhanService lopHocPhanService;
    private SinhVienService sinhVienService;
    private DiemService diemService;
    private DangKyLopService dangKyLopService;
    private MonHocService monHocService;
    private GiangVienService giangVienService;

    // Colors - Màu sắc nhất quán với hệ thống
    private final Color PRIMARY_COLOR = new Color(25, 118, 210);
    private final Color SECONDARY_COLOR = new Color(94, 53, 177);
    private final Color SUCCESS_COLOR = new Color(56, 142, 60);
    private final Color WARNING_COLOR = new Color(245, 124, 0);
    private final Color DANGER_COLOR = new Color(211, 47, 47);
    private final Color INFO_COLOR = new Color(2, 136, 209);
    private final Color SIDEBAR_BG = new Color(255, 255, 255);
    private final Color SIDEBAR_SELECTED = new Color(25, 118, 210);
    private final Color LIGHT_BG = new Color(245, 247, 250);
    private final Color CARD_BG = Color.WHITE;
    private final Color BORDER_COLOR = new Color(230, 230, 230);
    private final Color HEADER_BG = new Color(250, 250, 250);

    // Fonts
    private final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 24);
    private final Font SUBTITLE_FONT = new Font("Segoe UI", Font.BOLD, 16);
    private final Font NORMAL_FONT = new Font("Segoe UI", Font.PLAIN, 13);
    private final Font BOLD_FONT = new Font("Segoe UI", Font.BOLD, 13);
    private final Font SIDEBAR_FONT = new Font("Segoe UI", Font.BOLD, 14);

    // Current active panel
    private String currentPanel = "ThongTin";

    public SVFrame(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
        initComponents();
        setupExitManagement();
        this.setVisible(true);
        displayUserInfo();

        SwingUtilities.invokeLater(() -> {
            loadThongTinCaNhan();
        });
    }

    private void setupExitManagement() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                JOptionPane.showMessageDialog(SVFrame.this,
                        "Vui lòng sử dụng nút 'ĐĂNG XUẤT' để thoát chương trình!",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void displayUserInfo() {
        if (taiKhoan != null) {
            this.setTitle("HỆ THỐNG QUẢN LÝ TÌNH HÌNH HỌC TẬP CỦA SINH VIÊN - Xin chào: " + taiKhoan.getTenDangNhap());
        }
    }

    private void initComponents() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1400, 800);
        this.setLocationRelativeTo(null);
        getContentPane().setBackground(LIGHT_BG);

        initializeDAOs();
        loadSinhVienInfo();

        // Main split pane
        mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        mainSplitPane.setDividerLocation(250);
        mainSplitPane.setDividerSize(3);
        mainSplitPane.setBorder(null);

        createSidebar();
        createMainContent();

        mainSplitPane.setLeftComponent(pnSidebar);
        mainSplitPane.setRightComponent(pnMainContent);
        this.add(mainSplitPane);
    }

    private void initializeDAOs() {
        lopHocPhanService = new LopHocPhanService();
        sinhVienService = new SinhVienService();
        diemService = new DiemService();
        dangKyLopService = new DangKyLopService();
        monHocService = new MonHocService();
        giangVienService = new GiangVienService();
    }

    private void loadSinhVienInfo() {
        this.sinhVien = sinhVienService.getSinhVienById(taiKhoan.getTenDangNhap());
    }

    private void createSidebar() {
        pnSidebar = new JPanel();
        pnSidebar.setLayout(new BorderLayout());
        pnSidebar.setPreferredSize(new Dimension(250, 0));
        pnSidebar.setBackground(SIDEBAR_BG);
        pnSidebar.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1),
                new EmptyBorder(0, 0, 0, 0)
        ));

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(HEADER_BG);
        headerPanel.setBorder(new CompoundBorder(
                new MatteBorder(0, 0, 2, 0, BORDER_COLOR),
                new EmptyBorder(25, 15, 25, 15)
        ));
        headerPanel.setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("SINH VIÊN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(PRIMARY_COLOR);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblUserInfo = new JLabel();
        lblUserInfo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblUserInfo.setForeground(SECONDARY_COLOR);
        lblUserInfo.setHorizontalAlignment(SwingConstants.CENTER);

        if (sinhVien != null) {
            lblUserInfo.setText(sinhVien.getHoTen());
        }

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(HEADER_BG);
        titlePanel.add(lblTitle, BorderLayout.CENTER);
        titlePanel.add(lblUserInfo, BorderLayout.SOUTH);
        headerPanel.add(titlePanel, BorderLayout.CENTER);
        pnSidebar.add(headerPanel, BorderLayout.NORTH);

        // Menu items
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(5, 1, 0, 2));
        menuPanel.setBackground(SIDEBAR_BG);
        menuPanel.setBorder(new EmptyBorder(20, 15, 20, 15));

        String[] menuItems = {"THÔNG TIN CÁ NHÂN", "ĐĂNG KÝ LỚP", "LỚP ĐÃ ĐĂNG KÝ", "XEM ĐIỂM"};

        for (String item : menuItems) {
            JButton menuButton = createMenuButton(item);
            menuPanel.add(menuButton);

            menuButton.addActionListener(e -> {
                String panelName = item.replace(" ", "").toUpperCase();
                switchPanel(panelName);
                highlightActiveButton(menuButton);
            });
        }

        JButton btnLogout = createLogoutButton();
        menuPanel.add(btnLogout);

        pnSidebar.add(menuPanel, BorderLayout.CENTER);
    }

    private JButton createLogoutButton() {
        JButton button = new JButton("ĐĂNG XUẤT");
        button.setFont(SIDEBAR_FONT);
        button.setForeground(new Color(211, 47, 47));
        button.setBackground(SIDEBAR_BG);
        button.setBorder(new CompoundBorder(
                new MatteBorder(2, 0, 0, 0, new Color(255, 205, 210)),
                new EmptyBorder(15, 20, 15, 15)
        ));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);

        button.addActionListener(e -> confirmAndLogout());
        return button;
    }

    private void confirmAndLogout() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn đăng xuất?",
                "Xác nhận đăng xuất", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            updateUserStatusToOffline();
            this.dispose();
            SwingUtilities.invokeLater(() -> new Login().setVisible(true));
        }
    }

    private void updateUserStatusToOffline() {
        if (taiKhoan != null) {
            AuthService authService = new AuthService();
            try {
                authService.capNhatTrangThaiOffline(taiKhoan.getTenDangNhap());
            } catch (Exception ex) {
                System.err.println("Lỗi khi cập nhật trạng thái: " + ex.getMessage());
            }
        }
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(SIDEBAR_FONT);
        button.setForeground(new Color(60, 60, 60));
        button.setBackground(SIDEBAR_BG);
        button.setBorder(new CompoundBorder(
                new MatteBorder(0, 0, 1, 0, new Color(240, 240, 240)),
                new EmptyBorder(12, 20, 12, 15)
        ));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    private void highlightActiveButton(JButton activeButton) {
        for (Component comp : ((JPanel) pnSidebar.getComponent(1)).getComponents()) {
            if (comp instanceof JButton) {
                JButton btn = (JButton) comp;
                if (!btn.getText().contains("ĐĂNG XUẤT")) {
                    btn.setBackground(SIDEBAR_BG);
                    btn.setForeground(new Color(60, 60, 60));
                    btn.setBorder(new CompoundBorder(
                            new MatteBorder(0, 0, 1, 0, new Color(240, 240, 240)),
                            new EmptyBorder(12, 20, 12, 15)
                    ));
                }
            }
        }
        activeButton.setBackground(new Color(232, 240, 254));
        activeButton.setForeground(PRIMARY_COLOR);
        activeButton.setBorder(new CompoundBorder(
                new MatteBorder(0, 4, 1, 0, PRIMARY_COLOR),
                new EmptyBorder(12, 16, 12, 15)
        ));
    }

    private void createMainContent() {
        pnMainContent = new JPanel(new BorderLayout());
        pnMainContent.setBackground(LIGHT_BG);
        showThongTinCaNhanPanel();
    }

    private void switchPanel(String panelName) {
        currentPanel = panelName;
        pnMainContent.removeAll();

        switch (panelName) {
            case "THÔNGTINCÁNHÂN": showThongTinCaNhanPanel(); break;
            case "ĐĂNGKÝLỚP": showDangKyLopPanel(); break;
            case "LỚPĐÃĐĂNGKÝ": showLopDaDangKyPanel(); break;
            case "XEMĐIỂM": showXemDiemPanel(); break;
        }

        pnMainContent.revalidate();
        pnMainContent.repaint();
    }

    private JPanel createContentPanel(String title) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(LIGHT_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(LIGHT_BG);
        headerPanel.setBorder(new CompoundBorder(
                new MatteBorder(0, 0, 2, 0, BORDER_COLOR),
                new EmptyBorder(0, 0, 20, 0)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(PRIMARY_COLOR);

        JButton btnReloadDB = createStyledButton("Tải lại", SECONDARY_COLOR);
        btnReloadDB.setPreferredSize(new Dimension(120, 40));
        btnReloadDB.addActionListener(e -> reloadCurrentPanelData());

        JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        headerRight.setBackground(LIGHT_BG);
        headerRight.add(btnReloadDB);

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(headerRight, BorderLayout.EAST);
        panel.add(headerPanel, BorderLayout.NORTH);

        return panel;
    }

    private void reloadCurrentPanelData() {
        switch (currentPanel) {
            case "THÔNGTINCÁNHÂN": loadThongTinCaNhan(); break;
            case "ĐĂNGKÝLỚP": loadLopCoTheDangKyData(); break;
            case "LỚPĐÃĐĂNGKÝ": loadLopDaDangKyData(); break;
            case "XEMĐIỂM": loadDiemData(); break;
        }
        JOptionPane.showMessageDialog(this, "Đã tải lại dữ liệu thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(BOLD_FONT);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setBorder(new EmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    private JTable createStyledTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setFont(NORMAL_FONT);
        table.setRowHeight(35);
        table.setSelectionBackground(new Color(220, 235, 255));
        table.setSelectionForeground(Color.BLACK);
        table.setGridColor(new Color(240, 240, 240));
        table.setShowGrid(true);

        table.getTableHeader().setFont(BOLD_FONT);
        table.getTableHeader().setBackground(HEADER_BG);
        table.getTableHeader().setForeground(PRIMARY_COLOR);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setBorder(new LineBorder(BORDER_COLOR));
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));

        // Alternate row colors
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    if (row % 2 == 0) {
                        c.setBackground(Color.WHITE);
                    } else {
                        c.setBackground(new Color(248, 252, 248));
                    }
                }

                setBorder(new EmptyBorder(0, 8, 0, 8));
                return c;
            }
        });

        return table;
    }

    private JScrollPane createStyledScrollPane(Component view) {
        JScrollPane scrollPane = new JScrollPane(view);
        scrollPane.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1),
                new EmptyBorder(0, 0, 0, 0)
        ));
        scrollPane.getViewport().setBackground(Color.WHITE);

        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(16);
        verticalScrollBar.setBackground(Color.WHITE);

        return scrollPane;
    }

    private JPanel createCardPanel(String title, Component content) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BG);
        card.setBorder(new CompoundBorder(
                new CompoundBorder(
                        new LineBorder(BORDER_COLOR, 1),
                        new EmptyBorder(15, 15, 15, 15)
                ),
                new EmptyBorder(0, 0, 0, 0)
        ));

        if (title != null) {
            JLabel titleLabel = new JLabel(title);
            titleLabel.setFont(SUBTITLE_FONT);
            titleLabel.setForeground(PRIMARY_COLOR);
            titleLabel.setBorder(new CompoundBorder(
                    new MatteBorder(0, 0, 2, 0, new Color(230, 240, 255)),
                    new EmptyBorder(0, 0, 10, 0)
            ));
            card.add(titleLabel, BorderLayout.NORTH);
        }

        card.add(content, BorderLayout.CENTER);
        return card;
    }

    // ==================== PANEL THÔNG TIN CÁ NHÂN ====================
    private void showThongTinCaNhanPanel() {
        JPanel contentPanel = createContentPanel("THÔNG TIN CÁ NHÂN");

        if (sinhVien == null) {
            JLabel lblError = new JLabel("Không tìm thấy thông tin sinh viên!");
            lblError.setFont(TITLE_FONT);
            lblError.setForeground(DANGER_COLOR);
            lblError.setHorizontalAlignment(SwingConstants.CENTER);
            contentPanel.add(lblError, BorderLayout.CENTER);
            pnMainContent.add(contentPanel, BorderLayout.CENTER);
            return;
        }

        // Panel hiển thị thông tin
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(CARD_BG);
        infoPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        addInfoRow("Mã sinh viên:", sinhVien.getMaSV(), infoPanel);
        addInfoRow("Họ tên:", sinhVien.getHoTen(), infoPanel);
        addInfoRow("Giới tính:", sinhVien.getGioiTinh(), infoPanel);
        addInfoRow("Ngày sinh:", sinhVien.getNgaySinh(), infoPanel);
        addInfoRow("Email:", sinhVien.getEmail() != null ? sinhVien.getEmail() : "Chưa có", infoPanel);
        addInfoRow("Số điện thoại:", sinhVien.getSoDienThoai() != null ? sinhVien.getSoDienThoai() : "Chưa có", infoPanel);
        addInfoRow("Địa chỉ:", sinhVien.getDiaChi() != null ? sinhVien.getDiaChi() : "Chưa có", infoPanel);
        addInfoRow("Mã khoa:", sinhVien.getMaKhoa(), infoPanel);

        // Nút chỉnh sửa
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        buttonPanel.setBackground(CARD_BG);
        
        JButton btnChinhSua = createStyledButton("Chỉnh Sửa Thông Tin", PRIMARY_COLOR);
        btnChinhSua.addActionListener(e -> showFormChinhSuaThongTin());
        buttonPanel.add(btnChinhSua);
        
        infoPanel.add(buttonPanel);

        contentPanel.add(createCardPanel("THÔNG TIN SINH VIÊN", infoPanel), BorderLayout.CENTER);
        pnMainContent.add(contentPanel, BorderLayout.CENTER);

        loadThongTinCaNhan();
    }

    // Form chỉnh sửa thông tin - ĐƠN GIẢN
    private void showFormChinhSuaThongTin() {
        // XÓA panel cũ trước
        pnMainContent.removeAll();
        
        JPanel contentPanel = createContentPanel("CHỈNH SỬA THÔNG TIN");

        // Form đơn giản - 1 cột
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(CARD_BG);
        formPanel.setBorder(new EmptyBorder(30, 50, 30, 50));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        
        // Họ tên
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(createLabel("Họ tên:"), gbc);
        gbc.gridx = 1;
        JTextField txtHoTen = createTextField();
        txtHoTen.setText(sinhVien.getHoTen());
        formPanel.add(txtHoTen, gbc);
        
        // Giới tính
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(createLabel("Giới tính:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> cboGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"});
        cboGioiTinh.setFont(NORMAL_FONT);
        cboGioiTinh.setPreferredSize(new Dimension(300, 40));
        cboGioiTinh.setSelectedItem(sinhVien.getGioiTinh());
        formPanel.add(cboGioiTinh, gbc);
        
        // Email
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(createLabel("Email:"), gbc);
        gbc.gridx = 1;
        JTextField txtEmail = createTextField();
        txtEmail.setText(sinhVien.getEmail() != null ? sinhVien.getEmail() : "");
        formPanel.add(txtEmail, gbc);
        
        // Số điện thoại
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(createLabel("Số điện thoại:"), gbc);
        gbc.gridx = 1;
        JTextField txtSoDienThoai = createTextField();
        txtSoDienThoai.setText(sinhVien.getSoDienThoai() != null ? sinhVien.getSoDienThoai() : "");
        formPanel.add(txtSoDienThoai, gbc);
        
        // Địa chỉ
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(createLabel("Địa chỉ:"), gbc);
        gbc.gridx = 1;
        JTextField txtDiaChi = createTextField();
        txtDiaChi.setText(sinhVien.getDiaChi() != null ? sinhVien.getDiaChi() : "");
        formPanel.add(txtDiaChi, gbc);
        
        // Buttons
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        buttonPanel.setBackground(CARD_BG);
        
        JButton btnLuu = createStyledButton("Lưu", SUCCESS_COLOR);
        JButton btnHuy = createStyledButton("Hủy", new Color(158, 158, 158));
        
        buttonPanel.add(btnLuu);
        buttonPanel.add(btnHuy);
        formPanel.add(buttonPanel, gbc);
        
        contentPanel.add(createCardPanel("FORM CHỈNH SỬA", formPanel), BorderLayout.CENTER);
        pnMainContent.add(contentPanel, BorderLayout.CENTER);
        
        // Refresh UI
        pnMainContent.revalidate();
        pnMainContent.repaint();
        
        // Xử lý sự kiện nút Lưu
        btnLuu.addActionListener(e -> {
            // Validate dữ liệu
            if (txtHoTen.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Họ tên không được để trống!",
                        "Cảnh báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Validate email
            String email = txtEmail.getText().trim();
            if (!email.isEmpty() && !isValidEmail(email)) {
                JOptionPane.showMessageDialog(this,
                        "Email không hợp lệ!",
                        "Cảnh báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Validate số điện thoại
            String sdt = txtSoDienThoai.getText().trim();
            if (!sdt.isEmpty() && !sdt.matches("^[0-9]{10,11}$")) {
                JOptionPane.showMessageDialog(this,
                        "Số điện thoại không hợp lệ (10-11 số)!",
                        "Cảnh báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Xác nhận cập nhật
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc muốn cập nhật thông tin?",
                    "Xác nhận",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }
            
            // Cập nhật thông tin
            sinhVien.setHoTen(txtHoTen.getText().trim());
            sinhVien.setGioiTinh(cboGioiTinh.getSelectedItem().toString());
            sinhVien.setEmail(email.isEmpty() ? null : email);
            sinhVien.setSoDienThoai(sdt.isEmpty() ? null : sdt);
            sinhVien.setDiaChi(txtDiaChi.getText().trim().isEmpty() ? null : txtDiaChi.getText().trim());
            
            // Lưu vào database
            if (sinhVienService.capNhatSinhVien(sinhVien)) {
                JOptionPane.showMessageDialog(this,
                        "Cập nhật thông tin thành công!",
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
                // Reload lại panel
                showThongTinCaNhanPanel();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Cập nhật thông tin thất bại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Xử lý sự kiện nút Hủy
        btnHuy.addActionListener(e -> {
            // Xóa panel cũ và quay lại trang thông tin
            pnMainContent.removeAll();
            showThongTinCaNhanPanel();
            pnMainContent.revalidate();
            pnMainContent.repaint();
        });
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(BOLD_FONT);
        label.setForeground(new Color(80, 80, 80));
        return label;
    }
    
    private JLabel createLabelWithIcon(String icon, String text) {
        JLabel label = new JLabel(icon + " " + text);
        label.setFont(BOLD_FONT);
        label.setForeground(new Color(70, 70, 70));
        label.setPreferredSize(new Dimension(140, 45));
        return label;
    }
    
    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setFont(NORMAL_FONT);
        textField.setPreferredSize(new Dimension(300, 40));
        textField.setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(5, 10, 5, 10)
        ));
        return textField;
    }
    
    private JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setFont(NORMAL_FONT);
        textField.setPreferredSize(new Dimension(250, 45));
        textField.setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(8, 12, 8, 12)
        ));
        textField.setBackground(Color.WHITE);
        
        // Hiệu ứng focus
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textField.setBorder(new CompoundBorder(
                        new LineBorder(PRIMARY_COLOR, 2),
                        new EmptyBorder(7, 11, 7, 11)
                ));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                textField.setBorder(new CompoundBorder(
                        new LineBorder(new Color(200, 200, 200), 1),
                        new EmptyBorder(8, 12, 8, 12)
                ));
            }
        });
        
        return textField;
    }

    private void addInfoRow(String label, String value, JPanel parent) {
        JPanel row = new JPanel(new BorderLayout(10, 5));
        row.setBackground(CARD_BG);
        row.setBorder(new EmptyBorder(5, 0, 5, 0));

        JLabel lbl = new JLabel(label);
        lbl.setFont(BOLD_FONT);
        lbl.setForeground(new Color(80, 80, 80));
        lbl.setPreferredSize(new Dimension(150, 30));

        JLabel val = new JLabel(value);
        val.setFont(NORMAL_FONT);
        val.setForeground(new Color(40, 40, 40));

        row.add(lbl, BorderLayout.WEST);
        row.add(val, BorderLayout.CENTER);
        parent.add(row);
        parent.add(Box.createVerticalStrut(10));
    }

    private void loadThongTinCaNhan() {
        // Thông tin đã được load trong loadSinhVienInfo()
    }

    // ==================== PANEL ĐĂNG KÝ LỚP ====================
    private void showDangKyLopPanel() {
        JPanel contentPanel = createContentPanel("ĐĂNG KÝ LỚP HỌC PHẦN");

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(500);
        splitPane.setResizeWeight(0.7);
        splitPane.setDividerSize(2);
        splitPane.setBorder(null);

        String[] columnNames = {"Mã lớp", "Môn học", "Giảng viên", "Học kỳ", "Năm học", "Sĩ số", "Trạng thái"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tblLopCoTheDangKy = createStyledTable(model);
        JScrollPane scrollPane = createStyledScrollPane(tblLopCoTheDangKy);

        // Control panel
        JPanel controlPanel = new JPanel(new BorderLayout(10, 10));
        controlPanel.setBackground(LIGHT_BG);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(CARD_BG);
        infoPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblMaLop = new JLabel("Mã lớp: --");
        JLabel lblMonHoc = new JLabel("Môn học: --");
        JLabel lblGiangVien = new JLabel("Giảng viên: --");
        JLabel lblSiSo = new JLabel("Sĩ số: --/--");
        JLabel lblHocKy = new JLabel("Học kỳ: --");
        JLabel lblNamHoc = new JLabel("Năm học: --");

        lblMaLop.setFont(BOLD_FONT);
        lblMonHoc.setFont(BOLD_FONT);
        lblGiangVien.setFont(BOLD_FONT);
        lblSiSo.setFont(BOLD_FONT);
        lblHocKy.setFont(NORMAL_FONT);
        lblNamHoc.setFont(NORMAL_FONT);

        infoPanel.add(lblMaLop);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(lblMonHoc);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(lblGiangVien);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(lblSiSo);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(lblHocKy);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(lblNamHoc);

        JButton btnDangKy = createStyledButton("Đăng Ký Lớp", SUCCESS_COLOR);
        btnDangKy.addActionListener(e -> {
            int selectedRow = tblLopCoTheDangKy.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, 
                    "Vui lòng chọn lớp học phần!", 
                    "Cảnh báo", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Convert view index to model index nếu có sorter
            int modelRow = selectedRow;
            if (tblLopCoTheDangKy.getRowSorter() != null) {
                modelRow = tblLopCoTheDangKy.getRowSorter().convertRowIndexToModel(selectedRow);
            }

            String maLop = tblLopCoTheDangKy.getValueAt(modelRow, 0).toString();
            String maSV = sinhVien.getMaSV();

            // Xác nhận đăng ký
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc muốn đăng ký lớp " + maLop + "?",
                    "Xác nhận đăng ký",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            // Đăng ký - Service sẽ kiểm tra tất cả điều kiện
            boolean ketQua = dangKyLopService.dangKyLop(new DangKiLop(maSV, maLop));
            
            if (ketQua) {
                JOptionPane.showMessageDialog(this, 
                    "Đăng ký lớp học phần thành công!", 
                    "Thành công", 
                    JOptionPane.INFORMATION_MESSAGE);
                loadLopCoTheDangKyData();
                loadLopDaDangKyData();
            } else {
                // Lấy thông báo lỗi chi tiết từ Service
                String errorMessage = dangKyLopService.getLastErrorMessage();
                
                if (errorMessage == null || errorMessage.isEmpty()) {
                    errorMessage = "Đăng ký lớp học phần thất bại!\nVui lòng thử lại hoặc liên hệ quản trị viên.";
                }
                
                JOptionPane.showMessageDialog(this, 
                    errorMessage, 
                    "Không thể đăng ký", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(CARD_BG);
        buttonPanel.add(btnDangKy);

        JPanel detailPanel = new JPanel(new BorderLayout());
        detailPanel.setBackground(CARD_BG);
        detailPanel.add(createCardPanel("THÔNG TIN LỚP", infoPanel), BorderLayout.CENTER);
        detailPanel.add(buttonPanel, BorderLayout.SOUTH);

        controlPanel.add(detailPanel, BorderLayout.CENTER);

        splitPane.setTopComponent(createCardPanel("DANH SÁCH LỚP HỌC PHẦN CÓ THỂ ĐĂNG KÝ", scrollPane));
        splitPane.setBottomComponent(controlPanel);
        contentPanel.add(splitPane, BorderLayout.CENTER);
        pnMainContent.add(contentPanel, BorderLayout.CENTER);

        loadLopCoTheDangKyData();

        // Table selection listener
        tblLopCoTheDangKy.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tblLopCoTheDangKy.getSelectedRow();
                if (selectedRow != -1) {
                    // Convert view index to model index nếu có sorter
                    int modelRow = selectedRow;
                    if (tblLopCoTheDangKy.getRowSorter() != null) {
                        modelRow = tblLopCoTheDangKy.getRowSorter().convertRowIndexToModel(selectedRow);
                    }
                    
                    String maLop = tblLopCoTheDangKy.getValueAt(modelRow, 0).toString();
                    String tenMH = tblLopCoTheDangKy.getValueAt(modelRow, 1).toString();
                    String tenGV = tblLopCoTheDangKy.getValueAt(modelRow, 2).toString();
                    String hocKy = tblLopCoTheDangKy.getValueAt(modelRow, 3).toString();
                    String namHoc = tblLopCoTheDangKy.getValueAt(modelRow, 4).toString();
                    String siSo = tblLopCoTheDangKy.getValueAt(modelRow, 5).toString();

                    lblMaLop.setText("Mã lớp: " + maLop);
                    lblMonHoc.setText("Môn học: " + tenMH);
                    lblGiangVien.setText("Giảng viên: " + tenGV);
                    lblSiSo.setText("Sĩ số: " + siSo);
                    lblHocKy.setText("Học kỳ: " + hocKy);
                    lblNamHoc.setText("Năm học: " + namHoc);
                }
            }
        });
    }

    // ==================== PANEL LỚP ĐÃ ĐĂNG KÝ ====================
    private void showLopDaDangKyPanel() {
        JPanel contentPanel = createContentPanel("LỚP ĐÃ ĐĂNG KÝ");

        String[] columnNames = {"Mã lớp", "Môn học", "Giảng viên", "Học kỳ", "Năm học", "Sĩ số", "Trạng thái"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tblLopDaDangKy = createStyledTable(model);
        JScrollPane scrollPane = createStyledScrollPane(tblLopDaDangKy);

        // Control panel
        JPanel controlPanel = new JPanel(new BorderLayout(10, 10));
        controlPanel.setBackground(LIGHT_BG);

        JButton btnHuyDangKy = createStyledButton("Hủy Đăng Ký", DANGER_COLOR);
        btnHuyDangKy.addActionListener(e -> {
            int selectedRow = tblLopDaDangKy.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn lớp học phần cần hủy!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Convert view index to model index nếu có sorter
            int modelRow = selectedRow;
            if (tblLopDaDangKy.getRowSorter() != null) {
                modelRow = tblLopDaDangKy.getRowSorter().convertRowIndexToModel(selectedRow);
            }

            String maLop = tblLopDaDangKy.getValueAt(modelRow, 0).toString();
            String maSV = sinhVien.getMaSV();



            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc muốn hủy đăng ký lớp học phần này?",
                    "Xác nhận hủy đăng ký", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                if (dangKyLopService.huyDangKy(maSV, maLop)) {
                    JOptionPane.showMessageDialog(this, "Hủy đăng ký thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    loadLopDaDangKyData();
                    loadLopCoTheDangKyData();
                } else {
                    String errorMsg = dangKyLopService.getLastErrorMessage();
                    JOptionPane.showMessageDialog(this, 
                            (errorMsg != null && !errorMsg.isEmpty()) ? errorMsg : "Hủy đăng ký thất bại!", 
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(CARD_BG);
        buttonPanel.add(btnHuyDangKy);

        controlPanel.add(buttonPanel, BorderLayout.CENTER);

        contentPanel.add(createCardPanel("DANH SÁCH LỚP ĐÃ ĐĂNG KÝ", scrollPane), BorderLayout.CENTER);
        contentPanel.add(controlPanel, BorderLayout.SOUTH);
        pnMainContent.add(contentPanel, BorderLayout.CENTER);

        loadLopDaDangKyData();
    }

    // ==================== PANEL XEM ĐIỂM ====================
    private void showXemDiemPanel() {
        JPanel contentPanel = createContentPanel("XEM ĐIỂM SỐ");

        // Filter panel với FlowLayout để căn chỉnh đẹp hơn
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        filterPanel.setBackground(CARD_BG);
        filterPanel.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1),
                new EmptyBorder(10, 15, 10, 15)
        ));

        // Chiều cao chuẩn cho tất cả components
        final int COMPONENT_HEIGHT = 38;
        
        // Học kỳ
        JLabel lblHocKy = new JLabel("Học kỳ:");
        lblHocKy.setFont(BOLD_FONT);
        lblHocKy.setPreferredSize(new Dimension(60, COMPONENT_HEIGHT));
        filterPanel.add(lblHocKy);

        JComboBox<String> cboHocKy = createStyledComboBox();
        cboHocKy.addItem("Tất cả");
        cboHocKy.addItem("HK1");
        cboHocKy.addItem("HK2");
        
        // Load thêm danh sách học kỳ từ database
        if (sinhVien != null) {
            List<Integer> listHocKy = diemService.getHocKyBySinhVien(sinhVien.getMaSV());
            for (Integer hk : listHocKy) {
                boolean exists = false;
                for (int i = 0; i < cboHocKy.getItemCount(); i++) {
                    if (cboHocKy.getItemAt(i).equals(hk)) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    cboHocKy.addItem(hk.toString());
                }
            }
        }
        cboHocKy.setPreferredSize(new Dimension(120, COMPONENT_HEIGHT));
        filterPanel.add(cboHocKy);

        // Năm học
        JLabel lblNamHoc = new JLabel("Năm học:");
        lblNamHoc.setFont(BOLD_FONT);
        lblNamHoc.setPreferredSize(new Dimension(70, COMPONENT_HEIGHT));
        filterPanel.add(lblNamHoc);

        JComboBox<String> cboNamHoc = createStyledComboBox();
        cboNamHoc.addItem("Tất cả");
        cboNamHoc.addItem("2022-2023");
        cboNamHoc.addItem("2023-2024");
        cboNamHoc.addItem("2024-2025");
        cboNamHoc.addItem("2025-2026");
        
        // Load thêm danh sách năm học từ database
        if (sinhVien != null) {
            List<Integer> listNamHoc = diemService.getNamHocBySinhVien(sinhVien.getMaSV());
            for (Integer nh : listNamHoc) {
                boolean exists = false;
                for (int i = 0; i < cboNamHoc.getItemCount(); i++) {
                    if (cboNamHoc.getItemAt(i).equals(nh)) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    cboNamHoc.addItem(nh.toString());
                }
            }
        }
        cboNamHoc.setPreferredSize(new Dimension(140, COMPONENT_HEIGHT));
        filterPanel.add(cboNamHoc);

        // Buttons
        JButton btnLoc = createStyledButton("Lọc", PRIMARY_COLOR);
        btnLoc.setPreferredSize(new Dimension(100, COMPONENT_HEIGHT));
        filterPanel.add(btnLoc);

        JButton btnReset = createStyledButton("Đặt lại", SECONDARY_COLOR);
        btnReset.setPreferredSize(new Dimension(100, COMPONENT_HEIGHT));
        filterPanel.add(btnReset);

        // Table
        String[] columnNames = {"Mã lớp", "Môn học", "Học kỳ", "Năm học", "Điểm QT", "Điểm GK", "Điểm CK", "Điểm TK", "Điểm chữ", "Xếp loại"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tblDiem = createStyledTable(model);
        
        // Thiết lập độ rộng cột
        tblDiem.getColumnModel().getColumn(0).setPreferredWidth(80);  // Mã lớp
        tblDiem.getColumnModel().getColumn(1).setPreferredWidth(200); // Môn học
        tblDiem.getColumnModel().getColumn(2).setPreferredWidth(70);  // Học kỳ
        tblDiem.getColumnModel().getColumn(3).setPreferredWidth(100); // Năm học
        tblDiem.getColumnModel().getColumn(4).setPreferredWidth(70);  // Điểm QT
        tblDiem.getColumnModel().getColumn(5).setPreferredWidth(70);  // Điểm GK
        tblDiem.getColumnModel().getColumn(6).setPreferredWidth(70);  // Điểm CK
        tblDiem.getColumnModel().getColumn(7).setPreferredWidth(70);  // Điểm TK
        tblDiem.getColumnModel().getColumn(8).setPreferredWidth(80);  // Điểm chữ
        tblDiem.getColumnModel().getColumn(9).setPreferredWidth(100); // Xếp loại
        
        JScrollPane scrollPane = createStyledScrollPane(tblDiem);



        // GPA Panel với GridLayout để hiển thị GPA kỳ và GPA tích lũy
        JPanel gpaPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        gpaPanel.setBackground(LIGHT_BG);
        gpaPanel.setBorder(new EmptyBorder(0, 0, 0, 0));

        // GPA Kỳ (bên trái)
        JPanel gpaKyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        gpaKyPanel.setBackground(CARD_BG);
        gpaKyPanel.setBorder(new CompoundBorder(
                new LineBorder(PRIMARY_COLOR, 2),
                new EmptyBorder(15, 20, 15, 20)
        ));

        JLabel lblGPAKyTitle = new JLabel("GPA Kỳ:");
        lblGPAKyTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblGPAKyTitle.setForeground(PRIMARY_COLOR);
        gpaKyPanel.add(lblGPAKyTitle);

        JLabel lblGPAKyValue = new JLabel("0.00");
        lblGPAKyValue.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblGPAKyValue.setForeground(PRIMARY_COLOR);
        gpaKyPanel.add(lblGPAKyValue);

        JLabel lblTinChiKy = new JLabel("Tín chỉ: 0");
        lblTinChiKy.setFont(BOLD_FONT);
        lblTinChiKy.setForeground(new Color(80, 80, 80));
        gpaKyPanel.add(lblTinChiKy);

        JLabel lblXepLoaiKy = new JLabel("");
        lblXepLoaiKy.setFont(BOLD_FONT);
        lblXepLoaiKy.setForeground(INFO_COLOR);
        gpaKyPanel.add(lblXepLoaiKy);

        // GPA Tích lũy (bên phải)
        JPanel gpaTichLuyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        gpaTichLuyPanel.setBackground(CARD_BG);
        gpaTichLuyPanel.setBorder(new CompoundBorder(
                new LineBorder(SUCCESS_COLOR, 2),
                new EmptyBorder(15, 20, 15, 20)
        ));

        JLabel lblGPATichLuyTitle = new JLabel("GPA Tích lũy:");
        lblGPATichLuyTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblGPATichLuyTitle.setForeground(SUCCESS_COLOR);
        gpaTichLuyPanel.add(lblGPATichLuyTitle);

        JLabel lblGPATichLuyValue = new JLabel("0.00");
        lblGPATichLuyValue.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblGPATichLuyValue.setForeground(SUCCESS_COLOR);
        gpaTichLuyPanel.add(lblGPATichLuyValue);

        JLabel lblTongTinChi = new JLabel("Tổng tín chỉ: 0");
        lblTongTinChi.setFont(BOLD_FONT);
        lblTongTinChi.setForeground(new Color(80, 80, 80));
        gpaTichLuyPanel.add(lblTongTinChi);

        JLabel lblXepLoaiTichLuy = new JLabel("");
        lblXepLoaiTichLuy.setFont(BOLD_FONT);
        lblXepLoaiTichLuy.setForeground(INFO_COLOR);
        gpaTichLuyPanel.add(lblXepLoaiTichLuy);

        gpaPanel.add(gpaKyPanel);
        gpaPanel.add(gpaTichLuyPanel);

        // Layout
        JPanel mainPanel = new JPanel(new BorderLayout(0, 15));
        mainPanel.setBackground(LIGHT_BG);
        mainPanel.add(filterPanel, BorderLayout.NORTH);
        mainPanel.add(createCardPanel("BẢNG ĐIỂM CỦA TÔI", scrollPane), BorderLayout.CENTER);
        mainPanel.add(gpaPanel, BorderLayout.SOUTH);

        contentPanel.add(mainPanel, BorderLayout.CENTER);
        pnMainContent.add(contentPanel, BorderLayout.CENTER);

        // Load data và tính GPA
        loadDiemData();
        
        // Cập nhật GPA khi lọc
        btnLoc.addActionListener(e -> {
            String hocKy = cboHocKy.getSelectedItem().toString();
            String namHoc = cboNamHoc.getSelectedItem().toString();
            loadDiemDataWithFilter(hocKy, namHoc);
            // Tính GPA kỳ (theo bộ lọc)
            capNhatGPA(lblGPAKyValue, lblTinChiKy, lblXepLoaiKy, hocKy, namHoc);
            // GPA tích lũy luôn tính tất cả
            capNhatGPA(lblGPATichLuyValue, lblTongTinChi, lblXepLoaiTichLuy, "Tất cả", "Tất cả");
        });

        btnReset.addActionListener(e -> {
            cboHocKy.setSelectedIndex(0);
            cboNamHoc.setSelectedIndex(0);
            loadDiemData();
            // Khi reset, GPA kỳ = GPA tích lũy
            capNhatGPA(lblGPAKyValue, lblTinChiKy, lblXepLoaiKy, "Tất cả", "Tất cả");
            capNhatGPA(lblGPATichLuyValue, lblTongTinChi, lblXepLoaiTichLuy, "Tất cả", "Tất cả");
        });
        
        // Tính GPA ban đầu (hiển thị tất cả)
        capNhatGPA(lblGPAKyValue, lblTinChiKy, lblXepLoaiKy, "Tất cả", "Tất cả");
        capNhatGPA(lblGPATichLuyValue, lblTongTinChi, lblXepLoaiTichLuy, "Tất cả", "Tất cả");
    }

    // ==================== DATA LOADING METHODS ====================

    private void loadLopCoTheDangKyData() {
        if (tblLopCoTheDangKy != null && sinhVien != null) {
            DefaultTableModel model = (DefaultTableModel) tblLopCoTheDangKy.getModel();
            model.setRowCount(0);
            List<LopHocPhan> listLHP = lopHocPhanService.getAllLopHocPhan();
            String maSV = sinhVien.getMaSV();
            String maKhoaSV = sinhVien.getMaKhoa();

            for (LopHocPhan lhp : listLHP) {
                // CHỈ HIỂN THỊ LỚP ĐANG MỞ, CHƯA ĐĂNG KÝ, VÀ THUỘC KHOA SINH VIÊN
                if (lhp.isDangMo() && !dangKyLopService.kiemTraDaDangKy(maSV, lhp.getMaLop())) {
                    MonHoc mh = monHocService.getMonHocById(lhp.getMaMH());

                    if (mh != null && mh.getMaKhoa().equals(maKhoaSV)) {
                        GiangVien gv = giangVienService.getGiangVienById(lhp.getMaGV());
                        int siSoHienTai = dangKyLopService.demSoLuongDangKy(lhp.getMaLop());
                        String siSo = siSoHienTai + "/" + lhp.getSiSoToiDa();
                        
                        // Hiển thị trạng thái lớp: Mở/Đóng
                        String trangThai = lhp.getTrangThaiHienThi(); // "Mở" hoặc "Đóng"
                        
                        // Thêm thông tin sĩ số vào trạng thái
                        if (lhp.isDangMo()) {
                            if (siSoHienTai >= lhp.getSiSoToiDa()) {
                                trangThai = "Mở (Đã đầy)";
                            } else {
                                trangThai = "Mở (Còn chỗ)";
                            }
                        } else {
                            trangThai = "Đóng";
                        }

                        model.addRow(new Object[]{
                                lhp.getMaLop(),
                                mh.getTenMH(),
                                gv != null ? gv.getHoTen() : lhp.getMaGV(),
                                lhp.getHocKy(),
                                lhp.getNamHoc(),
                                siSo,
                                trangThai
                        });
                    }
                }
            }
        }
    }

    private void loadLopDaDangKyData() {
        if (tblLopDaDangKy != null && sinhVien != null) {
            DefaultTableModel model = (DefaultTableModel) tblLopDaDangKy.getModel();
            model.setRowCount(0);
            String maSV = sinhVien.getMaSV();
            List<DangKiLop> listDangKy = dangKyLopService.getLopDaDangKy(maSV);

            for (DangKiLop dk : listDangKy) {
                String maLop = dk.getMaLop();
                LopHocPhan lhp = lopHocPhanService.getLopHocPhanByMaLop(maLop);
                if (lhp != null) {
                    MonHoc mh = monHocService.getMonHocById(lhp.getMaMH());
                    GiangVien gv = giangVienService.getGiangVienById(lhp.getMaGV());
                    int siSoHienTai = dangKyLopService.demSoLuongDangKy(maLop);
                    String siSo = siSoHienTai + "/" + lhp.getSiSoToiDa();

                    // Kiểm tra trạng thái lớp
                    String trangThai = lhp.isDangMo() ? "Đang mở" : "Đã đóng";
                    
                    model.addRow(new Object[]{
                            lhp.getMaLop(),
                            mh != null ? mh.getTenMH() : lhp.getMaMH(),
                            gv != null ? gv.getHoTen() : lhp.getMaGV(),
                            lhp.getHocKy(),
                            lhp.getNamHoc(),
                            siSo,
                            trangThai
                    });
                }
            }
        }
    }

    private void loadDiemData() {
        if (tblDiem != null && sinhVien != null) {
            DefaultTableModel model = (DefaultTableModel) tblDiem.getModel();
            model.setRowCount(0);
            String maSV = sinhVien.getMaSV();
            List<Diem> listDiem = diemService.getDiemBySinhVien(maSV);

            for (Diem diem : listDiem) {
                LopHocPhan lhp = lopHocPhanService.getLopHocPhanByMaLop(diem.getMaLop());
                if (lhp != null) {
                    MonHoc mh = monHocService.getMonHocById(lhp.getMaMH());

                    model.addRow(new Object[]{
                            diem.getMaLop(),
                            mh != null ? mh.getTenMH() : lhp.getMaMH(),
                            lhp.getHocKy(),
                            lhp.getNamHoc(),
                            diem.getDiemQuaTrinh(),
                            diem.getDiemGiuaKy(),
                            diem.getDiemCuoiKy(),
                            diem.getDiemTongKet(),
                            diem.getDiemChu(),
                            diem.getXepLoai()
                    });
                }
            }
        }
    }

    private void loadDiemDataWithFilter(String hocKy, String namHoc) {
        if (tblDiem != null && sinhVien != null) {
            DefaultTableModel model = (DefaultTableModel) tblDiem.getModel();
            model.setRowCount(0);
            String maSV = sinhVien.getMaSV();
            
            // Sử dụng phương thức lọc mới
            List<Diem> listDiem = diemService.getDiemBySinhVienTheoKyNam(maSV, hocKy, namHoc);

            if (listDiem.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Không tìm thấy điểm với bộ lọc: Học kỳ " + hocKy + ", Năm học " + namHoc, 
                    "Thông báo", 
                    JOptionPane.INFORMATION_MESSAGE);
            }

            for (Diem diem : listDiem) {
                LopHocPhan lhp = lopHocPhanService.getLopHocPhanByMaLop(diem.getMaLop());
                if (lhp != null) {
                    MonHoc mh = monHocService.getMonHocById(lhp.getMaMH());

                    model.addRow(new Object[]{
                            diem.getMaLop(),
                            mh != null ? mh.getTenMH() : lhp.getMaMH(),
                            lhp.getHocKy(),
                            lhp.getNamHoc(),
                            diem.getDiemQuaTrinh(),
                            diem.getDiemGiuaKy(),
                            diem.getDiemCuoiKy(),
                            diem.getDiemTongKet(),
                            diem.getDiemChu(),
                            diem.getXepLoai()
                    });
                }
            }
        }
    }

    // ==================== UTILITY METHODS ====================
    
    private JComboBox<String> createStyledComboBox() {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setFont(NORMAL_FONT);
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(new Color(60, 60, 60));
        
        // Loại bỏ border xấu
        comboBox.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1),
                new EmptyBorder(5, 10, 5, 10)
        ));
        
        // Loại bỏ focus border chấm chấm
        comboBox.setFocusable(true);
        ((JComponent) comboBox.getEditor().getEditorComponent()).setBorder(null);
        
        return comboBox;
    }

    /**
     * Tính và cập nhật GPA theo công thức: GPA = Σ(điểm × tín chỉ) / Σ(tín chỉ)
     */
    private void capNhatGPA(JLabel lblGPAValue, JLabel lblTongTinChi, JLabel lblXepLoaiGPA, String hocKy, String namHoc) {
        if (sinhVien == null) return;

        try {
            // Lấy danh sách điểm theo bộ lọc
            List<Diem> listDiem;
            if ("Tất cả".equals(hocKy) && "Tất cả".equals(namHoc)) {
                listDiem = diemService.getDiemBySinhVien(sinhVien.getMaSV());
            } else {
                listDiem = diemService.getDiemBySinhVienTheoKyNam(sinhVien.getMaSV(), hocKy, namHoc);
            }

            double tongDiemNhan = 0.0; // Σ(điểm × tín chỉ)
            int tongTinChi = 0;        // Σ(tín chỉ)

            for (Diem diem : listDiem) {
                // Chỉ tính những môn có điểm tổng kết
                if (diem.getDiemTongKet() != null) {
                    // Lấy thông tin lớp học phần
                    LopHocPhan lhp = lopHocPhanService.getLopHocPhanByMaLop(diem.getMaLop());
                    if (lhp != null) {
                        // Lấy thông tin môn học để biết số tín chỉ
                        MonHoc mh = monHocService.getMonHocById(lhp.getMaMH());
                        if (mh != null) {
                            int tinChi = mh.getSoTinChi();
                            float diemTK = diem.getDiemTongKet();
                            
                            tongDiemNhan += (diemTK * tinChi);
                            tongTinChi += tinChi;
                        }
                    }
                }
            }

            // Tính GPA
            double gpa = tongTinChi > 0 ? tongDiemNhan / tongTinChi : 0.0;

            // Cập nhật UI
            lblGPAValue.setText(String.format("%.2f", gpa));
            lblTongTinChi.setText("Tổng tín chỉ: " + tongTinChi);

            // Xếp loại GPA
            String xepLoai = getXepLoaiGPA(gpa);
            lblXepLoaiGPA.setText("Xếp loại: " + xepLoai);

            // Đổi màu theo xếp loại
            if (gpa >= 3.6) {
                lblGPAValue.setForeground(new Color(56, 142, 60)); // Xanh lá - Xuất sắc
            } else if (gpa >= 3.2) {
                lblGPAValue.setForeground(new Color(2, 136, 209)); // Xanh dương - Giỏi
            } else if (gpa >= 2.5) {
                lblGPAValue.setForeground(new Color(245, 124, 0)); // Cam - Khá
            } else if (gpa >= 2.0) {
                lblGPAValue.setForeground(new Color(158, 158, 158)); // Xám - Trung bình
            } else {
                lblGPAValue.setForeground(new Color(211, 47, 47)); // Đỏ - Yếu/Kém
            }

        } catch (Exception e) {
            lblGPAValue.setText("N/A");
            lblTongTinChi.setText("Tổng tín chỉ: 0");
            lblXepLoaiGPA.setText("");
            e.printStackTrace();
        }
    }

    /**
     * Xếp loại GPA theo thang điểm 4.0
     */
    private String getXepLoaiGPA(double gpa) {
        if (gpa >= 3.6) return "Xuất sắc";
        else if (gpa >= 3.2) return "Giỏi";
        else if (gpa >= 2.5) return "Khá";
        else if (gpa >= 2.0) return "Trung bình";
        else if (gpa >= 1.0) return "Yếu";
        else return "Kém";
    }

    // ==================== PHƯƠNG THỨC KIỂM TRA EMAIL ====================
    private boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) return true;
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }
}
