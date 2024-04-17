package org.geekhub.application.session.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class WorkSessionEntity {
    private Long id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Duration totalTime;

    public WorkSessionEntity(Long id, LocalDateTime startTime, LocalDateTime endTime, Duration totalTime) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalTime = totalTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Duration getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Duration totalTime) {
        this.totalTime = totalTime;
    }
}
