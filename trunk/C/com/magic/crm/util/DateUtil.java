package com.magic.crm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    public DateUtil() {
    }

    public static long getDateLength(Date startDate, Date endDate)
            throws Exception {
        long l = endDate.getTime() - startDate.getTime();
        return l / 60 / 60 / 1000 / 24;
    }

    public static Date addYear(Date theDate, int amount) {

        if (theDate == null) {
            return null;
        }
        Calendar cld = Calendar.getInstance();
        cld.setTime(theDate);
        cld.add(Calendar.YEAR, amount);
        return new Date((cld.getTime().getTime()));
    }

    public static Date addMonth(Date theDate, int amount) {

        if (theDate == null) {
            return null;
        }
        Calendar cld = Calendar.getInstance();
        cld.setTime(theDate);
        cld.add(Calendar.MONTH, amount);
        return new Date((cld.getTime().getTime()));
    }

    public static Date addDay(Date theDate, int amount) {

        if (theDate == null) {
            return null;
        }
        Calendar cld = Calendar.getInstance();
        cld.setTime(theDate);
        cld.add(Calendar.DATE, amount);
        return new Date((cld.getTime().getTime()));
    }

    public static Date getDate(String d, String format) {
        try {
            java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(
                    format);
            return df.parse(d);
        } catch (Exception e) {
            return null;
        }
    }

    public static java.sql.Date getSqlDate(java.util.Date d) {
        try {
            return new java.sql.Date(d.getTime());
        } catch (Exception e) {
            return null;
        }
    }

    public static java.sql.Date getSqlDate() {
        try {
            return new java.sql.Date((new java.util.Date()).getTime());
        } catch (Exception e) {
            return null;
        }
    }

    public static String date2String(Date date, String format,
            Locale currentLocale) {
        SimpleDateFormat formatter = new SimpleDateFormat(format, currentLocale);
        String strDate = formatter.format(date);
        return strDate;
    }

    public static String date2String(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String strDate = formatter.format(date);
        return strDate;
    }

    public static boolean isNumber(String str) {

        try {

            Double.parseDouble(str);

            return true;

        } catch (NumberFormatException nfe) {

            return false;

        }

    }

    public static boolean isInt(String str) {

        try {

            Integer.parseInt(str);

            return true;

        } catch (NumberFormatException nfe) {

            return false;

        }

    }

    public static boolean isDate(String str) {

        SimpleDateFormat df = new SimpleDateFormat();

        try {

            df.applyPattern("yyyy-MM-dd");

            df.parse(str);

            return true;

        } catch (ParseException pe1) {

            try {

                df.applyPattern("yyyy/MM/dd");

                df.parse(str);

                return true;

            } catch (ParseException pe2) {

                try {

                    df.applyPattern("MM/dd/yy");

                    df.parse(str);

                    return true;

                } catch (ParseException pe) {

                    return false;

                }

            }

        }

    }

    public static void main(String[] args) {

        if (isNumber("3")) {
            System.out.println("date success");
        } else {
            System.out.println("date failed");
        }

    }
}