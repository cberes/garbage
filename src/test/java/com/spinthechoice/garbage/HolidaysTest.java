package com.spinthechoice.garbage;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class HolidaysTest {
    @Test
    void testMostCommonHolidays() {
        final Holidays holidays = new Holidays(
                AmericanHolidays.newYears(),
                AmericanHolidays.memorialDay(),
                AmericanHolidays.independenceDay(),
                AmericanHolidays.laborDay(),
                AmericanHolidays.thanksgiving(),
                AmericanHolidays.christmas());

        final Set<LocalDate> dates = holidays.dates(2019);
        assertThat(dates, hasSize(6));
        assertThat(dates, containsInAnyOrder(
                LocalDate.parse("2019-01-01"),
                LocalDate.parse("2019-05-27"),
                LocalDate.parse("2019-07-04"),
                LocalDate.parse("2019-09-02"),
                LocalDate.parse("2019-11-28"),
                LocalDate.parse("2019-12-25")));
    }

    @Test
    void testDayBeforeNextYear() {
        final Holidays holidays = new Holidays(AmericanHolidays.newYearsEve());

        final Set<LocalDate> dates = holidays.dates(2019);
        assertThat(dates, hasSize(1));
        assertThat(dates, containsInAnyOrder(LocalDate.parse("2019-12-31")));
    }

    @Test
    void testDayAfterPreviousYear() {
        final Holidays holidays = new Holidays(Holiday.builder()
                .setType(HolidayType.STATIC_DATE)
                .setMonth(Month.DECEMBER)
                .setDate(31)
                .setOffset(HolidayOffset.DAY_AFTER)
                .build());

        final Set<LocalDate> dates = holidays.dates(2019);
        assertThat(dates, hasSize(1));
        assertThat(dates, containsInAnyOrder(LocalDate.parse("2019-01-01")));
    }

    @Test
    void testDayAfterThanksgiving() {
        final Holidays holidays = new Holidays(AmericanHolidays.thanksgivingDayAfter());

        final Set<LocalDate> dates = holidays.dates(2019);
        assertThat(dates, hasSize(1));
        assertThat(dates, containsInAnyOrder(LocalDate.parse("2019-11-29")));
    }

    @Test
    void testNonexistentHoliday() {
        final Holidays holidays = new Holidays(Holiday.builder()
                .setType(HolidayType.NTH_DAY_OF_WEEK)
                .setMonth(Month.FEBRUARY)
                .setDayOfWeek(DayOfWeek.SUNDAY)
                .setWeekIndex(4)
                .build());

        final Set<LocalDate> dates = holidays.dates(2020);
        assertThat(dates, hasSize(0));
    }

    @Test
    void testNoHolidays() {
        final Holidays holidays = new Holidays();
        final Set<LocalDate> dates = holidays.dates(2020);
        assertThat(dates, hasSize(0));
    }
}
