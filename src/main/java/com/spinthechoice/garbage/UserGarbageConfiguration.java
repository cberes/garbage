package com.spinthechoice.garbage;

import java.time.DayOfWeek;

/**
 * Configuration for a single customer or premise.
 */
public final class UserGarbageConfiguration {
    private final DayOfWeek dayOfWeek;
    private final String garbageWeek;
    private final String recyclingWeek;

    /**
     * Creates a new configuration.
     * @param dayOfWeek the customer's day of week for collection
     * @param garbageWeek week name if garbage is not collected every week
     * @param recyclingWeek week name if recycling is not collected every week
     */
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
