package com.spinthechoice.garbage;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.IntStream.range;

/**
 * Main garbage class.
 */
public class Garbage {
    private static final int DAYS_PER_WEEK = 7;

    private final GlobalGarbageConfiguration globalConfig;
    private final UserGarbageConfiguration userConfig;
    private final LocalDate resetOnOrBeforeStart;
    private final Set<LocalDate> holidays;
    private int holidayYear;
    private final Set<LocalDate> leapDays;
    private int leapDayYear;

    public Garbage(final GlobalGarbageConfiguration globalConfig, final UserGarbageConfiguration userConfig) {
        this.globalConfig = globalConfig;
        this.userConfig = userConfig;
        this.resetOnOrBeforeStart = getResetDayEqualOrBeforeStart(globalConfig);
        this.holidays = new HashSet<>();
        this.leapDays = new HashSet<>();
    }

    private static LocalDate getResetDayEqualOrBeforeStart(final GlobalGarbageConfiguration config) {
        LocalDate date = config.getStart();
        while (date.getDayOfWeek() != config.getResetDay()) {
            date = date.minusDays(1);
        }
        return date;
    }

    /**
     * Gets the summary of the customer's garbage collection for the specified date.
     * @param date date to inspect
     * @return garbage collection summary
     */
    public GarbageDay compute(final LocalDate date) {
        final int plusDays = isLeapForward(date) ? 1 : 0;
        final DayOfWeek userDayOfWeek = userConfig.getDayOfWeek().plus(plusDays);
        final boolean dayOfWeekMatch = !isHoliday(date) && date.getDayOfWeek() == userDayOfWeek;
        return new GarbageDay(date,
                globalConfig.isGarbageEnabled() && dayOfWeekMatch && isUsersGarbageWeek(date),
                globalConfig.isRecyclingEnabled() && dayOfWeekMatch && isUsersRecyclingWeek(date),
                dayOfWeekMatch && isUsersBulkWeek(date));
    }

    private boolean isHoliday(final LocalDate date) {
        return getAllHolidaysInSameYear(date).contains(date);
    }

    private Set<LocalDate> getAllHolidaysInSameYear(final LocalDate date) {
        if (holidayYear != date.getYear()) {
            holidays.clear();
            holidays.addAll(new Holidays(globalConfig.getHolidays()).dates(date.getYear()));
            holidays.addAll(new Holidays(globalConfig.getLeapDays()).dates(date.getYear()));
            holidayYear = date.getYear();
        }
        return holidays;
    }

    private boolean isLeapForward(final LocalDate date) {
        return getAllLeapDaysInSameYear(date).contains(date);
    }

    private Set<LocalDate> getAllLeapDaysInSameYear(final LocalDate date) {
        if (leapDayYear != date.getYear()) {
            leapDays.clear();
            leapDays.addAll(new Holidays(globalConfig.getLeapDays()).dates(date.getYear()).stream()
                    .flatMap(holiday -> daysUntilReset(holiday, globalConfig.getResetDay()))
                    .collect(toSet()));
            leapDayYear = date.getYear();
        }
        return leapDays;
    }

    private static Stream<LocalDate> daysUntilReset(final LocalDate holiday, final DayOfWeek reset) {
        // in JDK 9, could do this ...
        // return Stream.iterate(holiday, day -> day.getDayOfWeek() != reset, day -> day.plusDays(1)).skip(1);
        final DateResetIterable iterable = new DateResetIterable(holiday, reset);
        return iterable.stream().skip(1);
    }

    private boolean isUsersGarbageWeek(final LocalDate date) {
        return isUsersWeek(date, globalConfig.getGarbageWeeks(), userConfig.getGarbageWeek());
    }

    private boolean isUsersRecyclingWeek(final LocalDate date) {
        return isUsersWeek(date, globalConfig.getRecyclingWeeks(), userConfig.getRecyclingWeek());
    }

    private boolean isUsersWeek(final LocalDate date, final List<String> weeks, final String usersWeek) {
        return date.isBefore(globalConfig.getStart()) || weeks == null || weeks.size() <= 1 ||
                getWeek(date, weeks).equals(usersWeek);
    }

    private String getWeek(final LocalDate date, final List<String> weeks) {
        return weeks.get(getWeekIndex(date, weeks.size()));
    }

    private int getWeekIndex(final LocalDate date, final int weekCount) {
        final long daysSinceStart = DAYS.between(resetOnOrBeforeStart, date.plusDays(1));
        final double weeksSinceStart = daysSinceStart / (double) DAYS_PER_WEEK;
        return (int) Math.floor(weeksSinceStart) % weekCount;
    }

    private boolean isUsersBulkWeek(final LocalDate date) {
        final Set<LocalDate> bulkDays = globalConfig.getBulkDays();
        return bulkDays != null && !bulkDays.isEmpty() &&
                range(0, DAYS_PER_WEEK)
                        .mapToObj(date::minusDays)
                        .anyMatch(bulkDays::contains);
    }
}
