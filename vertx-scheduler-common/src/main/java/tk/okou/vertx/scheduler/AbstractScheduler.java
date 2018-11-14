package tk.okou.vertx.scheduler;

import io.vertx.core.Context;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;

public abstract class AbstractScheduler implements Scheduler {
    protected final EventBus eventBus;
    private final Context context;

    public AbstractScheduler(Vertx vertx) {
        this.eventBus = vertx.eventBus();
        this.context = vertx.getOrCreateContext();
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
    public void schedule(String pattern, String jobName, Handler<Void> handler) {
        schedule(pattern, jobName);
        if (handler != null) {
            eventBus.consumer(jobName, r -> {
                context.runOnContext(handler);
            });
        }
    }
}
