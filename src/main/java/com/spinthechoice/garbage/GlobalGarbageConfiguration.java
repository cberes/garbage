package com.spinthechoice.garbage;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;

/**
 * Configuration for an entire municipality.
 */
public final class GlobalGarbageConfiguration {
    public static class Builder {
        private DayOfWeek resetDay;
        private LocalDate start;
        private boolean garbageEnabled;
        private List<String> garbageWeeks;
        private boolean recyclingEnabled;
        private List<String> recyclingWeeks;
        private Set<Holiday> leapDays;
        private Set<Holiday> holidays;
        private Set<LocalDate> bulkDays;

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
         * Sets whether garbage pick-up is enabled.
         * @param garbageEnabled {@code true} if garbage pick-up is enabled, {@code false} otherwise
         * @return this builder instance
         */
        public Builder setGarbageEnabled(final boolean garbageEnabled) {
            this.garbageEnabled = garbageEnabled;
            return this;
        }

        /**
         * @see #setGarbageWeeks(List)
         */
        public Builder setGarbageWeeks(final String... garbageWeeks) {
            return setGarbageWeeks(asList(garbageWeeks));
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
         * Sets whether recycling pick-up is enabled.
         * @param recyclingEnabled {@code true} if recycling pick-up is enabled, {@code false} otherwise
         * @return this builder instance
         */
        public Builder setRecyclingEnabled(final boolean recyclingEnabled) {
            this.recyclingEnabled = recyclingEnabled;
            return this;
        }

        /**
         * @see #setRecyclingWeeks(List)
         */
        public Builder setRecyclingWeeks(final String... recyclingWeeks) {
            return setRecyclingWeeks(asList(recyclingWeeks));
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
        public Builder setLeapDays(final Holiday... leapDays) {
            return setLeapDays(new HashSet<>(asList(leapDays)));
        }

        /**
         * Sets leap days.
         * @param leapDays days when collection is postponed until the following day
         * @return this builder instance
         */
        public Builder setLeapDays(final Set<Holiday> leapDays) {
            this.leapDays = leapDays;
            return this;
        }

        /**
         * @see #setHolidays(Set)
         */
        public Builder setHolidays(final Holiday... holidays) {
            return setHolidays(new HashSet<>(asList(holidays)));
        }

        /**
         * Sets holidays.
         * @param holidays days when collection is canceled but not rescheduled
         * @return this builder instance
         */
        public Builder setHolidays(final Set<Holiday> holidays) {
            this.holidays = holidays;
            return this;
        }

        /**
         * @see #setBulkDays(Set)
         */
        public Builder setBulkDays(final LocalDate... bulkDays) {
            return setBulkDays(new HashSet<>(asList(bulkDays)));
        }

        /**
         * Sets bulk days.
         * @param bulkDays first days of weeks when bulk garbage will be collected
         * @return this builder instance
         */
        public Builder setBulkDays(final Set<LocalDate> bulkDays) {
            this.bulkDays = bulkDays;
            return this;
        }
    }

    private final DayOfWeek resetDay;
    private final LocalDate start;
    private final boolean garbageEnabled;
    private final List<String> garbageWeeks;
    private final boolean recyclingEnabled;
    private final List<String> recyclingWeeks;
    private final Set<Holiday> leapDays;
    private final Set<Holiday> holidays;
    private final Set<LocalDate> bulkDays;

    public GlobalGarbageConfiguration(final Builder builder) {
        this.resetDay = builder.resetDay;
        this.start = builder.start;
        this.garbageEnabled = builder.garbageEnabled;
        this.garbageWeeks = builder.garbageWeeks;
        this.recyclingEnabled = builder.recyclingEnabled;
        this.recyclingWeeks = builder.recyclingWeeks;
        this.leapDays = builder.leapDays;
        this.holidays = builder.holidays;
        this.bulkDays = builder.bulkDays;
    }

    public DayOfWeek getResetDay() {
        return resetDay;
    }

    public LocalDate getStart() {
        return start;
    }

    public Set<Holiday> getLeapDays() {
        return leapDays;
    }

    public Set<Holiday> getHolidays() {
        return holidays;
    }

    public Set<LocalDate> getBulkDays() {
        return bulkDays;
    }

    public boolean isGarbageEnabled() {
        return garbageEnabled;
    }

    public List<String> getGarbageWeeks() {
        return garbageWeeks;
    }

    public boolean isRecyclingEnabled() {
        return recyclingEnabled;
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
