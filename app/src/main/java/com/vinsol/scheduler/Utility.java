package com.vinsol.scheduler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utility {
    public static String dateFormatToString(Date date){
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        return  df.format(date);
    }

    public static Date stringToDate(String date) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        return  df.parse(date);
    }

    public static String addDateToString (String date, int daysToAdd){
        final Calendar c = Calendar.getInstance();
        try {
            c.setTime(stringToDate(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, daysToAdd);
        return dateFormatToString(c.getTime());
    }

    public static String todayDate(){
        Calendar c = Calendar.getInstance();
        Date date  = Calendar.getInstance().getTime();
        return dateFormatToString(date);
    }

    public static Date todayInDate(){
        return Calendar.getInstance().getTime();
    }

    public static boolean compareTiming(String startTime, String endTime) {
        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            Date date1 = sdf.parse(startTime);
            Date date2 = sdf.parse(endTime);

            if(date1.before(date2)) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e){
            e.printStackTrace();
        }
        return false;
    }
}
