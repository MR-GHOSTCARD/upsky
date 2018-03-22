package com.etc.init.service.impl;

import javax.annotation.PostConstruct;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.SimpleTrigger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.base.quartz.ITask;
import com.etc.init.service.ISplInitService;

@Service
public class SplInitService implements ISplInitService,ApplicationContextAware,ITask {

	@Autowired
	private SchedulerFactoryBean startQuartz;
	
	@Autowired
	private ApplicationContext context;
	
	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		this.context=arg0;
	}

	@Override
	@PostConstruct
	public void init()  throws Exception{
//		if("1".equals(SysProperty.getProperty("loadSysPropertiesFromManager"))){
//			loadSysPropertiesFromManager();
//		}
		startQuartzTask();
	}
	
	
	
	
//	private void loadSysPropertiesFromManager()  throws Exception{
//		
//	}

	
	private void startQuartzTask()  throws Exception{
		SimpleTrigger simpleTrigger = new SimpleTrigger(SplInitService.class.getName(),"sky");
		simpleTrigger.setRepeatInterval(10000); //1分钟执行1次
		simpleTrigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
		JobDetail jobDetail=new JobDetail(SplInitService.class.getName(),"sky",com.base.quartz.Task.class);
		jobDetail.getJobDataMap().put("task", this);
		startQuartz.getScheduler().scheduleJob(jobDetail,simpleTrigger);
	}
	
	
	@Override
	public String execute(JobExecutionContext context){
		try{
			 refreshTask();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	private void refreshTask() throws Exception{
		if(startQuartz.getScheduler().getJobDetail("task1", "sky1") == null){
			SimpleTrigger simpleTrigger = new SimpleTrigger("task1","sky1");
			simpleTrigger.setRepeatInterval(1000); //1分钟执行1次
			simpleTrigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
			JobDetail jobDetail=new JobDetail("task1","sky1",com.base.quartz.Task.class);
			jobDetail.getJobDataMap().put("task", context.getBean("start1"));
			startQuartz.getScheduler().scheduleJob(jobDetail,simpleTrigger);
		}
	
	}

	
	
	
}
