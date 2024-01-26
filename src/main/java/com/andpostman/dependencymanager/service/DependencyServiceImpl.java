package com.andpostman.dependencymanager.service;

import com.andpostman.dependencymanager.component.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.andpostman.dependencymanager.dto.EventDto;
import com.andpostman.dependencymanager.dto.SubscriberDto;
import com.andpostman.dependencymanager.exception.EventExistException;
import com.andpostman.dependencymanager.model.Subscriber;
import com.andpostman.dependencymanager.repository.EventRepository;
import com.andpostman.dependencymanager.repository.SubscriberRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DependencyServiceImpl implements DependencyService{

    private EventRepository eventRepository;
    private SubscriberRepository subscriberRepository;
    private final KafkaProducer kafkaProducer;

    @Autowired
    private void setEventRepository(EventRepository eventRepository){this.eventRepository = eventRepository;}
    @Autowired
    private void setSubscriberRepository(SubscriberRepository subscriberRepository){this.subscriberRepository = subscriberRepository;}

    @Override
    public boolean isEventToSubscribeExist(SubscriberDto subscriber) {
        if (eventRepository.existsById(subscriber.getMasterProject(), subscriber.getMasterVersion())){
            log.info("В таблице Events был найден master проект");
            return true;
        }
        log.info("В таблице Events не был найден master проект");
        if (subscriberRepository.existsById(subscriber.getProject())){
            log.warn("Данный проект существует в таблице Subscribers");
            return false;
        }
        subscriberRepository.insertSubscriber(subscriber.getProject(), LocalDateTime.now(), subscriber.getMasterProject(), subscriber.getMasterVersion());
        log.info("Insert subscriber to DB: "+ subscriberRepository.findSubscriberById(subscriber.getProject()));
        return false;
    }

    @Override
    public String createEvent(EventDto event) {
        if (eventRepository.existsById(event.getProject(),event.getVersion())) {
            throw new EventExistException();
        }
        eventRepository.insertEvent(event.getProject(), event.getVersion(), LocalDateTime.now(), true);
        log.info("Insert event to DB: "+ eventRepository.findEventById(event.getProject(),event.getVersion()));
        List<Subscriber> eventSubscribers = subscriberRepository.findAllSubscribersByMasterProjectAndMasterVersion(event.getProject(), event.getVersion());
        if (eventSubscribers.isEmpty()) {
            log.info("Subscribers=0");
            return "Subscribers=0";
        } else {
            log.info("ВЫЗОВ KAFKA");
            return kafkaProducer.sendMessage(eventSubscribers);

        }
    }
}
