package com.crrcdt.backup;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <p>
 * backup服务启动类
 * </p>
 *
 * @author lyh
 * @date 2019年10月30日16:15:58
 */
@Slf4j
@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackages = {"com.crrcdt"})
@MapperScan("com.crrcdt.*.mapper")
public class BackupServiceBootApplication {

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");
        log.info(">>>>> crrcdt-backup-service 正在启动 <<<<<");
        SpringApplication.run(BackupServiceBootApplication.class, args);
        log.info(">>>>> crrcdt-backup-service 启动完成 <<<<<");
    }

}
