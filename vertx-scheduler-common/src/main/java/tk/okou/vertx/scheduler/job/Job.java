package tk.okou.vertx.scheduler.job;

public interface Job {
    String HOURLY = "Scheduler:HourlyJob";
    String DAILY = "Scheduler:DailyJob";
    String WEEKLY = "Scheduler:WeeklyJob";
    String pattern();
    String jobName();
}
