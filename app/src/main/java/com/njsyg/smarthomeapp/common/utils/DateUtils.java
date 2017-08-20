package com.njsyg.smarthomeapp.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 2016/7/19.
 */
public class DateUtils {
    public static String GetNowDate(){
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }
    public static String GetNowDateTime(){
        SimpleDateFormat sDateFormat = new SimpleDateFormat("HH:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }

    public static String ConvertDate(Date date)
    {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sDateFormat.format(date);
        return dateStr;
    }
    public static String ConvertShortTime(Date date)
    {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("HH:mm:ss");
        String dateStr = sDateFormat.format(date);
        return dateStr;
    }
    /// <summary>
    ///String转成Date  yyyy-MM-dd HH:mm:ss格式
    /// </summary>
    public static Date ConverStringToDate(String dateString)
    {
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.parse(dateString);
        }
        catch (ParseException e)
        {
            return null;
        }
    }
}
