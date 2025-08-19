package com.ximalaya.ssd.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 〈工具〉
 *
 * @author gongjiawei
 * @date 2025/8/11 下午3:39
 */
@Slf4j
public class DateUtils {
    public static final Long              INVALID_TIMESTAMP = 0L;

    public static final DateTimeFormatter DATE_TIME_DF      = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.systemDefault());

    public static final DateTimeFormatter TIME_DF           = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm")
            .withZone(ZoneId.systemDefault());

    public static final DateTimeFormatter DOWNLOAD_TIME_DF  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.systemDefault());

    public static final DateTimeFormatter FILE_DF           = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
            .withZone(ZoneId.systemDefault());

    public static final DateTimeFormatter DATE_OF           = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault());

    public static String stampToDateStr(long stamp){
        return DATE_TIME_DF.format(Instant.ofEpochMilli(stamp));
    }

    public static Date getYesterday(){
        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        now.add(Calendar.DATE, -1);
        return now.getTime();
    }

    public static Date onlyDay(Date date){
        Calendar tmp = Calendar.getInstance();
        tmp.setTime(date);
        tmp.set(Calendar.HOUR_OF_DAY, 0);
        tmp.set(Calendar.MINUTE, 0);
        tmp.set(Calendar.SECOND, 0);
        tmp.set(Calendar.MILLISECOND, 0);
        return tmp.getTime();
    }

    public static Date getDateBaseOn(Date date, long duration, TimeUnit timeUnit){
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.SECOND, (int) timeUnit.toSeconds(duration));
        return now.getTime();
    }

    public static int getDate(Date date){
        return Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(date));
    }

    public static Date getDate(int date){
        try{
            return new SimpleDateFormat("yyyyMMdd").parse(String.valueOf(date));
        }catch (ParseException e){
            log.error("date:{} parse error", date, e);
            return null;
        }
    }

    public static int dateDiff(Date begin,Date end){
        int dateDiff = (int) ((end.getTime() - begin.getTime()) / TimeUnit.DAYS.toMillis(1));
        dateDiff++;
        return dateDiff;
    }

    public static String dateFormat(Date date,String pattern){
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 已知年份和月份，获取该月的最后一天
     */
    public static int getLastDayOfSomeMonth(int year,int month){

        Calendar calendar = Calendar.getInstance();
        //设置年份
        calendar.set(Calendar.YEAR, year);
        //设置月份
        calendar.set(Calendar.MONTH, month - 1);
        //获取某月最大天数
        int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置该月份的最后一天
        calendar.set(Calendar.DAY_OF_MONTH, lastDay);

        return getDate(calendar.getTime());

    }

    /**
     * 已知年份和月份，获取该月的第一天
     */
    public static int getfirstDayOfSomeMonth(int year,int month){

        Calendar calendar = Calendar.getInstance();
        //设置年份
        calendar.set(Calendar.YEAR, year);
        //设置月份
        calendar.set(Calendar.MONTH, month - 1);
        //获取某月最大天数
        int firstDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        //设置该月份的最后一天
        calendar.set(Calendar.DAY_OF_MONTH, firstDay);

        return getDate(calendar.getTime());

    }

    public static Date getDateFromStr(String str){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try{
            date = sdf.parse(str);
            return date;
        }catch (Exception e){
            log.error("the String form of date is yyyyMMdd, not date:{}", str, e);
            return null;
        }
    }

    /**
     * 日期样式转换
     * 例如 20191012 换成 2019-10-12
     *
     * @param str
     * @return
     */
    public static String transferDate(String str){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        if ("0".equals(str)){
            return "";
        }
        try{
            date = sdf.parse(str);
        }catch (Exception e){
            log.error(

                            "the String form yyyyMMdd of date transfer to the form yyyy-MM-dd is failed, the original date : {}",
                            str,
                    e);
        }

        SimpleDateFormat newSdf = new SimpleDateFormat("yyyy-MM-dd");
        return newSdf.format(date);

    }
}
