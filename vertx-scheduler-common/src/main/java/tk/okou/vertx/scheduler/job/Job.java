package tk.okou.vertx.scheduler.job;

import io.vertx.codegen.annotations.DataObject;

@DataObject
public interface Job {
    String getPattern();
    String getJobName();
}
