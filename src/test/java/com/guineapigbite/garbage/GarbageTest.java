package com.guineapigbite.garbage;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class GarbageTest {
    private static final String FIRST_WEEK = "A";
    private static final String SECOND_WEEK = "B";

    @Test
    void testGarbageAndRecyclingOnLeapDay() {
        Garbage classUnderTest = new Garbage(globalConfig(),
                new UserGarbageConfiguration(DayOfWeek.THURSDAY, null, FIRST_WEEK));
        final GarbageDay result = classUnderTest.compute(LocalDate.parse("2019-05-31"));
        assertThat(result.isGarbageDay(), is(true));
        assertThat(result.isRecyclingDay(), is(true));
    }

    private static GlobalGarbageConfiguration globalConfig() {
        return new GlobalGarbageConfiguration(
                DayOfWeek.SUNDAY,
                LocalDate.parse("2019-05-01"),
                null,
                Set.of(FIRST_WEEK, SECOND_WEEK),
                Set.of(
                        LocalDate.parse("2019-05-27"),
                        LocalDate.parse("2019-07-04"),
                        LocalDate.parse("2019-09-02"),
                        LocalDate.parse("2019-11-28"),
                        LocalDate.parse("2019-12-25")));
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
}
