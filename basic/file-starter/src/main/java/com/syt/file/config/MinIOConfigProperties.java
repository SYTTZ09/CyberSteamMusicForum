package com.syt.file.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

@Data
@ConfigurationProperties(prefix = "minio")  // 文件上传 配置前缀file.oss
public class MinIOConfigProperties implements Serializable {

    /**
     * 用户名
     */
    private String accessKey;

    /**
     * 密码
     */
    private String secretKey;

    /**
     * 桶 类似于文件夹
     */
    private String bucket;

    /**
     * 终端地址
     */
    private String endpoint;

    /**
     * 读取地址
     */
    private String readPath;
}
