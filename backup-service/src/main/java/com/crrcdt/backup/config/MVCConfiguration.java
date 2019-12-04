package com.crrcdt.backup.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 配置converter（FastJson)
 * 不能扩展WebMvcConfigurationSupport,否则默认的MVC不能自动配置,会失效,只能实现接口
 * </p>
 *
 * @author root
 * @date 2019年10月31日15:31:27
 */
@Configuration
public class MVCConfiguration implements WebMvcConfigurer {

    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        return new StringHttpMessageConverter(Charset.forName("UTF-8"));
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        int indexOfJackson = 0;
        for (HttpMessageConverter converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                indexOfJackson = converters.indexOf(converter);
                break;
            }
        }
        converters.add(responseBodyConverter());
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                //结果是否格式化,默认为false
                SerializerFeature.PrettyFormat,
                //Date使用ISO8601格式输出，默认为false(  在零点的时候，不能显示时间0:00:00 ，前台不能解析，在IE中整点兼容性有问题）
                //SerializerFeature.UseISO8601DateFormat,
                //指定UTC格式
                SerializerFeature.WriteDateUseDateFormat,
                //消除对同一对象循环引用的问题，默认为false
                SerializerFeature.DisableCircularReferenceDetect,
                //是否输出值为null的字段,默认为false
                SerializerFeature.WriteMapNullValue
        );

        // 时间的默认处理格式（必须用这个来设置默认格式）
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
        fastConverter.setFastJsonConfig(fastJsonConfig);
        // 解决乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON);
        fastConverter.setSupportedMediaTypes(fastMediaTypes);
        // 需要放在jackson转换器之前，否则不起作用
        // 放在最前面，工作流中读取文件返回json字符串，未被处理
        if (indexOfJackson > 0) {
            converters.add(indexOfJackson, fastConverter);
        } else {
            converters.add(fastConverter);
        }
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    }

}
