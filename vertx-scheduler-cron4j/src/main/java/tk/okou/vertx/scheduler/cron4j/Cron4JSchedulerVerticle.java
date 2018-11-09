package tk.okou.vertx.scheduler.cron4j;

import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import it.sauronsoftware.cron4j.Scheduler;
import tk.okou.vertx.scheduler.SchedulerBinder;
import tk.okou.vertx.scheduler.SchedulerVerticle;
import tk.okou.vertx.scheduler.job.Job;

/**
 * 目前支持两种配置
 * 第一种
 * {
 * "type" : "Cycle",
 * "value" : "DAILY"
 * }
 * type固定为Cycle，value为枚举 [Cycle]里的值
 * 第二种
 * {
 * "pattern" : "0 0 * * Mon",
 * "jobName" : "Scheduler:WeeklyJob"
 * }
 * pattern为任务执行的pattern，jobName为任务触发时，监听的键
 * <p>
 * {@link SchedulerBinder}构造方法创建Binder对象
 * 然后通过下面两种方式可以配置binder
 * {@link SchedulerBinder#jobName(String)}
 * 并通过{@link SchedulerBinder#register(Handler)}监听相应的任务
 * <p>
 * 请注意，cron 表达式最多只允许五部分，每部分用空格分隔开来，这五部分从左到右依次表示分、时、天、月、周，其具体规则如下：
 * 分 ：从 0 到 59
 * 时 ：从 0 到 23
 * 天 ：从 1 到 31，字母 L 可以表示月的最后一天
 * 月 ：从 1 到 12，可以别名：jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov" and "dec"
 * 周 ：从 0 到 6，0 表示周日，6 表示周六，可以使用别名： "sun", "mon", "tue", "wed", "thu", "fri" and "sat"
 * <p>
 * 如上五部分的分、时、天、月、周又分别支持如下字符，其用法如下：
 * 数字 n：表示一个具体的时间点，例如 `5 * * * *` 表示 5 分这个时间点时执行
 * 逗号 , ：表示指定多个数值，例如 `3,5 * * * *` 表示 3 和 5 分这两个时间点执行
 * 减号 -：表示范围，例如 1-3 `* * * *` 表示 1 分、2 分再到 3 分这三个时间点执行
 * 星号 *：表示每一个时间点，例如 `* * * * *` 表示每分钟执行
 * 除号 /：表示指定一个值的增加幅度。例如 `*／5`表示每隔5分钟执行一次（序列：0:00, 0:05, 0:10, 0:15 等等）。再例如`3-18/5 * * * *` 是指在从3到18分钟值这个范围之中每隔5分钟执行一次（序列：0:03, 0:08, 0:13, 0:18, 1:03, 1:08 等等）。
 * <p>
 * 常见错误：cron4j在表达式中使用除号指定增加幅度时与linux稍有不同。例如在linux中表达式 `10/3 * * * *` 的含义是从第10分钟开始，每隔三分钟调度一次，而在cron4j中需要使用 `10-59/3 * * * *` 来表达。避免这个常见错误的技巧是：当需要使用除号指定增加幅度时，始终指定其范围。
 * <p>
 * 基于上面的技巧，每隔2分钟调度一次的表达式为：`0-59/2 * * * *` 或者 `*／2 * * * *` ， 而不能是`0/2 * * * *`
 * <p>
 * 下边举几个例子：
 * 如果想每分钟执行一次，那么表达式就是这样
 * `* * * * *`
 * `*／1 * * * *`
 * <p>
 * 每个小时执行一次
 * `1 *／1 * * *`
 * <p>
 * 每天23:59执行一次
 * `59 23 * * *`
 * <p>
 * 每天凌晨10分执行一次
 * `10 0 * * *`
 * <p>
 * 每月最后一天的23:59执行
 * `29 23 L * *`
 * <p>
 * 每天的9点到晚6点，每个小时执行一次
 * `1 9-18/1 * * *`
 * <p>
 * 每天的早晨9点和晚上6点各执行一次
 * `1 9,18 * * *`
 * <p>
 * 每天的早晨9点到晚上6点以后，每半个小时执行一次
 * `*／30 9-18 * * *`
 * <p>
 * 每天的晚上10点到0点，每5分钟执行一次
 * `*／5 22-0 * * *`
 * <p>
 * 每天的10点、12点、14点、16点、18点、20点、22点和0点各执行一次
 * `1 10-0/2 * * *`
 * `1 10,12,14,16,18,20,22,0 * * *`
 */
public class Cron4JSchedulerVerticle extends SchedulerVerticle {
    private Scheduler scheduler;

    @Override
    public void init(Vertx vertx, Context context) {
        super.init(vertx, context);
        scheduler = new Scheduler();
    }

    @Override
    protected void startScheduler() {
        this.scheduler.start();
    }

    @Override
    protected void addJob(Job job) {
        scheduler.schedule(job.pattern(), () -> vertx.eventBus().publish(job.jobName(), null));
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
    public void stop(Future<Void> stopFuture) {
        try {
            this.scheduler.stop();
            stopFuture.complete();
        } catch (Throwable t) {
            stopFuture.fail(t);
        }
    }
}
