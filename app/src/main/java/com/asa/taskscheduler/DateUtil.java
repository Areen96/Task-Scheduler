package com.asa.taskscheduler;

import java.util.Date;

/**
 * Created by Phant on 1/6/2018.
 */

public class DateUtil {
    public static long getDiff(Date date1, Date date2){
        return  Math.abs(date2.getTime() - date1.getTime());
    }
}
