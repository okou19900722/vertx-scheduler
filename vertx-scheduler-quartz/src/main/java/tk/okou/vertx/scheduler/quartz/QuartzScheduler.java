package tk.okou.vertx.scheduler.quartz;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Vertx;
import tk.okou.vertx.scheduler.InvalidPatternException;
import tk.okou.vertx.scheduler.Scheduler;
import tk.okou.vertx.scheduler.SchedulerException;
import tk.okou.vertx.scheduler.job.Job;
import tk.okou.vertx.scheduler.quartz.impl.QuartzSchedulerImpl;

import java.text.ParseException;

@VertxGen
public interface QuartzScheduler extends Scheduler {
    static QuartzScheduler create(Vertx vertx) throws SchedulerException {
        return new QuartzSchedulerImpl(vertx);
    }

    default void schedule(Cycle job) throws InvalidPatternException, SchedulerException {
        schedule((Job) job);
    }

}
