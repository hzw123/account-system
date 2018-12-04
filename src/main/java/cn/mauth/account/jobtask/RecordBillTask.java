package cn.mauth.account.jobtask;

import cn.mauth.account.core.model.AccountBook;
import cn.mauth.account.zip.AccountBillZip;
import cn.mauth.account.core.enums.BookStateEnum;
import cn.mauth.account.dao.accountbook.AccountBookService;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class RecordBillTask extends QuartzJobBean {

    private Logger logger = LoggerFactory.getLogger(RecordBillTask.class);

    @Autowired
    private AccountBookService accountBookService;

    @Autowired
    private AccountBillZip accountBillZip;

    private static volatile AtomicBoolean isRunning = new AtomicBoolean(false);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //保证同时只有一个定时任务在执行
        if(!isRunning.get()) {
            if(isRunning.compareAndSet(false, true)) {
                try {
                    logger.info("------------定时任务处理余额变动明细拆解入库---------------");
                    long processBegin = System.currentTimeMillis();
                    processMain();
                    logger.info("------------定时任务处理余额变动明细入库结束，耗时{}-----------",
                            System.currentTimeMillis() - processBegin);
                } catch (Exception e){
                    logger.info("复式记账明细入库--定时任务执行失败！", e);
                }
                isRunning.compareAndSet(true, false);
            } else {
                logger.info("复式记账明细入库--一个定时任务已经先执行，当前任务退出！"+new Gson().toJson(isRunning));
            }
        } else {
            logger.info("复式记账明细入库--一个定时任务已经在执行，当前任务退出！"+new Gson().toJson(isRunning));
        }
    }

    private void processMain(){
        int pageNum = 1;
        int pageSize = 1000;

        AccountBook queryParam = new AccountBook();
        queryParam.setBookState(BookStateEnum.INIT.getValue());

        //首先获取所有尚未进行余额变动明细拆解入库的复式记账明细
        PageInfo<AccountBook> pageAccountBooks =
                accountBookService.pageQueryAccountBook(pageNum, pageSize, queryParam);

        if(pageAccountBooks == null || pageAccountBooks.getTotal() < 1){
            logger.info("未找到需要进行复式明细拆分余额变动记账的记录");
            return;
        }

        while(pageNum <= pageAccountBooks.getPages()){
            List<AccountBook> accountBooks;
            if(pageNum == 1){
                accountBooks = pageAccountBooks.getList();
            } else {
                pageAccountBooks =
                        accountBookService.pageQueryAccountBook(pageNum, pageSize, queryParam);
                accountBooks = pageAccountBooks.getList();
            }

            if(accountBooks == null){
                logger.error("分页获取复式明细拆分余额变动记账的记录失败，退出任务");
                return;
            }

            try{
                accountBillZip.recordBill(accountBooks);
            } catch (Exception e){
                logger.error("将复式记账明细转换为单边余额变动明细失败", e);
                return;
            }

            pageNum++;
        }
    }
}
