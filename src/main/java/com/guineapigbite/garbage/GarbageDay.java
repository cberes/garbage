package com.guineapigbite.garbage;

import java.time.LocalDate;

public final class GarbageDay {
    private final LocalDate date;
    private final boolean garbageDay;
    private final boolean recyclingDay;

    public GarbageDay(final LocalDate date, final boolean garbageDay, final boolean recyclingDay) {
        this.date = date;
        this.garbageDay = garbageDay;
        this.recyclingDay = recyclingDay;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isGarbageDay() {
        return garbageDay;
    }

    public boolean isRecyclingDay() {
        return recyclingDay;
    }
}
