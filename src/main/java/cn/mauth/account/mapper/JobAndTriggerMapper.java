package cn.mauth.account.mapper;

import cn.mauth.account.core.model.JobAndTrigger;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface JobAndTriggerMapper {

    public List<JobAndTrigger> getJobAndTriggerDetails();

    public int countByJobName(@Param("jobName") String jobName,@Param("jobGroup") String jobGroup);

    public String getState(@Param("jobName") String jobName,@Param("jobGroup") String jobGroup);
}
