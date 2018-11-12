package tk.okou.vertx.scheduler;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Handler;
import tk.okou.vertx.scheduler.job.Job;

import java.util.UUID;

@VertxGen
public interface Scheduler {
    String HOURLY = "Scheduler:HourlyJob";
    String DAILY = "Scheduler:DailyJob";
    String WEEKLY = "Scheduler:WeeklyJob";

    void schedule(String pattern, String jobName);

    default void schedule(Job job) {
        schedule(job.getPattern(), job.getJobName());
    }

    default void schedule(String pattern, Handler<Void> handler) {
        schedule(pattern, UUID.randomUUID().toString(), handler);
    }

    default void schedule(Job job, Handler<Void> handler) {
        schedule(job.getPattern(), job.getJobName(), handler);
    }

    void schedule(String pattern, String jobName, Handler<Void> handler);

    void start();

}
