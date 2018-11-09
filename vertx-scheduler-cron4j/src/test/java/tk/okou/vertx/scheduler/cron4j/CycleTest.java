package tk.okou.vertx.scheduler.cron4j;

import it.sauronsoftware.cron4j.SchedulingPattern;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CycleTest {
    @Test
    public void test2(){
        SchedulingPattern dailyPattern = new SchedulingPattern(Cycle.DAILY.pattern);
        long millis = System.currentTimeMillis();
        int count = 3;
        TimeZone timeZone = TimeZone.getDefault();
        while(count > 0) {
            LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), timeZone.toZoneId());
            boolean match = dailyPattern.match(timeZone, millis);
            if (time.getHour() == 0 && time.getMinute() == 0) {
                count--;
                assertTrue(match);
            } else {
                assertFalse(match);
            }
            millis = ((millis / 60000) + 1) * 60000;
        }
    }
}
