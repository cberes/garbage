package com.guineapigbite.garbage;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

public class Garbage {
    private final GlobalGarbageConfiguration globalConfig;
    private final UserGarbageConfiguration userConfig;
    private final Set<LocalDate> leapDays;

    public Garbage(final GlobalGarbageConfiguration globalConfig, final UserGarbageConfiguration userConfig) {
        this.globalConfig = globalConfig;
        this.userConfig = userConfig;
        this.leapDays = getAllLeakDays(globalConfig);
    }

    private static Set<LocalDate> getAllLeakDays(final GlobalGarbageConfiguration config) {
        return config.getLeapDays().stream()
                .flatMap(holiday -> daysUntilReset(holiday, config.getResetDay()))
                .collect(toSet());
    }

    private static Stream<LocalDate> daysUntilReset(final LocalDate holiday, final DayOfWeek reset) {
        return Stream.iterate(holiday, day -> day.getDayOfWeek() != reset, day -> day.plusDays(1)).skip(1);
    }

    public GarbageDay compute(final LocalDate date) {
        // TODO skip weeks
        final int plusDays = isLeapForward(date) ? 1 : 0;
        final DayOfWeek userDayOfWeek = userConfig.getDayOfWeek().plus(plusDays);
        return new GarbageDay(date,
                !isHoliday(date) && date.getDayOfWeek() == userDayOfWeek,
                !isHoliday(date) && date.getDayOfWeek() == userDayOfWeek);
    }

    private boolean isHoliday(final LocalDate date) {
        return globalConfig.getLeapDays().contains(date);
    }

    private boolean isLeapForward(final LocalDate date) {
        return leapDays.contains(date);
    }
}
