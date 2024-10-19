package me.hieu.core.util;

import java.text.DateFormat;
import java.util.Date;

/**
 * Author: Le Thanh Hieu
 * Date: 06/10/2024
 */

public class DateUtil {

    public static String format(long time){
        return DateFormat.getDateTimeInstance().format(new Date(time));
    }

}
