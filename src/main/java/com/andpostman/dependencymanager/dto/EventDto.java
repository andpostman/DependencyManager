package com.andpostman.dependencymanager.dto;

import lombok.Data;
import lombok.NonNull;
import java.time.LocalDateTime;

@Data
public class EventDto {

    private @NonNull String project;

    private @NonNull String version;

    private @NonNull LocalDateTime time;

    private boolean state;

    public EventDto(@NonNull String project, @NonNull String version, boolean state) {
        this.project = project;
        this.version = version;
        this.time = LocalDateTime.now();
        this.state = state;
    }
}
