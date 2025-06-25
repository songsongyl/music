package com.music.demo.login.job;

import com.music.demo.domain.entity.User;
import com.music.demo.login.service.IRegistryService;
import com.music.demo.login.util.BloomFilterUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class PreLoadingUsernameJob extends QuartzJobBean {


    private final IRegistryService iRegistryService;


    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.debug("定时任务启动：{}",new Date());
        List<User> userList = iRegistryService.findAll();

        userList.forEach(user -> {
            BloomFilterUtil.getInstance().add(user.getUsername());
        });
        log.debug("布隆过滤器加载完成...");
    }
}
