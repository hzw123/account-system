package cn.mauth.account.dao.jobAndTrigger;

import cn.mauth.account.core.bean.Pageable;
import cn.mauth.account.core.model.JobAndTrigger;
import cn.mauth.account.core.util.PageUtil;
import cn.mauth.account.mapper.JobAndTriggerMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JobAndTriggerService {

    @Autowired
    private JobAndTriggerMapper mapper;

    public PageInfo<JobAndTrigger> getJobAndTriggerDetails(int pageNum, int pageSize) {

        PageUtil.startPage(Pageable.of(pageNum,pageSize));

        return new PageInfo<>(mapper.getJobAndTriggerDetails());
    }

    public int countByJobName(String jobName,String jobGroup){
        return mapper.countByJobName(jobName,jobGroup);
    }

    public String getState(String jobName,String jobGroup){
        return mapper.getState(jobName,jobGroup);
    }
}
