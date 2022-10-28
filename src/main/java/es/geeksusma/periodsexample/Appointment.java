package es.geeksusma.periodsexample;

import java.util.Date;
import java.util.Objects;

public class Appointment {

    private final Period period;

    private Appointment(Period period) {
        this.period = period;
    }

    public Date getStartDate() {
        return this.period.startAt();
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

    static class AppointmentBuilder {

        private Period period;

        static AppointmentBuilder builder() {
            return new AppointmentBuilder();
        }

        AppointmentBuilder period(Period period) {
            this.period = period;
            return this;
        }

        Appointment build() {
            return new Appointment(this.period);
        }
    }
}
