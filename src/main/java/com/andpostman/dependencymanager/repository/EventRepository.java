package com.andpostman.dependencymanager.repository;

import com.andpostman.dependencymanager.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;

public interface EventRepository extends JpaRepository<Event, Event.EventId> {

    @Query(nativeQuery = true, value = "select exists(select * from project.event event where event.project = ?1 and event.version = ?2)")
    boolean existsById(String project, String version);

    @Query(nativeQuery = true, value = "select * from project.event event where event.project = ?1 and event.version = ?2")
    Event findEventById( String project, String version);

    @Modifying
    @Query(nativeQuery = true, value = "insert into project.event(project,version,time,state)" +
            " values (:project,:version,:time,:state)")
    void insertEvent (@Param("project") String project, @Param("version") String version, @Param("time") LocalDateTime time, @Param("state") boolean state);
}
