package org.fleet.config.oss;

import org.fleet.common.util.oss.OssBootUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 云存储 配置
 */
@Configuration
public class OssConfiguration {

    @Value("${fleet.oss.endpoint}")
    private String endpoint;
    @Value("${fleet.oss.accessKey}")
    private String accessKeyId;
    @Value("${fleet.oss.secretKey}")
    private String accessKeySecret;
    @Value("${fleet.oss.bucketName}")
    private String bucketName;
    @Value("${fleet.oss.staticDomain}")
    private String staticDomain;

    @Bean
    public void initOssBootConfiguration() {
        OssBootUtil.setEndPoint(endpoint);
        OssBootUtil.setAccessKeyId(accessKeyId);
        OssBootUtil.setAccessKeySecret(accessKeySecret);
        OssBootUtil.setBucketName(bucketName);
        OssBootUtil.setStaticDomain(staticDomain);
    }
}