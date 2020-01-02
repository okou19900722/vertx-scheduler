package tk.okou.vertx.scheduler;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Handler;
import tk.okou.vertx.scheduler.job.Job;

@VertxGen
public interface Scheduler {
    String HOURLY = "Scheduler:HourlyJob";
    String DAILY = "Scheduler:DailyJob";
    String WEEKLY = "Scheduler:WeeklyJob";

    default void schedule(String pattern, String jobName) throws InvalidPatternException, SchedulerException {
        this.schedule(pattern, jobName, true);
    }

    void schedule(String pattern, String jobName, boolean publish) throws InvalidPatternException, SchedulerException;

    @GenIgnore
    default void schedule(Job job) throws InvalidPatternException, SchedulerException {
        schedule(job.getPattern(), job.getJobName(), job.publish());
    }

    void schedule(String pattern, Handler<Void> handler) throws InvalidPatternException, SchedulerException;

    void start() throws Exception;

    void stop() throws Exception;
}
