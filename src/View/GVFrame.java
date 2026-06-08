package View;

import service.*;
import model.*;
// import org.apache.poi.ss.usermodel.Workbook;
// import org.apache.poi.ss.usermodel.Sheet;
// import org.apache.poi.ss.usermodel.Row;
// import org.apache.poi.ss.usermodel.Cell;
// import org.apache.poi.ss.usermodel.CellStyle;
// import org.apache.poi.ss.usermodel.IndexedColors;
// import org.apache.poi.ss.usermodel.FillPatternType;
import until.Mail;
import until.ExcelExporter;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GVFrame extends JFrame {

    private TaiKhoan taiKhoan;
    private GiangVien giangVien;
    private JSplitPane mainSplitPane;

    // Main panels
    private JPanel pnSidebar, pnMainContent;

    // Tables
    private JTable tblLopHocPhan, tblSinhVien, tblDiem;

    // Services - thay thế DAOs
    private LopHocPhanService lopHocPhanService;
    private SinhVienService sinhVienService;
    private DiemService diemService;
    private DangKyLopService dangKyLopService;

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
    private String currentPanel = "LopHocPhan";
    private String currentMaLop = "";

    public GVFrame(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
        initComponents();
        setupExitManagement();
        this.setVisible(true);
        displayUserInfo();

        SwingUtilities.invokeLater(() -> {
            loadLopHocPhanData();
        });
    }

    private void setupExitManagement() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                JOptionPane.showMessageDialog(GVFrame.this,
                        "Vui lòng sử dụng nút 'ĐĂNG XUẤT' để thoát chương trình!",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void displayUserInfo() {
        if (taiKhoan != null) {
            this.setTitle("HỆ THỐNG QUẢN LÝ TÌNH HÌNH HỌC TẬP CỦA SINH VIÊN - GIẢNG VIÊN - Xin chào: " + taiKhoan.getTenDangNhap());
        }
    }

    private void initComponents() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1400, 800);
        this.setLocationRelativeTo(null);
        getContentPane().setBackground(LIGHT_BG);

        initializeDAOs();
        loadGiangVienInfo();

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
    }

    private void loadGiangVienInfo() {
        GiangVienService giangVienService = new GiangVienService();
        this.giangVien = giangVienService.getGiangVienById(taiKhoan.getTenDangNhap());
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

        JLabel lblTitle = new JLabel("GIẢNG VIÊN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(PRIMARY_COLOR);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblUserInfo = new JLabel();
        lblUserInfo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblUserInfo.setForeground(SECONDARY_COLOR);
        lblUserInfo.setHorizontalAlignment(SwingConstants.CENTER);

        if (giangVien != null) {
            lblUserInfo.setText(giangVien.getHoTen());
        }

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(HEADER_BG);
        titlePanel.add(lblTitle, BorderLayout.CENTER);
        titlePanel.add(lblUserInfo, BorderLayout.SOUTH);
        headerPanel.add(titlePanel, BorderLayout.CENTER);
        pnSidebar.add(headerPanel, BorderLayout.NORTH);

        // Menu items
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(4, 1, 0, 2));
        menuPanel.setBackground(SIDEBAR_BG);
        menuPanel.setBorder(new EmptyBorder(20, 15, 20, 15));

        String[] menuItems = {"LỚP HỌC PHẦN", "SINH VIÊN", "NHẬP ĐIỂM"};

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
        showLopHocPhanPanel();
    }

    private void switchPanel(String panelName) {
        currentPanel = panelName;
        pnMainContent.removeAll();

        switch (panelName) {
            case "LỚPHỌCPHẦN": showLopHocPhanPanel(); break;
            case "SINHVIÊN": showSinhVienPanel(); break;
            case "NHẬPĐIỂM": showNhapDiemPanel(); break;
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
            case "LỚPHỌCPHẦN": loadLopHocPhanData(); break;
            case "SINHVIÊN": loadSinhVienData(); break;
            case "NHẬPĐIỂM": loadDiemData(); break;
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

        // Thêm TableRowSorter để có thể sort
        table.setAutoCreateRowSorter(true);

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

        // Style scrollbar
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

    // ==================== PANEL LỚP HỌC PHẦN ====================
    private void showLopHocPhanPanel() {
        JPanel contentPanel = createContentPanel("LỚP HỌC PHẦN CỦA TÔI");

        // Main container
        JPanel mainContainer = new JPanel(new BorderLayout(0, 15));
        mainContainer.setBackground(LIGHT_BG);

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
        JLabel lblHocKyFilter = new JLabel("Học kỳ:");
        lblHocKyFilter.setFont(BOLD_FONT);
        lblHocKyFilter.setPreferredSize(new Dimension(60, COMPONENT_HEIGHT));
        filterPanel.add(lblHocKyFilter);

        JComboBox<String> cboHocKyFilter = createStyledComboBox();
        cboHocKyFilter.addItem("Tất cả");
        cboHocKyFilter.addItem("HK1");
        cboHocKyFilter.addItem("HK2");
        cboHocKyFilter.setPreferredSize(new Dimension(120, COMPONENT_HEIGHT));
        filterPanel.add(cboHocKyFilter);

        // Năm học
        JLabel lblNamHocFilter = new JLabel("Năm học:");
        lblNamHocFilter.setFont(BOLD_FONT);
        lblNamHocFilter.setPreferredSize(new Dimension(70, COMPONENT_HEIGHT));
        filterPanel.add(lblNamHocFilter);

        JComboBox<String> cboNamHocFilter = createStyledComboBox();
        cboNamHocFilter.addItem("Tất cả");
        cboNamHocFilter.addItem("2022-2023");
        cboNamHocFilter.addItem("2023-2024");
        cboNamHocFilter.addItem("2024-2025");
        cboNamHocFilter.addItem("2025-2026");
        cboNamHocFilter.setPreferredSize(new Dimension(140, COMPONENT_HEIGHT));
        filterPanel.add(cboNamHocFilter);

        // Buttons
        JButton btnLoc = createStyledButton("Lọc", PRIMARY_COLOR);
        btnLoc.setPreferredSize(new Dimension(100, COMPONENT_HEIGHT));
        filterPanel.add(btnLoc);

        JButton btnReset = createStyledButton("Đặt lại", SECONDARY_COLOR);
        btnReset.setPreferredSize(new Dimension(100, COMPONENT_HEIGHT));
        filterPanel.add(btnReset);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(500);
        splitPane.setResizeWeight(0.7);
        splitPane.setDividerSize(2);
        splitPane.setBorder(null);

        String[] columnNames = {"Mã lớp", "Mã MH", "Học kỳ", "Năm học", "Sĩ số tối đa", "Sĩ số hiện tại"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tblLopHocPhan = createStyledTable(model);
        JScrollPane scrollPane = createStyledScrollPane(tblLopHocPhan);

        // Control panel với thông tin chi tiết lớp
        JPanel controlPanel = new JPanel(new BorderLayout(10, 10));
        controlPanel.setBackground(LIGHT_BG);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(CARD_BG);
        infoPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblMaLop = new JLabel("Mã lớp: --");
        JLabel lblMonHoc = new JLabel("Môn học: --");
        JLabel lblSiSo = new JLabel("Sĩ số: --/--");
        JLabel lblHocKy = new JLabel("Học kỳ: --");
        JLabel lblNamHoc = new JLabel("Năm học: --");

        lblMaLop.setFont(BOLD_FONT);
        lblMonHoc.setFont(BOLD_FONT);
        lblSiSo.setFont(BOLD_FONT);
        lblHocKy.setFont(NORMAL_FONT);
        lblNamHoc.setFont(NORMAL_FONT);

        infoPanel.add(lblMaLop);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(lblMonHoc);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(lblSiSo);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(lblHocKy);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(lblNamHoc);

        JButton btnXemSinhVien = createStyledButton("Xem Sinh Viên", PRIMARY_COLOR);
        JButton btnNhapDiem = createStyledButton("Nhập Điểm", SUCCESS_COLOR);
        JButton btnXuatExcelLop = createStyledButton("Xuất Excel", INFO_COLOR);

        btnXemSinhVien.addActionListener(e -> {
            if (currentMaLop.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn lớp học phần!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            switchPanel("SINHVIÊN");
        });

        btnNhapDiem.addActionListener(e -> {
            if (currentMaLop.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn lớp học phần!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            switchPanel("NHẬPĐIỂM");
        });

        btnXuatExcelLop.addActionListener(e -> {
            xuatLopHocPhanExcel();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(CARD_BG);
        buttonPanel.add(btnXemSinhVien);
        buttonPanel.add(btnNhapDiem);
        buttonPanel.add(btnXuatExcelLop);

        JPanel detailPanel = new JPanel(new BorderLayout());
        detailPanel.setBackground(CARD_BG);
        detailPanel.add(createCardPanel("THÔNG TIN LỚP", infoPanel), BorderLayout.CENTER);
        detailPanel.add(buttonPanel, BorderLayout.SOUTH);

        controlPanel.add(detailPanel, BorderLayout.CENTER);

        splitPane.setTopComponent(createCardPanel("DANH SÁCH LỚP HỌC PHẦN", scrollPane));
        splitPane.setBottomComponent(controlPanel);
        
        // Add filter and splitPane to main container
        mainContainer.add(filterPanel, BorderLayout.NORTH);
        mainContainer.add(splitPane, BorderLayout.CENTER);
        
        contentPanel.add(mainContainer, BorderLayout.CENTER);
        pnMainContent.add(contentPanel, BorderLayout.CENTER);

        // Button actions
        btnLoc.addActionListener(e -> {
            String hocKy = cboHocKyFilter.getSelectedItem().toString();
            String namHoc = cboNamHocFilter.getSelectedItem().toString();
            loadLopHocPhanDataWithFilter(hocKy, namHoc);
        });

        btnReset.addActionListener(e -> {
            cboHocKyFilter.setSelectedIndex(0);
            cboNamHocFilter.setSelectedIndex(0);
            loadLopHocPhanData();
        });

        loadLopHocPhanData();

        // Table selection listener
        tblLopHocPhan.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tblLopHocPhan.getSelectedRow();
                int modelRow = getModelRowIndex(tblLopHocPhan, selectedRow);
                if (modelRow != -1) {
                    DefaultTableModel tableModel = (DefaultTableModel) tblLopHocPhan.getModel();
                    currentMaLop = tableModel.getValueAt(modelRow, 0).toString();
                    String maMH = tableModel.getValueAt(modelRow, 1).toString();
                    String hocKy = tableModel.getValueAt(modelRow, 2).toString();
                    String namHoc = tableModel.getValueAt(modelRow, 3).toString();
                    int siSoToiDa = Integer.parseInt(tableModel.getValueAt(modelRow, 4).toString());
                    int siSoHienTai = Integer.parseInt(tableModel.getValueAt(modelRow, 5).toString());

                    lblMaLop.setText("Mã lớp: " + currentMaLop);
                    lblMonHoc.setText("Môn học: " + maMH);
                    lblSiSo.setText("Sĩ số: " + siSoHienTai + "/" + siSoToiDa);
                    lblHocKy.setText("Học kỳ: " + hocKy);
                    lblNamHoc.setText("Năm học: " + namHoc);
                }
            }
        });
    }

    // ==================== PANEL SINH VIÊN ====================
    private void showSinhVienPanel() {
        JPanel contentPanel = createContentPanel("SINH VIÊN TRONG LỚP");

        if (currentMaLop.isEmpty()) {
            JLabel lblWarning = new JLabel("Vui lòng chọn lớp học phần từ tab 'Lớp học phần'");
            lblWarning.setFont(TITLE_FONT);
            lblWarning.setForeground(WARNING_COLOR);
            lblWarning.setHorizontalAlignment(SwingConstants.CENTER);
            contentPanel.add(lblWarning, BorderLayout.CENTER);
            pnMainContent.add(contentPanel, BorderLayout.CENTER);
            return;
        }

        String[] columnNames = {"Mã SV", "Họ tên", "Giới tính", "Email", "Số ĐT"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tblSinhVien = createStyledTable(model);
        JScrollPane scrollPane = createStyledScrollPane(tblSinhVien);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(LIGHT_BG);
        headerPanel.setBorder(new EmptyBorder(0, 0, 15, 0));

        JLabel titleLabel = new JLabel("SINH VIÊN LỚP: " + currentMaLop);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(PRIMARY_COLOR);

        JButton btnXuatExcelSV = createStyledButton("Xuất Excel", INFO_COLOR);
        JButton btnBack = createStyledButton("Quay lại", SECONDARY_COLOR);
        
        btnXuatExcelSV.addActionListener(e -> xuatSinhVienExcel());
        btnBack.addActionListener(e -> switchPanel("LỚPHỌCPHẦN"));

        JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        headerRight.setBackground(LIGHT_BG);
        headerRight.add(btnXuatExcelSV);
        headerRight.add(btnBack);

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(headerRight, BorderLayout.EAST);

        contentPanel.add(headerPanel, BorderLayout.NORTH);
        contentPanel.add(createCardPanel("DANH SÁCH SINH VIÊN", scrollPane), BorderLayout.CENTER);
        pnMainContent.add(contentPanel, BorderLayout.CENTER);

        loadSinhVienData();
    }

    // ==================== PANEL NHẬP ĐIỂM ====================
    private void showNhapDiemPanel() {
        JPanel contentPanel = createContentPanel("NHẬP ĐIỂM SINH VIÊN");

        if (currentMaLop.isEmpty()) {
            JLabel lblWarning = new JLabel("Vui lòng chọn lớp học phần từ tab 'Lớp học phần'");
            lblWarning.setFont(TITLE_FONT);
            lblWarning.setForeground(WARNING_COLOR);
            lblWarning.setHorizontalAlignment(SwingConstants.CENTER);
            contentPanel.add(lblWarning, BorderLayout.CENTER);
            pnMainContent.add(contentPanel, BorderLayout.CENTER);
            return;
        }

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(400);
        splitPane.setResizeWeight(0.6);
        splitPane.setDividerSize(2);
        splitPane.setBorder(null);

        // Table điểm
        String[] columnNames = {"Mã SV", "Họ tên", "Điểm QT", "Điểm GK", "Điểm CK", "Điểm TK", "Điểm chữ", "Xếp loại"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override public boolean isCellEditable(int row, int column) {
                return column == 2 || column == 3 || column == 4; // Chỉ cho phép sửa điểm QT, GK, CK
            }
        };
        tblDiem = createStyledTable(model);
        JScrollPane scrollPane = createStyledScrollPane(tblDiem);

        // Form nhập điểm
        JPanel formPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        formPanel.setBackground(CARD_BG);
        formPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JTextField txtMaSV = createStyledTextField();
        txtMaSV.setEnabled(false);
        JTextField txtHoTen = createStyledTextField();
        txtHoTen.setEnabled(false);
        JTextField txtDiemQT = createStyledTextField();
        JTextField txtDiemGK = createStyledTextField();
        JTextField txtDiemCK = createStyledTextField();
        JTextField txtDiemTK = createStyledTextField();
        txtDiemTK.setEnabled(false); // Điểm TK tự động tính, không cho sửa
        JTextField txtDiemChu = createStyledTextField();
        txtDiemChu.setEnabled(false); // Điểm chữ tự động tính, không cho sửa
        JTextField txtXepLoai = createStyledTextField();
        txtXepLoai.setEnabled(false); // Xếp loại tự động tính, không cho sửa

        // Thêm sự kiện tự động tính điểm khi nhập
        addAutoCalculateListener(txtDiemQT, txtDiemQT, txtDiemGK, txtDiemCK, txtDiemTK, txtDiemChu, txtXepLoai);
        addAutoCalculateListener(txtDiemGK, txtDiemQT, txtDiemGK, txtDiemCK, txtDiemTK, txtDiemChu, txtXepLoai);
        addAutoCalculateListener(txtDiemCK, txtDiemQT, txtDiemGK, txtDiemCK, txtDiemTK, txtDiemChu, txtXepLoai);

        formPanel.add(createFormField("Mã SV:", txtMaSV));
        formPanel.add(createFormField("Họ tên:", txtHoTen));
        formPanel.add(createFormField("Điểm QT:", txtDiemQT));
        formPanel.add(createFormField("Điểm GK:", txtDiemGK));
        formPanel.add(createFormField("Điểm CK:", txtDiemCK));
        formPanel.add(createFormField("Điểm TK:", txtDiemTK));
        formPanel.add(createFormField("Điểm chữ:", txtDiemChu));
        formPanel.add(createFormField("Xếp loại:", txtXepLoai));

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(CARD_BG);

        JButton btnTinhDiem = createStyledButton("Tính Điểm", WARNING_COLOR);
        JButton btnLuuDiem = createStyledButton("Lưu Điểm", SUCCESS_COLOR);
        JButton btnXuatExcel = createStyledButton("Xuất Excel", INFO_COLOR);
        JButton btnGuiEmail = createStyledButton("Gửi Email Kết Quả", new Color(0, 150, 136));
        JButton btnClear = createStyledButton("Làm mới", PRIMARY_COLOR);
        JButton btnBack = createStyledButton("Quay lại", SECONDARY_COLOR);

        buttonPanel.add(btnTinhDiem);
        buttonPanel.add(btnLuuDiem);
        buttonPanel.add(btnXuatExcel);
        buttonPanel.add(btnGuiEmail);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnBack);

        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.setBackground(LIGHT_BG);
        controlPanel.add(createCardPanel("NHẬP ĐIỂM", formPanel), BorderLayout.CENTER);
        controlPanel.add(buttonPanel, BorderLayout.SOUTH);

        splitPane.setTopComponent(createCardPanel("BẢNG ĐIỂM LỚP: " + currentMaLop, scrollPane));
        splitPane.setBottomComponent(controlPanel);
        contentPanel.add(splitPane, BorderLayout.CENTER);
        pnMainContent.add(contentPanel, BorderLayout.CENTER);

        loadDiemData();

        // Table selection listener
        tblDiem.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tblDiem.getSelectedRow();
                int modelRow = getModelRowIndex(tblDiem, selectedRow);
                if (modelRow != -1) {
                    DefaultTableModel tableModel = (DefaultTableModel) tblDiem.getModel();
                    txtMaSV.setText(tableModel.getValueAt(modelRow, 0).toString());
                    txtHoTen.setText(tableModel.getValueAt(modelRow, 1).toString());
                    txtDiemQT.setText(tableModel.getValueAt(modelRow, 2) != null ? tableModel.getValueAt(modelRow, 2).toString() : "");
                    txtDiemGK.setText(tableModel.getValueAt(modelRow, 3) != null ? tableModel.getValueAt(modelRow, 3).toString() : "");
                    txtDiemCK.setText(tableModel.getValueAt(modelRow, 4) != null ? tableModel.getValueAt(modelRow, 4).toString() : "");
                    txtDiemTK.setText(tableModel.getValueAt(modelRow, 5) != null ? tableModel.getValueAt(modelRow, 5).toString() : "");
                    txtDiemChu.setText(tableModel.getValueAt(modelRow, 6) != null ? tableModel.getValueAt(modelRow, 6).toString() : "");
                    txtXepLoai.setText(tableModel.getValueAt(modelRow, 7) != null ? tableModel.getValueAt(modelRow, 7).toString() : "");
                }
            }
        });

        // Tính điểm tự động
        btnTinhDiem.addActionListener(e -> {
            calculateAndDisplayScores(txtDiemQT, txtDiemGK, txtDiemCK, txtDiemTK, txtDiemChu, txtXepLoai);
        });

        // Lưu điểm
        btnLuuDiem.addActionListener(e -> {
            if (txtMaSV.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sinh viên!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Kiểm tra điểm hợp lệ
            if (!kiemTraDiemHopLe(txtDiemQT.getText(), txtDiemGK.getText(), txtDiemCK.getText())) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập điểm hợp lệ (0-10)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Diem diem = new Diem();
                diem.setMaSV(txtMaSV.getText());
                diem.setMaLop(currentMaLop);

                // Xử lý điểm QT
                if (!txtDiemQT.getText().isEmpty()) {
                    diem.setDiemQuaTrinh(Float.parseFloat(txtDiemQT.getText()));
                } else {
                    diem.setDiemQuaTrinh(null);
                }

                // Xử lý điểm GK
                if (!txtDiemGK.getText().isEmpty()) {
                    diem.setDiemGiuaKy(Float.parseFloat(txtDiemGK.getText()));
                } else {
                    diem.setDiemGiuaKy(null);
                }

                // Xử lý điểm CK
                if (!txtDiemCK.getText().isEmpty()) {
                    diem.setDiemCuoiKy(Float.parseFloat(txtDiemCK.getText()));
                } else {
                    diem.setDiemCuoiKy(null);
                }

                // ✅ Tính điểm TK tự động nếu có đủ 3 điểm
                if (diem.getDiemQuaTrinh() != null && diem.getDiemGiuaKy() != null && diem.getDiemCuoiKy() != null) {
                    float diemTK = (diem.getDiemQuaTrinh() * 0.2f) + 
                                   (diem.getDiemGiuaKy() * 0.3f) + 
                                   (diem.getDiemCuoiKy() * 0.5f);
                    diem.setDiemTongKet(diemTK);
                    
                    // ✅ Tính điểm chữ và xếp loại tự động
                    diem.setDiemChu(tinhDiemChu(diemTK));
                    diem.setXepLoai(tinhXepLoai(diemTK));
                } else {
                    diem.setDiemTongKet(null);
                    diem.setDiemChu(null);
                    diem.setXepLoai(null);
                }

                // Kiểm tra xem đã có điểm chưa và lấy id nếu có
                Diem diemHienTai = diemService.getDiemByMaSVAndMaLop(txtMaSV.getText(), currentMaLop);

                if (diemHienTai != null) {
                    // Cập nhật điểm - set id từ điểm hiện tại
                    diem.setId(diemHienTai.getId());
                    if (diemService.capNhatDiem(diem)) {
                        JOptionPane.showMessageDialog(this, "Cập nhật điểm thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        loadDiemData();
                    } else {
                        JOptionPane.showMessageDialog(this, "Cập nhật điểm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    // Thêm điểm mới
                    if (diemService.nhapDiem(diem)) {
                        JOptionPane.showMessageDialog(this, "Nhập điểm thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        loadDiemData();
                    } else {
                        JOptionPane.showMessageDialog(this, "Nhập điểm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập điểm hợp lệ (số thực)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnXuatExcel.addActionListener(e -> {
            xuatDiemExcel();
        });

        btnGuiEmail.addActionListener(e -> {
            guiEmailKetQuaHocTap();
        });

        btnClear.addActionListener(e -> {
            clearForm(txtMaSV, txtHoTen, txtDiemQT, txtDiemGK, txtDiemCK, txtDiemTK, txtDiemChu, txtXepLoai);
        });

        btnBack.addActionListener(e -> switchPanel("LỚPHỌCPHẦN"));
    }

    private void addAutoCalculateListener(JTextField sourceField, JTextField diemQT, JTextField diemGK,
                                          JTextField diemCK, JTextField diemTK, JTextField diemChu, JTextField xepLoai) {
        sourceField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                // Tính toán ngay khi có thay đổi ở bất kỳ trường điểm nào
                calculateAndDisplayScores(diemQT, diemGK, diemCK, diemTK, diemChu, xepLoai);
            }
        });

        sourceField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                // Tính toán lại khi rời khỏi trường nhập liệu
                calculateAndDisplayScores(diemQT, diemGK, diemCK, diemTK, diemChu, xepLoai);
            }
        });
    }

    private boolean kiemTraDiemHopLe(String diemQT, String diemGK, String diemCK) {
        try {
            // Kiểm tra điểm QT
            if (!diemQT.isEmpty()) {
                float qt = Float.parseFloat(diemQT);
                if (qt < 0 || qt > 10) return false;
            }

            // Kiểm tra điểm GK
            if (!diemGK.isEmpty()) {
                float gk = Float.parseFloat(diemGK);
                if (gk < 0 || gk > 10) return false;
            }

            // Kiểm tra điểm CK
            if (!diemCK.isEmpty()) {
                float ck = Float.parseFloat(diemCK);
                if (ck < 0 || ck > 10) return false;
            }

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void calculateAndDisplayScores(JTextField diemQT, JTextField diemGK, JTextField diemCK,
                                           JTextField diemTK, JTextField diemChu, JTextField xepLoai) {
        try {
            float diemQTVal = diemQT.getText().isEmpty() ? 0 : Float.parseFloat(diemQT.getText());
            float diemGKVal = diemGK.getText().isEmpty() ? 0 : Float.parseFloat(diemGK.getText());
            float diemCKVal = diemCK.getText().isEmpty() ? 0 : Float.parseFloat(diemCK.getText());

            // Validate điểm
            if (diemQTVal < 0 || diemQTVal > 10 || diemGKVal < 0 || diemGKVal > 10 || diemCKVal < 0 || diemCKVal > 10) {
                // Nếu điểm không hợp lệ, xóa các kết quả tính toán
                diemTK.setText("");
                diemChu.setText("");
                xepLoai.setText("");
                return;
            }

            // Chỉ tính toán khi có đủ 3 điểm
            if (!diemQT.getText().isEmpty() && !diemGK.getText().isEmpty() && !diemCK.getText().isEmpty()) {
                float diemTKVal = (diemQTVal * 0.2f) + (diemGKVal * 0.3f) + (diemCKVal * 0.5f);
                diemTK.setText(String.format("%.2f", diemTKVal));

                // Tính điểm chữ và xếp loại
                String diemChuVal = tinhDiemChu(diemTKVal);
                String xepLoaiVal = tinhXepLoai(diemTKVal);

                diemChu.setText(diemChuVal);
                xepLoai.setText(xepLoaiVal);
            }

        } catch (NumberFormatException ex) {
            // Nếu có lỗi định dạng, xóa các kết quả tính toán
            diemTK.setText("");
            diemChu.setText("");
            xepLoai.setText("");
        }
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

    // ==================== DATA LOADING METHODS ====================

    private void loadLopHocPhanData() {
        if (tblLopHocPhan != null && giangVien != null) {
            DefaultTableModel model = (DefaultTableModel) tblLopHocPhan.getModel();
            model.setRowCount(0);
            List<LopHocPhan> listLHP = lopHocPhanService.getLopByGiangVien(giangVien.getMaGV());
            for (LopHocPhan lhp : listLHP) {
                model.addRow(new Object[]{
                        lhp.getMaLop(),
                        lhp.getMaMH(),
                        lhp.getHocKy(),
                        lhp.getNamHoc(),
                        lhp.getSiSoToiDa(),
                        lhp.getSiSoHienTai()
                });
            }
        }
    }

    private void loadLopHocPhanDataWithFilter(String hocKy, String namHoc) {
        if (tblLopHocPhan != null && giangVien != null) {
            DefaultTableModel model = (DefaultTableModel) tblLopHocPhan.getModel();
            model.setRowCount(0);
            List<LopHocPhan> listLHP = lopHocPhanService.getLopByGiangVien(giangVien.getMaGV());
            
            boolean filterHocKy = !hocKy.equals("Tất cả");
            boolean filterNamHoc = !namHoc.equals("Tất cả");
            
            for (LopHocPhan lhp : listLHP) {
                // Áp dụng bộ lọc
                boolean match = true;
                
                if (filterHocKy && !lhp.getHocKy().equals(hocKy)) {
                    match = false;
                }
                
                if (filterNamHoc && !lhp.getNamHoc().equals(namHoc)) {
                    match = false;
                }
                
                if (match) {
                    model.addRow(new Object[]{
                            lhp.getMaLop(),
                            lhp.getMaMH(),
                            lhp.getHocKy(),
                            lhp.getNamHoc(),
                            lhp.getSiSoToiDa(),
                            lhp.getSiSoHienTai()
                    });
                }
            }
        }
    }

    private void loadSinhVienData() {
        if (tblSinhVien != null && !currentMaLop.isEmpty()) {
            DefaultTableModel model = (DefaultTableModel) tblSinhVien.getModel();
            model.setRowCount(0);
            List<DangKiLop> listDangKy = dangKyLopService.getSVDaDangKy(currentMaLop);
            for (DangKiLop dk : listDangKy) {
                String maSV = dk.getMaSV();
                SinhVien sv = sinhVienService.getSinhVienById(maSV);
                if (sv != null) {
                    model.addRow(new Object[]{
                            sv.getMaSV(),
                            sv.getHoTen(),
                            sv.getGioiTinh(),
                            sv.getEmail(),
                            sv.getSoDienThoai()
                    });
                }
            }
        }
    }

    private void loadDiemData() {
        if (tblDiem != null && !currentMaLop.isEmpty()) {
            DefaultTableModel model = (DefaultTableModel) tblDiem.getModel();
            model.setRowCount(0);
            
            // Lấy danh sách tất cả sinh viên đã đăng ký lớp
            List<DangKiLop> listDangKy = dangKyLopService.getSVDaDangKy(currentMaLop);
            
            // Lấy danh sách điểm đã có
            List<Diem> listDiem = diemService.getDiemByLop(currentMaLop);
            
            // Duyệt qua tất cả sinh viên đã đăng ký
            for (DangKiLop dk : listDangKy) {
                String maSV = dk.getMaSV();
                SinhVien sv = sinhVienService.getSinhVienById(maSV);
                if (sv == null) continue;
                
                // Tìm điểm của sinh viên này (nếu có)
                Diem diemSV = null;
                for (Diem d : listDiem) {
                    if (d.getMaSV().equals(maSV)) {
                        diemSV = d;
                        break;
                    }
                }
                
                // Hiển thị sinh viên với điểm (nếu có) hoặc null (nếu chưa có)
                if (diemSV != null) {
                    model.addRow(new Object[]{
                            diemSV.getMaSV(),
                            sv.getHoTen(),
                            diemSV.getDiemQuaTrinh(),
                            diemSV.getDiemGiuaKy(),
                            diemSV.getDiemCuoiKy(),
                            diemSV.getDiemTongKet(),
                            diemSV.getDiemChu(),
                            diemSV.getXepLoai()
                    });
                } else {
                    // Sinh viên chưa có điểm
                    model.addRow(new Object[]{
                            sv.getMaSV(),
                            sv.getHoTen(),
                            null, null, null, null, null, null
                    });
                }
            }
        }
    }

    // ==================== UTILITY METHODS ====================

    private JPanel createFormField(String label, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(CARD_BG);
        panel.setBorder(new EmptyBorder(5, 0, 5, 0));

        JLabel lbl = new JLabel(label);
        lbl.setFont(BOLD_FONT);
        lbl.setForeground(new Color(80, 80, 80));
        lbl.setPreferredSize(new Dimension(100, 30));

        panel.add(lbl, BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);

        return panel;
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setFont(NORMAL_FONT);
        textField.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1),
                new EmptyBorder(8, 10, 8, 10)
        ));
        textField.setPreferredSize(new Dimension(200, 40));
        textField.setBackground(new Color(252, 252, 252));

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                textField.setBorder(new CompoundBorder(
                        new LineBorder(PRIMARY_COLOR, 1),
                        new EmptyBorder(8, 10, 8, 10)
                ));
                textField.setBackground(Color.WHITE);
            }
            @Override
            public void focusLost(FocusEvent e) {
                textField.setBorder(new CompoundBorder(
                        new LineBorder(BORDER_COLOR, 1),
                        new EmptyBorder(8, 10, 8, 10)
                ));
                textField.setBackground(new Color(252, 252, 252));
            }
        });

        return textField;
    }

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

    private void clearForm(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }

    private String tinhDiemChu(float diem) {
        if (diem >= 8.5) return "A";
        else if (diem >= 8.0) return "B+";
        else if (diem >= 7.0) return "B";
        else if (diem >= 6.5) return "C+";
        else if (diem >= 5.5) return "C";
        else if (diem >= 5.0) return "D+";
        else if (diem >= 4.0) return "D";
        else return "F";
    }

    private String tinhXepLoai(float diem) {
        if (diem >= 8.5) return "Giỏi";
        else if (diem >= 7.0) return "Khá";
        else if (diem >= 5.5) return "Trung bình";
        else if (diem >= 4.0) return "Yếu";
        else return "Kém";
    }

    private boolean kiemTraDiemTonTai(String maSV, String maLop) {
        List<Diem> listDiem = diemService.getDiemByLop(maLop);
        for (Diem diem : listDiem) {
            if (diem.getMaSV().equals(maSV)) {
                return true;
            }
        }
        return false;
    }

    // ==================== XUẤT EXCEL ====================
    // TODO: Cần thêm thư viện Apache POI để sử dụng tính năng xuất Excel
    
    private void xuatDiemExcel() {
        if (currentMaLop == null || currentMaLop.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng chọn lớp học phần trước!", 
                "Cảnh báo", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            ExcelExporter.exportTable(tblDiem, this, "DiemLop_" + currentMaLop);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi xuất Excel: " + ex.getMessage(), 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void exportTableToExcel(JTable table, File file, String sheetName) throws Exception {
        // TODO: Cần thêm thư viện Apache POI
        throw new Exception("Tính năng xuất Excel chưa được cài đặt");
        /*
        Workbook workbook = null;
        FileOutputStream fileOut = null;
        
        try {
            workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet(sheetName);
            
            // Tạo style cho header
            CellStyle headerStyle = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 12);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            // Tạo style cho data cells
            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setBorderBottom(BorderStyle.THIN);
            dataStyle.setBorderTop(BorderStyle.THIN);
            dataStyle.setBorderLeft(BorderStyle.THIN);
            dataStyle.setBorderRight(BorderStyle.THIN);
            
            // Tạo header row
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < table.getColumnCount(); col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(table.getColumnName(col));
                cell.setCellStyle(headerStyle);
            }
            
            // Điền data vào các dòng
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            for (int row = 0; row < model.getRowCount(); row++) {
                Row excelRow = sheet.createRow(row + 1);
                for (int col = 0; col < model.getColumnCount(); col++) {
                    Cell cell = excelRow.createCell(col);
                    Object value = model.getValueAt(row, col);
                    
                    if (value == null) {
                        cell.setCellValue("");
                    } else if (value instanceof Number) {
                        cell.setCellValue(((Number) value).doubleValue());
                    } else {
                        cell.setCellValue(value.toString());
                    }
                    
                    cell.setCellStyle(dataStyle);
                }
            }
            
            // Auto-size columns
            for (int col = 0; col < table.getColumnCount(); col++) {
                sheet.autoSizeColumn(col);
            }
            
            // Ghi file ra disk
            fileOut = new FileOutputStream(file);
            workbook.write(fileOut);
            
        } finally {
            if (fileOut != null) {
                try { fileOut.close(); } catch (Exception e) { }
            }
            if (workbook != null) {
                try { workbook.close(); } catch (Exception e) { }
            }
        }
        */
    }

    private void xuatLopHocPhanExcel() {
        try {
            ExcelExporter.exportTable(tblLopHocPhan, this, "DanhSachLopHocPhan");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi xuất Excel: " + ex.getMessage(), 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void xuatSinhVienExcel() {
        try {
            ExcelExporter.exportTable(tblSinhVien, this, "DanhSachSinhVien");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi xuất Excel: " + ex.getMessage(), 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    // ==================== GỬI EMAIL KẾT QUẢ HỌC TẬP ====================
    private void guiEmailKetQuaHocTap() {
        // Kiểm tra đã chọn lớp chưa
        if (currentMaLop.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn lớp học phần!",
                    "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Xác nhận gửi email cho tất cả sinh viên trong lớp
        int confirm = JOptionPane.showConfirmDialog(this,
                "Gửi email kết quả học tập cho TẤT CẢ sinh viên trong lớp?\n\n" +
                "Lớp: " + currentMaLop + "\n" +
                "Lưu ý: Quá trình này có thể mất vài phút.\n" +
                "Chỉ gửi cho sinh viên có email và có điểm.",
                "Xác nhận gửi email",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        // Tạo progress dialog
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setString("Đang gửi email kết quả học tập...");
        progressBar.setStringPainted(true);

        JDialog progressDialog = new JDialog(this, "Gửi Email Kết Quả", true);
        progressDialog.add(progressBar);
        progressDialog.setSize(350, 100);
        progressDialog.setLocationRelativeTo(this);
        progressDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        // Gửi email trong background thread
        SwingWorker<Integer, Void> worker = new SwingWorker<Integer, Void>() {
            @Override
            protected Integer doInBackground() throws Exception {
                return Mail.sendResultToAllStudentsInClass(currentMaLop);
            }

            @Override
            protected void done() {
                progressDialog.dispose();
                try {
                    int successCount = get();
                    JOptionPane.showMessageDialog(GVFrame.this,
                            "✅ Hoàn thành gửi email kết quả!\n\n" +
                            "Đã gửi thành công: " + successCount + " email\n" +
                            "Lớp: " + currentMaLop,
                            "Kết quả gửi email",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(GVFrame.this,
                            "❌ Lỗi gửi email: " + e.getMessage(),
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        };

        worker.execute();
        progressDialog.setVisible(true);
    }

    /**
     * Gửi email cho sinh viên được chọn trong bảng
     */
    private void guiEmailChoSinhVienDuocChon() {
        // Kiểm tra đã chọn sinh viên trong bảng chưa
        int selectedRow = tblDiem.getSelectedRow();
        int modelRow = getModelRowIndex(tblDiem, selectedRow);
        if (modelRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn sinh viên trong bảng điểm!",
                    "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Lấy thông tin sinh viên được chọn
        DefaultTableModel tableModel = (DefaultTableModel) tblDiem.getModel();
        String maSV = tableModel.getValueAt(modelRow, 0).toString();
        String hoTenSV = tableModel.getValueAt(modelRow, 1).toString();

        // Lấy thông tin sinh viên
        SinhVien sv = sinhVienService.getSinhVienById(maSV);
        if (sv == null) {
            JOptionPane.showMessageDialog(this,
                    "Không tìm thấy thông tin sinh viên!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kiểm tra email
        if (sv.getEmail() == null || sv.getEmail().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Sinh viên chưa có địa chỉ email!",
                    "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Lấy điểm
        Diem diem = diemService.getDiemByMaSVAndMaLop(maSV, currentMaLop);
        if (diem == null) {
            JOptionPane.showMessageDialog(this,
                    "Sinh viên chưa có điểm!",
                    "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Xác nhận gửi email
        int confirm = JOptionPane.showConfirmDialog(this,
                "Gửi kết quả học tập qua email?\n\n" +
                "Sinh viên: " + hoTenSV + " (" + maSV + ")\n" +
                "Email: " + sv.getEmail() + "\n" +
                "Lớp: " + currentMaLop + "\n" +
                "Điểm TK: " + String.format("%.1f", diem.getDiemTongKet()),
                "Xác nhận gửi email",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        // Gửi email
        try {
            String tenMonHoc = diemService.getTenMonHocByMaLop(currentMaLop);
            
            Mail.sendResultToStudent(maSV, currentMaLop, tenMonHoc,
                    diem.getDiemQuaTrinh(), diem.getDiemGiuaKy(), 
                    diem.getDiemCuoiKy(), diem.getDiemTongKet(),
                    diem.getDiemChu(), diem.getXepLoai());
            
            JOptionPane.showMessageDialog(this,
                    "✅ Đã gửi email thành công!\n\n" +
                    "Người nhận: " + hoTenSV + "\n" +
                    "Email: " + sv.getEmail(),
                    "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);
                    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "❌ Lỗi gửi email: " + e.getMessage() + "\n\n" +
                    "Vui lòng kiểm tra:\n" +
                    "- Kết nối internet\n" +
                    "- Cấu hình email trong Mail.java",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Gửi email cho tất cả sinh viên trong lớp
     */
    private void guiEmailChoTatCaSinhVien() {
        // Xác nhận gửi email hàng loạt
        int confirm = JOptionPane.showConfirmDialog(this,
                "Gửi email kết quả học tập cho TẤT CẢ sinh viên trong lớp?\n\n" +
                "Lớp: " + currentMaLop + "\n" +
                "Lưu ý: Quá trình này có thể mất vài phút.\n" +
                "Chỉ gửi cho sinh viên có email và có điểm.",
                "Xác nhận gửi email hàng loạt",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        // Tạo progress dialog
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setString("Đang gửi email...");
        progressBar.setStringPainted(true);

        JDialog progressDialog = new JDialog(this, "Gửi Email", true);
        progressDialog.add(progressBar);
        progressDialog.setSize(300, 100);
        progressDialog.setLocationRelativeTo(this);
        progressDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        // Gửi email trong background thread
        SwingWorker<Integer, Void> worker = new SwingWorker<Integer, Void>() {
            @Override
            protected Integer doInBackground() throws Exception {
                return Mail.sendResultToAllStudentsInClass(currentMaLop);
            }

            @Override
            protected void done() {
                progressDialog.dispose();
                try {
                    int successCount = get();
                    JOptionPane.showMessageDialog(GVFrame.this,
                            "✅ Hoàn thành gửi email!\n\n" +
                            "Đã gửi thành công: " + successCount + " email\n" +
                            "Lớp: " + currentMaLop,
                            "Kết quả gửi email",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(GVFrame.this,
                            "❌ Lỗi gửi email hàng loạt: " + e.getMessage(),
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        };

        worker.execute();
        progressDialog.setVisible(true);
    }

    /**
     * Tạo nội dung email kết quả học tập (deprecated - sử dụng Mail.createResultEmailContent)
     */
    @Deprecated
    private String taoNoiDungEmailKetQua(SinhVien sv, LopHocPhan lop, Diem diem) {
        StringBuilder content = new StringBuilder();
        
        content.append("========================================\n");
        content.append("   THÔNG BÁO KẾT QUẢ HỌC TẬP\n");
        content.append("========================================\n\n");
        
        content.append("Kính gửi: ").append(sv.getHoTen()).append("\n");
        content.append("Mã sinh viên: ").append(sv.getMaSV()).append("\n\n");
        
        content.append("Kết quả học tập của bạn:\n");
        content.append("----------------------------------------\n");
        content.append("Lớp học phần: ").append(lop.getMaLop()).append("\n");
        content.append("Môn học: ").append(lop.getMaMH()).append("\n");
        content.append("Học kỳ: ").append(lop.getHocKy()).append("\n");
        content.append("Năm học: ").append(lop.getNamHoc()).append("\n\n");
        
        content.append("BẢNG ĐIỂM:\n");
        content.append("----------------------------------------\n");
        content.append("Điểm quá trình:  ").append(String.format("%.1f", diem.getDiemQuaTrinh())).append("\n");
        content.append("Điểm giữa kỳ:    ").append(String.format("%.1f", diem.getDiemGiuaKy())).append("\n");
        content.append("Điểm cuối kỳ:    ").append(String.format("%.1f", diem.getDiemCuoiKy())).append("\n");
        content.append("----------------------------------------\n");
        content.append("ĐIỂM TỔNG KẾT:   ").append(String.format("%.1f", diem.getDiemTongKet())).append("\n");
        content.append("Điểm chữ:        ").append(diem.getDiemChu()).append("\n");
        content.append("Xếp loại:        ").append(diem.getXepLoai()).append("\n");
        content.append("========================================\n\n");
        
        content.append("Chúc mừng bạn đã hoàn thành môn học!\n\n");
        content.append("Trân trọng,\n");
        content.append("Hệ thống Quản lý Tình hình Học tập của Sinh viên\n");
        content.append("----------------------------------------\n");
        content.append("Email này được gửi tự động, vui lòng không trả lời.\n");
        
        return content.toString();
    }


}