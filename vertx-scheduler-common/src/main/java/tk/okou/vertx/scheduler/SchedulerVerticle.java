package tk.okou.vertx.scheduler;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import tk.okou.vertx.scheduler.job.DefaultJob;
import tk.okou.vertx.scheduler.job.Job;

public abstract class SchedulerVerticle extends AbstractVerticle {
    @Override
    public void start(Future<Void> startFuture) {
        JsonObject config = config();
        JsonArray jobs = config.getJsonArray("jobs");
        try {
            for (Object obj : jobs) {
                Job job = createJob((JsonObject) obj);
                addJob(job);
            }
            startScheduler();
            startFuture.complete();
        } catch (Throwable t) {
            startFuture.fail(t);
        }
    }

    protected Job createJob(JsonObject job) {
        return new DefaultJob(job);
    }

    protected abstract void addJob(Job job) throws Exception;
    protected abstract void startScheduler() throws Exception;
}
