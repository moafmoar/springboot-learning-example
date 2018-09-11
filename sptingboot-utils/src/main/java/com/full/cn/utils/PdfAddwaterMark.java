package com.full.cn.utils;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;

import com.itextpdf.text.pdf.*;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * pdf添加水印
 * @author full
 * @Date 2017-07-26
 */
public class PdfAddwaterMark {
    public static void main(String[] args) throws DocumentException,
            IOException {
        // 要输出的pdf文件
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File("E:\\text\\QSG_162.pdf")));
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        // 将pdf文件先加水印然后输出
        setWatermark(bos, "E:\\text\\SanDisk_SecureAccess_QSG.pdf", format.format(cal.getTime())
                + "  下载使用人：" + "测试user", 16);
    }

    public static void setWatermark(BufferedOutputStream bos, String input,
                                    String waterMarkName, int permission) throws DocumentException,
            IOException {

        PdfReader reader = new PdfReader(input);
        PdfStamper stamper = new PdfStamper(reader, bos);
        int total = reader.getNumberOfPages() + 1;
        String waterText = "公司内部文件，请注意保密！";
        int j = waterText.length(); // 文字长度
        PdfContentByte content;
        BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
        PdfGState gs = new PdfGState();
        for (int i = 1; i < total; i++) {
            //for (int j = 0; j < 10; j++) {
            content = stamper.getOverContent(i);// 在内容上方加水印
            //content = stamper.getUnderContent(i);//在内容下方加水印
            gs.setFillOpacity(0.2f);
            // content.setGState(gs);
            content.beginText();
            for (int k = 0; k <j ; k++) {

                content.setColorFill(BaseColor.LIGHT_GRAY);
                content.setFontAndSize(base, 50);
                content.setTextMatrix(70, 200);
                content.showTextAligned(Element.ALIGN_CENTER, waterText, 300,350, 55);
                //Image image = Image.getInstance("E:\\text\\IMG_3862.JPG");
            /*img.setAlignment(Image.LEFT | Image.TEXTWRAP);
            img.setBorder(Image.BOX);
            img.setBorderWidth(10);
            img.setBorderColor(BaseColor.WHITE);
            img.scaleToFit(1000, 72);//大小
            img.setRotationDegrees(-30);//旋转 */
                //image.setAbsolutePosition(200, 206); // set the first background image of the absolute
                //image.scaleToFit(200,200);
                //content.addImage(image);
                content.setColorFill(BaseColor.BLACK);
                content.setFontAndSize(base, 8);
                content.showTextAligned(Element.ALIGN_CENTER, "下载时间："
                        + waterMarkName + "", 300, 10, 0);
            }

            content.endText();
        }



        // }
        System.out.println("水印添加成功！！");
        stamper.close();
    }


    public static void setWeater(String input, OutputStream bos, String waterMarkName)throws DocumentException,
            IOException {
        PdfReader reader = new PdfReader(input);
        PdfStamper stamper = new PdfStamper(reader, bos);
        int total = reader.getNumberOfPages() + 1;
        String waterText = "公司内部文件，请注意保密！";
        int j = waterText.length(); // 文字长度
        PdfContentByte content;
        BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);
        PdfGState gs = new PdfGState();
        for (int i = 1; i < total; i++) {
            //for (int j = 0; j < 10; j++) {
            content = stamper.getOverContent(i);// 在内容上方加水印
            //content = stamper.getUnderContent(i);//在内容下方加水印
            gs.setFillOpacity(0.2f);
            // content.setGState(gs);
            content.beginText();
            for (int k = 0; k <j ; k++) {

                content.setColorFill(BaseColor.LIGHT_GRAY);
                content.setFontAndSize(base, 50);
                content.setTextMatrix(70, 200);
                content.showTextAligned(Element.ALIGN_CENTER, waterText, 300,350, 55);
                //Image image = Image.getInstance("E:\\text\\IMG_3862.JPG");
            /*img.setAlignment(Image.LEFT | Image.TEXTWRAP);
            img.setBorder(Image.BOX);
            img.setBorderWidth(10);
            img.setBorderColor(BaseColor.WHITE);
            img.scaleToFit(1000, 72);//大小
            img.setRotationDegrees(-30);//旋转 */
                //image.setAbsolutePosition(200, 206); // set the first background image of the absolute
                //image.scaleToFit(200,200);
                //content.addImage(image);
                content.setColorFill(BaseColor.BLACK);
                content.setFontAndSize(base, 8);
                content.showTextAligned(Element.ALIGN_CENTER, "下载时间："
                        + waterMarkName + "", 300, 10, 0);
            }

            content.endText();
        }



        // }
        System.out.println("水印添加成功！！");
        stamper.close();
    }

}
