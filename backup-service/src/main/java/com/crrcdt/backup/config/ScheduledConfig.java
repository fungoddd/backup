package com.crrcdt.backup.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 定时任务配置ScheduledConfig
 * </p>
 *
 * @author lyh
 * @date 2019年10月30日15:46:08
 */
@EnableScheduling
@Component
public class ScheduledConfig {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledConfig.class);

    @Value("${scheduled.enable:true}")
    private boolean scheduledEnable;

    @Scheduled(cron = "0/5 * * * * ?")
    public void backupLinux() {

    }

}
