package com.etc.quartz;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.base.quartz.ITask;

@Service("start1")
public class QuartzStart1 implements ITask {
	@Autowired
	private SchedulerFactoryBean startQuartz;
	
	@Override
	public String execute(JobExecutionContext context) {
		// TODO Auto-generated method stub
		JobDataMap map=context.getJobDetail().getJobDataMap();
		String index=map.getString("index");
		if(StringUtils.isNotBlank(index)){
			System.out.println("start1:"+index);
		}else{
			String[] indexs={"1","2","3"};
			for(String i:indexs){
				Trigger trigger=TriggerUtils.makeImmediateTrigger(0, 1000);
				trigger.setName("start1:"+i);
				trigger.setGroup("start1");
				JobDetail jobDetail=new JobDetail("start1:"+i, "start1", com.base.quartz.Task.class);
				jobDetail.getJobDataMap().put("task", this);
				jobDetail.getJobDataMap().put("index", i);
				try {
					startQuartz.getScheduler().scheduleJob(jobDetail, trigger);
				} catch (SchedulerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

}
