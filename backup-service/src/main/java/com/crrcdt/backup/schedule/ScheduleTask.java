package com.crrcdt.backup.schedule;

import com.crrcdt.backup.utils.ssh2.Ssh2Client;
import com.crrcdt.backup.utils.ssh2.Ssh2ClientFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
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
@EnableScheduling
public class ScheduleTask {

    /**
     * 是否开启定时任务
     */
    @Value("${backup.scheduled.enable:true}")
    private String scheduledEnable;

    /**
     * 删除指定目录下所有文件
     */
    private void downLoadFile() throws Exception {
        Ssh2Client ssh2Client = Ssh2ClientFactory.openSftpChannel("test.crrcdt.com", 22, "root", "ubuntu@crrcdt2019#");
        String remoteDir = "/mnt/mysql/data/db_backup";
        String localDir = "/mnt/db_backups";
        ssh2Client.listFiles(remoteDir);
        ssh2Client.download(remoteDir, localDir);
        ssh2Client.disconnect();
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
            log.error("定时下载任务抛出异常:{}", e.toString());
        }
    }

    public static void main(String[] args) throws Exception {
        Ssh2Client ssh2Client = Ssh2ClientFactory.openSftpChannel("test.crrcdt.com", 22, "root", "ubuntu@crrcdt2019#");
        String remoteDir = "/mnt/mysql/data/db_backup";
        String localDir = "/Users/fungod/Downloads/下载测试/子目录";
        ssh2Client.listFiles(remoteDir);
        ssh2Client.download(remoteDir, localDir);
        ssh2Client.disconnect();
    }
}
