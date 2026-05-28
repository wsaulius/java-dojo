package org.example.phase4.quartz;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class QuartzJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        JobDataMap dataMap =
                context.getJobDetail().getJobDataMap();

        String email = dataMap.getString("email");
        String subject = dataMap.getString("subject");

        System.out.println("Sending email to: " + email);
        System.out.println("Subject: " + subject);
    }
}
