package com.full.cn.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by full on 2017/7/4.
 */
public class ExceltoPdf {

    public static void main(String[] args) {
        String Fname = "E:\\lucene\\test.xlsx";
        String Filename = Fname.substring(Fname.lastIndexOf("\\")+1);
        System.out.println(Filename);

        try {
            int maxNum = 1;
            File file = new File("test.xlsx");
            InputStream is1 = ExceltoPdf.class.getResourceAsStream(Filename);
            //InputStream is1 = new FileInputStream(file);
            POIFSFileSystem fs1 = new POIFSFileSystem(is1);
            HSSFWorkbook book1 = new HSSFWorkbook(fs1);
            HSSFSheet sheet1;
            for (int i = 0; i < book1.getNumberOfSheets(); i++) {
                sheet1 = book1.getSheetAt(i);
                for (int j = 0; j <= sheet1.getPhysicalNumberOfRows(); j++) {
                    HSSFRow row = sheet1.getRow(j);
                    if (null != row) {
                        if (maxNum < row.getPhysicalNumberOfCells()) {
                            maxNum = row.getPhysicalNumberOfCells();
                        }
                    }
                }
            }
            maxNum += 1;
            InputStream is = ExceltoPdf.class.getResourceAsStream("新建 Microsoft Excel 工作表.xlsx");
            Document doc = new Document();
            doc.setPageSize(PageSize.A4);
            PdfWriter.getInstance(doc, new FileOutputStream("test.pdf"));

            BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            Font headFont = new Font(bf, 12, Font.BOLD);
            Font font = new Font(bf, 10, Font.NORMAL);
            doc.open();

            POIFSFileSystem fs = new POIFSFileSystem(is);
            HSSFWorkbook book = new HSSFWorkbook(fs);
            HSSFSheet sheet;
            for (int i = 0; i < book.getNumberOfSheets(); i++) {
                sheet = book.getSheetAt(i);
                doc.add(new Paragraph(sheet.getSheetName(), headFont));
                PdfPTable table = new PdfPTable(maxNum);
                table.setWidthPercentage(100);
                table.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
                for (int j = 0; j <= sheet.getPhysicalNumberOfRows(); j++) {
                    PdfPCell cell = new PdfPCell();
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    HSSFRow row = sheet.getRow(j);
                    if (null != row) {
                        int currentNum = row.getPhysicalNumberOfCells();
                        for (int k = 0; k < maxNum; k++) {
                            if (currentNum < maxNum) {
                                String value = getCellFormatValue(row.getCell(k));
                                cell.setPhrase(new Paragraph(value, font));
                            } else {
                                cell.setPhrase(new Paragraph(" ", font));
                            }
                            table.addCell(cell);
                        }
                    }
                }
                doc.add(table);
            }
            doc.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static String getCellFormatValue(HSSFCell cell) {
        String cellValue = "";
        if (null != cell) {
            switch(cell.getCellType()) {
                case HSSFCell.CELL_TYPE_NUMERIC:
                case HSSFCell.CELL_TYPE_FORMULA:
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        cellValue = sdf.format(date);
                    } else {
                        DecimalFormat df = new DecimalFormat("########");
                        cellValue = df.format(cell.getNumericCellValue());
                    }
                    break;
                case HSSFCell.CELL_TYPE_STRING:
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                default:
                    cellValue = " ";
            }
        } else {
            cellValue = "";
        }
        return cellValue;
    }
}
