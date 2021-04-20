package org.fleet.config.oss;

import lombok.extern.slf4j.Slf4j;
import org.fleet.common.util.MinioUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Minio文件上传配置文件
 */
@Slf4j
@Configuration
public class MinioConfig {
    @Value(value = "${fleet.minio.minio_url}")
    private String minioUrl;
    @Value(value = "${fleet.minio.minio_name}")
    private String minioName;
    @Value(value = "${fleet.minio.minio_pass}")
    private String minioPass;
    @Value(value = "${fleet.minio.bucketName}")
    private String bucketName;

    @Bean
    public void initMinio(){
        if(!minioUrl.startsWith("http")){
            minioUrl = "http://" + minioUrl;
        }
        if(!minioUrl.endsWith("/")){
            minioUrl = minioUrl.concat("/");
        }
        MinioUtil.setMinioUrl(minioUrl);
        MinioUtil.setMinioName(minioName);
        MinioUtil.setMinioPass(minioPass);
        MinioUtil.setBucketName(bucketName);
    }

}
