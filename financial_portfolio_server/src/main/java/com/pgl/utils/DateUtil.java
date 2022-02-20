package com.pgl.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    /**
     * function to convert date to Timestamp
     * @throws ParseException
     */
    public static String getFormatDate(Date date) throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = sdf.format(date);
        date = sdf.parse(d);
        return d;
    }

    /**
     * Convert the date 'd' to date without time
     * @return
     */
    public static String getYearOfDate( Date d) {
        if(d == null) return null;
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return "" + c.get(Calendar.YEAR);
    }

    /**
     * Retrieve the hour in a date
     * @return
     */
    public static Integer getHourOfDate( Date d) {
        if(d == null) return null;
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.HOUR);
    }
    /**
     * Retrieve the minute in a date
     * @return
     */
    public static Integer getMinuteOfDate( Date d) {
        if(d == null) return null;
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.MINUTE);
    }

    /**
     * Retrieve the second in a date
     * @return
     */
    public static Integer getSecondOfDate( Date d) {
        if(d == null) return null;
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.SECOND);
    }

    /**
     * Convert the date 'd' to date without time
     * @return
     */
    public static Date getDateWithoutTime( Date d) {
        if(d == null) return null;
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);
        return c.getTime();
    }

    /*
     * Return the date corresponding to
     * day as DAY_OF_MONTH
     * month [1-12]
     * Year
     */
    public static Date getDate( int day, int month, int year) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.YEAR, year);
        return c.getTime();
    }

    /**
     * Set the current Time to the date d
     * @param d
     * @return
     */
    public static Date getDateWithCurrentTime( Date d) {
        if(d == null) return null;
        Calendar curent = Calendar.getInstance();
        curent.setTime(new Date(System.currentTimeMillis()));
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.HOUR, curent.get(Calendar.HOUR));
        c.set(Calendar.MINUTE, curent.get(Calendar.MINUTE));
        c.set(Calendar.SECOND, curent.get(Calendar.SECOND));
        c.set(Calendar.MILLISECOND, 0);
        c.set(Calendar.HOUR_OF_DAY, curent.get(Calendar.HOUR_OF_DAY));
        return c.getTime();
    }

    /**
     * Add 'seconds' seconds to date d
     * @param d
     * @param seconds
     * @return
     */
    public static Date addSecondToDate(Date d, int seconds ) {
        if(d == null) return null;
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.SECOND, c.get(Calendar.SECOND) + seconds);
        return c.getTime();
    }

    /**
     * Add 'miliseconds' to date d
     * @param d
     * @param miliseconds
     * @return
     */
    public static Date addMilliSecondToDate(Date d, int miliseconds ) {
        if(d == null) return null;
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.MILLISECOND, c.get(Calendar.MILLISECOND) + miliseconds);
        return c.getTime();
    }

    /**
     * Compare two date without minute and
     * @return
     */
    public static Boolean sameDay( Date d1, Date d2) {
        if(d1 == null || d2 == null) return false;
        if((d1.getTime() - d2.getTime())/(24*60*60*1000) > 24)
            return false;
        return true;
    }

    public static long  getNumberOfDaysBetween(Date d1, Date d2) {
        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();
        date1.setTime(d1);
        date2.setTime(d2);
        LocalDate l1 = LocalDate.of(date1.get(Calendar.YEAR), date1.get(Calendar.MONTH) +1, date1.get(Calendar.DAY_OF_MONTH));
        LocalDate l2 = LocalDate.of(date2.get(Calendar.YEAR), date2.get(Calendar.MONTH) +1, date2.get(Calendar.DAY_OF_MONTH));
        return l1.until(l2, ChronoUnit.DAYS);
    }

    public static Date addYearToDate(Date d, int numberOfYear ) {
        if(d == null) return null;
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.YEAR, c.get(Calendar.YEAR) + numberOfYear);
        return c.getTime();
    }

    public static Date addMonthToDate(Date d, int numberOfMonth ) {
        if(d == null) return null;
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) + numberOfMonth);
        return c.getTime();
    }

    public static Date addDayToDate(Date d, int numberOfDay ) {
        if(d == null) return null;
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + numberOfDay);
        return c.getTime();
    }

}
