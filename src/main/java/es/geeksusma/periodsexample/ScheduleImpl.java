package es.geeksusma.periodsexample;

import java.util.*;

public class ScheduleImpl implements Schedule {
    private List<Appointment> appointments = new LinkedList<>();

    @Override
    public Boolean empty() {
        return appointments.isEmpty();
    }

    //o(n) + o(n) = o(n)
    @Override
    public void add(Appointment appointment) {
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment to add is mandatory");
        }
        final Optional<Appointment> conflictedAppointment = appointments.stream().filter(a -> a.hasConflict(appointment)).findFirst();
        if (conflictedAppointment.isEmpty()) {
            appointments.add(appointment);
            Collections.sort(appointments, Comparator.comparing(Appointment::getStartDate));
        }
    }
}
