package tk.okou.vertx.scheduler.job;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject
public class DefaultJob implements Job {
    private final String pattern;
    private final String jobName;
    private final boolean publish;

    public DefaultJob(String pattern, String jobName) {
        this(pattern, jobName, true);
    }

    public DefaultJob(JsonObject json) {
        this(json.getString("pattern"), json.getString("jobName"), json.getBoolean("publish", true));
    }

    public DefaultJob(String pattern, String jobName, boolean publish) {
        this.pattern = pattern;
        this.jobName = jobName;
        this.publish = publish;
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
