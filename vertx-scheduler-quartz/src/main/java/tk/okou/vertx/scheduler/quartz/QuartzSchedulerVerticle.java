package tk.okou.vertx.scheduler.quartz;

import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;
import tk.okou.vertx.scheduler.SchedulerVerticle;
import tk.okou.vertx.scheduler.job.Job;

import java.text.ParseException;

public class QuartzSchedulerVerticle extends SchedulerVerticle {

    private Scheduler scheduler;

    @Override
    public void init(Vertx vertx, Context context) {
        super.init(vertx, context);
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void startScheduler() throws SchedulerException {
        this.scheduler.start();
    }

    @Override
    protected Job createJob(JsonObject job) {
        String type = job.getString("type");
        if (type != null) {
            return Cycle.valueOf(job.getString("value"));
        } else {
            return super.createJob(job);
        }
    }

    @Override
    protected void addJob(Job job) throws ParseException, SchedulerException {
        JobDetailImpl jobDetail = new JobDetailImpl();
        jobDetail.setJobClass(VertxJob.class);
        jobDetail.setKey(new JobKey(job.getJobName()));
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("eventBus", vertx.eventBus());
        jobDataMap.put("publish", true);
        jobDetail.setJobDataMap(jobDataMap);
        CronTriggerImpl trigger = new CronTriggerImpl();
        trigger.setName(job.getJobName());
        trigger.setCronExpression(job.getPattern());
        this.scheduler.scheduleJob(jobDetail, trigger);
    }
}