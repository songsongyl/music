package com.music.demo.search.job;

import com.music.demo.domain.entity.User;
import com.music.demo.search.service.IRankService;
import com.music.demo.search.service.ISearchService;
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
public class PreRankJob extends QuartzJobBean {


    private final IRankService iRankService;


    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.debug("本周排名启动:{}",new Date());
        iRankService.rankMusic();
        log.debug("本周排名记载完成...");
    }
}
