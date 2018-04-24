package com.piesat.project.common.config;


import com.piesat.project.datahandle.GFPreHandle;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import java.io.IOException;
import java.util.Properties;

@Configuration
public class QuartzConfigration {

//	@Autowired
//	JobFactory jobFactory;

//	@Bean
//	public JobFactory jobFactory(ApplicationContext applicationContext) {
//		AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
//		jobFactory.setApplicationContext(applicationContext);
//		return jobFactory;
//	}
//
//	@Bean
//	public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory) {
//		SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
//		try {
//			schedulerFactoryBean.setOverwriteExistingJobs(true);
//			schedulerFactoryBean.setQuartzProperties(quartzProperties());
//			schedulerFactoryBean.setJobFactory(jobFactory);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return schedulerFactoryBean;
//	}
//
//	// 指定quartz.properties
//	@Bean
//	public Properties quartzProperties() throws IOException {
//		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
//		propertiesFactoryBean.setLocation(new ClassPathResource("/config/quartz.properties"));
//		propertiesFactoryBean.afterPropertiesSet();
//		return propertiesFactoryBean.getObject();
//	}
//
//	// 创建schedule
//	@Bean(name = "scheduler")
//	public Scheduler scheduler() {
//		return schedulerFactoryBean().getScheduler();
//	}

    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public Scheduler schedulerFactoryBean(JobFactory jobFactory,
                                          @Qualifier("sampleJobTrigger") Trigger sampleJobTrigger) throws Exception {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        // this allows to update triggers in DB when updating settings in config file:
        factory.setOverwriteExistingJobs(true);
//		factory.setDataSource(dataSource);
        factory.setJobFactory(jobFactory);

        factory.setQuartzProperties(quartzProperties());
        factory.afterPropertiesSet();

        Scheduler scheduler = factory.getScheduler();
        scheduler.setJobFactory(jobFactory);
        scheduler.scheduleJob((JobDetail) sampleJobTrigger.getJobDataMap().get("jobDetail"), sampleJobTrigger);

        scheduler.start();
        return scheduler;
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/config/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    @Bean
    public JobDetailFactoryBean sampleJobDetail() {
        return createJobDetail(GFPreHandle.class);
    }

    @Bean(name = "sampleJobTrigger")
    public SimpleTriggerFactoryBean sampleJobTrigger(@Qualifier("sampleJobDetail") JobDetail jobDetail,
                                                     @Value("${samplejob.startDely}") long startDely,
                                                     @Value("${samplejob.frequency}") long frequency) {
        return createTrigger(jobDetail, startDely, frequency);
    }

    private static JobDetailFactoryBean createJobDetail(Class jobClass) {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(jobClass);
        // job has to be durable to be stored in DB:
        factoryBean.setDurability(true);
        return factoryBean;
    }

    private static SimpleTriggerFactoryBean createTrigger(JobDetail jobDetail, long startDely, long pollFrequencyMs) {
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setStartDelay(startDely);
        factoryBean.setRepeatInterval(pollFrequencyMs);
        factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        // in case of misfire, ignore all missed triggers and continue :
        factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
        return factoryBean;
    }

    // Use this method for creating cron triggers instead of simple triggers:
    private static CronTriggerFactoryBean createCronTrigger(JobDetail jobDetail, String cronExpression) {
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setCronExpression(cronExpression);
        factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
        return factoryBean;
    }
}
