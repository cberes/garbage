package com.spinthechoice.garbage;

import java.time.LocalDate;
import java.util.*;

import static java.util.Arrays.asList;
import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.IntStream.rangeClosed;

/**
 * Calculates actual dates of holidays given collections of holiday definitions.
 */
public class Holidays {
    private final Set<Holiday> holidays;

    /**
     * Creates a new instance using the specified holidays.
     * @param holidays holidays
     */
    public Holidays(final Holiday... holidays) {
        this(new HashSet<>(asList(holidays)));
    }

    /**
     * Creates a new instance using the specified holidays.
     * @param holidays holidays
     */
    public Holidays(final Collection<Holiday> holidays) {
        this.holidays = holidays == null ? emptySet() : new HashSet<>(holidays);
    }

    /**
     * Gets dates of holidays during the given year.
     * @param year year
     * @return dates of holidays
     */
    public Set<LocalDate> dates(final int year) {
        // for day offsets to work for all days, this needs to be run for the next and previous years
        // in addition to the current year; then remove all days not within the given year
        return rangeClosed(year - 1, year + 1)
                .boxed()
                .flatMap(y -> holidays.stream().map(holiday -> holiday.getType().toLocalDate(holiday, y)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(date -> date.getYear() == year)
                .collect(toSet());
    }
}
