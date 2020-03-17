package com.hunter.fota.rest;

import com.hunter.fota.service.PushService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/push")
public class PushController {

    private PushService pushService;

    public PushController(PushService pushService) {
        this.pushService = pushService;
    }

    @RequestMapping("/sendPush")
    public void sendPush(@RequestParam("projectCode") String projectCode, @RequestParam("timeInMin") int timeInMin) {
        pushService.sendPush(projectCode, timeInMin);
    }

}
