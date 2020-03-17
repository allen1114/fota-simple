package com.hunter.fota.conf;

import com.qiniu.util.Auth;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "fota.qiniu")
@Data
public class QiniuConfig {

    private String accessKey;
    private String secretKey;
    private String bucket;
    private String uploadUrl;
    private String downloadUrl;
    private String returnBody;
    private long expires;

    @Bean
    public Auth auth() {
        return Auth.create(accessKey, secretKey);
    }
}
