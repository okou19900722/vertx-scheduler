package tk.okou.vertx.scheduler.cron4j;

import io.vertx.core.Vertx;
import org.junit.Before;
import org.junit.Test;
import tk.okou.vertx.scheduler.Scheduler;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;

public class SchedulerTest {
    private Vertx vertx;

    @Before
    public void before() {
        vertx = Vertx.vertx();
    }

    @Test
    public void testScheduler() throws InterruptedException {
        Scheduler scheduler = Cron4JScheduler.create(vertx);
        CountDownLatch count = new CountDownLatch(1);
        scheduler.schedule("* * * * *", r -> {
            LocalDateTime triggerTime = LocalDateTime.now();
            assertEquals(0, triggerTime.getSecond());
            count.countDown();
        });
        scheduler.start();
        count.await();
    }
}
