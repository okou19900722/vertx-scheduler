package tk.okou.vertx.scheduler.cron4j;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import it.sauronsoftware.cron4j.SchedulingPattern;
import org.junit.Before;
import org.junit.Test;
import tk.okou.vertx.scheduler.SchedulerBinder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Cron4JSchedulerVerticleTest {
    private Vertx vertx;

    @Before
    public void init() {
        vertx = Vertx.vertx();
    }

    @Test
    public void test() throws InterruptedException {
        DeploymentOptions options = new DeploymentOptions();
        String jobName = "Scheduler:MinutelyJob";
        JsonObject config = new JsonObject();
        JsonArray jobs = new JsonArray();
        JsonObject job = new JsonObject();
        job.put("pattern", "* * * * *");
        job.put("jobName", jobName);
        jobs.add(job);
        config.put("jobs", jobs);
        options.setConfig(config);
        CountDownLatch count = new CountDownLatch(1);
        new SchedulerBinder(vertx).jobName(jobName).register(m -> {
            LocalDateTime triggerTime = LocalDateTime.now();
            assertEquals(0, triggerTime.getSecond());
            count.countDown();
        });
        vertx.deployVerticle(Cron4JSchedulerVerticle.class.getName(), options);
        count.await();
    }
}
