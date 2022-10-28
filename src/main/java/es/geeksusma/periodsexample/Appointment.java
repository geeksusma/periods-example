package es.geeksusma.periodsexample;

import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.Objects;

@Builder
public class Appointment {

    private final Period period;

    public Date getStartDate() {
        return this.period.getStart();
    }


    public boolean hasConflict(final Appointment appointment) {
        return this.period.isOverlapped(appointment.period);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(period, that.period);
    }

    @Override
    public int hashCode() {
        return Objects.hash(period);
    }
}
