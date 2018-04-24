package com.piesat.project.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
public class WelcomeJob implements Job{

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
//    	template.convertAndSend("/topic/getResponse", new Response("欢迎体验bootdo,这是一个任务计划，使用了websocket和quzrtz技术，可以在计划列表中取消，欢迎您加入qq群交流学习!" ));
        System.out.println("hahahahah");
    }

}