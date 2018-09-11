package com.full.cn.utils;

import com.full.cn.model.MailBean;
import com.full.cn.service.SendMail;
import org.junit.Test;
import org.omg.CORBA.portable.OutputStream;

/**
 * 发送带附件的邮件
 * @author moafmoar
 * @createdate 2017-12-11
 *
 */
public class SendMain {

    //@Test
    public static void sendmail(String filePath){
        MailBean mb = new MailBean();
        SendMail sm = new SendMail();
        mb.setHost("smtp.126.com"); // 设置SMTP主机(163)，若用126，则设为：smtp.126.com
        mb.setUsername("cao_manman@126.com"); // 设置发件人邮箱的用户名
        mb.setPassword(""); // 设置发件人邮箱的密码，需将*号改成正确的密码
        mb.setFrom("cao_manman@126.com"); // 设置发件人的邮箱
        mb.setTo("17629001026@189.cn"); // 设置收件人的邮箱
        mb.setSubject("测试_JavaMail"); // 设置邮件的主题
        mb.setContent("本邮件中包含三个附件，请检查！"); // 设置邮件的正文
        //OutputStream bos = new OutputStream("");
        //PdfAddwaterMark.setWeater(filePath,);
        mb.attachFile(filePath); // 往邮件中添加附件
        //mb.attachFile("往邮件中添加附件");
        //mb.attachFile("往邮件中添加附件");


        System.out.println("正在发送邮件...");
        // 发送邮件
        if (sm.sendMail(mb)){
            System.out.println("发送成功!");
        }else{
            System.out.println("发送失败!");
        }
    }

}
