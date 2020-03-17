package com.hunter.fota.service;

import com.hunter.fota.domain.Push;

public interface PushService {

    Push findById(Long id);

    Push create(Push push);

}
