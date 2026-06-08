package until;

import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;
import java.awt.Component;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Utility class to export JTable data to an Excel-compatible XML file (SpreadsheetML).
 * The generated file can be opened directly by Microsoft Excel and other spreadsheet tools.
 */
public class ExcelExporter {

    private ExcelExporter() {
        // Utility class
    }

    public static void exportTable(JTable table, Component parent, String defaultFileName) throws Exception {
        if (table == null) {
            throw new IllegalArgumentException("Bảng dữ liệu không được rỗng!");
        }

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Lưu file Excel");
        
        // Tạo filter cho file Excel
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel (*.xlsx)", "xlsx");
        chooser.setFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false); // Tắt "All Files"
        
        // Set file mặc định
        chooser.setSelectedFile(new File(defaultFileName + ".xlsx"));

        int result = chooser.showSaveDialog(parent);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file = chooser.getSelectedFile();
        
        // Đảm bảo file có đuôi .xlsx
        String fileName = file.getName();
        if (!fileName.toLowerCase().endsWith(".xlsx")) {
            file = new File(file.getParentFile(), fileName + ".xlsx");
        }

        writeXlSX(table.getModel(), file);
        JOptionPane.showMessageDialog(parent,
                "Xuất dữ liệu thành công!\n" + file.getAbsolutePath(),
                "Thành công",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private static void writeXlSX(TableModel model, File file) throws Exception {
        Workbook workbook = null;
        FileOutputStream out = null;
        
        try {
            workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Sheet1");

            // Tạo header với style
            Row header = sheet.createRow(0);
            org.apache.poi.ss.usermodel.CellStyle headerStyle = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            
            for (int col = 0; col < model.getColumnCount(); col++) {
                org.apache.poi.ss.usermodel.Cell cell = header.createCell(col);
                cell.setCellValue(model.getColumnName(col));
                cell.setCellStyle(headerStyle);
            }

            // Ghi dữ liệu
            for (int row = 0; row < model.getRowCount(); row++) {
                Row excelRow = sheet.createRow(row + 1);

                for (int col = 0; col < model.getColumnCount(); col++) {
                    Object value = model.getValueAt(row, col);
                    org.apache.poi.ss.usermodel.Cell cell = excelRow.createCell(col);

                    if (value instanceof Number) {
                        cell.setCellValue(((Number) value).doubleValue());
                    } else {
                        cell.setCellValue(value == null ? "" : value.toString());
                    }
                }
            }
            
            // Auto-size columns
            for (int col = 0; col < model.getColumnCount(); col++) {
                sheet.autoSizeColumn(col);
            }

            // Ghi file
            out = new FileOutputStream(file);
            workbook.write(out);
            
        } finally {
            // Đảm bảo đóng resources
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}


