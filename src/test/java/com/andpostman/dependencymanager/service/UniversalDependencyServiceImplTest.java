package com.andpostman.dependencymanager.service;

import com.andpostman.dependencymanager.dto.EventDto;
import com.andpostman.dependencymanager.dto.SubscriberDto;
import com.andpostman.dependencymanager.model.Subscriber;
import com.andpostman.dependencymanager.repository.EventRepository;
import com.andpostman.dependencymanager.repository.SubscriberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
@DisplayName("Операции с сервисом с events и subscribers")
class UniversalDependencyServiceImplTest {

    @Mock
    SubscriberRepository subscriberRepository;

    @Mock
    EventRepository eventRepository;

    @InjectMocks
    DependencyServiceImpl dependencyService;

    @Test
    @DisplayName("Проверка если событие на которое пытаются подписаться существует то вернуть true")
    void whenEventToSubscribeExist_thenReturnTrue(){
        when(eventRepository.existsById(anyString(), anyString())).thenReturn(true);
        assertTrue(dependencyService.isEventToSubscribeExist(new SubscriberDto("L", "Z", "v1")),
                "Should return true because it has a subscriber");
    }

    @Test
    @DisplayName("Проверка если подписчика не существует то вернуть false")
    void whenEventToSubscribeDoesNotExist_thenReturnFalse(){
        when(eventRepository.existsById(anyString(), anyString())).thenReturn(false);
        when(subscriberRepository.existsById(anyString())).thenReturn(false);
        assertFalse(dependencyService.isEventToSubscribeExist(new SubscriberDto("L", "Z", "v1")),
                "Should return false because there are not any subscribers");
    }

    @Test
    @DisplayName("Проверка наличия у события 0 подписчиков")
    void whenNoSubscribers_thenCountOfSubscribersShouldBe0(){
        Logger logger = (Logger) LoggerFactory.getLogger(DependencyServiceImpl.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);

        when(subscriberRepository.findAllSubscribersByMasterProjectAndMasterVersion(anyString(), anyString())).thenReturn(new ArrayList<>());
        dependencyService.createEvent(new EventDto("Z","v1",true));

        List<ILoggingEvent> logsList = listAppender.list;

        assertAll("Проверка если на собыите никто не подписан то вернуть 0 подписчиков",
                () -> assertEquals("Subscribers=0", logsList.get(1).getMessage(),
                        "Should be 0 subscribers"),
                () -> assertEquals(Level.INFO, logsList.get(1).getLevel(),
                        "Should be logged level INFO")
        );
    }

    @Test
    @DisplayName("Проверка наличия у события подписчиков")
    void whenSubscribersExist_thenSubscribersShouldBe(){
        Logger logger = (Logger) LoggerFactory.getLogger(DependencyServiceImpl.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);

        when(subscriberRepository.findAllSubscribersByMasterProjectAndMasterVersion(anyString(), anyString()))
                .thenReturn(Arrays.asList(any(Subscriber.class), any(Subscriber.class)));
        dependencyService.createEvent(new EventDto("Z","v1",true));

        List<ILoggingEvent> logsList = listAppender.list;

        assertAll("Проверка если на собыите подписаны то вернуть подписчиков",
                () -> assertThat(logsList.get(1).getMessage())
                        .as("Should be 2 subscribers")
                        .contains("Subscribers=2"),
                () -> assertThat(Level.INFO)
                        .as("Should be logged level INFO")
                        .isEqualTo(logsList.get(1).getLevel())
        );
    }




}
