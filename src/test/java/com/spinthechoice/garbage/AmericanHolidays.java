package com.spinthechoice.garbage;

import java.time.DayOfWeek;
import java.time.Month;

class AmericanHolidays {
    static Holiday newYearsEve() {
        return Holiday.builder()
                .setType(HolidayType.STATIC_DATE)
                .setMonth(Month.JANUARY)
                .setDate(1)
                .setOffset(HolidayOffset.DAY_BEFORE)
                .build();
    }

    static Holiday newYears() {
        return Holiday.builder()
                .setType(HolidayType.STATIC_DATE)
                .setMonth(Month.JANUARY)
                .setDate(1)
                .build();
    }

    static Holiday memorialDay() {
        return Holiday.builder()
                .setType(HolidayType.NTH_DAY_OF_WEEK)
                .setMonth(Month.MAY)
                .setDayOfWeek(DayOfWeek.MONDAY)
                .setWeekIndex(-1)
                .build();
    }

    static Holiday independenceDay() {
        return Holiday.builder()
                .setType(HolidayType.STATIC_DATE)
                .setMonth(Month.JULY)
                .setDate(4)
                .build();
    }

    static Holiday laborDay() {
        return Holiday.builder()
                .setType(HolidayType.NTH_DAY_OF_WEEK)
                .setMonth(Month.SEPTEMBER)
                .setDayOfWeek(DayOfWeek.MONDAY)
                .setWeekIndex(0)
                .build();
    }

    static Holiday thanksgiving() {
        return Holiday.builder()
                .setType(HolidayType.NTH_DAY_OF_WEEK)
                .setMonth(Month.NOVEMBER)
                .setDayOfWeek(DayOfWeek.THURSDAY)
                .setWeekIndex(3)
                .build();
    }

    static Holiday thanksgivingDayAfter() {
        return Holiday.builder()
                .setType(HolidayType.NTH_DAY_OF_WEEK)
                .setMonth(Month.NOVEMBER)
                .setDayOfWeek(DayOfWeek.THURSDAY)
                .setWeekIndex(3)
                .setOffset(HolidayOffset.DAY_AFTER)
                .build();
    }

    static Holiday christmas() {
        return Holiday.builder()
                .setType(HolidayType.STATIC_DATE)
                .setMonth(Month.DECEMBER)
                .setDate(25)
                .build();
    }
}
