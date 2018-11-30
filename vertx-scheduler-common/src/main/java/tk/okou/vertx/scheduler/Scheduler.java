package tk.okou.vertx.scheduler;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Handler;
import tk.okou.vertx.scheduler.job.Job;

import java.util.UUID;

@VertxGen
public interface Scheduler {
    String HOURLY = "Scheduler:HourlyJob";
    String DAILY = "Scheduler:DailyJob";
    String WEEKLY = "Scheduler:WeeklyJob";

    void schedule(String pattern, String jobName);

    @GenIgnore
    default void schedule(Job job) {
        schedule(job.getPattern(), job.getJobName());
    }

    default void schedule(String pattern, Handler<Void> handler) {
        schedule(pattern, UUID.randomUUID().toString(), handler);
    }
    @GenIgnore
    default void schedule(Job job, Handler<Void> handler) {
        schedule(job.getPattern(), job.getJobName(), handler);
    }

    void schedule(String pattern, String jobName, Handler<Void> handler);

    void start() throws Exception;

    void stop() throws Exception;


//    /**
//     *
//     * 开启一个定时任务。
//     *
//     * 开启的定时任务不处理任何事情，但可以通过{@link SchedulerBinder#jobName(String)}来获取任务的binder，
//     * 并通过{@link SchedulerBinder#register(Handler)}来监听定时任务的触发，并处理事情。
//     *
//     * 每一次触发，只有一个监听者会被调用。
//     *
//     * @param pattern
//     */
//    default void schedule(String pattern) {
//        schedule(pattern, null);
//    }
//
//    /**
//     * 与{@link #schedule(String)}方法类似，但使用{@link SchedulerBinder#jobName(String)}监听时，可以同时触发多个任务
//     *
//     * @param pattern
//     * @param jobName
//     */
//    default void schedule(String pattern, String jobName) {
//        schedule(pattern, jobName, null);
//    }
//
//    /**
//     * 开启一个定时任务，当定时任务触发时，会执行传入的回调函数
//     *
//     * @param pattern
//     * @param handler
//     */
//    default void schedule(String pattern, Handler<Void> handler) {
//        schedule(pattern, false, handler);
//    }
//
//    /**
//     *
//     * @param pattern
//     * @param jobName
//     * @param handler
//     */
//    void schedule(String pattern, String jobName, Handler<Void> handler);
}
