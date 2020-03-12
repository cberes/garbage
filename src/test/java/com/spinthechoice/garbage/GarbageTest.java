package com.spinthechoice.garbage;

import java.time.DayOfWeek;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import static java.util.Collections.emptySet;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class GarbageTest {
    private static final int DEFAULT_WEEK = 0;
    private static final int FIRST_WEEK = 0;
    private static final int SECOND_WEEK = 1;
    private static final int ALL_WEEKS = 2;

    @Test
    void testGarbageAndRecyclingOnLeapDay() {
        Garbage classUnderTest = new Garbage(globalConfig(),
                new UserGarbageConfiguration(DayOfWeek.THURSDAY, DEFAULT_WEEK, FIRST_WEEK));
        final GarbageDay result = classUnderTest.compute(LocalDate.parse("2019-05-31"));
        assertGarbage(result, "GR");
    }

    private static GlobalGarbageConfiguration globalConfig() {
        return globalConfig(DEFAULT_WEEK);
    }

    private static GlobalGarbageConfiguration globalConfig(final int garbageWeeks) {
        return GlobalGarbageConfiguration.builder()
                .setResetDay(DayOfWeek.SUNDAY)
                .setStart(LocalDate.parse("2019-05-01"))
                .setGarbageEnabled(true)
                .setGarbageWeeks(garbageWeeks)
                .setRecyclingEnabled(true)
                .setRecyclingWeeks(ALL_WEEKS)
                .setBulkDays(emptySet())
                .setLeapDays(
                        AmericanHolidays.memorialDay(),
                        AmericanHolidays.independenceDay(),
                        AmericanHolidays.laborDay(),
                        AmericanHolidays.thanksgiving(),
                        AmericanHolidays.christmas())
                .build();
    }

    private static void assertNoGarbage(final GarbageDay garbage) {
        assertGarbage(garbage, "");
    }

    private static void assertGarbage(final GarbageDay garbage, final String code) {
        assertThat(garbage.isGarbageDay(), is(code.contains("G")));
        assertThat(garbage.isRecyclingDay(), is(code.contains("R")));
        assertThat(garbage.isBulkDay(), is(code.contains("B")));
    }

    @Test
    void testOffOnLeapDay() {
        Garbage classUnderTest = new Garbage(globalConfig(),
                new UserGarbageConfiguration(DayOfWeek.THURSDAY, DEFAULT_WEEK, FIRST_WEEK));
        final GarbageDay result = classUnderTest.compute(LocalDate.parse("2019-05-30"));
        assertNoGarbage(result);
    }

    @Test
    void testOffOnHoliday() {
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            Garbage classUnderTest = new Garbage(globalConfig(),
                    new UserGarbageConfiguration(dayOfWeek, DEFAULT_WEEK, FIRST_WEEK));
            final GarbageDay result = classUnderTest.compute(LocalDate.parse("2019-05-27"));
            assertNoGarbage(result);
        }
    }

    @Test
    void testGarbageAndRecyclingOnNormalDay() {
        Garbage classUnderTest = new Garbage(globalConfig(),
                new UserGarbageConfiguration(DayOfWeek.THURSDAY, DEFAULT_WEEK, FIRST_WEEK));
        final GarbageDay result = classUnderTest.compute(LocalDate.parse("2019-05-16"));
        assertGarbage(result, "GR");
    }

    @Test
    void testGarbageOnlyOnNormalDay() {
        Garbage classUnderTest = new Garbage(globalConfig(),
                new UserGarbageConfiguration(DayOfWeek.THURSDAY, DEFAULT_WEEK, FIRST_WEEK));
        final GarbageDay result = classUnderTest.compute(LocalDate.parse("2019-05-23"));
        assertGarbage(result, "G");
    }

    @Test
    void testDateInResult() {
        final LocalDate date = LocalDate.parse("2019-05-23");
        Garbage classUnderTest = new Garbage(globalConfig(),
                new UserGarbageConfiguration(DayOfWeek.THURSDAY, DEFAULT_WEEK, FIRST_WEEK));
        final GarbageDay result = classUnderTest.compute(date);
        assertThat(result.getDate(), is(date));
    }

    @Test
    void testSingleWeek() {
        Garbage classUnderTest = new Garbage(globalConfig(0),
                new UserGarbageConfiguration(DayOfWeek.THURSDAY, DEFAULT_WEEK, FIRST_WEEK));
        for (LocalDate date : new LocalDate[] { LocalDate.parse("2019-05-16"), LocalDate.parse("2019-05-23") }) {
            final GarbageDay result = classUnderTest.compute(date);
            assertThat(result.isGarbageDay(), is(true));
        }
    }

    @Test
    void testOffOnNormalDay() {
        Garbage classUnderTest = new Garbage(globalConfig(),
                new UserGarbageConfiguration(DayOfWeek.THURSDAY, DEFAULT_WEEK, FIRST_WEEK));
        final GarbageDay result = classUnderTest.compute(LocalDate.parse("2019-05-22"));
        assertNoGarbage(result);
    }

    @Test
    void testResetAfterLeapDay() {
        Garbage classUnderTest = new Garbage(globalConfig(),
                new UserGarbageConfiguration(DayOfWeek.SUNDAY, DEFAULT_WEEK, SECOND_WEEK));
        final GarbageDay result = classUnderTest.compute(LocalDate.parse("2019-06-02"));
        assertGarbage(result, "GR");
    }

    @Test
    void testBeforeStartDay() {
        for (int week = 0; week < ALL_WEEKS; ++week) {
            Garbage classUnderTest = new Garbage(globalConfig(),
                    new UserGarbageConfiguration(DayOfWeek.TUESDAY, DEFAULT_WEEK, week));
            final GarbageDay result = classUnderTest.compute(LocalDate.parse("2019-04-30"));
            assertGarbage(result, "GR");
        }
    }

    @Test
    void testOffWhenAlternatingGarbage() {
        Garbage classUnderTest = new Garbage(globalConfig(ALL_WEEKS),
                new UserGarbageConfiguration(DayOfWeek.THURSDAY, FIRST_WEEK, FIRST_WEEK));
        final GarbageDay result = classUnderTest.compute(LocalDate.parse("2019-05-23"));
        assertNoGarbage(result);
    }

    @Test
    void testOnWhenAlternatingGarbage() {
        Garbage classUnderTest = new Garbage(globalConfig(ALL_WEEKS),
                new UserGarbageConfiguration(DayOfWeek.THURSDAY, SECOND_WEEK, SECOND_WEEK));
        final GarbageDay result = classUnderTest.compute(LocalDate.parse("2019-05-23"));
        assertGarbage(result, "GR");
    }

    @Test
    void testOnWhenHolidayWithNoLeap() {
        final GlobalGarbageConfiguration globalConfig = GlobalGarbageConfiguration.builder()
                .setResetDay(DayOfWeek.SUNDAY)
                .setStart(LocalDate.parse("2019-01-01"))
                .setGarbageEnabled(true)
                .setRecyclingEnabled(true)
                .setHolidays(AmericanHolidays.christmas(), AmericanHolidays.newYears())
                .build();
        Garbage classUnderTest = new Garbage(globalConfig,
                new UserGarbageConfiguration(DayOfWeek.THURSDAY, DEFAULT_WEEK, DEFAULT_WEEK));
        final GarbageDay result = classUnderTest.compute(LocalDate.parse("2019-12-26"));
        assertGarbage(result, "GR");
    }

    @Test
    void testOffWhenHolidayWithNoLeap() {
        final GlobalGarbageConfiguration globalConfig = GlobalGarbageConfiguration.builder()
                .setResetDay(DayOfWeek.SUNDAY)
                .setStart(LocalDate.parse("2019-01-01"))
                .setGarbageEnabled(true)
                .setRecyclingEnabled(true)
                .setHolidays(AmericanHolidays.christmas(), AmericanHolidays.newYears())
                .build();
        Garbage classUnderTest = new Garbage(globalConfig,
                new UserGarbageConfiguration(DayOfWeek.WEDNESDAY, DEFAULT_WEEK, DEFAULT_WEEK));
        final GarbageDay result = classUnderTest.compute(LocalDate.parse("2019-12-25"));
        assertNoGarbage(result);
    }

    @Test
    void testConfigWeekOverrides() {
        final GlobalGarbageConfiguration globalConfig = GlobalGarbageConfiguration.builder()
                .setResetDay(DayOfWeek.SUNDAY)
                .setStart(LocalDate.parse("2019-05-01"))
                .setGarbageEnabled(true)
                .setRecyclingEnabled(true)
                .setGarbageWeeks(ALL_WEEKS)
                .setRecyclingWeeks(ALL_WEEKS)
                .build();
        Garbage classUnderTest = new Garbage(globalConfig,
                new UserGarbageConfiguration(DayOfWeek.TUESDAY, FIRST_WEEK, SECOND_WEEK));
        final GarbageDay result = classUnderTest.compute(LocalDate.parse("2019-05-21"));
        assertGarbage(result, "R");
    }

    @Test
    void testGarbageDisabled() {
        final GlobalGarbageConfiguration globalConfig = GlobalGarbageConfiguration.builder()
                .setResetDay(DayOfWeek.SUNDAY)
                .setStart(LocalDate.parse("2019-01-01"))
                .setRecyclingEnabled(true)
                .build();
        Garbage classUnderTest = new Garbage(globalConfig,
                new UserGarbageConfiguration(DayOfWeek.THURSDAY, DEFAULT_WEEK, DEFAULT_WEEK));
        final GarbageDay result = classUnderTest.compute(LocalDate.parse("2019-05-16"));
        assertGarbage(result, "R");
    }

    @Test
    void testRecyclingDisabled() {
        final GlobalGarbageConfiguration globalConfig = GlobalGarbageConfiguration.builder()
                .setResetDay(DayOfWeek.SUNDAY)
                .setStart(LocalDate.parse("2019-01-01"))
                .setGarbageEnabled(true)
                .build();
        Garbage classUnderTest = new Garbage(globalConfig,
                new UserGarbageConfiguration(DayOfWeek.THURSDAY, DEFAULT_WEEK, DEFAULT_WEEK));
        final GarbageDay result = classUnderTest.compute(LocalDate.parse("2019-05-16"));
        assertGarbage(result, "G");
    }

    @Test
    void testBulkOnSpecifiedDay() {
        final GlobalGarbageConfiguration globalConfig = GlobalGarbageConfiguration.builder()
                .setResetDay(DayOfWeek.SUNDAY)
                .setStart(LocalDate.parse("2019-01-01"))
                .setBulkDays(LocalDate.parse("2019-04-08"), LocalDate.parse("2019-09-16"))
                .build();
        Garbage classUnderTest = new Garbage(globalConfig,
                new UserGarbageConfiguration(DayOfWeek.MONDAY, DEFAULT_WEEK, DEFAULT_WEEK));
        assertNoGarbage(classUnderTest.compute(LocalDate.parse("2019-04-01")));
        assertGarbage(classUnderTest.compute(LocalDate.parse("2019-04-08")), "B");
        assertNoGarbage(classUnderTest.compute(LocalDate.parse("2019-04-15")));
    }

    @Test
    void testBulkOnPreviousDay() {
        final GlobalGarbageConfiguration globalConfig = GlobalGarbageConfiguration.builder()
                .setResetDay(DayOfWeek.SUNDAY)
                .setStart(LocalDate.parse("2019-01-01"))
                .setBulkDays(LocalDate.parse("2019-04-08"), LocalDate.parse("2019-09-16"))
                .build();
        Garbage classUnderTest = new Garbage(globalConfig,
                new UserGarbageConfiguration(DayOfWeek.SUNDAY, DEFAULT_WEEK, DEFAULT_WEEK));
        assertNoGarbage(classUnderTest.compute(LocalDate.parse("2019-04-07")));
        assertGarbage(classUnderTest.compute(LocalDate.parse("2019-04-14")), "B");
        assertNoGarbage(classUnderTest.compute(LocalDate.parse("2019-04-21")));
    }
}
