package com.bjpowernode.micrtask;

import com.bjpowernode.api.service.IncomeService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TaskManager {

    @DubboReference(interfaceClass = IncomeService.class,version = "1.0")
    private IncomeService incomeService;

    @Scheduled(cron = "30 20 11 * * ?")
    public void testTask(){
        System.out.println("定时任务执行："+ new Date());
    }

    //@Scheduled(cron = "*/5 * * * * ?")
    /*public void testTasks(){
        Date date = new Date();
        System.out.println("定时任务执行："+common.dateformat(date));
    }
     **/

    @Scheduled(cron = "30 0 0 * * ?")
    public void invokeGenerateIncomePlan(){
        System.out.println("定时任务执行");
        incomeService.generateIncomePlan();
    }

    @Scheduled(cron = "0 1 0 * * ?")
    public void invokeGenerateIncome(){
        System.out.println("收益返还定时任务执行");
        incomeService.generateIncomeBack();
    }

    @Scheduled(cron = "30 1 0 * * ?")
    public void invokeCleanData(){
        System.out.println("数据清理程序执行");
        int rows = incomeService.cleanRecord();
        if (rows <1 ){
            System.out.println("未发现多余数据");
        } else {
            System.out.println("一共清理掉:"+rows+"条数据");
        }
    }
}
