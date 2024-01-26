package com.andpostman.dependencymanager.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.annotation.Transactional;
import com.andpostman.dependencymanager.dto.EventDto;
import com.andpostman.dependencymanager.model.Event;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@SpringBootTest
@DisplayName("Операции с сохранением данных в БД в таблице events")
class ValidatingEventRepositoryTest {

    @Autowired
    private EventRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Проверка занесения в базу данных пустого значения")
    void whenEventIsInvalid_thenThrowsException(){
        assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            repository.insertEvent(null,"v1.1", LocalDateTime.now(),true);
            entityManager.flush();
        },"When invalid params should be thrown an exception");
    }

    @Test
    @Transactional
    @DisplayName("Проверка создания event")
    void whenFindEvent_thenReturnEvent(){
        EventDto event = new EventDto("V", "v1", true);
        repository.insertEvent(event.getProject(),event.getVersion(),event.getTime(),event.isState());
        Event findEvent = repository.findEventById(event.getProject(),event.getVersion());

        assertThat(findEvent.getProject())
                .as("After adding event in db he should be equals with event which we getting from db ")
                .isEqualTo(event.getProject());

    }




}
