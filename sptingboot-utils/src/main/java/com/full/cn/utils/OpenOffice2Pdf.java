package com.full.cn.utils;

import com.artofsolving.jodconverter.BasicDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.DocumentFormatRegistry;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;


import java.io.File;
import java.io.FileNotFoundException;
import java.net.ConnectException;
import java.util.regex.Pattern;

/**
 * Created by full on 2017/7/6.
 */
public class OpenOffice2Pdf {
    /**
     * @param args
     */
    private static OfficeManager officeManager;
    private static String OFFICE_HOME = "/opt/openoffice.org4";
    private static int port[] = { 8100 };
    private static OpenOfficeConnection connection;

    public static Boolean convert2PDF(String inputFile, String outputFile) throws FileNotFoundException {

        //startService();
        System.out.println("进行文档转换转换:" + inputFile + " --> " + outputFile);
        boolean flag = true;

        try {
            connection=new SocketOpenOfficeConnection("10.68.68.127",8100);
            connection.connect();
            DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);
            converter.convert(new File(inputFile), new File(outputFile));
            stopService();
        } catch (ConnectException e) {
            e.printStackTrace();
            flag = false;
        }



        System.out.println();
        return flag;
    }

    // 打开服务器
    public static void startService() {
        DefaultOfficeManagerConfiguration configuration = new DefaultOfficeManagerConfiguration();
        try {
            System.out.println("准备启动服务....");
            configuration.setOfficeHome(OFFICE_HOME);// 设置OpenOffice.org安装目录
            configuration.setPortNumbers(port); // 设置转换端口，默认为8100
            configuration.setTaskExecutionTimeout(1000 * 60 * 5L);// 设置任务执行超时为5分钟
            configuration.setTaskQueueTimeout(1000 * 60 * 60 * 24L);// 设置任务队列超时为24小时

            officeManager = configuration.buildOfficeManager();
            officeManager.start(); // 启动服务
            System.out.println("office转换服务启动成功!");
        } catch (Exception ce) {
            System.out.println("office转换服务启动失败!详细信息:" + ce);
        }
    }

    // 关闭服务器
    public static void stopService() {
        System.out.println("关闭office转换服务....");
        if (connection != null) {
            connection.disconnect();
        }
        System.out.println("关闭office转换成功!");
    }

    public static String getOfficeHome() {
        String osName = System.getProperty("os.name");
        if (Pattern.matches("Linux.*", osName)) {
            return "/opt/openoffice.org4";
        } else if (Pattern.matches("Windows.*", osName)) {
            return "D:/Program Files/OpenOffice.org 3";
        } else if (Pattern.matches("Mac.*", osName)) {
            return "/Application/OpenOffice.org.app/Contents";
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        String path = "e:/text/";
        //OpenOfficePdfConvert opc = new OpenOfficePdfConvert();

        convert2PDF(path+"daochu.docx", path+"daochu2.pdf");
    }
}
