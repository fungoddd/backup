package com.crrcdt.backup;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * <p>
 * backup服务启动类
 * </p>
 *
 * @author lyh
 * @date 2019年10月30日16:15:58
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.crrcdt"})
@MapperScan("com.crrcdt.*.mapper")
public class BackupServiceBootApplication {

    private static Logger log = LoggerFactory.getLogger(BackupServiceBootApplication.class);

    public static void main(String[] args) {
        log.info(">>>>> crrcdt-backup-service 正在启动 <<<<<");
        SpringApplication.run(BackupServiceBootApplication.class, args);
        log.info(">>>>> crrcdt-backup-service 启动完成 <<<<<");
    }

}
