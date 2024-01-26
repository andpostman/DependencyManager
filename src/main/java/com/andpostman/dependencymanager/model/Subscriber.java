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
@Table(name = "subscriber", schema = "project", indexes = @Index(name = "subscriber_id_idx", columnList = "project"))
@Immutable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Getter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "project")
public class Subscriber implements Serializable{

    @Id
    private @NonNull String project;

    @Column(name = "time")
    private @NonNull LocalDateTime time;

    @Column(name = "master")
    private @NonNull String masterProject;

    @Column(name = "version")
    private @NonNull String masterVersion;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("project",project)
                .append("time",time)
                .append("master",masterProject)
                .append("version",masterVersion)
                .toString();
    }
}