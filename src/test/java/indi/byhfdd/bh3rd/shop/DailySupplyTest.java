package indi.byhfdd.bh3rd.shop;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.junit.Test;

public class DailySupplyTest {

    @Test
    public void mapsMondayThroughSundayToTheSevenCards() {
        Calendar date = new GregorianCalendar(TimeZone.getTimeZone("Asia/Shanghai"));
        date.clear();
        date.set(2026, Calendar.OCTOBER, 19, 12, 0, 0);
        for (int index = 0; index < 7; index++) {
            assertEquals(index, DailySupply.getOfferIndex(date));
            date.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    @Test
    public void rollsSundayToMondayAtServerMidnight() {
        Calendar date = new GregorianCalendar(TimeZone.getTimeZone("Asia/Shanghai"));
        date.clear();
        date.set(2026, Calendar.OCTOBER, 25, 23, 59, 59);
        assertEquals(6, DailySupply.getOfferIndex(date));
        date.add(Calendar.SECOND, 1);
        assertEquals(0, DailySupply.getOfferIndex(date));
    }
}
