package org.example.phase4.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

public class QuartzJobListener implements JobListener {

    @Override
    public String getName() {
        return "Quartz job listener";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        System.out.println("Job is about to start: " + context.getJobDetail().getKey());
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        System.out.println("Job was vetoed (cancelled before execution) " + context.getJobDetail().getKey());
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {

        System.out.println("Job finished: " + context.getJobDetail().getKey());

        if (jobException != null) {
            System.out.println("Job failed with exception!");
        }
    }
}
