package com.solera.periodsexample;

public interface Schedule {
    Boolean empty();

    void add(Appointment appointment);
}
