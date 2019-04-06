package com.spinthechoice.garbage;

import java.time.LocalDate;

/**
 * Summary of a single day of garbage collection.
 */
public final class GarbageDay {
    private final LocalDate date;
    private final boolean garbageDay;
    private final boolean recyclingDay;
    private final boolean bulkDay;

    public GarbageDay(final LocalDate date, final boolean garbageDay, final boolean recyclingDay, final boolean bulkDay) {
        this.date = date;
        this.garbageDay = garbageDay;
        this.recyclingDay = recyclingDay;
        this.bulkDay = bulkDay;
    }

    /**
     * Returns the day in question.
     * @return the day in question
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns whether garbage will be collected this day for the customer.
     * @return {@code true} if garbage will be collected; {@code false} otherwise
     */
    public boolean isGarbageDay() {
        return garbageDay;
    }

    /**
     * Returns whether bulk garbage will be collected this day for the customer.
     * @return {@code true} if bulk garbage will be collected; {@code false} otherwise
     */
    public boolean isBulkDay() {
        return bulkDay;
    }

    /**
     * Returns whether recycling will be collected this day for the customer.
     * @return {@code true} if recycling will be collected; {@code false} otherwise
     */
    public boolean isRecyclingDay() {
        return recyclingDay;
    }
}
