package com.hunter.fota.service.impl;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import com.hunter.fota.exception.JPushException;
import com.hunter.fota.service.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class PushServiceImpl implements PushService {

    private JPushClient jPushClient;
    private Message upgradePushMessage;

    public PushServiceImpl(JPushClient jPushClient, @Qualifier("upgradePushMessage") Message upgradePushMessage) {
        this.jPushClient = jPushClient;
        this.upgradePushMessage = upgradePushMessage;
    }

    @Override
    public void sendPush(String projectCode, int timeInMin) {
        Audience audience = Audience.tag(projectCode);

        Options options = Options.newBuilder().setBigPushDuration(timeInMin).build();
        PushPayload pushPayload = PushPayload.newBuilder()
                .setAudience(audience)
                .setOptions(options)
                .setPlatform(Platform.android())
                .setMessage(upgradePushMessage)
                .build();
        try {
            if (!jPushClient.sendPush(pushPayload).isResultOK()) {
                throw new JPushException("push fail");
            }
        } catch (APIConnectionException | APIRequestException e) {
            e.printStackTrace();
            throw new JPushException(e);
        }
    }
}
