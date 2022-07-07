package Utils.HelperMethods;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateHelper {
    private static long currentTimeInMillis = generateCurrentDateInMillis();
    public static String convertDateTimeToJSONFormat(Date date) {
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        sdf.setTimeZone(TimeZone.getTimeZone("CET"));
        String text = sdf.format(date);

        return text;
    }

    public static Date generateCurrentDate() {
        updateCurrentDateInMillis();
        return new Date(System.currentTimeMillis());
    }

    public static Date generateOneDayAfterCurrentDate() {
        updateCurrentDateInMillis();
        // +100.000.000 is just a random number indicating that this is the future datetime
        // This random number must be larger than 86.400.000, which is 1 day equally
        return new Date(currentTimeInMillis + (long) Math.pow(10, 8));
    }

    public static Date generateFutureDate() {
        updateCurrentDateInMillis();
        // The date interval must be larger than 100.000.000 to match the problem requirements
        return new Date(currentTimeInMillis + (long) Math.pow(10, 9));
    }

    public static Date generatePassDate() {
        updateCurrentDateInMillis();
        // -10000 is just a random number indicating that this is the past datetime
        return new Date(currentTimeInMillis - (long) Math.pow(10, 8));
    }

    private static long generateCurrentDateInMillis() {
        return System.currentTimeMillis();
    }



    private static void updateCurrentDateInMillis() {
        currentTimeInMillis = generateCurrentDateInMillis();
    }




}

