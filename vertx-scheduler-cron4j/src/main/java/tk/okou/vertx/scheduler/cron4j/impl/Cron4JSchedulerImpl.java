package tk.okou.vertx.scheduler.cron4j.impl;

import io.vertx.core.Vertx;
import it.sauronsoftware.cron4j.Scheduler;
import tk.okou.vertx.scheduler.AbstractScheduler;
import tk.okou.vertx.scheduler.cron4j.Cron4JScheduler;

public class Cron4JSchedulerImpl extends AbstractScheduler implements Cron4JScheduler {

    private final Scheduler scheduler;

    public Cron4JSchedulerImpl(Vertx vertx) {
        super(vertx);
        this.scheduler = new Scheduler();
    }

    @Override
    public void schedule(String pattern, String jobName) {
        addJob(pattern, jobName);
    }

    @Override
    public void start() {
        this.scheduler.start();
    }

    @Override
    public void stop() {
        this.scheduler.stop();
    }

    private void addJob(String pattern, String jobName) {
        this.addJob(pattern, jobName, true);
    }

    private void addJob(String pattern, String jobName, boolean publish) {
        scheduler.schedule(pattern, () -> {
            if (publish) {
                eventBus.publish(jobName, null);
            } else {
                eventBus.send(jobName, null);
            }
        });
    }
}
