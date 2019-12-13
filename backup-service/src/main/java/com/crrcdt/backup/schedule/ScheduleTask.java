package com.crrcdt.backup.schedule;

import com.crrcdt.backup.utils.ssh2.Ssh2Client;
import com.crrcdt.backup.utils.ssh2.Ssh2ClientFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;


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

    /**
     *
     */
    private void backupMySQL() throws Exception {
        Ssh2Client ssh2Client = Ssh2ClientFactory.openSftpChannel("test.crrcdt.com", 22, "root", "ubuntu@crrcdt2020");
        String remoteDir = "/mnt/mysql/data/db_backup";
        //String localDir = "/Users/fungod/Downloads/backups/mysql";
        String localDir = "/mnt/backups/mysql";
        ssh2Client.listFiles(remoteDir);
        ssh2Client.download(remoteDir, localDir);
        ssh2Client.disconnect();
        String dir = localDir + File.separator + new File(remoteDir).getName();
        File file = new File(dir);
        if (file.exists()) {
            file.renameTo(new File(dir + DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd")));
        }
    }

    private void backupOracle() throws Exception {
        Ssh2Client ssh2Client = Ssh2ClientFactory.openSftpChannel("saas.crrcdt.com", 22, "root", "ubuntu@crrcdt2019");
        String remoteDir = "/mnt/oracle11g-ee/oracle_db_bak/EE_backup";
        //String localDir = "/Users/fungod/Downloads/backups/oracle";
        String localDir = "/mnt/backups/oracle";
        ssh2Client.listFiles(remoteDir);
        ssh2Client.download(remoteDir, localDir);
        ssh2Client.disconnect();
        String dir = localDir + File.separator + new File(remoteDir).getName();
        File file = new File(dir);
        if (file.exists()) {
            file.renameTo(new File(dir + DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd")));
        }
    }

    private void backupGitLab() throws Exception {
        Ssh2Client ssh2Client = Ssh2ClientFactory.openSftpChannel("gitlab.crrcdt.com", 22, "root", "ubuntu@crrcdt2019");
        String remoteDir = "/mnt/gitlab/data/backups/1575155216_2019_12_01_11.6.3_gitlab_backup.tar";
        //String localDir = "/Users/fungod/Downloads/backups/gitlab/1575155216_2019_12_01_11.6.3_gitlab_backup.tar";
        String localDir = "/mnt/backups/gitlab";
        ssh2Client.listFiles(remoteDir);
        ssh2Client.download(remoteDir, localDir);
        ssh2Client.disconnect();
        String dir = localDir + File.separator + new File(remoteDir).getName();
        File file = new File(dir);
        if (file.exists()) {
            file.renameTo(new File(dir + DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd")));
        }
    }

    /**
     * 每周六凌晨1点备份MySQL
     */
    @Scheduled(cron = "0 0 1 ? * SAT")
    public void task1() {
        if (!scheduledEnable) {
            return;
        }
        try {
            System.setProperty("java.awt.headless", "false");
            backupMySQL();
        } catch (Exception e) {
            log.error("定时备份MySQL任务异常! ", e);
        }
    }

    /**
     * 每周日凌晨1点备份Oracle
     */
    @Scheduled(cron = "0 0 1 ? * SUN")
    public void task2() {
        if (!scheduledEnable) {
            return;
        }
        try {
            System.setProperty("java.awt.headless", "false");
            backupOracle();
        } catch (Exception e) {
            log.error("定时备份Oracle任务异常! ", e);
        }
    }

    /**
     * 每月1日晚上9点执行
     */
    @Scheduled(cron = "0 0 17 14 * ?")
    public void task3() {
        if (!scheduledEnable) {
            return;
        }
        try {
            System.setProperty("java.awt.headless", "false");
            backupGitLab();
        } catch (Exception e) {
            log.error("定时备份GitLab任务异常! ", e);
        }
    }

    public static void main(String[] args) throws Exception {
        Ssh2Client ssh2Client = Ssh2ClientFactory.openSftpChannel("test.crrcdt.com", 22, "root", "ubuntu@crrcdt2020");
        String remoteDir = "/mnt/mysql/data/db_backup/project_ck/project_ck-2019-12-12-22-25-07.sql";
        String localDir = "/Users/fungod/Downloads/backups";
        //String localDir = "/mnt/backups/gitlab";
        ssh2Client.listFiles(remoteDir);
        ssh2Client.download(remoteDir, localDir);
        ssh2Client.disconnect();
        String dir = localDir + File.separator + new File(remoteDir).getName();
        File file = new File(dir);
        if (file.exists()) {
            file.renameTo(new File(dir + DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd")));
        }
    }

}
