package com.guineapigbite.garbage;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

import static java.util.Collections.emptySet;

public final class GlobalGarbageConfiguration {
    private final DayOfWeek resetDay;
    private final LocalDate start;
    private final Set<String> garbageWeeks;
    private final Set<String> recyclingWeeks;
    private final Set<LocalDate> leapDays;

    public GlobalGarbageConfiguration(final DayOfWeek resetDay,
                                      final LocalDate start,
                                      final Set<String> garbageWeeks,
                                      final Set<String> recyclingWeeks, final Set<LocalDate> leapDays) {
        this.resetDay = resetDay;
        this.start = start;
        this.garbageWeeks = garbageWeeks == null || garbageWeeks.size() <= 1 ? emptySet() : garbageWeeks;
        this.recyclingWeeks = recyclingWeeks == null || recyclingWeeks.size() <= 1 ? emptySet() : recyclingWeeks;
        this.leapDays = leapDays == null ? emptySet() : leapDays;
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

    public Set<String> getGarbageWeeks() {
        return garbageWeeks;
    }

    public Set<String> getRecyclingWeeks() {
        return recyclingWeeks;
    }
}
