package com.andpostman.dependencymanager.model;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "event", schema = "project", indexes = @Index(name = "event_id_idx",columnList = "project, version"))
@Immutable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@IdClass(Event.EventId.class)
@Getter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "project, version")
public class Event{

    @Id
    private @NonNull String project;

    @Id
    private @NonNull String version;

    @Column(name = "time")
    private @NonNull LocalDateTime time;

    @Column(name = "state")
    private boolean state;


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @EqualsAndHashCode
    public static class EventId implements Serializable {

        @Id
        @Column(name = "project")
        private @NonNull String project;

        @Id
        @Column(name = "version")
        private @NonNull String version;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("project",project)
                .append("version",version)
                .append("time",time)
                .append("state",state)
                .toString();
    }

}
