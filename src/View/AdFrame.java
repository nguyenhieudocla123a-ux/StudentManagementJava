package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import service.DangKyLopService;
import service.DiemService;
import service.GiangVienService;
import service.KhoaService;
import service.LopHocPhanService;
import service.MonHocService;
import service.SinhVienService;
import service.AuthService;
import service.TaiKhoanService;
import model.DangKiLop;
import model.GiangVien;
import model.Khoa;
import model.LopHocPhan;
import model.MonHoc;
import model.SinhVien;
import model.TaiKhoan;
import until.ExcelExporter;

public class AdFrame extends JFrame {

    //User
    private TaiKhoan taiKhoan;

    private JSplitPane mainSplitPane;

    // Main panels
    private JPanel pnSidebar, pnMainContent;

    // Tables
    private JTable tblTaiKhoan, tblKhoa, tblGiangVien, tblSinhVien, tblMonHoc, tblLopHocPhan, tblDangKy;

    // Services - thay thế DAOs
    private AuthService authService;
    private TaiKhoanService taiKhoanService;
    private KhoaService khoaService;
    private GiangVienService giangVienService;
    private SinhVienService sinhVienService;
    private MonHocService monHocService;
    private LopHocPhanService lopHocPhanService;
    private DangKyLopService dangKyLopService;
    private DiemService diemService;

    // Colors - TÔNG XANH DƯƠNG TƯƠI SÁNG (giống SVFrame style)
    private final Color PRIMARY_COLOR = new Color(0, 122, 255);
    private final Color SECONDARY_COLOR = new Color(90, 200, 250);
    private final Color SUCCESS_COLOR = new Color(52, 199, 89);
    private final Color WARNING_COLOR = new Color(255, 149, 0);
    private final Color DANGER_COLOR = new Color(255, 59, 48);
    private final Color INFO_COLOR = new Color(2, 136, 209);
    private final Color SIDEBAR_BG = new Color(248, 250, 252);
    private final Color SIDEBAR_HOVER = new Color(230, 240, 255);
    private final Color SIDEBAR_SELECTED = new Color(0, 122, 255);
    private final Color LIGHT_BG = new Color(245, 248, 255);
    private final Color CARD_BG = Color.WHITE;
    private final Color BORDER_COLOR = new Color(225, 235, 245);
    private final Color HEADER_BG = new Color(240, 247, 255);

    // Fonts
    private final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 24);
    private final Font SUBTITLE_FONT = new Font("Segoe UI", Font.BOLD, 16);
    private final Font NORMAL_FONT = new Font("Segoe UI", Font.PLAIN, 13);
    private final Font BOLD_FONT = new Font("Segoe UI", Font.BOLD, 13);
    private final Font SIDEBAR_FONT = new Font("Segoe UI", Font.BOLD, 14);

    // Current active panel
    private String currentPanel = "TÀIKHOẢN";

    public AdFrame(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
        initComponents();
        setupExitManagement();
        this.setVisible(true);
        displayUserInfo();
        // Load data cho panel mặc định sau khi hiển thị
        SwingUtilities.invokeLater(() -> {
            loadTaiKhoanData();
        });
    }
    // PHƯƠNG THỨC CẤM NÚT X VÀ THIẾT LẬP THOÁT
    private void setupExitManagement() {
        // CẤM nút X - không cho đóng bằng nút X
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Xử lý khi ấn nút X - hiển thị thông báo
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                JOptionPane.showMessageDialog(AdFrame.this,
                        "Vui lòng sử dụng nút 'ĐĂNG XUẤT' để thoát chương trình!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
    private void displayUserInfo() {
        if (taiKhoan!= null) {
            // Cập nhật title với tên user
            this.setTitle("HỆ THỐNG QUẢN LÝ TÌNH HÌNH HỌC TẬP CỦA SINH VIÊN - ADMIN - Xin chào: " + taiKhoan.getTenDangNhap());
        }
    }
    private void initComponents() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1400, 800);
        this.setLocationRelativeTo(null);
        getContentPane().setBackground(LIGHT_BG);

        // Initialize DAOs
        initializeDAOs();

        // Create main split pane
        mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        mainSplitPane.setDividerLocation(250);
        mainSplitPane.setDividerSize(3);
        mainSplitPane.setBorder(null);

        // Create sidebar
        createSidebar();

        // Create main content area
        createMainContent();

        mainSplitPane.setLeftComponent(pnSidebar);
        mainSplitPane.setRightComponent(pnMainContent);

        this.add(mainSplitPane);
    }

    private void initializeDAOs() {
        authService = new AuthService();
        taiKhoanService = new TaiKhoanService();
        khoaService = new KhoaService();
        giangVienService = new GiangVienService();
        sinhVienService = new SinhVienService();
        monHocService = new MonHocService();
        lopHocPhanService = new LopHocPhanService();
        dangKyLopService = new DangKyLopService();
        diemService = new DiemService();
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

        JLabel lblTitle = new JLabel("ADMIN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(PRIMARY_COLOR);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblUserInfo = new JLabel();
        lblUserInfo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblUserInfo.setForeground(SECONDARY_COLOR);
        lblUserInfo.setHorizontalAlignment(SwingConstants.CENTER);

        if (taiKhoan != null) {
            lblUserInfo.setText(taiKhoan.getTenDangNhap());
        }

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(HEADER_BG);
        titlePanel.add(lblTitle, BorderLayout.CENTER);
        titlePanel.add(lblUserInfo, BorderLayout.SOUTH);
        headerPanel.add(titlePanel, BorderLayout.CENTER);
        pnSidebar.add(headerPanel, BorderLayout.NORTH);

        // Menu items
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(7, 1, 0, 2)); // Giảm từ 8 xuống 7
        menuPanel.setBackground(SIDEBAR_BG);
        menuPanel.setBorder(new EmptyBorder(20, 15, 20, 15));

        String[] menuItems = {
                "TÀI KHOẢN", "GIẢNG VIÊN",
                "SINH VIÊN", "MÔN HỌC", "LỚP HỌC PHẦN", "ĐĂNG KÝ LỚP"
        };

        for (String item : menuItems) {
            JButton menuButton = createMenuButton(item);
            menuPanel.add(menuButton);

            menuButton.addActionListener(e -> {
                switchPanel(item.replace(" ", "").toUpperCase());
                highlightActiveButton(menuButton);
            });
        }

        // NÚT ĐĂNG XUẤT
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

    // COMPACT LOGOUT BUTTON
    private JButton createCompactLogoutButton() {
        JButton button = new JButton("🚪 ĐĂNG XUẤT");
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(Color.red);
        button.setBackground(SIDEBAR_BG);
        button.setBorder(new EmptyBorder(8, 10, 8, 10));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addActionListener(e -> confirmAndLogout());
        return button;
    }
    private void confirmAndLogout() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn đăng xuất?",
                "Xác nhận đăng xuất",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            // Reset highlight sidebar trước khi đăng xuất
            resetSidebarHighlight();

            // CẬP NHẬT TRẠNG THÁI OFFLINE
            updateUserStatusToOffline();

            // Đóng frame hiện tại
            this.dispose();

            // Mở lại màn hình login
            SwingUtilities.invokeLater(() -> {
                new Login().setVisible(true);
            });
        }
    }

    // PHƯƠNG THỨC CẬP NHẬT TRẠNG THÁI OFFLINE
    private void updateUserStatusToOffline() {
        if (taiKhoan != null) {
            try {
                // Debug: Kiểm tra token trước logout
                String currentToken = until.ApiClient.getJwtToken();
                if (currentToken != null && !currentToken.isEmpty()) {
                    System.out.println("🔑 Token hiện tại trước logout: " + currentToken.substring(0, Math.min(30, currentToken.length())) + "...");
                } else {
                    System.err.println("⚠️ WARNING: Token bị null lúc logout!");
                }
                
                boolean success = authService.capNhatTrangThaiOffline(taiKhoan.getTenDangNhap());
                if (success) {
                    System.out.println("✅ Đã cập nhật trạng thái offline cho: " + taiKhoan.getTenDangNhap());
                } else {
                    System.err.println("❌ Lỗi khi cập nhật trạng thái offline!");
                }
            } catch (Exception ex) {
                System.err.println("❌ Lỗi khi cập nhật trạng thái: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(SIDEBAR_FONT);
        button.setForeground(new Color(60, 60, 60));
        button.setBackground(SIDEBAR_BG);
        button.setBorder(new CompoundBorder(
                new EmptyBorder(12, 15, 12, 15),
                new MatteBorder(0, 0, 1, 0, new Color(240, 240, 240))
        ));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hiệu ứng hover - SỬA LẠI: Không thay đổi khi đang active
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (!button.getBackground().equals(SIDEBAR_SELECTED)) {
                    button.setBackground(SIDEBAR_HOVER);
                    button.setForeground(PRIMARY_COLOR);
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!button.getBackground().equals(SIDEBAR_SELECTED)) {
                    button.setBackground(SIDEBAR_BG);
                    button.setForeground(new Color(60, 60, 60));
                }
            }
        });

        return button;
    }

    // COMPACT MENU BUTTON - nhỏ gọn hơn với icon
    private JButton createCompactMenuButton(String icon, String text) {
        JButton button = new JButton(icon + " " + text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Font nhỏ hơn
        button.setForeground(new Color(60, 60, 60));
        button.setBackground(SIDEBAR_BG);
        button.setBorder(new CompoundBorder(
                new EmptyBorder(8, 10, 8, 10), // Padding nhỏ hơn
                new MatteBorder(0, 0, 1, 0, new Color(240, 240, 240))
        ));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hiệu ứng hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (!button.getBackground().equals(SIDEBAR_SELECTED)) {
                    button.setBackground(SIDEBAR_HOVER);
                    button.setForeground(PRIMARY_COLOR);
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!button.getBackground().equals(SIDEBAR_SELECTED)) {
                    button.setBackground(SIDEBAR_BG);
                    button.setForeground(new Color(60, 60, 60));
                }
            }
        });

        return button;
    }

    private void highlightActiveButton(JButton activeButton) {
        // Reset tất cả các nút menu về trạng thái ban đầu
        Component[] components = ((JPanel) pnSidebar.getComponent(1)).getComponents();
        for (Component comp : components) {
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

        // Highlight nút đang active (chỉ nếu không phải nút đăng xuất)
        if (!activeButton.getText().contains("ĐĂNG XUẤT")) {
            activeButton.setBackground(new Color(232, 240, 254));
            activeButton.setForeground(PRIMARY_COLOR);
            activeButton.setBorder(new CompoundBorder(
                    new MatteBorder(0, 4, 1, 0, PRIMARY_COLOR),
                    new EmptyBorder(12, 16, 12, 15)
            ));
        }
    }


    private void createMainContent() {
        pnMainContent = new JPanel(new BorderLayout());
        pnMainContent.setBackground(LIGHT_BG);
        pnMainContent.setBorder(new EmptyBorder(0, 0, 0, 0));

        // Show default panel (TaiKhoan)
        showTaiKhoanPanel();
    }

    private void switchPanel(String panelName) {
        currentPanel = panelName;
        pnMainContent.removeAll();

        switch (panelName) {
            case "TÀIKHOẢN":
                showTaiKhoanPanel();
                break;
            case "GIẢNGVIÊN":
                showGiangVienPanel();
                break;
            case "SINHVIÊN":
                showSinhVienPanel();
                break;
            case "MÔNHỌC":
                showMonHocPanel();
                break;
            case "LỚPHỌCPHẦN":
                showLopHocPhanPanel();
                break;
            case "ĐĂNGKÝLỚP":
                showDangKyLopPanel();
                break;
        }

        pnMainContent.revalidate();
        pnMainContent.repaint();
    }
    private void resetSidebarHighlight() {
        JPanel menuPanel = (JPanel) pnSidebar.getComponent(1);
        for (Component comp : menuPanel.getComponents()) {
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

        // Nút Tải lại Database duy nhất
        JButton btnReloadDB = createStyledButton("Tải lại", SECONDARY_COLOR);
        btnReloadDB.setPreferredSize(new Dimension(160, 35));

        btnReloadDB.addActionListener(e -> reloadCurrentPanelData());

        JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        headerRight.setBackground(LIGHT_BG);
        headerRight.add(btnReloadDB);

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(headerRight, BorderLayout.EAST);

        panel.add(headerPanel, BorderLayout.NORTH);

        return panel;
    }

    // Phương thức tải lại dữ liệu cho panel hiện tại
    private void reloadCurrentPanelData() {
        switch (currentPanel) {
            case "TÀIKHOẢN":
                loadTaiKhoanData();
                break;
            case "GIẢNGVIÊN":
                loadGiangVienData();
                break;
            case "SINHVIÊN":
                loadSinhVienData();
                break;
            case "MÔNHỌC":
                loadMonHocData();
                break;
            case "LỚPHỌCPHẦN":
                loadLopHocPhanData();
                break;
            case "ĐĂNGKÝLỚP":
                loadDangKyData();
                break;
        }
        JOptionPane.showMessageDialog(this, "Đã tải lại dữ liệu thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
    }

    // ==================== HELPER METHODS ====================
    
    /**
     * Helper method để convert view row index sang model row index
     * Tránh lỗi "row index is bigger than sorter's row count"
     */
    private int getModelRowIndex(JTable table, int viewRowIndex) {
        if (viewRowIndex == -1) return -1;
        if (table.getRowSorter() != null) {
            return table.getRowSorter().convertRowIndexToModel(viewRowIndex);
        }
        return viewRowIndex;
    }
    
    /**
     * Helper method để lấy giá trị từ table một cách an toàn
     * Tự động convert view index sang model index
     */
    private Object getTableValueAt(JTable table, int viewRowIndex, int columnIndex) {
        int modelRow = getModelRowIndex(table, viewRowIndex);
        if (modelRow == -1) return null;
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        return model.getValueAt(modelRow, columnIndex);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setBorder(new EmptyBorder(12, 25, 12, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(150, 45));
        button.setMinimumSize(new Dimension(130, 45));
        button.setOpaque(true);

        return button;
    }

    private JTable createStyledTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setFont(NORMAL_FONT);
        table.setRowHeight(35);
        
        // Thêm TableRowSorter để có thể sort
        table.setAutoCreateRowSorter(true);
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
                        c.setBackground(new Color(248, 250, 252));
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

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setFont(NORMAL_FONT);
        textField.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1),
                new EmptyBorder(10, 12, 10, 12)
        ));
        textField.setPreferredSize(new Dimension(250, 40));
        textField.setBackground(new Color(248, 249, 250));
        return textField;
    }

    private JPanel createSearchPanel(JTable table) {
        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setBackground(CARD_BG);
        searchPanel.setBorder(new EmptyBorder(10, 15, 10, 15));

        // Left: Label
        JLabel searchLabel = new JLabel("Tìm kiếm:");
        searchLabel.setFont(BOLD_FONT);
        searchLabel.setForeground(new Color(80, 80, 80));

        // Center: TextField
        JTextField searchField = new JTextField();
        searchField.setFont(NORMAL_FONT);
        searchField.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1),
                new EmptyBorder(10, 12, 10, 12)
        ));
        searchField.setPreferredSize(new Dimension(250, 40));
        searchField.setBackground(Color.WHITE);

        // Right: Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttonPanel.setBackground(CARD_BG);

        JButton searchButton = createStyledButton("Tìm", PRIMARY_COLOR);
        searchButton.setPreferredSize(new Dimension(80, 40));

        JButton clearButton = createStyledButton("Xóa", new Color(158, 158, 158));
        clearButton.setPreferredSize(new Dimension(80, 40));

        buttonPanel.add(searchButton);
        buttonPanel.add(clearButton);

        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(buttonPanel, BorderLayout.EAST);

        // Search functionality
        if (table != null && table.getModel() instanceof DefaultTableModel) {
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) table.getModel());
            table.setRowSorter(sorter);

            searchButton.addActionListener(e -> {
                String text = searchField.getText().trim();
                if (text.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    try {
                        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Lỗi tìm kiếm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            clearButton.addActionListener(e -> {
                searchField.setText("");
                sorter.setRowFilter(null);
            });

            searchField.addActionListener(e -> searchButton.doClick());
        }

        return searchPanel;
    }

    private void showTaiKhoanPanel() {
        JPanel contentPanel = createContentPanel("QUẢN LÝ TÀI KHOẢN");

        // LAYOUT: Search + Buttons + Table
        JPanel mainPanel = new JPanel(new BorderLayout(0, 10));
        mainPanel.setBackground(LIGHT_BG);

        // Table
        String[] columnNames = {"Tên đăng nhập", "Mật khẩu", "Loại người dùng", "Trạng thái"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblTaiKhoan = createStyledTable(model);

        // TOP: Search + Buttons
        JPanel topPanel = new JPanel(new BorderLayout(0, 10));
        topPanel.setBackground(LIGHT_BG);

        JPanel searchPanel = createSearchPanel(tblTaiKhoan);

        // Buttons panel - với border đẹp hơn
        JPanel buttonCard = new JPanel(new BorderLayout());
        buttonCard.setBackground(CARD_BG);
        buttonCard.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1),
                new EmptyBorder(10, 15, 10, 15)
        ));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.setBackground(CARD_BG);

        JButton btnThem = createStyledButton("Thêm Mới", SUCCESS_COLOR);
        JButton btnSua = createStyledButton("Chỉnh Sửa", WARNING_COLOR);
        JButton btnXoa = createStyledButton("Xóa", DANGER_COLOR);
        JButton btnExport = createStyledButton("Xuất Excel", new Color(67, 160, 71));

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnExport);

        buttonCard.add(buttonPanel, BorderLayout.CENTER);

        topPanel.add(searchPanel, BorderLayout.NORTH);
        topPanel.add(buttonCard, BorderLayout.SOUTH);

        // CENTER: Table
        JScrollPane scrollPane = createStyledScrollPane(tblTaiKhoan);
        JPanel tableCard = createCardPanel("DANH SÁCH TÀI KHOẢN", scrollPane);

        // Assemble
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(tableCard, BorderLayout.CENTER);

        contentPanel.add(mainPanel, BorderLayout.CENTER);
        pnMainContent.add(contentPanel, BorderLayout.CENTER);

        loadTaiKhoanData();

        // Events
        btnThem.addActionListener(e -> themTaiKhoan());
        btnSua.addActionListener(e -> suaTaiKhoan());
        btnXoa.addActionListener(e -> xoaTaiKhoan());
        btnExport.addActionListener(e -> {
            try {
                ExcelExporter.exportTable(tblTaiKhoan, this, "DanhSachTaiKhoan");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi xuất Excel: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void showKhoaPanel() {
        JPanel contentPanel = createContentPanel("QUẢN LÝ KHOA");

        // LAYOUT: TABLE Ở TRÊN - FORM Ở DƯỚI
        JPanel mainPanel = new JPanel(new BorderLayout(0, 10));
        mainPanel.setBackground(LIGHT_BG);

        // Table
        String[] columnNames = {"Mã khoa", "Tên khoa"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblKhoa = createStyledTable(model);

        // TOP: Search + Table
        JPanel topPanel = new JPanel(new BorderLayout(0, 10));
        topPanel.setBackground(LIGHT_BG);

        JPanel searchPanel = createSearchPanel(tblKhoa);
        JScrollPane scrollPane = createStyledScrollPane(tblKhoa);
        JPanel tableCard = createCardPanel("DANH SÁCH KHOA", scrollPane);

        topPanel.add(searchPanel, BorderLayout.NORTH);
        topPanel.add(tableCard, BorderLayout.CENTER);

        // BOTTOM: Form + Buttons
        JPanel bottomPanel = new JPanel(new BorderLayout(0, 0));
        bottomPanel.setBackground(LIGHT_BG);

        JPanel formCard = new JPanel(new BorderLayout(0, 10));
        formCard.setBackground(CARD_BG);
        formCard.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1),
                new EmptyBorder(15, 20, 15, 20)
        ));

        // Form - GridBagLayout
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(CARD_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JTextField txtMaKhoa = createStyledTextField();
        JTextField txtTenKhoa = createStyledTextField();

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        formPanel.add(new JLabel("Mã khoa:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.3;
        formPanel.add(txtMaKhoa, gbc);

        gbc.gridx = 2; gbc.weightx = 0;
        formPanel.add(new JLabel("Tên khoa:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.7;
        formPanel.add(txtTenKhoa, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.setBackground(CARD_BG);

        JButton btnThem = createStyledButton("Thêm", SUCCESS_COLOR);
        JButton btnSua = createStyledButton("Sửa", WARNING_COLOR);
        JButton btnXoa = createStyledButton("Xóa", DANGER_COLOR);
        JButton btnClear = createStyledButton("Làm mới", new Color(158, 158, 158));
        JButton btnExport = createStyledButton("Xuất Excel", new Color(67, 160, 71));

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnExport);

        formCard.add(formPanel, BorderLayout.CENTER);
        formCard.add(buttonPanel, BorderLayout.SOUTH);

        bottomPanel.add(formCard, BorderLayout.CENTER);

        // Assemble
        mainPanel.add(topPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        contentPanel.add(mainPanel, BorderLayout.CENTER);
        pnMainContent.add(contentPanel, BorderLayout.CENTER);

        loadKhoaData();

        // Events
        tblKhoa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tblKhoa.getSelectedRow();
                int modelRow = getModelRowIndex(tblKhoa, selectedRow);
                if (modelRow != -1) {
                    DefaultTableModel tableModel = (DefaultTableModel) tblKhoa.getModel();
                    txtMaKhoa.setText(tableModel.getValueAt(modelRow, 0).toString());
                    txtTenKhoa.setText(tableModel.getValueAt(modelRow, 1).toString());
                    txtMaKhoa.setEnabled(false);
                }
            }
        });

        btnThem.addActionListener(e -> {
            String maKhoa = txtMaKhoa.getText().trim();
            String tenKhoa = txtTenKhoa.getText().trim();
            if (maKhoa.isEmpty() || tenKhoa.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                if (khoaService.themKhoa(new Khoa(maKhoa, tenKhoa))) {
                    JOptionPane.showMessageDialog(this, "Thêm khoa thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    loadKhoaData();
                    txtMaKhoa.setText("");
                    txtTenKhoa.setText("");
                }
            } catch (RuntimeException ex) {
                // Hiển thị thông báo lỗi cụ thể
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Thêm khoa thất bại: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnSua.addActionListener(e -> {
            if (tblKhoa.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn khoa cần sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String maKhoa = txtMaKhoa.getText().trim();
            String tenKhoa = txtTenKhoa.getText().trim();
            if (tenKhoa.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên khoa không được để trống!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (khoaService.capNhatKhoa(new Khoa(maKhoa, tenKhoa))) {
                JOptionPane.showMessageDialog(this, "Cập nhật khoa thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                loadKhoaData();
                txtMaKhoa.setText("");
                txtTenKhoa.setText("");
                txtMaKhoa.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật khoa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnXoa.addActionListener(e -> {
            int selectedRow = tblKhoa.getSelectedRow();
            int modelRow = getModelRowIndex(tblKhoa, selectedRow);
            if (modelRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn khoa cần xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            DefaultTableModel tableModel = (DefaultTableModel) tblKhoa.getModel();
            String maKhoa = tableModel.getValueAt(modelRow, 0).toString();
            String tenKhoa = tableModel.getValueAt(modelRow, 1).toString();
            
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc muốn xóa khoa '" + tenKhoa + "' (Mã: " + maKhoa + ")?\n" +
                    "Lưu ý: Chỉ có thể xóa khoa chưa có sinh viên, giảng viên hoặc môn học.",
                    "Xác nhận xóa khoa", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    if (khoaService.xoaKhoa(maKhoa)) {
                        JOptionPane.showMessageDialog(this, "Xóa khoa thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        loadKhoaData();
                        txtMaKhoa.setText("");
                        txtTenKhoa.setText("");
                        txtMaKhoa.setEnabled(true);
                        tblKhoa.clearSelection();
                    }
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Không thể xóa", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Xóa khoa thất bại: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnClear.addActionListener(e -> {
            txtMaKhoa.setText("");
            txtTenKhoa.setText("");
            txtMaKhoa.setEnabled(true);
            tblKhoa.clearSelection();
        });

        btnExport.addActionListener(e -> {
            try {
                ExcelExporter.exportTable(tblKhoa, this, "DanhSachKhoa");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi xuất Excel: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void showGiangVienPanel() {
        JPanel contentPanel = createContentPanel("QUẢN LÝ GIẢNG VIÊN");

        // LAYOUT: TABLE Ở TRÊN - FORM Ở DƯỚI
        JPanel mainPanel = new JPanel(new BorderLayout(0, 10));
        mainPanel.setBackground(LIGHT_BG);

        // Table
        String[] columnNames = {"Mã GV", "Họ tên", "Tên đăng nhập", "Email", "Số điện thoại", "Mã khoa"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblGiangVien = createStyledTable(model);

        // TOP: Search + Table
        JPanel topPanel = new JPanel(new BorderLayout(0, 10));
        topPanel.setBackground(LIGHT_BG);

        JPanel searchPanel = createSearchPanel(tblGiangVien);
        JScrollPane scrollPane = createStyledScrollPane(tblGiangVien);
        JPanel tableCard = createCardPanel("DANH SÁCH GIẢNG VIÊN", scrollPane);

        topPanel.add(searchPanel, BorderLayout.NORTH);
        topPanel.add(tableCard, BorderLayout.CENTER);

        // BOTTOM: Form + Buttons
        JPanel bottomPanel = new JPanel(new BorderLayout(0, 0));
        bottomPanel.setBackground(LIGHT_BG);

        JPanel formCard = new JPanel(new BorderLayout(0, 10));
        formCard.setBackground(CARD_BG);
        formCard.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1),
                new EmptyBorder(15, 20, 15, 20)
        ));

        // Form - GridBagLayout để căn chỉnh đẹp
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(CARD_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JTextField txtMaGV = createStyledTextField();
        JTextField txtHoTen = createStyledTextField();
        JTextField txtTenDangNhap = createStyledTextField(); // ← THÊM TRƯỜNG USERNAME
        JTextField txtEmail = createStyledTextField();
        JTextField txtSoDT = createStyledTextField();
        JComboBox<String> cboKhoa = new JComboBox<>();
        cboKhoa.setFont(NORMAL_FONT);
        cboKhoa.setPreferredSize(new Dimension(250, 40));

        // Row 1
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        formPanel.add(new JLabel("Mã GV:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.15;
        formPanel.add(txtMaGV, gbc);

        gbc.gridx = 2; gbc.weightx = 0;
        formPanel.add(new JLabel("Họ tên:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.25;
        formPanel.add(txtHoTen, gbc);

        gbc.gridx = 4; gbc.weightx = 0;
        formPanel.add(new JLabel("Tên đăng nhập:"), gbc); // ← THÊM LABEL
        gbc.gridx = 5; gbc.weightx = 0.2;
        formPanel.add(txtTenDangNhap, gbc); // ← THÊM TEXTFIELD

        // Row 2
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.25;
        formPanel.add(txtEmail, gbc);

        gbc.gridx = 2; gbc.weightx = 0;
        formPanel.add(new JLabel("SĐT:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.15;
        formPanel.add(txtSoDT, gbc);

        gbc.gridx = 4; gbc.weightx = 0;
        formPanel.add(new JLabel("Khoa:"), gbc);
        gbc.gridx = 5; gbc.weightx = 0.2;
        formPanel.add(cboKhoa, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.setBackground(CARD_BG);

        JButton btnThem = createStyledButton("Thêm", SUCCESS_COLOR);
        JButton btnSua = createStyledButton("Sửa", WARNING_COLOR);
        JButton btnXoa = createStyledButton("Xóa", DANGER_COLOR);
        JButton btnClear = createStyledButton("Làm mới", new Color(158, 158, 158));
        JButton btnExport = createStyledButton("Xuất Excel", new Color(67, 160, 71));

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnExport);

        formCard.add(formPanel, BorderLayout.CENTER);
        formCard.add(buttonPanel, BorderLayout.SOUTH);

        bottomPanel.add(formCard, BorderLayout.CENTER);

        // Assemble
        mainPanel.add(topPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        contentPanel.add(mainPanel, BorderLayout.CENTER);
        pnMainContent.add(contentPanel, BorderLayout.CENTER);

        // Load data và combobox
        loadGiangVienData();
        loadKhoaIntoComboBox(cboKhoa);

        // Events
        tblGiangVien.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tblGiangVien.getSelectedRow();
                if (selectedRow != -1) {
                    txtMaGV.setText(getTableValueAt(tblGiangVien, selectedRow, 0).toString());
                    txtHoTen.setText(getTableValueAt(tblGiangVien, selectedRow, 1).toString());
                    Object tenDangNhap = getTableValueAt(tblGiangVien, selectedRow, 2); // ← THÊM DÒNG NÀY
                    txtTenDangNhap.setText(tenDangNhap != null ? tenDangNhap.toString() : ""); // ← THÊM DÒNG NÀY
                    Object email = getTableValueAt(tblGiangVien, selectedRow, 3); // ← SỬA INDEX
                    txtEmail.setText(email != null ? email.toString() : "");
                    Object soDT = getTableValueAt(tblGiangVien, selectedRow, 4); // ← SỬA INDEX
                    txtSoDT.setText(soDT != null ? soDT.toString() : "");
                    String maKhoa = getTableValueAt(tblGiangVien, selectedRow, 5).toString(); // ← SỬA INDEX
                    cboKhoa.setSelectedItem(maKhoa);
                    txtMaGV.setEnabled(false); // Chỉ disable khi sửa
                }
            }
        });

        btnThem.addActionListener(e -> {
            String maGV = txtMaGV.getText().trim();
            String hoTen = txtHoTen.getText().trim();
            String tenDangNhap = txtTenDangNhap.getText().trim(); // ← THÊM DÒNG NÀY
            String email = txtEmail.getText().trim();
            String soDT = txtSoDT.getText().trim();
            String maKhoa = cboKhoa.getSelectedItem() != null ? cboKhoa.getSelectedItem().toString() : "";

            if (maGV.isEmpty() || hoTen.isEmpty() || tenDangNhap.isEmpty()) { // ← SỬA DÒNG NÀY
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin bắt buộc (Mã GV, Họ tên, Tên đăng nhập)!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (maKhoa.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn Khoa cho giảng viên!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Kiểm tra mã GV đã tồn tại chưa
            if (giangVienService.getGiangVienById(maGV) != null) {
                JOptionPane.showMessageDialog(this, "Mã giảng viên đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validation email (nếu có)
            if (!email.isEmpty() && !isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "Email không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            GiangVien gv = new GiangVien();
            gv.setMaGV(maGV);
            gv.setHoTen(hoTen);
            gv.setTenDangNhap(tenDangNhap); // ← THÊM DÒNG NÀY
            gv.setEmail(email);
            gv.setSoDienThoai(soDT);
            gv.setKhoa(maKhoa);

            try {
                if (giangVienService.themGiangVien(gv)) {
                    JOptionPane.showMessageDialog(this, "Thêm giảng viên thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    loadGiangVienData();
                    clearForm(txtMaGV, txtHoTen, txtTenDangNhap, txtEmail, txtSoDT);
                    txtMaGV.setEnabled(true);
                    if (cboKhoa.getItemCount() > 0) cboKhoa.setSelectedIndex(0);
                }
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Thêm giảng viên thất bại: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnSua.addActionListener(e -> {
            int selectedRow = tblGiangVien.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn giảng viên cần sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String maGV = txtMaGV.getText().trim();
            String hoTen = txtHoTen.getText().trim();
            String tenDangNhap = txtTenDangNhap.getText().trim(); // ← THÊM DÒNG NÀY
            String email = txtEmail.getText().trim();
            String soDT = txtSoDT.getText().trim();
            String maKhoa = cboKhoa.getSelectedItem() != null ? cboKhoa.getSelectedItem().toString() : "";

            if (maGV.isEmpty() || hoTen.isEmpty() || tenDangNhap.isEmpty()) { // ← SỬA DÒNG NÀY
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin bắt buộc (Mã GV, Họ tên, Tên đăng nhập)!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (maKhoa.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn Khoa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validation email (nếu có)
            if (!email.isEmpty() && !isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "Email không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            GiangVien gv = new GiangVien();
            gv.setMaGV(maGV);
            gv.setHoTen(hoTen);
            gv.setTenDangNhap(tenDangNhap); // ← THÊM DÒNG NÀY
            gv.setEmail(email);
            gv.setSoDienThoai(soDT);
            gv.setKhoa(maKhoa);

            if (giangVienService.capNhatGiangVien(gv)) {
                JOptionPane.showMessageDialog(this, "Cập nhật giảng viên thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                loadGiangVienData();
                clearForm(txtMaGV, txtHoTen, txtTenDangNhap, txtEmail, txtSoDT); // ← SỬA DÒNG NÀY
                if (cboKhoa.getItemCount() > 0) cboKhoa.setSelectedIndex(0);
                txtMaGV.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật giảng viên thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnXoa.addActionListener(e -> {
            int selectedRow = tblGiangVien.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn giảng viên cần xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String maGV = tblGiangVien.getValueAt(selectedRow, 0).toString();
            String hoTen = tblGiangVien.getValueAt(selectedRow, 1).toString();

            // Kiểm tra xem giảng viên có đang dạy lớp học phần nào không
            if (lopHocPhanService.kiemTraGiangVienCoLopHocPhan(maGV)) {
                JOptionPane.showMessageDialog(this,
                        "Không thể xóa giảng viên '" + hoTen + "' vì đang có lớp học phần!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);

                return;
            }

            // Xác nhận xóa
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc muốn xóa giảng viên '" + hoTen + "' và tài khoản tương ứng?",
                    "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                if (giangVienService.xoaGiangVien(maGV)) {
                    JOptionPane.showMessageDialog(this, "Xóa giảng viên và tài khoản thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    loadGiangVienData();
                    loadTaiKhoanData(); // Reload cả tài khoản
                    clearForm(txtMaGV, txtHoTen, txtTenDangNhap, txtEmail, txtSoDT);
                    txtMaGV.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa giảng viên thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnClear.addActionListener(e -> {
            clearForm(txtMaGV, txtHoTen, txtTenDangNhap, txtEmail, txtSoDT);
            if (cboKhoa.getItemCount() > 0) cboKhoa.setSelectedIndex(0);
            txtMaGV.setEnabled(true);
            tblGiangVien.clearSelection();
        });

        btnExport.addActionListener(e -> {
            try {
                ExcelExporter.exportTable(tblGiangVien, this, "DanhSachGiangVien");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi xuất Excel: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // ==================== PHƯƠNG THỨC KIỂM TRA EMAIL ====================
    private boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) return true;
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }

    private void showSinhVienPanel() {
        JPanel contentPanel = createContentPanel("QUẢN LÝ SINH VIÊN");

        // LAYOUT: TABLE Ở TRÊN - FORM Ở DƯỚI
        JPanel mainPanel = new JPanel(new BorderLayout(0, 10));
        mainPanel.setBackground(LIGHT_BG);

        // Table
        String[] columnNames = {"Mã SV", "Họ tên", "Tên đăng nhập", "Giới tính", "Ngày sinh", "Email", "Số ĐT", "Địa chỉ", "Mã khoa"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblSinhVien = createStyledTable(model);

        // TOP: Search + Table
        JPanel topPanel = new JPanel(new BorderLayout(0, 10));
        topPanel.setBackground(LIGHT_BG);

        JPanel searchPanel = createSearchPanel(tblSinhVien);
        JScrollPane scrollPane = createStyledScrollPane(tblSinhVien);
        JPanel tableCard = createCardPanel("DANH SÁCH SINH VIÊN", scrollPane);

        topPanel.add(searchPanel, BorderLayout.NORTH);
        topPanel.add(tableCard, BorderLayout.CENTER);

        // BOTTOM: Form + Buttons
        JPanel bottomPanel = new JPanel(new BorderLayout(0, 0));
        bottomPanel.setBackground(LIGHT_BG);

        JPanel formCard = new JPanel(new BorderLayout(0, 10));
        formCard.setBackground(CARD_BG);
        formCard.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1),
                new EmptyBorder(15, 20, 15, 20)
        ));

        // Form - GridBagLayout 2 rows
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(CARD_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JTextField txtMaSV = createStyledTextField();
        JTextField txtHoTen = createStyledTextField();
        JTextField txtTenDangNhap = createStyledTextField(); // ← THÊM TRƯỜNG USERNAME
        JComboBox<String> cboGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"});
        cboGioiTinh.setFont(NORMAL_FONT);
        cboGioiTinh.setPreferredSize(new Dimension(100, 40));
        JTextField txtNgaySinh = createStyledTextField();
        JTextField txtEmail = createStyledTextField();
        JTextField txtSoDT = createStyledTextField();
        JTextField txtDiaChi = createStyledTextField();
        JComboBox<String> cboKhoaSV = new JComboBox<>();
        cboKhoaSV.setFont(NORMAL_FONT);
        cboKhoaSV.setPreferredSize(new Dimension(150, 40));

        // Row 1
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        formPanel.add(new JLabel("Mã SV:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.15;
        formPanel.add(txtMaSV, gbc);

        gbc.gridx = 2; gbc.weightx = 0;
        formPanel.add(new JLabel("Họ tên:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.25;
        formPanel.add(txtHoTen, gbc);

        gbc.gridx = 4; gbc.weightx = 0;
        formPanel.add(new JLabel("Tên đăng nhập:"), gbc); // ← THÊM LABEL
        gbc.gridx = 5; gbc.weightx = 0.2;
        formPanel.add(txtTenDangNhap, gbc); // ← THÊM TEXTFIELD

        gbc.gridx = 6; gbc.weightx = 0;
        formPanel.add(new JLabel("Giới tính:"), gbc);
        gbc.gridx = 7; gbc.weightx = 0.1;
        formPanel.add(cboGioiTinh, gbc);

        // Row 2
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        formPanel.add(new JLabel("Ngày sinh:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.15;
        formPanel.add(txtNgaySinh, gbc);
        // Row 2
        gbc.gridx = 2; gbc.gridy = 1; gbc.weightx = 0;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.25;
        formPanel.add(txtEmail, gbc);

        gbc.gridx = 4; gbc.weightx = 0;
        formPanel.add(new JLabel("SĐT:"), gbc);
        gbc.gridx = 5; gbc.weightx = 0.15;
        formPanel.add(txtSoDT, gbc);

        gbc.gridx = 6; gbc.weightx = 0;
        formPanel.add(new JLabel("Địa chỉ:"), gbc);
        gbc.gridx = 7; gbc.weightx = 0.25;
        formPanel.add(txtDiaChi, gbc);

        // Row 3
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        formPanel.add(new JLabel("Khoa:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.15;
        formPanel.add(cboKhoaSV, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.setBackground(CARD_BG);

        JButton btnThem = createStyledButton("Thêm", SUCCESS_COLOR);
        JButton btnSua = createStyledButton("Sửa", WARNING_COLOR);
        JButton btnXoa = createStyledButton("Xóa", DANGER_COLOR);
        JButton btnClear = createStyledButton("Làm mới", new Color(158, 158, 158));
        JButton btnExport = createStyledButton("Xuất Excel", new Color(67, 160, 71));

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnExport);

        formCard.add(formPanel, BorderLayout.CENTER);
        formCard.add(buttonPanel, BorderLayout.SOUTH);

        bottomPanel.add(formCard, BorderLayout.CENTER);

        // Assemble
        mainPanel.add(topPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        contentPanel.add(mainPanel, BorderLayout.CENTER);
        pnMainContent.add(contentPanel, BorderLayout.CENTER);

        // Load dữ liệu và combobox
        loadSinhVienData();
        loadKhoaIntoComboBox(cboKhoaSV);

        // Thêm sinh viên
        btnThem.addActionListener(e -> {
            String maSV = txtMaSV.getText().trim();
            String hoTen = txtHoTen.getText().trim();
            String tenDangNhap = txtTenDangNhap.getText().trim(); // ← THÊM DÒNG NÀY
            String gioiTinh = cboGioiTinh.getSelectedItem() != null ? cboGioiTinh.getSelectedItem().toString() : "Nam";
            String ngaySinh = txtNgaySinh.getText().trim();
            String email = txtEmail.getText().trim();
            String soDT = txtSoDT.getText().trim();
            String diaChi = txtDiaChi.getText().trim();
            String maKhoa = cboKhoaSV.getSelectedItem() != null ? cboKhoaSV.getSelectedItem().toString() : "";

            if (maSV.isEmpty() || hoTen.isEmpty() || tenDangNhap.isEmpty() || ngaySinh.isEmpty()) { // ← SỬA DÒNG NÀY
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin bắt buộc (Mã SV, Họ tên, Tên đăng nhập, Ngày sinh)!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!isValidDate(ngaySinh)) {
                JOptionPane.showMessageDialog(this, "Ngày sinh không đúng định dạng (yyyy-MM-dd)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validation email (nếu có)
            if (!email.isEmpty() && !isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "Email không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            SinhVien sv = new SinhVien(maSV, hoTen, gioiTinh, ngaySinh, email, soDT, diaChi, maKhoa);
            sv.setTenDangNhap(tenDangNhap); // ← THÊM DÒNG NÀY

            try {
                if (sinhVienService.themSinhVien(sv)) {
                    JOptionPane.showMessageDialog(this, "Thêm sinh viên thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    loadSinhVienData();
                    clearForm(txtMaSV, txtHoTen, txtTenDangNhap, txtNgaySinh, txtEmail, txtSoDT);
                    txtDiaChi.setText("");
                    txtMaSV.setEnabled(true); // Enable lại để thêm mới
                }
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Thêm sinh viên thất bại: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Sửa sinh viên
        btnSua.addActionListener(e -> {
            int selectedRow = tblSinhVien.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sinh viên cần sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String maSV = txtMaSV.getText().trim();
            String hoTen = txtHoTen.getText().trim();
            String tenDangNhap = txtTenDangNhap.getText().trim(); // ← THÊM DÒNG NÀY
            String gioiTinh = cboGioiTinh.getSelectedItem() != null ? cboGioiTinh.getSelectedItem().toString() : "Nam";
            String ngaySinh = txtNgaySinh.getText().trim();
            String email = txtEmail.getText().trim();
            String soDT = txtSoDT.getText().trim();
            String diaChi = txtDiaChi.getText().trim();
            String maKhoa = cboKhoaSV.getSelectedItem() != null ? cboKhoaSV.getSelectedItem().toString() : "";

            if (maSV.isEmpty() || hoTen.isEmpty() || tenDangNhap.isEmpty() || ngaySinh.isEmpty()) { // ← SỬA DÒNG NÀY
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin bắt buộc (Mã SV, Họ tên, Tên đăng nhập, Ngày sinh)!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!isValidDate(ngaySinh)) {
                JOptionPane.showMessageDialog(this, "Ngày sinh không đúng định dạng (yyyy-MM-dd)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validation email (nếu có)
            if (!email.isEmpty() && !isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "Email không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            SinhVien sv = new SinhVien(maSV, hoTen, gioiTinh, ngaySinh, email, soDT, diaChi, maKhoa);
            sv.setTenDangNhap(tenDangNhap); // ← THÊM DÒNG NÀY

            if (sinhVienService.capNhatSinhVien(sv)) {
                JOptionPane.showMessageDialog(this, "Cập nhật sinh viên thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                loadSinhVienData();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật sinh viên thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnXoa.addActionListener(e -> {
            int selectedRow = tblSinhVien.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sinh viên cần xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String maSV = tblSinhVien.getValueAt(selectedRow, 0).toString();
            String hoTen = tblSinhVien.getValueAt(selectedRow, 1).toString();

            // Kiểm tra xem sinh viên có đang đăng ký lớp học phần nào không
            if (dangKyLopService.kiemTraSinhVienCoDangKy(maSV)) {
                JOptionPane.showMessageDialog(this,
                        "Không thể xóa sinh viên '" + hoTen + "' vì đang có đăng ký lớp học phần!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Xác nhận xóa
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc muốn xóa sinh viên '" + hoTen + "' và tài khoản tương ứng?",
                    "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                if (sinhVienService.xoaSinhVien(maSV)) {
                    JOptionPane.showMessageDialog(this, "Xóa sinh viên và tài khoản thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    loadSinhVienData();
                    loadTaiKhoanData(); // Reload cả tài khoản
                    clearForm(txtMaSV, txtHoTen, txtTenDangNhap, txtNgaySinh, txtEmail, txtSoDT, txtDiaChi);
                    txtMaSV.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa sinh viên thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnClear.addActionListener(e -> {
            clearForm(txtMaSV, txtHoTen, txtTenDangNhap, txtNgaySinh, txtEmail, txtSoDT, txtDiaChi);
            txtMaSV.setEnabled(true);
            tblSinhVien.clearSelection();
            if (cboKhoaSV.getItemCount() > 0) cboKhoaSV.setSelectedIndex(0);
            if (cboGioiTinh.getItemCount() > 0) cboGioiTinh.setSelectedIndex(0);
        });

        btnExport.addActionListener(e -> exportSinhVienToExcel());

        // Table selection listener
        tblSinhVien.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tblSinhVien.getSelectedRow();
                if (selectedRow != -1) {
                    txtMaSV.setText(tblSinhVien.getValueAt(selectedRow, 0).toString());
                    txtHoTen.setText(tblSinhVien.getValueAt(selectedRow, 1).toString());
                    txtTenDangNhap.setText(tblSinhVien.getValueAt(selectedRow, 2) != null ? tblSinhVien.getValueAt(selectedRow, 2).toString() : ""); // ← THÊM DÒNG NÀY
                    cboGioiTinh.setSelectedItem(tblSinhVien.getValueAt(selectedRow, 3).toString()); // ← SỬA INDEX
                    txtNgaySinh.setText(tblSinhVien.getValueAt(selectedRow, 4).toString()); // ← SỬA INDEX
                    txtEmail.setText(tblSinhVien.getValueAt(selectedRow, 5) != null ? tblSinhVien.getValueAt(selectedRow, 5).toString() : ""); // ← SỬA INDEX
                    txtSoDT.setText(tblSinhVien.getValueAt(selectedRow, 6) != null ? tblSinhVien.getValueAt(selectedRow, 6).toString() : ""); // ← SỬA INDEX
                    txtDiaChi.setText(tblSinhVien.getValueAt(selectedRow, 7) != null ? tblSinhVien.getValueAt(selectedRow, 7).toString() : ""); // ← SỬA INDEX
                    cboKhoaSV.setSelectedItem(tblSinhVien.getValueAt(selectedRow, 8).toString()); // ← SỬA INDEX
                    txtMaSV.setEnabled(false); // Chỉ disable khi sửa
                }
            }
        });
    }

    private JPanel createFormField(String label, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout(15, 8));
        panel.setBackground(CARD_BG);
        panel.setBorder(new EmptyBorder(5, 0, 5, 0));

        JLabel lbl = new JLabel(label);
        lbl.setFont(BOLD_FONT);
        lbl.setForeground(new Color(60, 60, 60));
        lbl.setPreferredSize(new Dimension(120, 35));

        panel.add(lbl, BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);

        return panel;
    }

    private void showMonHocPanel() {
        JPanel contentPanel = createContentPanel("QUẢN LÝ MÔN HỌC");

        // LAYOUT MỚI: TABLE Ở TRÊN - FORM Ở DƯỚI
        JPanel mainPanel = new JPanel(new BorderLayout(0, 10));
        mainPanel.setBackground(LIGHT_BG);

        // Table
        String[] columnNames = {"Mã MH", "Tên MH", "Số tín chỉ", "Mô tả", "Mã khoa"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblMonHoc = createStyledTable(model);

        // TOP: Search + Table
        JPanel topPanel = new JPanel(new BorderLayout(0, 10));
        topPanel.setBackground(LIGHT_BG);

        JPanel searchPanel = createSearchPanel(tblMonHoc);
        JScrollPane scrollPane = createStyledScrollPane(tblMonHoc);
        JPanel tableCard = createCardPanel("DANH SÁCH MÔN HỌC", scrollPane);

        topPanel.add(searchPanel, BorderLayout.NORTH);
        topPanel.add(tableCard, BorderLayout.CENTER);

        // BOTTOM: Form + Buttons
        JPanel bottomPanel = new JPanel(new BorderLayout(0, 0));
        bottomPanel.setBackground(LIGHT_BG);

        JPanel formCard = new JPanel(new BorderLayout(0, 10));
        formCard.setBackground(CARD_BG);
        formCard.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1),
                new EmptyBorder(15, 20, 15, 20)
        ));

        // Form - GridBagLayout
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(CARD_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JTextField txtMaMH = createStyledTextField();
        JTextField txtTenMH = createStyledTextField();
        JTextField txtSoTinChi = createStyledTextField();
        JTextField txtMoTa = createStyledTextField();
        JComboBox<String> cboKhoaMH = new JComboBox<>();
        cboKhoaMH.setFont(NORMAL_FONT);
        cboKhoaMH.setPreferredSize(new Dimension(150, 40));

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        formPanel.add(new JLabel("Mã MH:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.15;
        formPanel.add(txtMaMH, gbc);

        gbc.gridx = 2; gbc.weightx = 0;
        formPanel.add(new JLabel("Tên MH:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.25;
        formPanel.add(txtTenMH, gbc);

        gbc.gridx = 4; gbc.weightx = 0;
        formPanel.add(new JLabel("Số TC:"), gbc);
        gbc.gridx = 5; gbc.weightx = 0.1;
        formPanel.add(txtSoTinChi, gbc);

        gbc.gridx = 6; gbc.weightx = 0;
        formPanel.add(new JLabel("Mô tả:"), gbc);
        gbc.gridx = 7; gbc.weightx = 0.3;
        formPanel.add(txtMoTa, gbc);

        gbc.gridx = 8; gbc.weightx = 0;
        formPanel.add(new JLabel("Khoa:"), gbc);
        gbc.gridx = 9; gbc.weightx = 0.15;
        formPanel.add(cboKhoaMH, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.setBackground(CARD_BG);

        JButton btnThem = createStyledButton("Thêm", SUCCESS_COLOR);
        JButton btnSua = createStyledButton("Sửa", WARNING_COLOR);
        JButton btnXoa = createStyledButton("Xóa", DANGER_COLOR);
        JButton btnClear = createStyledButton("Làm mới", new Color(158, 158, 158));
        JButton btnExport = createStyledButton("Xuất Excel", new Color(67, 160, 71));

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnExport);

        formCard.add(formPanel, BorderLayout.CENTER);
        formCard.add(buttonPanel, BorderLayout.SOUTH);

        bottomPanel.add(formCard, BorderLayout.CENTER);

        // Assemble
        mainPanel.add(topPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        contentPanel.add(mainPanel, BorderLayout.CENTER);
        pnMainContent.add(contentPanel, BorderLayout.CENTER);

        // Load data và combobox
        loadMonHocData();
        loadKhoaIntoComboBox(cboKhoaMH);

        // Events
        btnThem.addActionListener(e -> {
            String maMH = txtMaMH.getText().trim();
            String tenMH = txtTenMH.getText().trim();
            String soTinChiStr = txtSoTinChi.getText().trim();
            String moTa = txtMoTa.getText().trim();
            String maKhoa = cboKhoaMH.getSelectedItem() != null ? cboKhoaMH.getSelectedItem().toString() : "";

            if (maMH.isEmpty() || tenMH.isEmpty() || soTinChiStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (maKhoa.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn Khoa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                int soTinChi = Integer.parseInt(soTinChiStr);

                MonHoc mh = new MonHoc();
                mh.setMaMH(maMH);
                mh.setTenMH(tenMH);
                mh.setSoTinChi(soTinChi);
                mh.setMoTa(moTa);
                mh.setMaKhoa(maKhoa);

                try {
                    if (monHocService.themMonHoc(mh)) {
                        JOptionPane.showMessageDialog(this, "Thêm môn học thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        loadMonHocData();
                        clearForm(txtMaMH, txtTenMH, txtSoTinChi);
                        txtMoTa.setText("");
                        txtMaMH.setEnabled(true); // Enable lại để thêm mới
                    }
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Thêm môn học thất bại: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Số tín chỉ phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnSua.addActionListener(e -> {
            int selectedRow = tblMonHoc.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn môn học cần sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String maMH = tblMonHoc.getValueAt(selectedRow, 0).toString();
            String tenMH = txtTenMH.getText().trim();
            String soTinChiStr = txtSoTinChi.getText().trim();
            String moTa = txtMoTa.getText().trim();
            String maKhoa = cboKhoaMH.getSelectedItem() != null ? cboKhoaMH.getSelectedItem().toString() : "";

            if (tenMH.isEmpty() || soTinChiStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (maKhoa.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn Khoa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                int soTinChi = Integer.parseInt(soTinChiStr);

                MonHoc mh = new MonHoc();
                mh.setMaMH(maMH);
                mh.setTenMH(tenMH);
                mh.setSoTinChi(soTinChi);
                mh.setMoTa(moTa);
                mh.setMaKhoa(maKhoa);

                if (monHocService.capNhatMonHoc(mh)) {
                    JOptionPane.showMessageDialog(this, "Cập nhật môn học thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    loadMonHocData();
                    clearForm(txtMaMH, txtTenMH, txtSoTinChi);
                    txtMoTa.setText("");
                    txtMaMH.setEnabled(true); // Enable lại sau khi sửa
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật môn học thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Số tín chỉ phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnXoa.addActionListener(e -> {
            int selectedRow = tblMonHoc.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn môn học cần xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String maMH = tblMonHoc.getValueAt(selectedRow, 0).toString();
            String tenMH = tblMonHoc.getValueAt(selectedRow, 1).toString();

            // Kiểm tra xem môn học có đang được sử dụng không
            if (monHocService.kiemTraMonHocDuocSuDung(maMH)) {
                JOptionPane.showMessageDialog(this,
                        "Không thể xóa môn học '" + tenMH + "' vì đang được sử dụng trong lớp học phần!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc muốn xóa môn học '" + tenMH + "'?",
                    "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                if (monHocService.xoaMonHoc(maMH)) {
                    JOptionPane.showMessageDialog(this, "Xóa môn học thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    loadMonHocData();
                    clearForm(txtMaMH, txtTenMH, txtSoTinChi);
                    txtMoTa.setText("");
                    txtMaMH.setEnabled(true); // Enable lại sau khi xóa
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa môn học thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnClear.addActionListener(e -> {
            clearForm(txtMaMH, txtTenMH, txtSoTinChi, txtMoTa);
            txtMaMH.setEnabled(true);
            tblMonHoc.clearSelection();
            if (cboKhoaMH.getItemCount() > 0) cboKhoaMH.setSelectedIndex(0);
        });

        btnExport.addActionListener(e -> {
            try {
                ExcelExporter.exportTable(tblMonHoc, this, "DanhSachMonHoc");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi xuất Excel: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Table selection listener
        tblMonHoc.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tblMonHoc.getSelectedRow();
                if (selectedRow != -1) {
                    txtMaMH.setText(tblMonHoc.getValueAt(selectedRow, 0).toString());
                    txtTenMH.setText(tblMonHoc.getValueAt(selectedRow, 1).toString());
                    txtSoTinChi.setText(tblMonHoc.getValueAt(selectedRow, 2).toString());
                    txtMoTa.setText(tblMonHoc.getValueAt(selectedRow, 3) != null ?
                            tblMonHoc.getValueAt(selectedRow, 3).toString() : "");
                    cboKhoaMH.setSelectedItem(tblMonHoc.getValueAt(selectedRow, 4).toString());
                    txtMaMH.setEnabled(false); // Chỉ disable khi sửa
                }
            }
        });
    }

    private void showLopHocPhanPanel() {
        JPanel contentPanel = createContentPanel("QUẢN LÝ LỚP HỌC PHẦN");

        // LAYOUT MỚI: TABLE Ở TRÊN - FORM Ở DƯỚI
        JPanel mainPanel = new JPanel(new BorderLayout(0, 10));
        mainPanel.setBackground(LIGHT_BG);

        // Table
        String[] columnNames = {"Mã lớp", "Mã MH", "Mã GV", "Học kỳ", "Năm học", "Sĩ số tối đa", "Sĩ số hiện tại", "Trạng thái"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblLopHocPhan = createStyledTable(model);

        // TOP: Search + Table
        JPanel topPanel = new JPanel(new BorderLayout(0, 10));
        topPanel.setBackground(LIGHT_BG);

        JPanel searchPanel = createSearchPanel(tblLopHocPhan);
        JScrollPane scrollPane = createStyledScrollPane(tblLopHocPhan);
        JPanel tableCard = createCardPanel("DANH SÁCH LỚP HỌC PHẦN", scrollPane);

        topPanel.add(searchPanel, BorderLayout.NORTH);
        topPanel.add(tableCard, BorderLayout.CENTER);

        // BOTTOM: Form + Buttons
        JPanel bottomPanel = new JPanel(new BorderLayout(0, 0));
        bottomPanel.setBackground(LIGHT_BG);

        JPanel formCard = new JPanel(new BorderLayout(0, 10));
        formCard.setBackground(CARD_BG);
        formCard.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1),
                new EmptyBorder(15, 20, 15, 20)
        ));

        // Form - GridBagLayout
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(CARD_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JTextField txtMaLop = createStyledTextField();
        // Mặc định cho phép nhập mã lớp khi thêm mới
        txtMaLop.setEnabled(true);
        JComboBox<String> cboMonHoc = new JComboBox<>();
        cboMonHoc.setFont(NORMAL_FONT);
        cboMonHoc.setPreferredSize(new Dimension(150, 40));
        JComboBox<String> cboGiangVien = new JComboBox<>();
        cboGiangVien.setFont(NORMAL_FONT);
        cboGiangVien.setPreferredSize(new Dimension(150, 40));
        JTextField txtHocKy = createStyledTextField();
        JTextField txtNamHoc = createStyledTextField();
        JTextField txtSiSoToiDa = createStyledTextField();
        JLabel lblSiSoHienTai = new JLabel("0");
        lblSiSoHienTai.setFont(BOLD_FONT);
        lblSiSoHienTai.setForeground(PRIMARY_COLOR);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        formPanel.add(new JLabel("Mã lớp:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.15;
        formPanel.add(txtMaLop, gbc);

        gbc.gridx = 2; gbc.weightx = 0;
        formPanel.add(new JLabel("Môn học:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.2;
        formPanel.add(cboMonHoc, gbc);

        gbc.gridx = 4; gbc.weightx = 0;
        formPanel.add(new JLabel("Giảng viên:"), gbc);
        gbc.gridx = 5; gbc.weightx = 0.2;
        formPanel.add(cboGiangVien, gbc);

        gbc.gridx = 6; gbc.weightx = 0;
        formPanel.add(new JLabel("Học kỳ:"), gbc);
        gbc.gridx = 7; gbc.weightx = 0.1;
        formPanel.add(txtHocKy, gbc);

        gbc.gridx = 8; gbc.weightx = 0;
        formPanel.add(new JLabel("Năm học:"), gbc);
        gbc.gridx = 9; gbc.weightx = 0.12;
        formPanel.add(txtNamHoc, gbc);

        gbc.gridx = 10; gbc.weightx = 0;
        formPanel.add(new JLabel("SS tối đa:"), gbc);
        gbc.gridx = 11; gbc.weightx = 0.1;
        formPanel.add(txtSiSoToiDa, gbc);

        gbc.gridx = 12; gbc.weightx = 0;
        formPanel.add(new JLabel("SS hiện tại:"), gbc);
        gbc.gridx = 13; gbc.weightx = 0.08;
        formPanel.add(lblSiSoHienTai, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.setBackground(CARD_BG);

        JButton btnThem = createStyledButton("Thêm", SUCCESS_COLOR);
        JButton btnSua = createStyledButton("Sửa", WARNING_COLOR);
        JButton btnXoa = createStyledButton("Xóa", DANGER_COLOR);
        JButton btnDongMo = createStyledButton("Đóng/Mở Lớp", INFO_COLOR);
        JButton btnClear = createStyledButton("Làm mới", new Color(158, 158, 158));
        JButton btnExport = createStyledButton("Xuất Excel", new Color(67, 160, 71));

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnDongMo);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnExport);

        formCard.add(formPanel, BorderLayout.CENTER);
        formCard.add(buttonPanel, BorderLayout.SOUTH);

        bottomPanel.add(formCard, BorderLayout.CENTER);

        // Assemble
        mainPanel.add(topPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        contentPanel.add(mainPanel, BorderLayout.CENTER);
        pnMainContent.add(contentPanel, BorderLayout.CENTER);

        // Load data và combobox
        loadLopHocPhanData();
        loadMonHocIntoComboBox(cboMonHoc);
        loadGiangVienIntoComboBox(cboGiangVien);

        // Events
        btnThem.addActionListener(e -> {
            String maLop = txtMaLop.getText().trim();
            String maMH = cboMonHoc.getSelectedItem() != null ? cboMonHoc.getSelectedItem().toString() : "";
            String maGV = cboGiangVien.getSelectedItem() != null ? cboGiangVien.getSelectedItem().toString() : "";
            String hocKy = txtHocKy.getText().trim();
            String namHoc = txtNamHoc.getText().trim();
            String siSoStr = txtSiSoToiDa.getText().trim();

            if (maLop.isEmpty() || hocKy.isEmpty() || namHoc.isEmpty() || siSoStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (maMH.isEmpty() || maGV.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn Môn học và Giảng viên!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                int siSoToiDa = Integer.parseInt(siSoStr);

                // Kiểm tra sĩ số tối đa
                if (siSoToiDa <= 0) {
                    JOptionPane.showMessageDialog(this, "Sĩ số tối đa phải lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                LopHocPhan lhp = new LopHocPhan();
                lhp.setMaLop(maLop);
                lhp.setMaMH(maMH);
                lhp.setMaGV(maGV);
                lhp.setHocKy(hocKy);
                lhp.setNamHoc(namHoc);
                lhp.setSiSoToiDa(siSoToiDa);

                try {
                    if (lopHocPhanService.themLopHocPhan(lhp)) {
                        JOptionPane.showMessageDialog(this, "Thêm lớp học phần thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        loadLopHocPhanData();
                        clearForm(txtMaLop, txtHocKy, txtNamHoc, txtSiSoToiDa);
                        lblSiSoHienTai.setText("0");
                        // Cho phép nhập mã lớp cho lần thêm tiếp theo
                        txtMaLop.setEnabled(true);
                    }
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Thêm lớp học phần thất bại: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Sĩ số tối đa phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnSua.addActionListener(e -> {
            int selectedRow = tblLopHocPhan.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn lớp học phần cần sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String maLop = tblLopHocPhan.getValueAt(selectedRow, 0).toString();
            String maMH = cboMonHoc.getSelectedItem() != null ? cboMonHoc.getSelectedItem().toString() : "";
            String maGV = cboGiangVien.getSelectedItem() != null ? cboGiangVien.getSelectedItem().toString() : "";
            String hocKy = txtHocKy.getText().trim();
            String namHoc = txtNamHoc.getText().trim();
            String siSoStr = txtSiSoToiDa.getText().trim();
            String siSohienTai = lblSiSoHienTai.getText().trim();

            if (hocKy.isEmpty() || namHoc.isEmpty() || siSoStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (maMH.isEmpty() || maGV.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn Môn học và Giảng viên!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                int siSoToiDa = Integer.parseInt(siSoStr);
                int siSoHienTai = Integer.parseInt(lblSiSoHienTai.getText().trim());

                // Kiểm tra sĩ số tối đa hợp lệ
                if (siSoToiDa < siSoHienTai) {
                    JOptionPane.showMessageDialog(this,
                            "Sĩ số tối đa không được nhỏ hơn sĩ số hiện tại (" + siSoHienTai + ")!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (siSoToiDa <= 0) {
                    JOptionPane.showMessageDialog(this, "Sĩ số tối đa phải lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Tạo đối tượng lớp học phần để cập nhật
                LopHocPhan lhp = new LopHocPhan();
                lhp.setMaLop(maLop);
                lhp.setMaMH(maMH);
                lhp.setMaGV(maGV);
                lhp.setHocKy(hocKy);
                lhp.setNamHoc(namHoc);
                lhp.setSiSoToiDa(siSoToiDa);
                lhp.setSiSoHienTai(siSoHienTai);

                if (lopHocPhanService.capNhatLopHocPhan(lhp)) {
                    JOptionPane.showMessageDialog(this, "Cập nhật lớp học phần thành công!",
                            "Thành công", JOptionPane.INFORMATION_MESSAGE);

                    loadLopHocPhanData();

                    // Xóa form nhưng KHÔNG reset sĩ số hiện tại
                    clearForm(txtMaLop, txtHocKy, txtNamHoc, txtSiSoToiDa);
                    // Cho phép nhập mã lớp sau khi sửa xong
                    txtMaLop.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật lớp học phần thất bại!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Sĩ số tối đa phải là số nguyên!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnXoa.addActionListener(e -> {
            int selectedRow = tblLopHocPhan.getSelectedRow();
            int modelRow = getModelRowIndex(tblLopHocPhan, selectedRow);
            if (modelRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn lớp học phần cần xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            DefaultTableModel tableModel = (DefaultTableModel) tblLopHocPhan.getModel();
            String maLop = tableModel.getValueAt(modelRow, 0).toString();
            String maMH = tableModel.getValueAt(modelRow, 1).toString();
            String hocKy = tableModel.getValueAt(modelRow, 3).toString();
            String namHoc = tableModel.getValueAt(modelRow, 4).toString();
            
            // Kiểm tra điều kiện xóa
            if (!lopHocPhanService.kiemTraLopCoTheXoa(maLop)) {
                JOptionPane.showMessageDialog(this,
                        "Không thể xóa lớp học phần '" + maLop + "' vì đã có sinh viên đăng ký!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc muốn xóa lớp học phần?\n" +
                    "Mã lớp: " + maLop + "\n" +
                    "Môn học: " + maMH + "\n" +
                    "Học kỳ: " + hocKy + " - " + namHoc + "\n\n" +
                    "Lưu ý: Chỉ có thể xóa lớp chưa có sinh viên đăng ký.",
                    "Xác nhận xóa lớp học phần", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            
            if (confirm == JOptionPane.YES_OPTION) {
                if (lopHocPhanService.xoaLopHocPhan(maLop)) {
                    JOptionPane.showMessageDialog(this, "Xóa lớp học phần thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    loadLopHocPhanData();
                    clearForm(txtMaLop, txtHocKy, txtNamHoc, txtSiSoToiDa);
                    lblSiSoHienTai.setText("0");
                    // Cho phép nhập mã lớp sau khi xóa
                    txtMaLop.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa lớp học phần thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnDongMo.addActionListener(e -> {
            int selectedRow = tblLopHocPhan.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn lớp học phần cần đóng/mở!", 
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String maLop = tblLopHocPhan.getValueAt(selectedRow, 0).toString();
            String trangThaiHienTai = tblLopHocPhan.getValueAt(selectedRow, 7).toString();
            
            // Chuyển đổi trạng thái: Mở -> dong, Đóng -> mo
            String trangThaiMoi = trangThaiHienTai.equals("Mở") ? "dong" : "mo";
            String hanhDong = trangThaiMoi.equals("mo") ? "MỞ" : "ĐÓNG";

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc muốn " + hanhDong + " lớp " + maLop + "?\n" +
                    (trangThaiMoi.equals("dong") ? "Sinh viên sẽ không thể đăng ký lớp này." : "Sinh viên sẽ có thể đăng ký lớp này."),
                    "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                if (lopHocPhanService.capNhatTrangThai(maLop, trangThaiMoi)) {
                    JOptionPane.showMessageDialog(this, 
                        "Đã " + (trangThaiMoi.equals("mo") ? "mở" : "đóng") + " lớp " + maLop + " thành công!", 
                        "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    loadLopHocPhanData();
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật trạng thái thất bại!", 
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnClear.addActionListener(e -> {
            clearForm(txtMaLop, txtHocKy, txtNamHoc, txtSiSoToiDa);
            lblSiSoHienTai.setText("0");
            tblLopHocPhan.clearSelection();
            // Cho phép nhập mã lớp khi làm mới form (để thêm mới)
            txtMaLop.setEnabled(true);
            if (cboMonHoc.getItemCount() > 0) cboMonHoc.setSelectedIndex(0);
            if (cboGiangVien.getItemCount() > 0) cboGiangVien.setSelectedIndex(0);
        });

        btnExport.addActionListener(e -> {
            try {
                ExcelExporter.exportTable(tblLopHocPhan, this, "DanhSachLopHocPhan");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi xuất Excel: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Table selection listener
        tblLopHocPhan.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tblLopHocPhan.getSelectedRow();
                if (selectedRow != -1) {
                    txtMaLop.setText(String.valueOf(tblLopHocPhan.getValueAt(selectedRow, 0)));
                    // Không cho sửa mã lớp khi chỉnh sửa thông tin
                    txtMaLop.setEnabled(false);

                    Object maMH = tblLopHocPhan.getValueAt(selectedRow, 1);
                    if (maMH != null) {
                        cboMonHoc.setSelectedItem(maMH.toString());
                    }

                    Object maGV = tblLopHocPhan.getValueAt(selectedRow, 2);
                    if (maGV != null) {
                        cboGiangVien.setSelectedItem(maGV.toString());
                    }

                    txtHocKy.setText(String.valueOf(tblLopHocPhan.getValueAt(selectedRow, 3)));
                    txtNamHoc.setText(String.valueOf(tblLopHocPhan.getValueAt(selectedRow, 4)));
                    txtSiSoToiDa.setText(String.valueOf(tblLopHocPhan.getValueAt(selectedRow, 5)));
                    lblSiSoHienTai.setText(String.valueOf(tblLopHocPhan.getValueAt(selectedRow, 6)));
                }
            }
        });
    }

    private void showDangKyLopPanel() {
        JPanel contentPanel = createContentPanel("QUẢN LÝ ĐĂNG KÝ LỚP HỌC PHẦN");

        // LAYOUT MỚI: TABLE Ở TRÊN - FORM Ở DƯỚI
        JPanel mainPanel = new JPanel(new BorderLayout(0, 10));
        mainPanel.setBackground(LIGHT_BG);

        // Table
        String[] columnNames = {"Mã SV", "Mã Lớp", "Họ tên SV", "Tên Môn Học", "Giảng viên", "Học kỳ", "Năm học", "Trạng thái"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblDangKy = createStyledTable(model);

        // TOP: Search + Table
        JPanel topPanel = new JPanel(new BorderLayout(0, 10));
        topPanel.setBackground(LIGHT_BG);

        JPanel searchPanel = createSearchPanel(tblDangKy);
        JScrollPane scrollPane = createStyledScrollPane(tblDangKy);
        JPanel tableCard = createCardPanel("DANH SÁCH ĐĂNG KÝ LỚP HỌC PHẦN", scrollPane);

        topPanel.add(searchPanel, BorderLayout.NORTH);
        topPanel.add(tableCard, BorderLayout.CENTER);

        // BOTTOM: Form + Buttons
        JPanel bottomPanel = new JPanel(new BorderLayout(0, 0));
        bottomPanel.setBackground(LIGHT_BG);

        JPanel formCard = new JPanel(new BorderLayout(0, 10));
        formCard.setBackground(CARD_BG);
        formCard.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1),
                new EmptyBorder(15, 20, 15, 20)
        ));

        // Form - GridBagLayout
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(CARD_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JComboBox<String> cboSinhVien = new JComboBox<>();
        cboSinhVien.setFont(NORMAL_FONT);
        cboSinhVien.setPreferredSize(new Dimension(150, 40));
        JLabel lblTenSV = new JLabel("");
        lblTenSV.setFont(NORMAL_FONT);

        JComboBox<String> cboLopHocPhan = new JComboBox<>();
        cboLopHocPhan.setFont(NORMAL_FONT);
        cboLopHocPhan.setPreferredSize(new Dimension(150, 40));
        JLabel lblTenMonHoc = new JLabel("");
        lblTenMonHoc.setFont(NORMAL_FONT);
        JLabel lblTenGV = new JLabel("");
        lblTenGV.setFont(NORMAL_FONT);

        JLabel lblSiSoHienTai = new JLabel("0");
        lblSiSoHienTai.setFont(BOLD_FONT);
        lblSiSoHienTai.setForeground(PRIMARY_COLOR);
        JLabel lblSiSoToiDa = new JLabel("0");
        lblSiSoToiDa.setFont(BOLD_FONT);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        formPanel.add(new JLabel("Sinh viên:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.15;
        formPanel.add(cboSinhVien, gbc);
        gbc.gridx = 2; gbc.weightx = 0.2;
        formPanel.add(lblTenSV, gbc);

        gbc.gridx = 3; gbc.weightx = 0;
        formPanel.add(new JLabel("Lớp HP:"), gbc);
        gbc.gridx = 4; gbc.weightx = 0.15;
        formPanel.add(cboLopHocPhan, gbc);
        gbc.gridx = 5; gbc.weightx = 0.2;
        formPanel.add(lblTenMonHoc, gbc);

        gbc.gridx = 6; gbc.weightx = 0;
        formPanel.add(new JLabel("Giảng viên:"), gbc);
        gbc.gridx = 7; gbc.weightx = 0.15;
        formPanel.add(lblTenGV, gbc);

        gbc.gridx = 8; gbc.weightx = 0;
        formPanel.add(new JLabel("Sĩ số:"), gbc);
        gbc.gridx = 9; gbc.weightx = 0.05;
        JPanel siSoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 0));
        siSoPanel.setBackground(CARD_BG);
        siSoPanel.add(lblSiSoHienTai);
        siSoPanel.add(new JLabel("/"));
        siSoPanel.add(lblSiSoToiDa);
        formPanel.add(siSoPanel, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.setBackground(CARD_BG);

        JButton btnDangKy = createStyledButton("Đăng Ký", SUCCESS_COLOR);
        JButton btnHuyDangKy = createStyledButton("Hủy ĐK", DANGER_COLOR);
        JButton btnClear = createStyledButton("Làm mới", new Color(158, 158, 158));
        JButton btnExport = createStyledButton("Xuất Excel", new Color(67, 160, 71));

        buttonPanel.add(btnDangKy);
        buttonPanel.add(btnHuyDangKy);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnExport);

        formCard.add(formPanel, BorderLayout.CENTER);
        formCard.add(buttonPanel, BorderLayout.SOUTH);

        bottomPanel.add(formCard, BorderLayout.CENTER);

        // Assemble
        mainPanel.add(topPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        contentPanel.add(mainPanel, BorderLayout.CENTER);
        pnMainContent.add(contentPanel, BorderLayout.CENTER);

        // LOAD DATA
        loadDangKyData();
        loadSinhVienIntoComboBox(cboSinhVien);
        loadLopHocPhanIntoComboBox(cboLopHocPhan);

        // ================== EVENT: CHỌN SINH VIÊN ==================
        cboSinhVien.addActionListener(e -> {
            String maSV = String.valueOf(cboSinhVien.getSelectedItem());
            if (!maSV.isEmpty()) {
                SinhVien sv = sinhVienService.getSinhVienById(maSV);
                if (sv != null) {
                    lblTenSV.setText(sv.getHoTen());
                }
            }
        });

        // ================== EVENT: CHỌN LỚP HỌC PHẦN ==================
        cboLopHocPhan.addActionListener(e -> {
            String maLop = String.valueOf(cboLopHocPhan.getSelectedItem());
            if (!maLop.isEmpty()) {
                LopHocPhan lhp = lopHocPhanService.getLopHocPhanByMaLop(maLop);

                if (lhp != null) {
                    MonHoc mh = monHocService.getMonHocById(lhp.getMaMH());
                    if (mh != null) lblTenMonHoc.setText(mh.getTenMH());

                    GiangVien gv = giangVienService.getGiangVienById(lhp.getMaGV());
                    if (gv != null) lblTenGV.setText(gv.getHoTen());

                    // cập nhật sĩ số
                    int siSoHienTai = dangKyLopService.demSoLuongDangKy(maLop);
                    lblSiSoHienTai.setText(String.valueOf(siSoHienTai));
                    lblSiSoToiDa.setText(String.valueOf(lhp.getSiSoToiDa()));
                }
            }
        });

        // ================== EVENT: ĐĂNG KÝ ==================
        btnDangKy.addActionListener(e -> {
            String maSV = String.valueOf(cboSinhVien.getSelectedItem());
            String maLop = String.valueOf(cboLopHocPhan.getSelectedItem());

            if (maSV.isEmpty() || maLop.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sinh viên và lớp học phần!");
                return;
            }



            // kiểm tra đã đăng ký
            if (dangKyLopService.kiemTraDaDangKy(maSV, maLop)) {
                JOptionPane.showMessageDialog(this, "Sinh viên đã đăng ký lớp học phần này!");
                return;
            }



            if (dangKyLopService.dangKyLop(new DangKiLop(maSV, maLop))) {
                JOptionPane.showMessageDialog(this, 
                    "Đăng ký thành công!", 
                    "Thành công", 
                    JOptionPane.INFORMATION_MESSAGE);
                loadDangKyData();

                // cập nhật sĩ số mới
                lblSiSoHienTai.setText(String.valueOf(dangKyLopService.demSoLuongDangKy(maLop)));
            } else {
                // Lấy thông báo lỗi chi tiết từ DAO
                String errorMessage = dangKyLopService.getLastErrorMessage();
                if (errorMessage == null || errorMessage.isEmpty()) {
                    errorMessage = "Đăng ký thất bại!\nVui lòng kiểm tra lại thông tin.";
                }
                JOptionPane.showMessageDialog(this, 
                    errorMessage, 
                    "Không thể đăng ký", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        // ================== EVENT: HỦY ĐĂNG KÝ ==================
        btnHuyDangKy.addActionListener(e -> {
            int row = tblDangKy.getSelectedRow();
            int modelRow = getModelRowIndex(tblDangKy, row);
            if (modelRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn đăng ký cần hủy!");
                return;
            }

            DefaultTableModel tableModel = (DefaultTableModel) tblDangKy.getModel();
            String maSV = tableModel.getValueAt(modelRow, 0).toString();
            String maLop = tableModel.getValueAt(modelRow, 1).toString();
            


            // Xác nhận hủy đăng ký
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc muốn hủy đăng ký?\n" +
                    "Sinh viên: " + maSV + "\n" +
                    "Lớp học phần: " + maLop,
                    "Xác nhận hủy đăng ký", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            
            if (confirm == JOptionPane.YES_OPTION) {
                if (dangKyLopService.huyDangKy(maSV, maLop)) {
                    JOptionPane.showMessageDialog(this, "Hủy đăng ký thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    loadDangKyData();

                    // cập nhật sĩ số mới
                    lblSiSoHienTai.setText(String.valueOf(dangKyLopService.demSoLuongDangKy(maLop)));
                } else {
                    String errorMsg = dangKyLopService.getLastErrorMessage();
                    JOptionPane.showMessageDialog(this, 
                            (errorMsg != null && !errorMsg.isEmpty()) ? errorMsg : "Hủy đăng ký thất bại!", 
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnClear.addActionListener(e -> {
            if (cboSinhVien.getItemCount() > 0) cboSinhVien.setSelectedIndex(0);
            if (cboLopHocPhan.getItemCount() > 0) cboLopHocPhan.setSelectedIndex(0);
            lblTenSV.setText("");
            lblTenMonHoc.setText("");
            lblTenGV.setText("");
            lblSiSoHienTai.setText("0");
            lblSiSoToiDa.setText("0");
            tblDangKy.clearSelection();
        });

        btnExport.addActionListener(e -> {
            try {
                ExcelExporter.exportTable(tblDangKy, this, "DanhSachDangKy");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi xuất Excel: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // Data loading methods
    private void loadTaiKhoanData() {
        if (tblTaiKhoan != null) {
            DefaultTableModel model = (DefaultTableModel) tblTaiKhoan.getModel();
            model.setRowCount(0);
            List<TaiKhoan> listTaiKhoan = taiKhoanService.getAllTaiKhoan();
            for (TaiKhoan tk : listTaiKhoan) {
                if (taiKhoan != null && tk.getTenDangNhap().equals(taiKhoan.getTenDangNhap())) {
                    continue; // Bỏ qua không hiển thị
                }

                model.addRow(new Object[]{
                        tk.getTenDangNhap(),
                        tk.getMatKhau(),
                        tk.getLoaiNguoiDung(),
                        tk.getOnlineStatus()
                });
            }
        }
    }

    private void loadKhoaData() {
        if (tblKhoa != null) {
            DefaultTableModel model = (DefaultTableModel) tblKhoa.getModel();
            model.setRowCount(0);
            List<Khoa> listKhoa = khoaService.getAllKhoa();
            for (Khoa khoa : listKhoa) {
                model.addRow(new Object[]{khoa.getMaKhoa(), khoa.getTenKhoa()});
            }
        }
    }

    private void loadGiangVienData() {
        System.out.println("🔍 [DEBUG] loadGiangVienData() called");
        try {
            if (tblGiangVien != null) {
                System.out.println("🔍 [DEBUG] tblGiangVien is not null");
                DefaultTableModel model = (DefaultTableModel) tblGiangVien.getModel();
                model.setRowCount(0);
                System.out.println("🔍 [DEBUG] Calling getAllGiangVien()...");
                List<GiangVien> listGV = giangVienService.getAllGiangVien();
                System.out.println("🔍 [DEBUG] Got " + listGV.size() + " giảng viên");
                for (GiangVien gv : listGV) {
                    model.addRow(new Object[]{
                            gv.getMaGV(), gv.getHoTen(), gv.getTenDangNhap(), // ← THÊM TÊN ĐĂNG NHẬP
                            gv.getEmail(), gv.getSoDienThoai(), gv.getMaKhoa()
                    });
                }
                System.out.println("✓ [DEBUG] Loaded " + listGV.size() + " rows into table");
            } else {
                System.err.println("❌ [DEBUG] tblGiangVien is NULL!");
            }
        } catch (Exception e) {
            System.err.println("❌ [ERROR] loadGiangVienData failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadSinhVienData() {
        System.out.println("🔍 [DEBUG] loadSinhVienData() called");
        try {
            if (tblSinhVien != null) {
                System.out.println("🔍 [DEBUG] tblSinhVien is not null");
                DefaultTableModel model = (DefaultTableModel) tblSinhVien.getModel();
                model.setRowCount(0);
                System.out.println("🔍 [DEBUG] Calling getAllSinhVien()...");
                List<SinhVien> listSV = sinhVienService.getAllSinhVien();
                System.out.println("🔍 [DEBUG] Got " + listSV.size() + " sinh viên");
                for (SinhVien sv : listSV) {
                    model.addRow(new Object[]{
                            sv.getMaSV(), sv.getHoTen(), sv.getTenDangNhap(), // ← THÊM TÊN ĐĂNG NHẬP
                            sv.getGioiTinh(), sv.getNgaySinh(), sv.getEmail(),
                            sv.getSoDienThoai(), sv.getDiaChi(), sv.getMaKhoa()
                    });
                }
                System.out.println("✓ [DEBUG] Loaded " + listSV.size() + " rows into table");
            } else {
                System.err.println("❌ [DEBUG] tblSinhVien is NULL!");
            }
        } catch (Exception e) {
            System.err.println("❌ [ERROR] loadSinhVienData failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadMonHocData() {
        System.out.println("🔍 [DEBUG] loadMonHocData() called");
        try {
            if (tblMonHoc != null) {
                System.out.println("🔍 [DEBUG] tblMonHoc is not null");
                DefaultTableModel model = (DefaultTableModel) tblMonHoc.getModel();
                model.setRowCount(0);
                System.out.println("🔍 [DEBUG] Calling getAllMonHoc()...");
                List<MonHoc> listMonHoc = monHocService.getAllMonHoc();
                System.out.println("🔍 [DEBUG] Got " + listMonHoc.size() + " môn học");
                for (MonHoc mh : listMonHoc) {
                    model.addRow(new Object[]{
                            mh.getMaMH(), mh.getTenMH(), mh.getSoTinChi(),
                            mh.getMoTa(), mh.getMaKhoa()
                    });
                }
                System.out.println("✓ [DEBUG] Loaded " + listMonHoc.size() + " rows into table");
            } else {
                System.err.println("❌ [DEBUG] tblMonHoc is NULL!");
            }
        } catch (Exception e) {
            System.err.println("❌ [ERROR] loadMonHocData failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadLopHocPhanData() {
        if (tblLopHocPhan != null) {
            DefaultTableModel model = (DefaultTableModel) tblLopHocPhan.getModel();
            model.setRowCount(0);
            List<LopHocPhan> listLHP = lopHocPhanService.getAllLopHocPhan();
            for (LopHocPhan lhp : listLHP) {
                Integer siSoHienTai = lhp.getSiSoHienTai();
                int currentSize = (siSoHienTai != null) ? siSoHienTai : 0;
                
                // Hiển thị trạng thái dễ đọc: mo -> Mở, dong -> Đóng
                String trangThaiHienThi = lhp.getTrangThaiHienThi();

                model.addRow(new Object[]{
                        lhp.getMaLop(),
                        lhp.getMaMH(),
                        lhp.getMaGV(),
                        lhp.getHocKy(),
                        lhp.getNamHoc(),
                        lhp.getSiSoToiDa(),
                        currentSize,
                        trangThaiHienThi
                });
            }
        }
    }

    private void loadDangKyData() {
        if (tblDangKy != null) {
            DefaultTableModel model = (DefaultTableModel) tblDangKy.getModel();
            model.setRowCount(0);
            List<DangKiLop> listDangKy = dangKyLopService.getAllDangKy();
            for (DangKiLop dk : listDangKy) {
                try {
                    SinhVien sv = sinhVienService.getSinhVienById(dk.getMaSV());
                    LopHocPhan lhp = lopHocPhanService.getLopHocPhanByMaLop(dk.getMaLop());
                    if (lhp == null) continue;
                    MonHoc mh = monHocService.getMonHocById(lhp.getMaMH());
                    GiangVien gv = giangVienService.getGiangVienById(lhp.getMaGV());
                    String trangThaiHienThi = "mo".equals(lhp.getTrangThai()) ? "Mở" : "Đóng";
                    model.addRow(new Object[]{
                            dk.getMaSV(),
                            dk.getMaLop(),
                            sv != null ? sv.getHoTen() : "",
                            mh != null ? mh.getTenMH() : "",
                            gv != null ? gv.getHoTen() : "",
                            lhp.getHocKy(),
                            lhp.getNamHoc(),
                            trangThaiHienThi
                    });
                } catch (Exception e) {
                    System.err.println("❌ Lỗi load đăng ký " + dk.getMaSV() + "/" + dk.getMaLop() + ": " + e.getMessage());
                }
            }
        }
    }

    private void loadKhoaIntoComboBox(JComboBox<String> cboKhoa) {
        if (cboKhoa != null) {
            cboKhoa.removeAllItems();
            List<Khoa> listKhoa = khoaService.getAllKhoa();
            for (Khoa khoa : listKhoa) {
                cboKhoa.addItem(khoa.getMaKhoa());
            }
        }
    }

    private void loadMonHocIntoComboBox(JComboBox<String> cboMonHoc) {
        if (cboMonHoc != null) {
            cboMonHoc.removeAllItems();
            List<MonHoc> listMonHoc = monHocService.getAllMonHoc();
            for (MonHoc monHoc : listMonHoc) {
                cboMonHoc.addItem(monHoc.getMaMH());
            }
        }
    }

    private void loadGiangVienIntoComboBox(JComboBox<String> cboGiangVien) {
        if (cboGiangVien != null) {
            cboGiangVien.removeAllItems();
            List<GiangVien> listGV = giangVienService.getAllGiangVien();
            for (GiangVien gv : listGV) {
                cboGiangVien.addItem(gv.getMaGV());
            }
        }
    }

    private void loadSinhVienIntoComboBox(JComboBox<String> cboSinhVien) {
        if (cboSinhVien != null) {
            cboSinhVien.removeAllItems();
            List<SinhVien> listSV = sinhVienService.getAllSinhVien();
            for (SinhVien sv : listSV) {
                cboSinhVien.addItem(sv.getMaSV());
            }
        }
    }

    private void exportSinhVienToExcel() {
        if (tblSinhVien == null || tblSinhVien.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Không có dữ liệu để xuất.",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            ExcelExporter.exportTable(tblSinhVien, this, "DanhSachSinhVien");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Xuất Excel thất bại: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadLopHocPhanIntoComboBox(JComboBox<String> cboLopHocPhan) {
        if (cboLopHocPhan != null) {
            cboLopHocPhan.removeAllItems();
            List<LopHocPhan> listLHP = lopHocPhanService.getAllLopHocPhan();
            for (LopHocPhan lhp : listLHP) {
                cboLopHocPhan.addItem(lhp.getMaLop());
            }
        }
    }

    // CRUD operations
    private void themTaiKhoan() {
        JTextField txtUsername = new JTextField();
        JPasswordField txtPassword = new JPasswordField();
        JComboBox<String> cboLoaiTK = new JComboBox<>(new String[]{"Admin", "Giangvien", "Sinhvien"});

        Object[] message = {
                "Tên đăng nhập:", txtUsername,
                "Mật khẩu:", txtPassword,
                "Loại tài khoản:", cboLoaiTK
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Thêm tài khoản",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword());
            String loaiTK = cboLoaiTK.getSelectedItem() != null ? cboLoaiTK.getSelectedItem().toString() : "SinhVien";

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (taiKhoanService.kiemTraTenDangNhapTonTai(username)) {
                JOptionPane.showMessageDialog(this, "Tên đăng nhập đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            TaiKhoan tk = new TaiKhoan();
            tk.setTenDangNhap(username);
            tk.setMatKhau(password);
            tk.setLoaiNguoiDung(loaiTK);

            try {
                if (taiKhoanService.themTaiKhoan(tk)) {
                    JOptionPane.showMessageDialog(this, "Thêm tài khoản thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    loadTaiKhoanData();
                }
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Thêm tài khoản thất bại: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void suaTaiKhoan() {
        int selectedRow = tblTaiKhoan.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản cần sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String username = tblTaiKhoan.getValueAt(selectedRow, 0).toString();

        JPasswordField txtPassword = new JPasswordField();
        JComboBox<String> cboLoaiTK = new JComboBox<>(new String[]{"Admin", "GiangVien", "SinhVien"});
        cboLoaiTK.setSelectedItem(tblTaiKhoan.getValueAt(selectedRow, 2).toString());

        Object[] message = {
                "Tên đăng nhập: " + username,
                "Mật khẩu mới:", txtPassword,
                "Loại tài khoản:", cboLoaiTK
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Sửa tài khoản",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String password = new String(txtPassword.getPassword());
            String loaiTK = cboLoaiTK.getSelectedItem() != null ? cboLoaiTK.getSelectedItem().toString() : "SinhVien";

            TaiKhoan tk = new TaiKhoan();
            tk.setTenDangNhap(username);
            tk.setMatKhau(password.isEmpty() ? "password" : password);
            tk.setLoaiNguoiDung(loaiTK);

            if (taiKhoanService.capNhatTaiKhoan(tk)) {
                JOptionPane.showMessageDialog(this, "Cập nhật tài khoản thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                loadTaiKhoanData();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật tài khoản thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void xoaTaiKhoan() {
        int selectedRow = tblTaiKhoan.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản cần xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String username = tblTaiKhoan.getValueAt(selectedRow, 0).toString();
        String loaiTaiKhoan = tblTaiKhoan.getValueAt(selectedRow, 2).toString();

        // Xác nhận xóa
        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa tài khoản '" + username + "' và toàn bộ thông tin " +
                        loaiTaiKhoan.toLowerCase() + " tương ứng?",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            boolean success = taiKhoanService.xoaTaiKhoan(username);

            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Xóa tài khoản và thông tin " + loaiTaiKhoan.toLowerCase() + " thành công!",
                        "Thành công", JOptionPane.INFORMATION_MESSAGE);

                // Reload dữ liệu
                switch (loaiTaiKhoan) {
                    case "SinhVien":
                        loadSinhVienData();
                        break;
                    case "GiangVien":
                        loadGiangVienData();
                        break;
                }
                loadTaiKhoanData();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Xóa tài khoản thất bại!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Đã xảy ra lỗi khi xóa tài khoản: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    // Utility methods
    private void clearForm(JComponent... fields) {
        for (JComponent field : fields) {
            if (field instanceof JTextField) {
                ((JTextField) field).setText("");
            } else if (field instanceof JTextArea) {
                ((JTextArea) field).setText("");
            }
        }
    }

    private boolean isValidDate(String date) {
        try {
            java.sql.Date.valueOf(date);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}