package org.geekhub.application.session;

import org.geekhub.application.exception.ValidationException;

import java.time.LocalTime;

public class WorkSessionValidation {

    private WorkSessionValidation() {
    }

    public static void timeValidation(byte hours, byte minutes) {
        if (hours >= 24 || minutes >= 60 || hours < 0 || minutes < 0) {
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
