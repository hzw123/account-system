package cn.mauth.account.config;

import cn.mauth.account.core.util.GlobalConstant;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class AccountConfiguration {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Bean
    public Executor asyncServiceExecutor() {
        logger.info("\n-------asyncServiceExecutor start ");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(5);
        //配置最大线程数
        executor.setMaxPoolSize(100);

        executor.setQueueCapacity(10);

        executor.setKeepAliveSeconds(600);
        //配置队列大小
        executor.setQueueCapacity(99999);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("async-service-");

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }

    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("reasonable", "true");
        p.setProperty("dialect", "mysql");
        pageHelper.setProperties(p);
        return pageHelper;
    }



//    @Bean
//    @Primary
//    public DataSource multipleDataSource(@Qualifier( GlobalConstant.ACCOUNT_SYSTEM_DATA_SOURCE_KEY ) DataSource dataSourceMaster ,
//            @Qualifier( GlobalConstant.QUARTZ_DATA_SOURCE_KEY ) DataSource dataSourceQuratz) {
//        DynamicDataSource bean = new DynamicDataSource();
//        Map<Object, Object> targetDataSources = new HashMap<>();
//
//        targetDataSources.put(GlobalConstant.ACCOUNT_SYSTEM_DATA_SOURCE_KEY,dataSourceMaster);
//        targetDataSources.put(GlobalConstant.QUARTZ_DATA_SOURCE_KEY,dataSourceQuratz);
//
//        bean.setTargetDataSources(targetDataSources);
//
//        bean.setDefaultTargetDataSource(dataSourceMaster);
//
//        return bean;
//    }

}
