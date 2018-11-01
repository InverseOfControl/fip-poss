package com.ipaylinks.poss.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * 文件上传配置
 *
 * @author Alan
 */
@Configuration
public class MultipartConfig {

    @Value("${spring.http.multipart.max-file-size}")
    private String maxSingleFileSize;

    @Value("${spring.http.multipart.max-request-size}")
    private String maxAllFileSize;


    /**
     * 文件上传配置
     *
     * @return 返回
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大
        factory.setMaxFileSize(maxSingleFileSize);
        // 设置总上传数据总大小
        factory.setMaxRequestSize(maxAllFileSize);
        return factory.createMultipartConfig();
    }


}
