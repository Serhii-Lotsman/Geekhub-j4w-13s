package org.geekhub.application.session.model;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalTime;

public class WorkSessionEntity {

    private Long id;

    private String email;

    private LocalDate date;

    private LocalTime timeBegin;

    @Nullable
    private LocalTime timeEnd;

    @Nullable
    private LocalTime totalTime;

    public WorkSessionEntity() {
    }

    public WorkSessionEntity(
        @NonNull Long id,
        @NonNull String email,
        @NonNull LocalDate date,
        @NonNull LocalTime timeBegin,
        @Nullable LocalTime timeEnd,
        @Nullable LocalTime totalTime
    ) {
        this.id = id;
        this.email = email;
        this.date = date;
        this.timeBegin = timeBegin;
        this.timeEnd = timeEnd;
        this.totalTime = totalTime;
    }

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public LocalDate getDate() {
        return date;
    }

    public void setDate(@NonNull LocalDate date) {
        this.date = date;
    }

    @NonNull
    public LocalTime getTimeBegin() {
        return timeBegin;
    }

    public void setTimeBegin(@NonNull LocalTime timeBegin) {
        this.timeBegin = timeBegin;
    }

    @Nullable
    public LocalTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(@Nullable LocalTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    @Nullable
    public LocalTime getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(@Nullable LocalTime totalTime) {
        this.totalTime = totalTime;
    }
}
