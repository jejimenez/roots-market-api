package co.logike.roots.market.core.app.components;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import co.logike.roots.market.core.app.entity.OrderGroupingProducerReport;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.IndexedColors;

public class OrderGroupingProducerExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<OrderGroupingProducerReport> listUsers;

    public OrderGroupingProducerExcelExporter(List<OrderGroupingProducerReport> listUsers) {
        this.listUsers = listUsers;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Pedidos");

        Row row = sheet.createRow(0);
        int r = 0;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, r, "Productor", style);
        createCell(row, ++r, "Producto", style);
        createCell(row, ++r, "Presentacion", style);
        createCell(row, ++r, "Cantidad", style);
        createCell(row, ++r, "Costo", style);
        createCell(row, ++r, "Total", style);
        createCell(row, ++r, "Cliente", style);
        createCell(row, ++r, "Fecha Orden", style);

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else if (value instanceof BigDecimal){
            cell.setCellValue(value.toString());
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;
        Row rowfinal = sheet.createRow(rowCount);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        // TOTAL AND HEADER
        CellStyle styleTotals = workbook.createCellStyle();
        XSSFFont fontTotals = workbook.createFont();
        fontTotals.setFontHeight(14);
        styleTotals.setFont(font);
        styleTotals.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        styleTotals.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        CellStyle currentstyle;
        for (OrderGroupingProducerReport order : listUsers) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            currentstyle = order.getGroupingClient() == '1' ? styleTotals : style;
            createCell(row, columnCount++, order.getGroupingProducer() == '1' ? "TOTAL" : order.getProducer(), currentstyle);
            createCell(row, columnCount++, order.getGroupingClient() == '1' ? "Total" : order.getProduct(), currentstyle);
            createCell(row, columnCount++, order.getPackaging(), currentstyle);
            createCell(row, columnCount++, order.getUnits() == null ? "" :order.getUnits().intValue(), currentstyle);
            createCell(row, columnCount++, order.getCost() == null ? "" : order.getCost().intValue(), currentstyle);
            createCell(row, columnCount++, order.getTotal() == null ? "" : order.getTotal().intValue(), currentstyle);
            createCell(row, columnCount++, order.getClient(), currentstyle);
            createCell(row, columnCount++, order.getCreationTime(), currentstyle);
            rowfinal = row;
        }

    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }
}
