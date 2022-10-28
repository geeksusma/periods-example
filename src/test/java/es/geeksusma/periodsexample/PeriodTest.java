package es.geeksusma.periodsexample;

import org.assertj.core.util.DateUtil;
import org.junit.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class PeriodTest {

    @Test
    public void should_thrownIllegal_when_periodIsBuiltButStartDateIsAfterThanEndDate() {
        //given
        final Date startDate = DateUtil.tomorrow();
        final Date endDate = DateUtil.now();

        //when
        final Throwable raisedException = catchThrowable(() -> Period.PeriodBuilder.builder().start(startDate).end(endDate).build());

        //then
        assertThat(raisedException).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("End date of the period must be greater than start date");
    }

    @Test
    public void should_buildPeriod_when_startDateIsBeforeThanEndDate() {
        //given
        final Date startDate = DateUtil.now();
        final Date endDate = DateUtil.tomorrow();
        final Period periodExpected = Period.PeriodBuilder.builder()
                .start(startDate)
                .end(endDate)
                .build();

        //when
        final Period period = Period.PeriodBuilder.builder().start(startDate).end(endDate).build();

        //then
        assertThat(period).isEqualTo(periodExpected);

    }

    @Test
    public void should_buildPeriod_when_startAndEndAreTheSame() {
        //given
        final Date now = DateUtil.now();
        final Period expected = Period.PeriodBuilder.builder().start(now).end(now).build();

        //when
        final Period period = Period.PeriodBuilder.builder().start(now).end(now).build();

        //then
        assertThat(period).isEqualTo(expected);
    }

    @Test
    public void should_throwIllegal_when_periodIsOpen() {

        //when
        final Throwable periodNotEndedException = catchThrowable(() -> Period.PeriodBuilder.builder().start(DateUtil.now()).build());
        final Throwable periodNotStartedException = catchThrowable(() -> Period.PeriodBuilder.builder().end(DateUtil.now()).build());

        //then
        assertThat(periodNotEndedException).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Start and end dates are required");
        assertThat(periodNotStartedException).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Start and end dates are required");
    }


    @Test
    public void should_returnFalse_when_periodDoesNotOverlaps() {
        //given
        final Period aprilPeriod = Period.PeriodBuilder.builder()
                .start(DateUtil.parseDatetime("2020-04-26T03:01:02"))
                .end(DateUtil.parseDatetime("2020-04-29T03:01:02"))
                .build();
        final Period marchPeriod = Period.PeriodBuilder.builder()
                .start(DateUtil.parseDatetime("2020-03-26T03:01:02"))
                .end(DateUtil.parseDatetime("2020-03-27T03:01:02"))
                .build();

        final Period startAtEndOfPeriod = Period.PeriodBuilder.builder()
                .start(DateUtil.parseDatetime("2020-04-29T03:01:02"))
                .end(DateUtil.parseDatetime("2020-05-01T03:01:02"))
                .build();

        final Period endsAtTheBeginningOfPeriod = Period.PeriodBuilder.builder()
                .start(DateUtil.parseDatetime("2020-02-29T03:01:02"))
                .end(DateUtil.parseDatetime("2020-04-26T03:01:02"))
                .build();

        //when then

        assertThat(aprilPeriod.isOverlapped(marchPeriod)).isFalse();
        assertThat(aprilPeriod.isOverlapped(startAtEndOfPeriod)).isFalse();
        assertThat(aprilPeriod.isOverlapped(endsAtTheBeginningOfPeriod)).isFalse();
    }

    @Test
    public void should_returnOverlapped_when_periodCollides() {
        //given
        final Period from26ofAprilTo29 = Period.PeriodBuilder.builder()
                .start(DateUtil.parseDatetime("2020-04-26T03:01:02"))
                .end(DateUtil.parseDatetime("2020-04-29T03:01:02"))
                .build();

        final Period from25ofAprilTo28 = Period.PeriodBuilder.builder()
                .start(DateUtil.parseDatetime("2020-04-25T03:01:02"))
                .end(DateUtil.parseDatetime("2020-04-28T03:01:02"))
                .build();

        final Period from28ofAprilTo30 = Period.PeriodBuilder.builder()
                .start(DateUtil.parseDatetime("2020-04-28T03:01:02"))
                .end(DateUtil.parseDatetime("2020-04-30T03:01:02"))
                .build();

        final Period from25ofAprilTo30 = Period.PeriodBuilder.builder()
                .start(DateUtil.parseDatetime("2020-03-25T03:01:02"))
                .end(DateUtil.parseDatetime("2020-04-30T03:01:02"))
                .build();

        final Period from27ofAprilTo28 = Period.PeriodBuilder.builder()
                .start(DateUtil.parseDatetime("2020-03-27T03:01:02"))
                .end(DateUtil.parseDatetime("2020-04-28T03:01:02"))
                .build();

        //when then
        assertThat(from26ofAprilTo29.isOverlapped(from25ofAprilTo28)).isTrue();
        assertThat(from26ofAprilTo29.isOverlapped(from28ofAprilTo30)).isTrue();
        assertThat(from26ofAprilTo29.isOverlapped(from25ofAprilTo30)).isTrue();
        assertThat(from26ofAprilTo29.isOverlapped(from26ofAprilTo29)).isTrue();
        assertThat(from26ofAprilTo29.isOverlapped(from27ofAprilTo28)).isTrue();

    }

    @Test
    public void should_throwIllegal_when_notingToCheckIfOverlaps() {
        //given
        final Period periodToCheck = Period.PeriodBuilder.builder().start(DateUtil.now()).end(DateUtil.tomorrow()).build();

        //when
        final Throwable raisedException = catchThrowable(() -> periodToCheck.isOverlapped(null));

        //then
        assertThat(raisedException).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Period to check is mandatory");
    }
}
