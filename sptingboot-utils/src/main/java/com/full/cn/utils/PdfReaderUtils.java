package com.full.cn.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by full on 2017/7/3.
 */
public class PdfReaderUtils {

    //@Test
    public static Map<String,String> Pdfread(String fileName){
        File pdfFile = new File(fileName);
        Map<String,String> contextmap = new HashMap<>();
        contextmap.put("fileName",pdfFile.getName());

        PDDocument document = null;
        String content = null;
        try
        {
            // 方式二：
            document=PDDocument.load(pdfFile);
            // 获取页码
            int pages = document.getNumberOfPages();
            // 读文本内容
            PDFTextStripper stripper=new PDFTextStripper();
            // 设置按顺序输出
            stripper.setSortByPosition(true);
            stripper.setStartPage(1);
            stripper.setEndPage(pages);
            content = stripper.getText(document);
            System.out.println("======================start====================");
            System.out.println(content);
            contextmap.put("context",content);

            System.out.println("====================== end ====================");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return contextmap;
    }
}
