package tk.okou.vertx.scheduler.quartz;

import io.vertx.core.eventbus.EventBus;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

public class VertxJob implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        EventBus eventBus = (EventBus) context.getMergedJobDataMap().get("eventBus");
        boolean publish = (Boolean) context.getMergedJobDataMap().get("publish");
        String jobName = context.getJobDetail().getKey().getName();
        eventBus.publish(jobName, null);
    }
}
