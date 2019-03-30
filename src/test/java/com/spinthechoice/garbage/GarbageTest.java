package com.spinthechoice.garbage;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class GarbageTest {
    private static final String FIRST_WEEK = "A";
    private static final String SECOND_WEEK = "B";
    private static final List<String> ALL_WEEKS = List.of(FIRST_WEEK, SECOND_WEEK);

    @Test
    void testGarbageAndRecyclingOnLeapDay() {
        Garbage classUnderTest = new Garbage(globalConfig(),
                new UserGarbageConfiguration(DayOfWeek.THURSDAY, null, FIRST_WEEK));
        final GarbageDay result = classUnderTest.compute(LocalDate.parse("2019-05-31"));
        assertThat(result.isGarbageDay(), is(true));
        assertThat(result.isRecyclingDay(), is(true));
    }

    private static GlobalGarbageConfiguration globalConfig() {
        return globalConfig(null);
    }

    private static GlobalGarbageConfiguration globalConfig(final List<String> garbageWeeks) {
        return GlobalGarbageConfiguration.builder()
                .setResetDay(DayOfWeek.SUNDAY)
                .setStart(LocalDate.parse("2019-05-01"))
                .setGarbageWeeks(garbageWeeks)
                .setRecyclingWeeks(ALL_WEEKS)
                .setLeapDays(
                        LocalDate.parse("2019-05-27"),
                        LocalDate.parse("2019-07-04"),
                        LocalDate.parse("2019-09-02"),
                        LocalDate.parse("2019-11-28"),
                        LocalDate.parse("2019-12-25"))
                .build();
    }

    @Test
    void testOffOnLeapDay() {
        Garbage classUnderTest = new Garbage(globalConfig(),
                new UserGarbageConfiguration(DayOfWeek.THURSDAY, null, FIRST_WEEK));
        final GarbageDay result = classUnderTest.compute(LocalDate.parse("2019-05-30"));
        assertThat(result.isGarbageDay(), is(false));
        assertThat(result.isRecyclingDay(), is(false));
    }

    @Test
    void testOffOnHoliday() {
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            Garbage classUnderTest = new Garbage(globalConfig(),
                    new UserGarbageConfiguration(dayOfWeek, null, FIRST_WEEK));
            final GarbageDay result = classUnderTest.compute(LocalDate.parse("2019-05-27"));
            assertThat(result.isGarbageDay(), is(false));
            assertThat(result.isRecyclingDay(), is(false));
        }
    }

    @Test
    void testGarbageAndRecyclingOnNormalDay() {
        Garbage classUnderTest = new Garbage(globalConfig(),
                new UserGarbageConfiguration(DayOfWeek.THURSDAY, null, FIRST_WEEK));
        final GarbageDay result = classUnderTest.compute(LocalDate.parse("2019-05-16"));
        assertThat(result.isGarbageDay(), is(true));
        assertThat(result.isRecyclingDay(), is(true));
    }

    @Test
    void testGarbageOnlyOnNormalDay() {
        Garbage classUnderTest = new Garbage(globalConfig(),
                new UserGarbageConfiguration(DayOfWeek.THURSDAY, null, FIRST_WEEK));
        final GarbageDay result = classUnderTest.compute(LocalDate.parse("2019-05-23"));
        assertThat(result.isGarbageDay(), is(true));
        assertThat(result.isRecyclingDay(), is(false));
    }

    @Test
    void testDateInResult() {
        final LocalDate date = LocalDate.parse("2019-05-23");
        Garbage classUnderTest = new Garbage(globalConfig(),
                new UserGarbageConfiguration(DayOfWeek.THURSDAY, null, FIRST_WEEK));
        final GarbageDay result = classUnderTest.compute(date);
        assertThat(result.getDate(), is(date));
    }

    @Test
    void testSingleWeek() {
        Garbage classUnderTest = new Garbage(globalConfig(singletonList("ONLY")),
                new UserGarbageConfiguration(DayOfWeek.THURSDAY, null, FIRST_WEEK));
        for (LocalDate date : new LocalDate[] { LocalDate.parse("2019-05-16"), LocalDate.parse("2019-05-23") }) {
            final GarbageDay result = classUnderTest.compute(date);
            assertThat(result.isGarbageDay(), is(true));
        }
    }

    @Test
    void testOffOnNormalDay() {
        Garbage classUnderTest = new Garbage(globalConfig(),
                new UserGarbageConfiguration(DayOfWeek.THURSDAY, null, FIRST_WEEK));
        final GarbageDay result = classUnderTest.compute(LocalDate.parse("2019-05-22"));
        assertThat(result.isGarbageDay(), is(false));
        assertThat(result.isRecyclingDay(), is(false));
    }

    @Test
    void testResetAfterLeapDay() {
        Garbage classUnderTest = new Garbage(globalConfig(),
                new UserGarbageConfiguration(DayOfWeek.SUNDAY, null, SECOND_WEEK));
        final GarbageDay result = classUnderTest.compute(LocalDate.parse("2019-06-02"));
        assertThat(result.isGarbageDay(), is(true));
        assertThat(result.isRecyclingDay(), is(true));
    }

    @Test
    void testBeforeStartDay() {
        for (String week : ALL_WEEKS) {
            Garbage classUnderTest = new Garbage(globalConfig(),
                    new UserGarbageConfiguration(DayOfWeek.TUESDAY, null, week));
            final GarbageDay result = classUnderTest.compute(LocalDate.parse("2019-04-30"));
            assertThat(result.isGarbageDay(), is(true));
            assertThat(result.isRecyclingDay(), is(true));
        }
    }

    @Test
    void testOffWhenAlternatingGarbage() {
        Garbage classUnderTest = new Garbage(globalConfig(List.of(FIRST_WEEK, SECOND_WEEK)),
                new UserGarbageConfiguration(DayOfWeek.THURSDAY, FIRST_WEEK, FIRST_WEEK));
        final GarbageDay result = classUnderTest.compute(LocalDate.parse("2019-05-23"));
        assertThat(result.isGarbageDay(), is(false));
        assertThat(result.isRecyclingDay(), is(false));
    }

    @Test
    void testOnWhenAlternatingGarbage() {
        Garbage classUnderTest = new Garbage(globalConfig(List.of(FIRST_WEEK, SECOND_WEEK)),
                new UserGarbageConfiguration(DayOfWeek.THURSDAY, SECOND_WEEK, SECOND_WEEK));
        final GarbageDay result = classUnderTest.compute(LocalDate.parse("2019-05-23"));
        assertThat(result.isGarbageDay(), is(true));
        assertThat(result.isRecyclingDay(), is(true));
    }

    @Test
    void testOnWhenHolidayWithNoLeap() {
        final GlobalGarbageConfiguration globalConfig = GlobalGarbageConfiguration.builder()
                .setResetDay(DayOfWeek.SUNDAY)
                .setStart(LocalDate.parse("2019-01-01"))
                .setHolidays(LocalDate.parse("2019-12-25"), LocalDate.parse("2020-01-01"))
                .build();
        Garbage classUnderTest = new Garbage(globalConfig,
                new UserGarbageConfiguration(DayOfWeek.THURSDAY, null, null));
        final GarbageDay result = classUnderTest.compute(LocalDate.parse("2019-12-26"));
        assertThat(result.isGarbageDay(), is(true));
        assertThat(result.isRecyclingDay(), is(true));
    }

    @Test
    void testOffWhenHolidayWithNoLeap() {
        final GlobalGarbageConfiguration globalConfig = GlobalGarbageConfiguration.builder()
                .setResetDay(DayOfWeek.SUNDAY)
                .setStart(LocalDate.parse("2019-01-01"))
                .setHolidays(LocalDate.parse("2019-12-25"), LocalDate.parse("2020-01-01"))
                .build();
        Garbage classUnderTest = new Garbage(globalConfig,
                new UserGarbageConfiguration(DayOfWeek.WEDNESDAY, null, null));
        final GarbageDay result = classUnderTest.compute(LocalDate.parse("2019-12-25"));
        assertThat(result.isGarbageDay(), is(false));
        assertThat(result.isRecyclingDay(), is(false));
    }

    @Test
    void testConfigWeekOverrides() {
        final GlobalGarbageConfiguration globalConfig = GlobalGarbageConfiguration.builder()
                .setResetDay(DayOfWeek.SUNDAY)
                .setStart(LocalDate.parse("2019-05-01"))
                .setGarbageWeeks(FIRST_WEEK, SECOND_WEEK)
                .setRecyclingWeeks(FIRST_WEEK, SECOND_WEEK)
                .build();
        Garbage classUnderTest = new Garbage(globalConfig,
                new UserGarbageConfiguration(DayOfWeek.TUESDAY, FIRST_WEEK, SECOND_WEEK));
        final GarbageDay result = classUnderTest.compute(LocalDate.parse("2019-05-21"));
        assertThat(result.isGarbageDay(), is(false));
        assertThat(result.isRecyclingDay(), is(true));
    }
}
