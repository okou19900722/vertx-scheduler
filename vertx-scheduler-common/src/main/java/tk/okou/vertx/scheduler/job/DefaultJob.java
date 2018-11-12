package tk.okou.vertx.scheduler.job;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject
public class DefaultJob implements Job{
    private final String pattern;
    private final  String jobName;
    public DefaultJob(String pattern, String jobName){
        this.pattern = pattern;
        this.jobName = jobName;
    }
    public DefaultJob(JsonObject json) {
        this.pattern = json.getString("pattern");
        this.jobName = json.getString("jobName");
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
