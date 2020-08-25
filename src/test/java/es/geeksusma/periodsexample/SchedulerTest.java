package es.geeksusma.periodsexample;

import org.assertj.core.util.DateUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class SchedulerTest {

    private Schedule schedule;

    @Before
    public void setUp() {
        schedule = new ScheduleImpl();
    }

    @Test
    public void should_returnEmpty_when_noAppointments() {
        //when
        final Boolean isEmpty = schedule.empty();

        //then
        assertThat(isEmpty).isTrue();
    }

    @Test
    public void should_returnNotEmpty_when_appointmentIsAdded() {
        //given
        final Period period = new Period(DateUtil.now(), DateUtil.tomorrow());
        final Appointment appointment = Appointment.builder().period(period).build();

        //when
        schedule.add(appointment);

        //then
        assertThat(schedule.empty()).isFalse();
    }

    @Test
    public void should_thrownIllegal_when_noAppointmentToAdd() {
        //when
        final Throwable raisedException = catchThrowable(() -> schedule.add(null));

        //then
        assertThat(raisedException).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Appointment to add is mandatory");
    }

    @Test
    public void should_orderByStartDate_when_addingAppointments() {
        //given
        final Appointment appointmentFor28OfMarch = Appointment.builder()
                .period(new Period(DateUtil.parseDatetime("2020-03-28T03:01:02"), DateUtil.parseDatetime("2020-03-29T03:01:02")))
                .build();

        final Appointment appointmentFor29OfMarch = Appointment.builder()
                .period(new Period(DateUtil.parseDatetime("2020-03-29T03:01:02"), DateUtil.parseDatetime("2020-03-30T03:01:02")))
                .build();
        final List<Appointment> expectedAppointments = Arrays.asList(appointmentFor28OfMarch, appointmentFor29OfMarch);

        //when
        schedule.add(appointmentFor29OfMarch);
        schedule.add(appointmentFor28OfMarch);

        //then
        assertThat(schedule).extracting("appointments").isEqualTo(expectedAppointments);
    }

    @Test
    public void should_skipAppointment_if_conflict() {
        //given
        final Appointment appointmentFor28OfMarch = Appointment.builder()
                .period(new Period(DateUtil.parseDatetime("2020-03-28T03:01:02"), DateUtil.parseDatetime("2020-03-29T03:01:02")))
                .build();

        final List<Appointment> expectedAppointments = Collections.singletonList(appointmentFor28OfMarch);

        //when
        schedule.add(appointmentFor28OfMarch);
        schedule.add(appointmentFor28OfMarch);

        //then
        assertThat(schedule).extracting("appointments").isEqualTo(expectedAppointments);

    }
}
