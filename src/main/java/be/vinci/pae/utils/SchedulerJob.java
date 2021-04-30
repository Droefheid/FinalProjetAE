package be.vinci.pae.utils;


import java.time.LocalDateTime;
import org.quartz.DateBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import be.vinci.pae.api.utils.FatalException;
import be.vinci.pae.domaine.option.OptionDTO;

public class SchedulerJob implements Job {

  private SchedulerFactory sf = new StdSchedulerFactory();

  public void schedulerOption(LocalDateTime date, OptionDTO option) {

    JobDetail job =
        JobBuilder.newJob(SchedulerJob.class).withIdentity("optionJob", "group1").build();
    Trigger trigger = TriggerBuilder.newTrigger().withIdentity("OptionTrigger", "group1")
        .startAt(DateBuilder.dateOf(date.getSecond(), date.getMinute(), date.getHour(),
            date.getYear(), date.getMonth().getValue(), date.getDayOfMonth()))
        .withSchedule(
            SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
        .build();
    scheduler(job, trigger, option);
  }

  public void scheduler(JobDetail job, Trigger trigger, OptionDTO option) {
    Scheduler scheduler;
    try {
      scheduler = sf.getScheduler();
      scheduler.scheduleJob(job, trigger);
      job.getJobDataMap().put("option", option);
      scheduler.scheduleJob(job, trigger);
    } catch (SchedulerException e) {
      throw new FatalException("Scheduler error", e);
    }
  }

  public void StartScheduler() {
    Scheduler scheduler;
    try {
      scheduler = sf.getScheduler();
      scheduler.start();
      scheduler.shutdown(true);
    } catch (SchedulerException e) {
      throw new FatalException("Start Scheduler error", e);
    }
  }

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    JobKey jobKey = context.getJobDetail().getKey();

    // Execute method

  }

}
