package tk.okou.vertx.scheduler.quartz;

import tk.okou.vertx.scheduler.job.Job;

public enum Cycle implements Job {
    HOURLY("0 0 * * * *", Job.HOURLY),
    DAILY("0 0 0 * * *", Job.DAILY),
    WEEKLY("0 0 0 * * Mon", Job.WEEKLY);

    public final String pattern, jobName;

    Cycle(String pattern, String jobName) {
        this.pattern = pattern;
        this.jobName = jobName;
    }

    @Override
    public String pattern() {
        return pattern;
    }

    @Override
    public String jobName() {
        return jobName;
    }
}
