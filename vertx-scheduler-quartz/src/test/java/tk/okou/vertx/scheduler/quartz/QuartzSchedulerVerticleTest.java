package tk.okou.vertx.scheduler.quartz;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.junit.Before;
import org.junit.Test;
import tk.okou.vertx.scheduler.SchedulerBinder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.CountDownLatch;

public class QuartzSchedulerVerticleTest {
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
        job.put("pattern", "* * * * * ?");
        job.put("jobName", jobName);
        jobs.add(job);
        config.put("jobs", jobs);
        options.setConfig(config);
        CountDownLatch count = new CountDownLatch(1);
        long start = System.currentTimeMillis();
        Promise<Void> promise = Promise.promise();
        new SchedulerBinder(vertx).jobName(jobName).register(m -> {
            long end = System.currentTimeMillis();
            try {
                assertTrue((end - start) < 1000);
                promise.complete();
            } catch (Throwable t) {
                promise.fail(t);
            } finally {
                count.countDown();
            }
        });
        vertx.deployVerticle(QuartzSchedulerVerticle.class.getName(), options);
        count.await();
        promise.future().setHandler(it -> {
            if (it.failed()) {
                throw new RuntimeException(it.cause());
            }
        });
    }
}
