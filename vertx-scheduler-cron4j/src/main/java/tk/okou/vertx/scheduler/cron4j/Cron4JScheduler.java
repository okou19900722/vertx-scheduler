package tk.okou.vertx.scheduler.cron4j;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Vertx;
import tk.okou.vertx.scheduler.cron4j.impl.Cron4JSchedulerImpl;

@VertxGen
public interface Cron4JScheduler extends tk.okou.vertx.scheduler.Scheduler {
    static Cron4JScheduler create(Vertx vertx){
        return new Cron4JSchedulerImpl(vertx);
    }
}
