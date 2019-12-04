package com.crrcdt.backup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.collect.Lists.newArrayList;

/**
 * <p>
 * Swagger2配置
 * </p>
 *
 * @author lyh
 * @date 2019年10月31日12:14:07
 */
@Component
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .securitySchemes(newArrayList(this.apiKey()))
                .apiInfo(apiInfo())
                .select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.crrcdt"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("文件备份服务Swagger2测试")
                //创建人
                .contact(new Contact("fungoddd", "http://www.baidu.com", "fungod@dingtalk.com"))
                //版本号
                .version("1.0")
                //描述
                .description("API描述")
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("access-token", "access-token", "header");
    }

}
