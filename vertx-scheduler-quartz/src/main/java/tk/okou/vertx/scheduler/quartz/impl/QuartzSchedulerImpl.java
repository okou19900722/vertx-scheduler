package tk.okou.vertx.scheduler.quartz.impl;

import io.vertx.core.Vertx;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;
import tk.okou.vertx.scheduler.AbstractScheduler;
import tk.okou.vertx.scheduler.quartz.QuartzScheduler;
import tk.okou.vertx.scheduler.quartz.VertxJob;

import java.text.ParseException;

public class QuartzSchedulerImpl extends AbstractScheduler implements QuartzScheduler {
    private final Scheduler scheduler;

    public QuartzSchedulerImpl(Vertx vertx) throws SchedulerException {
        super(vertx);
        this.scheduler = StdSchedulerFactory.getDefaultScheduler();
    }

    @Override
    public void schedule(String pattern, String jobName) {

    }

    @Override
    public void start() throws SchedulerException {
        this.scheduler.start();
    }

    @Override
    public void stop() throws SchedulerException {
        this.scheduler.shutdown();
    }

    private void addJob(String pattern, String jobName, boolean publish) throws ParseException, SchedulerException {
        JobDetailImpl jobDetail = new JobDetailImpl();
        jobDetail.setJobClass(VertxJob.class);
        jobDetail.setKey(new JobKey(jobName));
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("eventBus", eventBus);
        jobDataMap.put("publish", publish);
        jobDetail.setJobDataMap(jobDataMap);
        CronTriggerImpl trigger = new CronTriggerImpl();
        trigger.setName(jobName);
        trigger.setCronExpression(pattern);
        this.scheduler.scheduleJob(jobDetail, trigger);
    }
}
