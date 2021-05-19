package com.xiaoma.quartz.execute;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class PrintWordsJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String taskTime = new SimpleDateFormat("yy-MM-dd HH-mm-ss").format(new Date());
        System.out.println("taskTime start at:" + taskTime+ ",prints: Hello Job-" + new Random().nextInt(100));
    }
}