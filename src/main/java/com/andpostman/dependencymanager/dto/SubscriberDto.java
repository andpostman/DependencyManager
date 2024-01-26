package com.andpostman.dependencymanager.dto;

import lombok.Data;
import lombok.NonNull;
import java.time.LocalDateTime;

@Data
public class SubscriberDto {

    private @NonNull String project;

    private @NonNull LocalDateTime time;

    private @NonNull String masterProject;

    private @NonNull String masterVersion;

    public SubscriberDto(@NonNull String project, @NonNull String masterProject, @NonNull String masterVersion) {
        this.project = project;
        this.time = LocalDateTime.now();
        this.masterProject = masterProject;
        this.masterVersion = masterVersion;
    }
}
