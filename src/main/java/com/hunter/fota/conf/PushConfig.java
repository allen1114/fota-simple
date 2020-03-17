package com.hunter.fota.conf;

import cn.jiguang.common.ClientConfig;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Message;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "fota.jpush")
@Data
public class PushConfig {

    private String masterSecret;
    private String appKey;
    private String upgradePushMessageContentType;
    private String upgradePushMessageMsgContent;

    @Bean
    public JPushClient jPushClient() {
        ClientConfig config = ClientConfig.getInstance();
        config.setTimeToLive(60 * 60 * 24);
        return new JPushClient(masterSecret, appKey, null, config);
    }

    @Bean(name = "upgradePushMessage")
    public Message upgradePushMessage() {
        return Message.newBuilder().setContentType(upgradePushMessageContentType).setMsgContent(upgradePushMessageMsgContent).build();
    }
}
