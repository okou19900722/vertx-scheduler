package tk.okou.vertx.scheduler.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

public class RunnabeJob implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        Runnable runnable = (Runnable) context.getMergedJobDataMap().get("runnable");
        runnable.run();
    }
}