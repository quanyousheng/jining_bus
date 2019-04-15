package com.whpe.qrcode.shandong_jining.bigtools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 129674 on 2018/4/12.
 */

public class DateUtils {
    public static String getWeichatTimestamp(){

        return System.currentTimeMillis()+"";
    }

    public static String getNowDateyyyyMMddhhmmss(){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateNowStr = sdf.format(d);
        return dateNowStr;
    }

    public static String getNowtext(String st_date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date=new Date();
        try {
            date = sdf.parse(st_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat new_sdf = new SimpleDateFormat("MM-dd HH:mm");
        String dateNowStr = new_sdf.format(date);
        return dateNowStr;
    }

    public static String getNowtext(String st_date,String gs){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date=new Date();
        try {
            date = sdf.parse(st_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat new_sdf = new SimpleDateFormat(gs);
        String dateNowStr = new_sdf.format(date);
        return dateNowStr;
    }

    public static String getNowDatetext(String st_date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        try {
            date = sdf.parse(st_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat new_sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = new_sdf.format(date);
        return dateNowStr;
    }

    public static long getBetween(long new_time,long old_time){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        long second=0;
        try
        {
            Date d1 = df.parse(""+new_time);
            Date d2 = df.parse(""+old_time);
            long diff = d1.getTime() - d2.getTime();
            second = diff / (1000);
        }
        catch (Exception e)
        {
        }
        return second;
    }
}
