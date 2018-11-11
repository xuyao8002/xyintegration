package com.xuyao.integration.schedule;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@EnableScheduling
public class ScheduleTask {

    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //@Scheduled(cron="0/5 * * * * ?")
    public void cronTask(){
        System.out.println(df.format(new Date()));
    }

    //方法执行完成后隔fixedDelay秒执行下一次任务
    //@Scheduled(fixedDelay=6000)
    public void fixedDelayTask() throws InterruptedException{
        System.out.println(df.format(new Date()));
    }

    //每fixedRate秒执行一次任务
    //@Scheduled(fixedRate=7000)
    public void fixedRateTask() throws InterruptedException{
        System.out.println(df.format(new Date()));
    }
}
