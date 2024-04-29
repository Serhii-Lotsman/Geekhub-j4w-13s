package org.geekhub.application.validation;

import org.geekhub.application.exception.ValidationException;

import java.time.LocalTime;

public class WorkSessionValidation {

    public static final int HOURS = 24;
    public static final int MINUTES = 60;
    public static final int ZERO = 0;

    private WorkSessionValidation() {
    }

    public static void timeValidation(byte hours, byte minutes) {
        if (hours >= HOURS || minutes >= MINUTES || hours < ZERO || minutes < ZERO) {
            throw new ValidationException(
                "Hours and minutes cannot be negative or exceed 23 and 59, respectively"
            );
        }
    }

    public static void sequenceOfTimeValidation(LocalTime startTime, LocalTime endTime) {
        if (startTime.isAfter(endTime)) {
            throw new ValidationException(
                "Start time cannot be before end time. Start time is " + startTime
            );
        }
    }
}
