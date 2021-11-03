package test.jutils.date;

import com.jutils.date.DateFormatHelper;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDateFormatHelper {
    private static final TimeZone TIME_ZONE_CN = TimeZone.getTimeZone("Asia/Shanghai");
    private static final TimeZone TIME_ZONE_US_PACIFIC = TimeZone.getTimeZone("US/Pacific");
    private static final TimeZone TIME_ZONE_US_MOUNTAIN = TimeZone.getTimeZone("US/Mountain");
    private static final TimeZone TIME_ZONE_US_CENTRAL = TimeZone.getTimeZone("US/Central");
    private static final TimeZone TIME_ZONE_US_EASTERN = TimeZone.getTimeZone("US/Eastern");

    @Test
    public void testFormat() {
        assertEquals("2000-01-02T03:04:05Z",
                DateFormatHelper.format("yyyy-MM-dd'T'HH:mm:ssX", new Date(946782245000L)));
        assertEquals("2000-01-02 11:04:05 CST",
                DateFormatHelper.format(TIME_ZONE_CN, "yyyy-MM-dd HH:mm:ss z", new Date(946782245000L)));
        assertEquals("2000-01-01 19:04:05 PST",
                DateFormatHelper.format(TIME_ZONE_US_PACIFIC, "yyyy-MM-dd HH:mm:ss z", new Date(946782245000L)));
        assertEquals("2000-01-01 20:04:05 MST",
                DateFormatHelper.format(TIME_ZONE_US_MOUNTAIN, "yyyy-MM-dd HH:mm:ss z", new Date(946782245000L)));
        assertEquals("2000-01-01 21:04:05 CST",
                DateFormatHelper.format(TIME_ZONE_US_CENTRAL, "yyyy-MM-dd HH:mm:ss z", new Date(946782245000L)));
        assertEquals("2000-01-01 22:04:05 EST",
                DateFormatHelper.format(TIME_ZONE_US_EASTERN, "yyyy-MM-dd HH:mm:ss z", new Date(946782245000L)));
    }

    @Test
    public void testParse() throws ParseException {
        assertEquals(new Date(946782245000L),
                DateFormatHelper.parse("yyyy-MM-dd'T'HH:mm:ssX", "2000-01-02T03:04:05Z"));
        assertEquals(new Date(946782245000L),
                DateFormatHelper.parse(TIME_ZONE_CN, "yyyy-MM-dd HH:mm:ss z", "2000-01-02 11:04:05 CST"));
        assertEquals(new Date(946782245000L),
                DateFormatHelper.parse(TIME_ZONE_US_PACIFIC, "yyyy-MM-dd HH:mm:ss z", "2000-01-01 19:04:05 PST"));
        assertEquals(new Date(946782245000L),
                DateFormatHelper.parse(TIME_ZONE_US_MOUNTAIN, "yyyy-MM-dd HH:mm:ss z", "2000-01-01 20:04:05 MST"));
        assertEquals(new Date(946782245000L),
                DateFormatHelper.parse(TIME_ZONE_US_CENTRAL, "yyyy-MM-dd HH:mm:ss z", "2000-01-01 21:04:05 CST"));
        assertEquals(new Date(946782245000L),
                DateFormatHelper.parse(TIME_ZONE_US_EASTERN, "yyyy-MM-dd HH:mm:ss z", "2000-01-01 22:04:05 EST"));
    }
}
