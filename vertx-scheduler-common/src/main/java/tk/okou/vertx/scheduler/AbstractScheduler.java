package tk.okou.vertx.scheduler;

import io.vertx.core.Context;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;

public abstract class AbstractScheduler implements Scheduler {
    private Vertx vertx;
    private final EventBus eventBus;

    public AbstractScheduler(Vertx vertx) {
        this.vertx = vertx;
        this.eventBus = vertx.eventBus();
        Context context = vertx.getOrCreateContext();
        context.addCloseHook(r -> {
            try {
                this.stop();
                r.handle(Future.succeededFuture());
            } catch (Throwable e) {
                r.handle(Future.failedFuture(e));
            }

        });
    }

    @Override
    public void schedule(String pattern, String jobName, boolean publish) throws InvalidPatternException, SchedulerException {
        schedule(pattern, () -> {
            if (publish) {
                this.eventBus.publish(jobName, null);
            } else {
                this.eventBus.send(jobName, null);
            }
        });
    }

    @Override
    public void schedule(String pattern, Handler<Void> handler) throws InvalidPatternException, SchedulerException {
        Context context = vertx.getOrCreateContext();
        schedule(pattern, () -> context.runOnContext(handler));
    }

    protected abstract void schedule(String pattern, Runnable runnable) throws InvalidPatternException, SchedulerException;
}
