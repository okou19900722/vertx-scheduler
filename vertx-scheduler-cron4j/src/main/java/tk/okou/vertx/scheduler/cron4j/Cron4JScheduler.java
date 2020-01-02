package tk.okou.vertx.scheduler.cron4j;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Vertx;
import tk.okou.vertx.scheduler.InvalidPatternException;
import tk.okou.vertx.scheduler.SchedulerException;
import tk.okou.vertx.scheduler.cron4j.impl.Cron4JSchedulerImpl;
import tk.okou.vertx.scheduler.job.Job;


@VertxGen
public interface Cron4JScheduler extends tk.okou.vertx.scheduler.Scheduler {
    static Cron4JScheduler create(Vertx vertx) {
        return new Cron4JSchedulerImpl(vertx);
    }

    default void schedule(Cycle job) throws SchedulerException, InvalidPatternException {
        schedule((Job) job);
    }
}
