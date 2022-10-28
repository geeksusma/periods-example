package es.geeksusma.periodsexample;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Date;
import java.util.Objects;

@Getter
public class Period {

    private final Date start;
    @Getter(AccessLevel.NONE)
    private final Date end;

    public Period(final Date start, final Date end) {
        assertIfPeriodIsClosed(start, end);
        this.start = start;
        this.end = end;
    }

    private void assertIfPeriodIsClosed(Date start, Date end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end dates are required");
        }
        if (end.compareTo(start) < 0) {
            throw new IllegalArgumentException("End date of the period must be greater than start date");
        }
    }


    public boolean isOverlapped(final Period period) {
        if (period == null) {
            throw new IllegalArgumentException("Period to check is mandatory");
        }
        return this.equals(period) || this.isBetween(period.start) || this.isBetween(period.end) || this.isSelfContainedInPeriod(period);
    }

    private boolean isSelfContainedInPeriod(Period period) {
        return period.start.before(this.start) && period.end.after(this.end);
    }

    private boolean isBetween(Date dateToCheck) {
        return this.start.before(dateToCheck) && this.end.after(dateToCheck);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Period period = (Period) o;
        return Objects.equals(start, period.start) && Objects.equals(end, period.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    static class PeriodBuilder {
        private Date start;
        private Date end;

        static PeriodBuilder builder() {
            return new PeriodBuilder();
        }

        PeriodBuilder start(Date start) {
            this.start = start;
            return this;
        }

        PeriodBuilder end(Date end) {
            this.end = end;
            return this;
        }

        Period build() {
            return new Period(start, end);
        }
    }
}
