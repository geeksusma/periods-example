package es.geeksusma.periodsexample;

public interface Schedule {
    Boolean empty();

    void add(Appointment appointment);
}
