package com.spinthechoice.garbage;

import java.time.LocalDate;

/**
 * Offset from a more common holiday.
 * Useful for things like the day after Thanksgiving and (to a lesser extent) Christmas Eve.
 */
public enum HolidayOffset {
    /** No offset. */
    DAY_OF {
        @Override
        public LocalDate apply(final LocalDate d) {
            return d;
        }
    },
    /** Add 1 day. */
    DAY_AFTER {
        @Override
        public LocalDate apply(final LocalDate d) {
            return d.plusDays(1);
        }
    },
    /** Subtract 1 day. */
    DAY_BEFORE {
        @Override
        public LocalDate apply(final LocalDate d) {
            return d.minusDays(1);
        }
    };

    /**
     * Apply this date offset to the given datw.
     * @param d date
     * @return the offset date
     */
    public abstract LocalDate apply(LocalDate d);
}
