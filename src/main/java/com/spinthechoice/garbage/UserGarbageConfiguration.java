package com.spinthechoice.garbage;

import java.time.DayOfWeek;

public final class UserGarbageConfiguration {
    private final DayOfWeek dayOfWeek;
    private final String garbageWeek;
    private final String recyclingWeek;

    public UserGarbageConfiguration(final DayOfWeek dayOfWeek, final String garbageWeek, final String recyclingWeek) {
        this.dayOfWeek = dayOfWeek;
        this.garbageWeek = garbageWeek;
        this.recyclingWeek = recyclingWeek;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public String getGarbageWeek() {
        return garbageWeek;
    }

    public String getRecyclingWeek() {
        return recyclingWeek;
    }
}
