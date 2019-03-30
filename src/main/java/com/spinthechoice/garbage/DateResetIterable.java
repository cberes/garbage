package com.spinthechoice.garbage;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

class DateResetIterable implements Iterable<LocalDate> {
    private final DayOfWeek reset;
    private final LocalDate start;

    DateResetIterable(final LocalDate start, final DayOfWeek reset) {
        this.reset = reset;
        this.start = start;
    }

    @Override
    public Iterator<LocalDate> iterator() {
        return new DateResetIterator(start, reset);
    }

    Stream<LocalDate> stream() {
        return StreamSupport.stream(spliterator(), false);
    }
}
