package com.base.quartz;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
public class Task implements  StatefulJob{
		@Override
		public void execute(JobExecutionContext context) throws JobExecutionException {
			
			// 取得任务的上下文信息
			JobDataMap map = context.getJobDetail().getJobDataMap();
			try{
				ITask t=(ITask)map.get("task");
				String content=t.execute(context);
				context.put("content", content);
			}catch(Exception ex){
				context.put("exception", ex.getMessage());
			}
		}
}

