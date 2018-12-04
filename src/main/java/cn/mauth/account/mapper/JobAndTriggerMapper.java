package cn.mauth.account.mapper;

import cn.mauth.account.core.model.JobAndTrigger;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JobAndTriggerMapper {

    public List<JobAndTrigger> getJobAndTriggerDetails();
}
