package com.bjpowernode.micrtask;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

public class TaskTest {

    @Test
    public void today(){
        Date date = new Date();
        System.out.println("今天："+common.dateformat(date));

        Date yesterday = DateUtils.addDays(date,-1);
        System.out.println("昨天："+common.dateformat(yesterday));

        Date tomorrow = DateUtils.addDays(date,+1);
        System.out.println("明天："+common.dateformat(tomorrow));

        Date truncate = DateUtils.truncate(DateUtils.addDays(date,-1), Calendar.DATE);
        System.out.println("截断："+common.dateformat(truncate));
    }

    @Test
    public void clean(){
        Date date = new Date();
        Date threeDay = DateUtils.addDays(date,-3);
        System.out.println("三天前:"+common.dateformat(threeDay));
    }
}
