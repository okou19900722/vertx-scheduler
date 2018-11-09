package tk.okou.vertx.scheduler.quartz;

import io.vertx.core.Vertx;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

public class VertxJob implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        Vertx vertx = (Vertx) context.getMergedJobDataMap().get("vertx");
        String jobName = context.getJobDetail().getKey().getName();
        vertx.eventBus().publish(jobName, null);
    }
}
