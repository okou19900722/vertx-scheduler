package tk.okou.vertx.scheduler;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import tk.okou.vertx.scheduler.job.Job;

public class SchedulerBinder {
    private final Vertx vertx;
    private String jobName;

    public SchedulerBinder(Vertx vertx) {
        this.vertx = vertx;
    }

    public SchedulerBinder jobName(String jobName) {
        this.jobName = jobName;
        return this;
    }
    public SchedulerBinder job(Job jobOption) {
        this.jobName = jobOption.getJobName();
        return this;
    }

    public void register(Handler<Message<Void>> task) {
        if (jobName == null) {
            throw new RuntimeException("jobName required!");
        }
        vertx.eventBus().consumer(jobName, task);
    }
}
