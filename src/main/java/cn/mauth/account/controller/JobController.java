package cn.mauth.account.controller;

import cn.mauth.account.core.bean.JobBean;
import cn.mauth.account.core.model.JobAndTrigger;
import cn.mauth.account.dao.jobAndTrigger.JobAndTriggerService;
import com.github.pagehelper.PageInfo;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/job")
public class JobController extends BaseController {

    @Autowired
    private JobAndTriggerService service;

    @Autowired
    private Scheduler scheduler;

    @PostMapping(value = "/addJob")
    public void addJob(JobBean jobBean) throws Exception {

        jobAdd(jobBean.getJobClassName(),jobBean.getJobGroupName(),jobBean.getCronExpression());

    }

    private void jobAdd(String jobClassName, String jobGroupName, String cronExpression) throws Exception {

        try {
            // 启动调度器
            scheduler.start();

            //构建job信息
            JobDetail jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass()).withIdentity(jobClassName, jobGroupName).build();

            //表达式调度构建器(即任务执行的时间)
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobClassName, jobGroupName)
                    .withSchedule(scheduleBuilder).build();

            scheduler.scheduleJob(jobDetail, trigger);

        } catch (SchedulerException e) {
            System.out.println("创建定时任务失败" + e);
            throw new Exception("创建定时任务失败");
        }
    }

    /**
     * 暂停任务
     * @param jobBean
     * @throws Exception
     */
    @PostMapping(value="/pauseJob")
    public void pauseJob(JobBean jobBean) throws Exception {

        scheduler.pauseJob(JobKey.jobKey(jobBean.getJobClassName(), jobBean.getJobGroupName()));

    }


    /**
     * 恢复任务
     * @param jobClassName
     * @param jobGroupName
     * @throws Exception
     */
    @PostMapping(value="/resumeJob")
    public void resumeJob(String jobClassName, String jobGroupName) throws Exception {

        scheduler.resumeJob(JobKey.jobKey(jobClassName, jobGroupName));

    }


    /**
     * 更新任务
     * @param jobBean
     * @throws Exception
     */
    @PostMapping(value="/rescheduleJob")
    public void rescheduleJob(JobBean jobBean) throws Exception {

        jobReschedule(jobBean.getJobClassName(), jobBean.getJobGroupName(), jobBean.getCronExpression());

    }

    private void jobReschedule(String jobClassName, String jobGroupName, String cronExpression) throws Exception {
        try {

            TriggerKey triggerKey = TriggerKey.triggerKey(jobClassName, jobGroupName);
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);

        } catch (SchedulerException e) {

            System.out.println("更新定时任务失败"+e);

            throw new Exception("更新定时任务失败");
        }
    }


    @PostMapping(value="/deleteJob")
    public void deleteJob(String jobClassName,String jobGroupName) throws Exception {

        jobDelete(jobClassName, jobGroupName);

    }

    private void jobDelete(String jobClassName, String jobGroupName) throws Exception {

        scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));

        scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName, jobGroupName));

        scheduler.deleteJob(JobKey.jobKey(jobClassName, jobGroupName));

    }


    @GetMapping(value="/queryJob")
    public Map<String, Object> queryJob(@RequestParam(value="pageNum")Integer pageNum, @RequestParam(value="pageSize")Integer pageSize){
        PageInfo<JobAndTrigger> jobAndTrigger = service.getJobAndTriggerDetails(pageNum, pageSize);
        Map<String, Object> map = new HashMap<>();
        map.put("JobAndTrigger", jobAndTrigger);
        map.put("number", jobAndTrigger.getTotal());
        return map;
    }

    private QuartzJobBean getClass(String classname) throws Exception {

        Class<?> clazz = Class.forName(classname);

        return (QuartzJobBean)clazz.newInstance();
    }

}
