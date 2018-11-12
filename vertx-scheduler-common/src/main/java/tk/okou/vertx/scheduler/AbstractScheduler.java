package tk.okou.vertx.scheduler;

import io.vertx.core.Context;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;

public abstract class AbstractScheduler implements Scheduler {
    protected final EventBus eventBus;
    protected final Context context;

    public AbstractScheduler(Vertx vertx) {
        this.eventBus = vertx.eventBus();
        this.context = vertx.getOrCreateContext();
    }

    @Override
    public void schedule(String pattern, String jobName, Handler<Void> handler) {
        schedule(pattern, jobName);
        eventBus.consumer(jobName, r -> {
            context.runOnContext(handler);
        });
    }
}
