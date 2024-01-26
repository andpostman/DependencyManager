package com.andpostman.dependencymanager.service;

import com.andpostman.dependencymanager.dto.EventDto;
import com.andpostman.dependencymanager.dto.SubscriberDto;

public interface DependencyService {

    boolean isEventToSubscribeExist(SubscriberDto subscriber);

    String createEvent(EventDto event);
}
