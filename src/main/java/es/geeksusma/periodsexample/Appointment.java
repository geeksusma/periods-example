package es.geeksusma.periodsexample;

import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode
@Builder
public class Appointment {

    private final Period period;

    public Date getStartDate() {
        return this.period.getStart();
    }


    public boolean hasConflict(final Appointment appointment) {
        return this.period.isOverlapped(appointment.period);
    }
}
