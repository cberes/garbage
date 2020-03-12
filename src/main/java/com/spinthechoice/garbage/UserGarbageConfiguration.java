package com.spinthechoice.garbage;

import java.time.DayOfWeek;

/**
 * Configuration for a single customer or premise.
 */
public final class UserGarbageConfiguration {
    private final DayOfWeek dayOfWeek;
    private final int garbageWeek;
    private final int recyclingWeek;

    /**
     * Creates a new configuration.
     * @param dayOfWeek the customer's day of week for collection
     * @param garbageWeek 0-based index of week of garbage collection (not used if collected every week)
     * @param recyclingWeek 0-based index of week of recycling collection (not used if collected every week)
     */
    public UserGarbageConfiguration(final DayOfWeek dayOfWeek, final int garbageWeek, final int recyclingWeek) {
        this.dayOfWeek = dayOfWeek;
        this.garbageWeek = garbageWeek;
        this.recyclingWeek = recyclingWeek;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public int getGarbageWeek() {
        return garbageWeek;
    }

    public int getRecyclingWeek() {
        return recyclingWeek;
    }
}
