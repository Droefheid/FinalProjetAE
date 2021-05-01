package be.vinci.pae.utils;


import java.time.LocalDateTime;
import java.util.Date;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import be.vinci.pae.api.utils.FatalException;
import be.vinci.pae.domaine.option.OptionDTO;
import be.vinci.pae.domaine.option.OptionUCC;
import jakarta.inject.Inject;

public class SchedulerJob implements Job {

  private SchedulerFactory sf = new StdSchedulerFactory();

  @Inject
  private OptionUCC optionUcc;

  /**
   * Schedule a job for an option.
   * 
   * @param date trigger to start the job.
   * @param option the option that needs the job.
   */
  public void schedulerOption(LocalDateTime date, OptionDTO option) {

    JobDetail job = JobBuilder.newJob(SchedulerJob.class)
        .withIdentity("" + option.getId(), "" + option.getId()).build();
    Trigger trigger = TriggerBuilder.newTrigger().withIdentity("OptionTrigger", "group1")
        .startAt(new Date(date.getYear(), date.getMonth().getValue(), date.getDayOfMonth(),
            date.getHour(), date.getMinute(), date.getSecond()))
        .withSchedule(
            SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
        .build();
    scheduler(job, trigger, option);
  }

  /**
   * put the job and trigger in the schedule.
   * 
   * @param job to execute.
   * @param trigger date to trigger job.
   * @param option with required info.
   */
  public void scheduler(JobDetail job, Trigger trigger, OptionDTO option) {
    Scheduler scheduler;
    try {
      scheduler = sf.getScheduler();
      job.getJobDataMap().put("option", option);
      scheduler.scheduleJob(job, trigger);
    } catch (SchedulerException e) {
      throw new FatalException("Scheduler error", e);
    }
  }

  /**
   * Gets a scheduler and stops the list.
   */
  public void stopScheduler() {
    Scheduler scheduler;
    try {
      scheduler = sf.getScheduler();
      scheduler.shutdown(true);
    } catch (SchedulerException e) {
      throw new FatalException("Stop Scheduler error", e);
    }
  }

  /**
   * Gets a scheduler and starts the list.
   */
  public void startScheduler() {
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
    OptionDTO option = (OptionDTO) context.getMergedJobDataMap().get("option");
    optionUcc.changeOptionState(option);
  }

}
