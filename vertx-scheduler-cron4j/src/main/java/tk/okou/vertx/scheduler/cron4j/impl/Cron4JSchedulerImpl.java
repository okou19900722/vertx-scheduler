package tk.okou.vertx.scheduler.cron4j.impl;

import io.vertx.core.Vertx;
import it.sauronsoftware.cron4j.Scheduler;
import tk.okou.vertx.scheduler.AbstractScheduler;
import tk.okou.vertx.scheduler.InvalidPatternException;
import tk.okou.vertx.scheduler.SchedulerException;
import tk.okou.vertx.scheduler.cron4j.Cron4JScheduler;

public class Cron4JSchedulerImpl extends AbstractScheduler implements Cron4JScheduler {

    private final Scheduler scheduler;

    public Cron4JSchedulerImpl(Vertx vertx) {
        super(vertx);
        this.scheduler = new Scheduler();
    }

    @Override
    public void schedule(String pattern, Runnable runnable) throws InvalidPatternException, SchedulerException {
        try {
            scheduler.schedule(pattern, runnable);
        } catch (it.sauronsoftware.cron4j.InvalidPatternException e) {
            throw new InvalidPatternException(e);
        } catch (Throwable e) {
            throw new SchedulerException(e);
        }
    }

    @Override
    public void start() {
        this.scheduler.start();
    }

    @Override
    public void stop() {
        this.scheduler.stop();
    }

}
