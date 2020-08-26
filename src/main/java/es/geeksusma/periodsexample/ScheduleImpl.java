package es.geeksusma.periodsexample;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ScheduleImpl implements Schedule {
    private List<Appointment> appointments = new LinkedList<>();

    @Override
    public Boolean empty() {
        return appointments.isEmpty();
    }

    //o(n) + o(n) = o(n)
    @Override
    public void add(Appointment appointment) {
        assertAppointment(appointment);
        final Optional<Appointment> conflictedAppointment = getFirstConflictedAppointment(appointment);
        if (conflictedAppointment.isEmpty()) {
            addSorted(appointment);
        }
    }

    private void addSorted(Appointment appointment) {
        appointments.add(appointment);
        appointments.sort(Comparator.comparing(Appointment::getStartDate));
    }

    private Optional<Appointment> getFirstConflictedAppointment(Appointment appointment) {
        return appointments.stream().filter(a -> a.hasConflict(appointment)).findFirst();
    }

    private void assertAppointment(Appointment appointment) {
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment to add is mandatory");
        }
    }
}
