package com.spinthechoice.garbage;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Iterator;

class DateResetIterator implements Iterator<LocalDate> {
    private final DayOfWeek reset;
    private LocalDate next;

    DateResetIterator(final LocalDate start, final DayOfWeek reset) {
        this.reset = reset;
        this.next = start;
    }

    @Override
    public boolean hasNext() {
        return next.getDayOfWeek() != reset;
    }

    @Override
    public LocalDate next() {
        final LocalDate date = next;
        next = next.plusDays(1);
        return date;
    }
}
