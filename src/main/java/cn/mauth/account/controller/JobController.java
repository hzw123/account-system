package cn.mauth.account.controller;

import cn.mauth.account.core.bean.JobBean;
import cn.mauth.account.core.enums.TriggerEnum;
import cn.mauth.account.core.model.JobAndTrigger;
import cn.mauth.account.core.util.PageUtil;
import cn.mauth.account.core.util.Result;
import cn.mauth.account.dao.jobAndTrigger.JobAndTriggerService;
import com.github.pagehelper.PageInfo;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/job")
public class JobController extends BaseController {

    @Autowired
    private JobAndTriggerService service;

    @Autowired
    private Scheduler scheduler;

    @PostMapping(value = "/addJob")
    public Result<String> addJob(JobBean jobBean) throws Exception {

        logger.info("=== 添加任务==="+jobBean);

        int col=service.countByJobName(jobBean.getJobClassName(),jobBean.getJobGroupName());

        if(col>0){
           return Result.error("该任务已经添加");
        }

        jobAdd(jobBean.getJobClassName(),jobBean.getJobGroupName(),jobBean.getCronExpression());

        return Result.SUCCESS;
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
            logger.error("创建定时任务失败" + e);
            throw new Exception("创建定时任务失败");
        }
    }

    /**
     * 暂停任务
     * @param jobBean
     * @throws Exception
     */
    @PostMapping(value="/pauseJob")
    public Result<String> pauseJob(JobBean jobBean) throws Exception {

        logger.info("===暂停任务==="+jobBean);

        String state=service.getState(jobBean.getJobClassName(),jobBean.getJobGroupName());

        if(state==null || TriggerEnum.PAUSED.getValue().equals(state)){
            return Result.error("该任务早已暂停运行");
        }

        scheduler.pauseJob(JobKey.jobKey(jobBean.getJobClassName(), jobBean.getJobGroupName()));

        return Result.SUCCESS;
    }


    /**
     * 恢复任务
     * @param jobClassName
     * @param jobGroupName
     * @throws Exception
     */
    @PostMapping(value="/resumeJob")
    public Result<String> resumeJob(String jobClassName, String jobGroupName) throws Exception {

        logger.info("===恢复任务===jobName:{},jobGroup:{}",jobClassName,jobGroupName);

        String state=service.getState(jobClassName,jobGroupName);

        if(state==null || TriggerEnum.WAITING.getValue().equals(state)){
            return Result.error("该任务正在运行,不需要恢复");
        }

        scheduler.resumeJob(JobKey.jobKey(jobClassName, jobGroupName));

        return Result.SUCCESS;
    }


    /**
     * 更新任务
     * @param jobBean
     * @throws Exception
     */
    @PostMapping(value="/rescheduleJob")
    public Result<String> rescheduleJob(JobBean jobBean) throws Exception {

        logger.info("===更新任务==="+jobBean);

        int col=service.countByJobName(jobBean.getJobClassName(),jobBean.getJobGroupName());

        if(col==0){
            return Result.error("没有找到该任务");
        }

        jobReschedule(jobBean.getJobClassName(), jobBean.getJobGroupName(), jobBean.getCronExpression());

        return Result.SUCCESS;
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

            logger.error("更新定时任务失败"+e);

            throw new Exception("更新定时任务失败");
        }
    }


    @PostMapping(value="/deleteJob")
    public Result<String> deleteJob(String jobClassName,String jobGroupName) throws Exception {

        logger.info("===删除任务===jobName:{},jobGroup:{}",jobClassName,jobGroupName);

        int col=service.countByJobName(jobClassName,jobGroupName);

        if(col==0){
            return Result.error("没有找到该任务");
        }

        jobDelete(jobClassName, jobGroupName);

        return Result.SUCCESS;
    }

    private void jobDelete(String jobClassName, String jobGroupName) throws Exception {

        scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));

        scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName, jobGroupName));

        scheduler.deleteJob(JobKey.jobKey(jobClassName, jobGroupName));

    }


    @GetMapping(value="/queryJob")
    public Map<String, Object> queryJob(@RequestParam(value="pageNum")Integer pageNum, @RequestParam(value="pageSize")Integer pageSize){
        return PageUtil.result(service.getJobAndTriggerDetails(pageNum, pageSize));
    }

    private QuartzJobBean getClass(String classname) throws Exception {

        Class<?> clazz = Class.forName(classname);

        return (QuartzJobBean)clazz.newInstance();
    }

}
