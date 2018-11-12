package tk.okou.vertx.scheduler.quartz;

import tk.okou.vertx.scheduler.Scheduler;
import tk.okou.vertx.scheduler.job.Job;

public enum Cycle implements Job {
    HOURLY("0 0 * * * *", Scheduler.HOURLY),
    DAILY("0 0 0 * * *", Scheduler.DAILY),
    WEEKLY("0 0 0 * * Mon", Scheduler.WEEKLY);

    public final String pattern, jobName;

    Cycle(String pattern, String jobName) {
        this.pattern = pattern;
        this.jobName = jobName;
    }

    @Override
    public String getPattern() {
        return pattern;
    }

    @Override
    public String getJobName() {
        return jobName;
    }
}
