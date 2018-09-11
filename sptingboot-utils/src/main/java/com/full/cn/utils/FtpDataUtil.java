package com.full.cn.utils;

import org.apache.commons.net.ftp.FTPClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by full on 2017/5/26.
 *
 */
public class FtpDataUtil {
    public static String strecoding = "UTF-8";
    public static List<Map<String,String>> readFile(String fileName,String ip,int port,String userName,String userPwd,String path) throws ParseException {
        InputStream ins = null;
        StringBuilder builder = null;
        Map<String,String> dataMap = null;
        FTPClient  ftpClient = new FTPClient();
        try {
            // 连接
            ftpClient.connect(ip, port);
            // 登录
            ftpClient.login(userName, userPwd);
            if (path != null && path.length() > 0) {
                // 跳转到指定目录
                ftpClient.changeWorkingDirectory(path);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Map<String,String>> dataList = new ArrayList<Map<String, String>>();
        try {
            ftpClient.enterLocalPassiveMode();
            // 从服务器上读取指定的文件
            ins = ftpClient.retrieveFileStream(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(ins, strecoding));
            String line;
            builder = new StringBuilder(150);
            while ((line = reader.readLine()) != null) {
                dataMap = new HashMap<String, String>();
                System.out.println(line);
                String[] datas = line.split(",");
                dataMap.put("usercode",datas[0]);
                dataMap.put("username",datas[1]);
                dataMap.put("orgcode",datas[2]);
                dataMap.put("orgName",datas[3]);
                dataList.add(dataMap);

                builder.append(line);
            }
            reader.close();
            if (ins != null) {
                ins.close();
            }
            // 主动调用一次getReply()把接下来的226消费掉. 这样做是可以解决这个返回null问题
            ftpClient.getReply();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;
    }

}
