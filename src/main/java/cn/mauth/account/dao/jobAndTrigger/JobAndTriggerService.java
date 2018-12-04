package cn.mauth.account.dao.jobAndTrigger;

import cn.mauth.account.core.model.JobAndTrigger;
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

        PageHelper.startPage(pageNum, pageSize);

        List<JobAndTrigger> list = mapper.getJobAndTriggerDetails();

        PageInfo<JobAndTrigger> page = new PageInfo<>(list);

        return page;
    }
}
