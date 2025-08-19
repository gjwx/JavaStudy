package com.ximalaya.ssd;

import com.ximalaya.ssd.util.DateCommon;

/**
 * 〈测试工具运行入口〉
 *
 * @author gongjiawei
 * @date 2025/8/11 下午3:35
 */
public class maintest {
    public static void main(String[] args) {
        String t="30";
        int oldDateBegin = 0;
        int oldDateEnd = 0;
        String appKey="30";
        String  dataScope=  "30";
        Integer dimension=1;
        String orderBy="playNum";

        int dateBegin=DateCommon.getDateBeginAndEnd(t)[0];
        int dateEnd=DateCommon.getDateBeginAndEnd(t)[1];
        System.out.println(dateBegin+" "+dateEnd);
        oldDateBegin = DateCommon.getSomeDate(dateBegin, -1);
        oldDateEnd = DateCommon.getSomeDate(dateEnd, -1);

        System.out.println(oldDateBegin+" "+oldDateEnd);
        System.out.println();

    }
}
