package es.geeksusma.periodsexample;

import lombok.*;

import java.util.Date;

@Getter
@ToString
@EqualsAndHashCode
@Builder(access = AccessLevel.PACKAGE)
public class Period {

    private Date start;
    private Date end;

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

}
