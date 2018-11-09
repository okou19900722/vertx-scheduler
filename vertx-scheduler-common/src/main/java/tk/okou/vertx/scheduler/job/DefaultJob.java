package tk.okou.vertx.scheduler.job;

public class DefaultJob implements Job{
    private final String pattern;
    private final  String jobName;
    public DefaultJob(String pattern, String jobName){
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
