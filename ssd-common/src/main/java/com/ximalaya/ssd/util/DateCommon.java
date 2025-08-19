package com.ximalaya.ssd.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 〈时间获取工具〉
 *
 * @author gongjiawei
 * @date 2025/8/11 下午3:37
 */
public class DateCommon {

    /**
     * dateScope ： 1(昨天)、7（近7日）、30（近30日）、2019-10 等形式
     * 获取 时间段内的 dateBegin 和 dateEnd
     *
     * @param dateScope
     * @return
     */
    public static int[] getDateBeginAndEnd(String dateScope){

        int[] date = new int[2];
        if ("1".equals(dateScope)){
            date[0] = DateUtils.getDate(DateUtils.getYesterday());

        }else if ("7".equals(dateScope)){
            date[1] = DateUtils.getDate(DateUtils.getYesterday());
            Date before = DateUtils.getDateBaseOn(DateUtils.getYesterday(), -6, TimeUnit.DAYS);
            date[0] = DateUtils.getDate(before);

        }else if ("30".equals(dateScope)){
            date[1] = DateUtils.getDate(DateUtils.getYesterday());
            Date before = DateUtils.getDateBaseOn(DateUtils.getYesterday(), -29, TimeUnit.DAYS);
            date[0] = DateUtils.getDate(before);

        }else{
            String[] dateStr = dateScope.split("-");
            int year = Integer.parseInt(dateStr[0]);
            int month = Integer.parseInt(dateStr[1]);
            date[0] = DateUtils.getfirstDayOfSomeMonth(year, month);
            date[1] = DateUtils.getLastDayOfSomeMonth(year, month);
        }

        return date;
    }

    /**
     * 根据条数，获取查询次数
     * 控制在5000条以内，分页查询每次查500条
     *
     * @param totalNum
     * @return
     */
//    public static int getQueryCount(int totalNum){
//
//        if (totalNum > ContentExportUtils.MAX_EXPORT_RECORD_NUM){
//            log.warn(autoRequestInfo("========导出数据过多，有{},只能3取前5000条", totalNum));
//            totalNum = ContentExportUtils.MAX_EXPORT_RECORD_NUM;
//        }
//
//        int queryCount;
//        if (totalNum > ContentExportUtils.PAGE_SIZE){
//            queryCount = totalNum % ContentExportUtils.PAGE_SIZE == 0 ? (totalNum / ContentExportUtils.PAGE_SIZE)
//                    : (totalNum / ContentExportUtils.PAGE_SIZE + 1);
//        }else{
//            queryCount = 1;
//        }
//
//        return queryCount;
//    }

    /**
     * 获取某天的前N天日期（如：20191010，后退一周日期为 20191004
     *
     * @param date
     * @param num
     * @return
     */
    public static int getSomeDate(int date,int num){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date newDate = null;
        try{
            newDate = sdf.parse(String.valueOf(date));
        }catch (Exception e){

        }
        Date beforeDate = DateUtils.getDateBaseOn(newDate, num, TimeUnit.DAYS);
        return DateUtils.getDate(beforeDate);
    }
}
