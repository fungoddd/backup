package com.crrcdt.backup.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * <p>
 * 定时任务
 * </p>
 *
 * @author LiuYuHang
 * @date 2019年12月9日14:17:55
 */
@Slf4j
@Component
public class ScheduleTask {

    /**
     * 是否开启定时任务
     */
    @Value("${backup.scheduled.enable:true}")
    private boolean scheduledEnable;

}
