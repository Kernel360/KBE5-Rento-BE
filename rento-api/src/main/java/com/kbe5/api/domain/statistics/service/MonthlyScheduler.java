package com.kbe5.api.domain.statistics.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MonthlyScheduler {

    private final MonthlyService monthlyService;

    //@Scheduled(cron = "0 0 1 * * *") <- 배포 시에는 이걸로 해야함
    @Scheduled(fixedDelay = 86_400_000) //하루 한번 실행 24*60(분)*60(초)*1000(밀리초) <- 서버 실행 시 한번 생성되게끔 하려고 추가
    public void createMonthlyStats(){
        monthlyService.generateMonthlyStats();
    }
}
