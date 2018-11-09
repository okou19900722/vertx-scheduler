package tk.okou.vertx.scheduler;

import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Handler;

@VertxGen
public interface Scheduler {
    void schedule(String pattern, Handler<Void> handler);
}
