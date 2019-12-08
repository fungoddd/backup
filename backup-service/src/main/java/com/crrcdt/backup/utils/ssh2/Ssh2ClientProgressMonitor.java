package com.crrcdt.backup.utils.ssh2;

import com.jcraft.jsch.SftpProgressMonitor;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;


/**
 * <p>SFTP断点续传</p>
 *
 * @author LiuYuHang
 * @date 2019-12-09 21:42:12
 */
@Slf4j
public class Ssh2ClientProgressMonitor implements SftpProgressMonitor {

    private ProgressMonitor monitor;

    /**
     * 当前接收的总字节数
     */
    private long count = 0;

    /**
     * 最终文件大小
     */
    private long max = 0;

    /**
     * 进度
     */
    private long percent = -1;

    @Override
    public void init(int op, String remotePath, String localDir, long max) {
        String message;
        String note;
        if (op == SftpProgressMonitor.PUT) {
            message = "SFTP 断点续传上传文件开始";
            log.info(message);
        } else {
            message = "SFTP 断点续传下载文件开始";
            log.info(message);
        }
        monitor = new ProgressMonitor(null, message + ": " + remotePath, "", 0, (int) max);
        this.max = max;
        this.count = 0;
        this.percent = -1;
    }

    @Override
    public boolean count(long count) {
        this.count += count;
        if (percent >= this.count * 100 / max) {
            return true;
        }
        percent = this.count * 100 / max;

        int kb = 1024;
        double rate = (double) (this.count / kb);
        double maxSize = (double) (max) / kb;
        String rateStr = String.format("%.0f", rate) + "kb";
        String maxStr = String.format("%.0f", maxSize) + "kb";
        if (rate > kb) {
            rate = rate / kb;
            rateStr = String.format("%.1f", rate) + "m";
        }
        if (max > kb) {
            maxSize = maxSize / kb;
            maxStr = String.format("%.1f", maxSize) + "m";
        }
        log.info("SFTP 文件已传输 {} ({}%） 文件总大小 {} ", rateStr, percent, maxStr);
        monitor.setNote("SFTP 文件已传输 " + rateStr + " (" + percent + "%） 文件总大小 " + maxStr);
        monitor.setProgress((int) this.count);
        return !(monitor.isCanceled());

    }

    @Override
    public void end() {
        monitor.close();
        log.info("SFTP 文件传输结束.");
    }
}
