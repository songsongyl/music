package com.music.demo.login.job;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PreLoadingUsernameJobConfig {

    /**
     * 描述任务详情对象
     * @return
     */
    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob(PreLoadingUsernameJob.class)
                .storeDurably(true)
                .build();
    }

    @Bean
    public Trigger trigger() {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail())
                .withSchedule(CronScheduleBuilder.cronSchedule("0 45 20 * * ? *"))
                .startNow()
                .build();
    }
}
