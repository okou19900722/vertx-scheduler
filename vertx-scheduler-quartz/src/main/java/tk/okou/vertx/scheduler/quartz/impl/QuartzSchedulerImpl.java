package tk.okou.vertx.scheduler.quartz.impl;

import io.vertx.core.Vertx;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;
import tk.okou.vertx.scheduler.AbstractScheduler;
import tk.okou.vertx.scheduler.InvalidPatternException;
import tk.okou.vertx.scheduler.SchedulerException;
import tk.okou.vertx.scheduler.quartz.QuartzScheduler;
import tk.okou.vertx.scheduler.quartz.RunnabeJob;

import java.text.ParseException;

public class QuartzSchedulerImpl extends AbstractScheduler implements QuartzScheduler {
    private final Scheduler scheduler;

    public QuartzSchedulerImpl(Vertx vertx) throws SchedulerException {
        super(vertx);
        try {
            this.scheduler = StdSchedulerFactory.getDefaultScheduler();
        } catch (Exception e) {
            throw new SchedulerException(e);
        }
    }

    @Override
    public void schedule(String pattern, Runnable runnable) throws InvalidPatternException, SchedulerException {
        JobDetailImpl jobDetail = new JobDetailImpl();
        jobDetail.setJobClass(RunnabeJob.class);
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("callback", runnable);
        jobDetail.setJobDataMap(jobDataMap);
        CronTriggerImpl trigger = new CronTriggerImpl();
        try {
            trigger.setCronExpression(pattern);
            this.scheduler.scheduleJob(jobDetail, trigger);
        } catch (ParseException e) {
            throw new InvalidPatternException(e);
        } catch (org.quartz.SchedulerException e) {
            throw new SchedulerException(e);
        }
    }
    @Override
    public void start() throws SchedulerException {
        try {
            this.scheduler.start();
        } catch (Exception e) {
            throw new SchedulerException(e);
        }
    }

    @Override
    public void stop() throws SchedulerException {
        try {
            this.scheduler.shutdown();
        } catch (Exception e) {
            throw new SchedulerException(e);
        }
    }

}
