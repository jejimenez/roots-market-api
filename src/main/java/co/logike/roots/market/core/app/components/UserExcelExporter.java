package co.logike.roots.market.core.app.components;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import co.logike.roots.market.core.api.objects.OrderProductDTO;
import co.logike.roots.market.core.app.entity.OrderReport;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class UserExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<OrderReport> listUsers;

    public UserExcelExporter(List<OrderReport> listUsers) {
        this.listUsers = listUsers;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Pedidos");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Id", style);
        createCell(row, 1, "Orden_de_compra", style);
        createCell(row, 2, "Cliente", style);
        createCell(row, 3, "Address", style);
        createCell(row, 4, "Producto", style);
        createCell(row, 5, "Cantidad", style);
        createCell(row, 6, "Costo", style);
        createCell(row, 7, "Precio", style);
        createCell(row, 8, "Total", style);
        createCell(row, 9, "Productor", style);
        createCell(row, 10, "Fecha", style);

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
        
        for (OrderReport user : listUsers) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, user.getId().toString(), style);
            createCell(row, columnCount++, user.getPurchaseOrder().toString(), style);
            createCell(row, columnCount++, user.getClient(), style);
            createCell(row, columnCount++, user.getAddress(), style);
            createCell(row, columnCount++, user.getProduct(), style);
            createCell(row, columnCount++, user.getUnits() == null ? null : user.getUnits().intValue(), style);
            createCell(row, columnCount++, user.getCost() == null ? null : user.getCost().intValue(), style);
            createCell(row, columnCount++, user.getPrice() == null ? null : user.getPrice().intValue(), style);
            createCell(row, columnCount++, user.getPrice() == null ? null : (user.getUnits() == null ? "" : (user.getPrice().intValue() * user.getUnits().intValue()) ), style);
            createCell(row, columnCount++, user.getProducer(), style);
            createCell(row, columnCount++, user.getCreationTime(), style);
            rowfinal = row;
        }
        String strFormula= "SUM(A1:A10)";
        Cell cellSum = rowfinal.createCell(rowfinal.getRowNum());
        cellSum.setCellFormula(strFormula);

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
