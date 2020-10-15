package com.spinthechoice.garbage;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.rangeClosed;

/**
 * All methods of defining when a holiday takes place.
 */
public enum HolidayType {
    /**
     * Holidays that take place on the same month and date each year.
     * For example, Christmas and Cinco de Mayo.
     */
    STATIC_DATE {
        @Override
        public Optional<LocalDate> toLocalDate(final Holiday holiday, final int year) {
            try {
                final LocalDate date = LocalDate.of(year, holiday.getMonth().getValue(), holiday.getDate());
                return Optional.of(holiday.getOffset().apply(date));
            } catch (DateTimeException e) {
                return Optional.empty();
            }
        }
    },
    /**
     * Holidays that take place on the nth occurrence of a day of a week during a month.
     * For example, Thanksgiving and Labor Day.
     */
    NTH_DAY_OF_WEEK {
        @Override
        public Optional<LocalDate> toLocalDate(final Holiday holiday, final int year) {
            final int daysInMonth = daysInMonth(holiday, year);
            final List<LocalDate> dates = rangeClosed(1, daysInMonth)
                    .boxed()
                    .map(dayOfMonth -> LocalDate.of(year, holiday.getMonth().getValue(), dayOfMonth))
                    .filter(date -> date.getDayOfWeek() == holiday.getDayOfWeek())
                    .collect(toList());
            final int index = holiday.getWeekIndex() < 0 ? holiday.getWeekIndex() + dates.size() : holiday.getWeekIndex();
            return index >= dates.size() ? Optional.empty() : Optional.of(holiday.getOffset().apply(dates.get(index)));
        }

        private int daysInMonth(final Holiday holiday, final int year) {
            final LocalDate firstOfMonth = LocalDate.of(year, holiday.getMonth().getValue(), 1);
            final boolean leapYear = firstOfMonth.isLeapYear();
            return holiday.getMonth().length(leapYear);
        }
    };

    /**
     * Converts the holiday to its date for the given year.
     * Because of date offsets, the date may be in the next or previous year.
     * @param holiday holiday definition
     * @param year requested year
     * @return date
     */
    public abstract Optional<LocalDate> toLocalDate(Holiday holiday, final int year);
}
