package com.base.quartz;

import org.quartz.JobExecutionContext;

public interface ITask {
	public String execute(JobExecutionContext context);
}
