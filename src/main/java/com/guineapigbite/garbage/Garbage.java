package com.guineapigbite.garbage;

import java.time.LocalDate;

public class Garbage {
    private final GlobalGarbageConfiguration globalConfig;
    private final UserGarbageConfiguration userConfig;

    public Garbage(final GlobalGarbageConfiguration globalConfig, final UserGarbageConfiguration userConfig) {
        this.globalConfig = globalConfig;
        this.userConfig = userConfig;
    }

    public GarbageDay compute(final LocalDate date) {
        // TODO leap days
        // TODO skip weeks
        return new GarbageDay(date,
                date.getDayOfWeek() == userConfig.getDayOfWeek(),
                date.getDayOfWeek() == userConfig.getDayOfWeek());
    }
}
