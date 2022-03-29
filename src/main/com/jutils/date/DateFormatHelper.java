package com.jutils.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Date format helper.
 * <pre>
 * -------------------------------------------------------------------
 * |  Format                        |  Example                       |
 * |--------------------------------+--------------------------------|
 * |  yyyy-MM-dd'T'HH:mm:ss.SSSX    |  2001-07-04T19:08:56.235Z      |
 * |--------------------------------+--------------------------------|
 * |  EEE MMM dd HH:mm:ss zzz yyyy  |  Wed Jul 04 19:08:56 UTC 2001  |
 * -------------------------------------------------------------------
 * </pre>
 */
public class DateFormatHelper {
    private static final TimeZone TIME_ZONE_UTC = TimeZone.getTimeZone("UTC");
    private static final Map<String, ThreadLocal<DateFormat>> THREAD_LOCAL_MAP = new ConcurrentHashMap<>();

    public static String format(String format, Date date) {
        return getDateFormat(TIME_ZONE_UTC, format).format(date);
    }

    public static String format(TimeZone timeZone, String format, Date date) {
        return getDateFormat(timeZone, format).format(date);
    }

    public static Date parse(String format, String date) throws ParseException {
        return getDateFormat(TIME_ZONE_UTC, format).parse(date);
    }

    public static Date parse(TimeZone timeZone, String format, String date) throws ParseException {
        return getDateFormat(timeZone, format).parse(date);
    }

    private static DateFormat getDateFormat(TimeZone timeZone, String format) {
        String key = timeZone.getID() + "#" + format;
        ThreadLocal<DateFormat> threadLocal = THREAD_LOCAL_MAP.get(key);
        if (threadLocal == null) {
            threadLocal = new ThreadLocal<>();
            THREAD_LOCAL_MAP.put(key, threadLocal);
        }
        DateFormat dateFormat = threadLocal.get();
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat(format);
            dateFormat.setTimeZone(timeZone);
            threadLocal.set(dateFormat);
        }
        return dateFormat;
    }
}
