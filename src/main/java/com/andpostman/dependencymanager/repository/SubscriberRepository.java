package com.andpostman.dependencymanager.repository;

import com.andpostman.dependencymanager.model.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SubscriberRepository extends JpaRepository<Subscriber, String> {

    @Query(nativeQuery = true, value = "select * from project.subscriber subscriber where subscriber.master = ?1 and subscriber.version = ?2")
    List<Subscriber> findAllSubscribersByMasterProjectAndMasterVersion(String masterProject, String masterVersion);

    @Query(nativeQuery = true, value = "select * from project.subscriber subscriber where subscriber.project = ?1")
    Subscriber findSubscriberById(String project);

    @Query(nativeQuery = true, value = "select exists(select * from project.subscriber subscriber where subscriber.project = ?1)")
    boolean existsById(String project);

    @Modifying
    @Query(nativeQuery = true, value = "insert into project.subscriber(project,time,master,version)" +
            " values (:project,:time,:master,:version)")
    void insertSubscriber(@Param("project") String project, @Param("time") LocalDateTime time, @Param("master") String master,@Param("version") String version);
}
