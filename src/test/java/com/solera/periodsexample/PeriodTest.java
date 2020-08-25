package com.solera.periodsexample;

import org.assertj.core.api.Assertions;
import org.assertj.core.util.DateUtil;
import org.junit.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class PeriodTest {

    @Test
    public void should_thrownIllegal_when_periodIsBuiltButStartDateIsAfterThanEndDate() {
        //given
        final Date startDate = DateUtil.tomorrow();
        final Date endDate = DateUtil.now();

        //when
        final Throwable raisedException = Assertions.catchThrowable(() -> new Period(startDate, endDate));

        //then
        assertThat(raisedException).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("End date of the period must be greater than start date");
    }

    @Test
    public void should_buildPeriod_when_startDateIsBeforeThanEndDate() {
        //given
        final Date startDate = DateUtil.now();
        final Date endDate = DateUtil.tomorrow();
        final Period periodExpected = Period.builder()
                .start(startDate)
                .end(endDate)
                .build();

        //when
        final Period period = new Period(startDate, endDate);

        //then
        assertThat(period).isEqualTo(periodExpected);

    }

    @Test
    public void should_buildPeriod_when_startAndEndAreTheSame() {
        //given
        final Date now = DateUtil.now();
        final Period expected = Period.builder().start(now).end(now).build();

        //when
        final Period period = new Period(now, now);

        //then
        assertThat(period).isEqualTo(expected);
    }

    @Test
    public void should_throwIllegal_when_periodIsOpen() {

        //when
        final Throwable periodNotEndedException = Assertions.catchThrowable(() -> new Period(DateUtil.now(), null));
        final Throwable periodNotStartedException = Assertions.catchThrowable(() -> new Period(null, DateUtil.now()));

        //then
        assertThat(periodNotEndedException).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Start and end dates are required");
        assertThat(periodNotStartedException).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Start and end dates are required");
    }


    @Test
    public void should_returnFalse_when_periodDoesNotOverlaps() {
        //given
        final Period aprilPeriod = Period.builder()
                .start(DateUtil.parseDatetime("2020-04-26T03:01:02"))
                .end(DateUtil.parseDatetime("2020-04-29T03:01:02"))
                .build();
        final Period marchPeriod = Period.builder()
                .start(DateUtil.parseDatetime("2020-03-26T03:01:02"))
                .end(DateUtil.parseDatetime("2020-03-27T03:01:02"))
                .build();

        final Period startAtEndOfPeriod = Period.builder()
                .start(DateUtil.parseDatetime("2020-04-29T03:01:02"))
                .end(DateUtil.parseDatetime("2020-05-01T03:01:02"))
                .build();

        final Period endsAtTheBeginningOfPeriod = Period.builder()
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
        final Period from26ofAprilTo29 = Period.builder()
                .start(DateUtil.parseDatetime("2020-04-26T03:01:02"))
                .end(DateUtil.parseDatetime("2020-04-29T03:01:02"))
                .build();

        final Period from25ofAprilTo28 = Period.builder()
                .start(DateUtil.parseDatetime("2020-04-25T03:01:02"))
                .end(DateUtil.parseDatetime("2020-04-28T03:01:02"))
                .build();

        final Period from28ofAprilTo30 = Period.builder()
                .start(DateUtil.parseDatetime("2020-03-28T03:01:02"))
                .end(DateUtil.parseDatetime("2020-04-30T03:01:02"))
                .build();

        final Period from25ofAprilTo30 = Period.builder()
                .start(DateUtil.parseDatetime("2020-03-25T03:01:02"))
                .end(DateUtil.parseDatetime("2020-04-30T03:01:02"))
                .build();

        final Period from27ofAprilTo28 = Period.builder()
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
}
