package org.example.phase4.quartz;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class QuartzDemo {
    public static void main(String[] args) throws SchedulerException, InterruptedException {

        //CRON format (second minute hour day month day-of-week)
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.getListenerManager().addJobListener(new QuartzJobListener());

        scheduler.start();

        JobDetail job = newJob(QuartzJob.class)
                .withIdentity("emailJob", "group1")
                .usingJobData("email", "test@example.com")
                .usingJobData("subject", "Welcome!")
                .build();

        Trigger trigger = newTrigger()
                .startNow()
                .withSchedule(
                        simpleSchedule()
                                .withIntervalInSeconds(5)
                                .repeatForever()
                                .withMisfireHandlingInstructionFireNow()
                )
                .build();

        scheduler.scheduleJob(job, trigger);

        Thread.sleep(10000);

        scheduler.shutdown();
    }
}
