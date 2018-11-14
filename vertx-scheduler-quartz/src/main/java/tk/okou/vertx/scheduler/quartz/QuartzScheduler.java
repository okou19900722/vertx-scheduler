package tk.okou.vertx.scheduler.quartz;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Vertx;
import org.quartz.SchedulerException;
import tk.okou.vertx.scheduler.Scheduler;
import tk.okou.vertx.scheduler.quartz.impl.QuartzSchedulerImpl;

@VertxGen
public interface QuartzScheduler extends Scheduler {
    static QuartzScheduler create(Vertx vertx) throws SchedulerException {
        return new QuartzSchedulerImpl(vertx);
    }
}
