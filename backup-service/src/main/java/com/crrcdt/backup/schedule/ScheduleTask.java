package com.crrcdt.backup.schedule;

import com.crrcdt.backup.utils.sftp.SftpClient;
import com.crrcdt.backup.utils.sftp.SftpClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * <p>
 * 定时清理日志文件 Controller
 * </p>
 *
 * @author lyh
 * @date 2019年9月20日14:17:55
 */
@Component
@EnableScheduling
public class ScheduleTask {

    private final static Logger logger = LoggerFactory.getLogger(ScheduleTask.class);

    /**
     * 是否开启定时任务
     */
    @Value("${backup.scheduled.enable:true}")
    private String scheduledEnable;

    /**
     * 删除指定目录下所有文件
     */
    private void downLoadFile() throws Exception {
        SftpClient sftpClient = SftpClientFactory.createConn("test.crrcdt.com", 22, "root", "ubuntu@crrcdt2019#");
        String remoteDir = "/mnt/mysql/data/db_backup";
        String localDir = "/mnt/db_backups";
        sftpClient.listFiles(remoteDir);
        sftpClient.download(remoteDir, localDir);
        sftpClient.disconnect();
    }

    /**
     * 每天凌晨一点下载文件
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void task() {
        if (!Boolean.parseBoolean(scheduledEnable)) {
            return;
        }
        try {
            downLoadFile();
        } catch (Exception e) {
            logger.error("定时下载任务抛出异常:{}", e.toString());
        }
    }
}
