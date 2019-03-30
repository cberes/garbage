package com.spinthechoice.garbage;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Configuration for an entire municipality.
 */
public final class GlobalGarbageConfiguration {
    public static class Builder {
        private DayOfWeek resetDay;
        private LocalDate start;
        private List<String> garbageWeeks;
        private List<String> recyclingWeeks;
        private Set<LocalDate> leapDays;
        private Set<LocalDate> holidays;

        /**
         * Creates a new configuration object.
         * @return configuration
         */
        public GlobalGarbageConfiguration build() {
            return new GlobalGarbageConfiguration(this);
        }

        /**
         * Sets the reset day of week.
         * @param resetDay day of week that leap days reset to normal
         * @return this builder instance
         */
        public Builder setResetDay(final DayOfWeek resetDay) {
            this.resetDay = resetDay;
            return this;
        }

        /**
         * Sets the start date.
         * @param start start date for every-other-week collection
         * @return this builder instance
         */
        public Builder setStart(final LocalDate start) {
            this.start = start;
            return this;
        }

        /**
         * @see #setGarbageWeeks(List)
         */
        public Builder setGarbageWeeks(final String... garbageWeeks) {
            return setGarbageWeeks(List.of(garbageWeeks));
        }

        /**
         * Sets garbage weeks.
         * @param garbageWeeks names of all garbage weeks (if more than 1)
         * @return this builder instance
         */
        public Builder setGarbageWeeks(final List<String> garbageWeeks) {
            this.garbageWeeks = garbageWeeks;
            return this;
        }

        /**
         * @see #setRecyclingWeeks(List)
         */
        public Builder setRecyclingWeeks(final String... recyclingWeeks) {
            return setRecyclingWeeks(List.of(recyclingWeeks));
        }

        /**
         * Sets recycling weeks.
         * @param recyclingWeeks names of all recycling weeks (if more than 1)
         * @return this builder instance
         */
        public Builder setRecyclingWeeks(final List<String> recyclingWeeks) {
            this.recyclingWeeks = recyclingWeeks;
            return this;
        }

        /**
         * @see #setLeapDays(Set)
         */
        public Builder setLeapDays(final LocalDate... leapDays) {
            return setLeapDays(Set.of(leapDays));
        }

        /**
         * Sets leap days.
         * @param leapDays days when collection is postponed until the following day
         * @return this builder instance
         */
        public Builder setLeapDays(final Set<LocalDate> leapDays) {
            this.leapDays = leapDays;
            return this;
        }

        /**
         * @see #setHolidays(Set)
         */
        public Builder setHolidays(final LocalDate... holidays) {
            return setHolidays(Set.of(holidays));
        }

        /**
         * Sets holidays.
         * @param holidays days when collection is canceled but not rescheduled
         * @return this builder instance
         */
        public Builder setHolidays(final Set<LocalDate> holidays) {
            this.holidays = holidays;
            return this;
        }
    }

    private final DayOfWeek resetDay;
    private final LocalDate start;
    private final List<String> garbageWeeks;
    private final List<String> recyclingWeeks;
    private final Set<LocalDate> leapDays;
    private final Set<LocalDate> holidays;

    public GlobalGarbageConfiguration(final Builder builder) {
        this.resetDay = builder.resetDay;
        this.start = builder.start;
        this.garbageWeeks = builder.garbageWeeks;
        this.recyclingWeeks = builder.recyclingWeeks;
        this.leapDays = builder.leapDays;
        this.holidays = builder.holidays;
    }

    public DayOfWeek getResetDay() {
        return resetDay;
    }

    public LocalDate getStart() {
        return start;
    }

    public Set<LocalDate> getLeapDays() {
        return leapDays;
    }

    public Set<LocalDate> getHolidays() {
        return holidays;
    }

    public List<String> getGarbageWeeks() {
        return garbageWeeks;
    }

    public List<String> getRecyclingWeeks() {
        return recyclingWeeks;
    }

    /**
     * Creates a new builder instance.
     * @return new builder instance.
     */
    public static Builder builder() {
        return new Builder();
    }
}
