package cn.mauth.account.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用于处理日期转换
 */
public final class DateUtil {
    public static String formatDate(Date date, String format){
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }
}
