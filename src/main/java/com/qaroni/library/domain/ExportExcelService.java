package com.qaroni.library.domain;

import com.qaroni.library.application.adapters.repository.entity.Author;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ExportExcelService {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public void newExcel() {
        workbook = new XSSFWorkbook();
    }

    public HttpServletResponse initResponseForExportExcel(HttpServletResponse response, String fileName) {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName + "_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        return response;
    }

    public void writeTableHeaderExcel(int rowNumber,  String titleName, String[] headers) {

        org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowNumber);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(20);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);

        createCell(row, rowNumber, titleName, style);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headers.length - 1));
        font.setFontHeightInPoints((short) 10);

        // header
        row = sheet.createRow(1);
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        for (int i = 0; i < headers.length; i++) {
            createCell(row, i, headers[i], style);
        }
    }

    public void createCell(org.apache.poi.ss.usermodel.Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        org.apache.poi.ss.usermodel.Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    public CellStyle getFontContentExcel() {
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        return style;
    }

    public void writeTableData(Long numBooks, List<Author> authors) {
        // font style content
        CellStyle style = getFontContentExcel();

        // starting write on row
        int startRow = 1;

        Row row = sheet.createRow(startRow++);
        createCell(row, 1, "Total de libros", style);
        createCell(row, 2, numBooks, style);

        row = sheet.createRow(startRow++);
        createCell(row, 1, "Total de autores", style);
        createCell(row, 2, authors.size(), style);

        String[] headers = new String[]{"Id", "Document Id", "Full Name", "Númnero de Libros"};
        writeTableHeaderExcel(startRow++, "Libros por autor", headers);

        for (Author author : authors) {
            row = sheet.createRow(startRow++);
            int columnCount = 0;
            createCell(row, columnCount++, author.getId(), style);
            createCell(row, columnCount++, author.getDocumentId(), style);
            createCell(row, columnCount++, author.getFullName(), style);
            createCell(row, columnCount, author.getBooks().size(), style);
        }
    }

    public void exportToExcel(HttpServletResponse response, Long numBooks, List<Author> authors) throws IOException {
        newExcel();

        sheet = workbook.createSheet("Library");

        // response  writer to excel
        response = initResponseForExportExcel(response, "Library");
        ServletOutputStream outputStream = response.getOutputStream();

        // write content row
        writeTableData(numBooks, authors);

        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
