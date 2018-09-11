package com.full.cn.utils;

import org.junit.Test;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataUtils {

    public static void main(String[] args) {
        String str = "1100";
        long mss = Long.parseLong(str);
        long minutes = (mss % ( 60 * 60)) /60;  //分钟
        long seconds = mss % 60;   //秒
        long hours = (mss % ( 60 * 60 * 24)) / (60 * 60); //小时
        long days = mss / ( 60 * 60 * 24); //天
        String data = hours+"时"+minutes+"分"+seconds+"秒";
        System.out.println(data);

        BigInteger a1 = new BigInteger("1");
        BigInteger a2 = new BigInteger("2");
        System.out.println(a1.equals(a2));


    }

    @Test
    public void getLastDate() {
        long day = Long.parseLong("1516002025000");
        Date date = new Date();
        long date_3_hm = date.getTime() - 3600000 * 34 * day;
        Date date_3_hm_date = new Date(date_3_hm);
        System.out.println(date_3_hm_date);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date_3_hm_date);
        System.out.println(dateString);
        //return date_3_hm_date;
    }


    /**
     * long型时间格式化
     */
    @Test
    public void formatTimeInMillis() throws ParseException {
        //long timeInMillis = 1516002025000L;
        String longtime = "20180117145000";
        long timeInMillis = Long.parseLong(longtime);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeInMillis);
        Date date = cal.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String fmt = dateFormat.format(date);
        Date date1 = dateFormat.parse(fmt);
        System.out.println(fmt);
        //System.out.println(date1);
        //return fmt;
    }

    @Test
    public void getdata(){

            String str = "2011/05/17 11:39:00";
            Date date = null;
            try {
                date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
//	将date转化为String
            String s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
            System.out.println(s);

    }

    @Test
    public void stringDateFmt(){

        String str="20180117150000";
        SimpleDateFormat fo = new SimpleDateFormat("yyyyMMddHHmmss");//yyyyMMddHHmm是要转化成日期的字符串的格式
        Date date = new Date();
        try {
            date = fo.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

        System.out.println(s);

    }
}
