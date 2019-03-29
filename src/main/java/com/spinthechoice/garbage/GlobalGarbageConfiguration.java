package com.spinthechoice.garbage;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public final class GlobalGarbageConfiguration {
    private final DayOfWeek resetDay;
    private final LocalDate start;
    private final List<String> garbageWeeks;
    private final List<String> recyclingWeeks;
    private final Set<LocalDate> leapDays;

    public GlobalGarbageConfiguration(final DayOfWeek resetDay,
                                      final LocalDate start,
                                      final List<String> garbageWeeks,
                                      final List<String> recyclingWeeks,
                                      final Set<LocalDate> leapDays) {
        this.resetDay = resetDay;
        this.start = start;
        this.garbageWeeks = garbageWeeks;
        this.recyclingWeeks = recyclingWeeks;
        this.leapDays = leapDays;
    }

    public DayOfWeek getResetDay() {
        return resetDay;
    }

    public LocalDate getStart() {
        return start;
    }

    public Set<LocalDate> getLeapDays() {
        return leapDays;
    }

    public List<String> getGarbageWeeks() {
        return garbageWeeks;
    }

    public List<String> getRecyclingWeeks() {
        return recyclingWeeks;
    }
}
