package com.andpostman.dependencymanager.repository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.annotation.Transactional;
import com.andpostman.dependencymanager.dto.SubscriberDto;
import com.andpostman.dependencymanager.model.Subscriber;
import javax.persistence.EntityManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@SpringBootTest
@DisplayName("Операции с сохранением данных в БД в таблице subscribers")
class ValidatingSubscriberRepositoryTest {

    @Autowired
    private SubscriberRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Проверка занесения в базу данных пустого значения")
    void whenSubscriberIsInvalid_thenThrowsException(){
        assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            repository.insertSubscriber("Z",null,null,"G");
            entityManager.flush();
        },"When invalid params should be thrown an exception");
    }

    @Test
    @Transactional
    @DisplayName("Проверка создания подписчика")
    void whenFindSubscriber_thenReturnSubscriber(){
        SubscriberDto subscriber = new SubscriberDto("Z","B","v1");
        repository.insertSubscriber(subscriber.getProject(), subscriber.getTime(), subscriber.getMasterProject(), subscriber.getMasterVersion());
        Subscriber findSubscriber = repository.findSubscriberById(subscriber.getProject());

        assertThat(findSubscriber.getProject())
                .as("After adding subscriber in db he should be equals with subscriber which we getting from db ")
                .isEqualTo(subscriber.getProject());

    }

}
