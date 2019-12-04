package com.crrcdt.backup.config;

import liquibase.integration.spring.SpringLiquibase;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * <p>
 * liquibase全局配置,默认关闭liquibase
 * </p>
 *
 * @author lyh
 * @date 2019年10月31日12:17:08
 */

@Configuration
public class LiquibaseConfiguration {

    /**
     * 是否开启liquibase配置
     */
    @Value("${spring.liquibase.enabled:false}")
    private Boolean isShouldRun;
    /**
     * 数据库变更日志所在位置
     */
    @Value("${spring.liquibase.change-log:classpath:/DB/changelog/db.changelog-master.xml}")
    private String changeLogPath;
    /**
     * 数据库变更日志上下文
     */
    @Value("${spring.liquibase.contexts:}")
    private String contexts;
    /**
     * 数据源信息,不存在不装载
     */
    @Autowired(required = false)
    private DataSource dataSource;

    @Bean(name = "liquibase")
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setShouldRun(isShouldRun);
        liquibase.setChangeLog(changeLogPath);
        liquibase.setDataSource(dataSource);
        //如果获取到context上下文属性且不为空串,则加载该context配置
        if (StringUtils.isNotBlank(contexts)) {
            liquibase.setContexts(contexts);
        }
        //执行变更日志前drop当前连接数据库,默认false
        liquibase.setDropFirst(false);
        return liquibase;
    }

}

