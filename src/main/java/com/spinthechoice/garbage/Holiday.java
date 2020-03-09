package com.spinthechoice.garbage;

import java.time.DayOfWeek;
import java.time.Month;

public final class Holiday {
    public static class Builder {
        private HolidayType type;
        private Month month;
        private int date;
        private DayOfWeek dayOfWeek;
        private int weekIndex; // -1 for last, 0 for first, 1 for second, etc.
        private HolidayOffset offset;

        public Builder setType(final HolidayType type) {
            this.type = type;
            return this;
        }

        public Builder setMonth(final Month month) {
            this.month = month;
            return this;
        }

        public Builder setDate(final int date) {
            this.date = date;
            return this;
        }

        public Builder setDayOfWeek(final DayOfWeek dayOfWeek) {
            this.dayOfWeek = dayOfWeek;
            return this;
        }

        public Builder setWeekIndex(final int weekIndex) {
            this.weekIndex = weekIndex;
            return this;
        }

        public Builder setOffset(final HolidayOffset offset) {
            this.offset = offset;
            return this;
        }

        /**
         * Creates a new holiday.
         * @return holiday
         */
        public Holiday build() {
            return new Holiday(this);
        }
    }

    private final HolidayType type;
    private final Month month;
    private final int date;
    private final DayOfWeek dayOfWeek;
    private final int weekIndex; // -1 for last, 0 for first, 1 for second, etc.
    private final HolidayOffset offset;

    private Holiday(final Builder builder) {
        this.type = builder.type;
        this.month = builder.month;
        this.date = builder.date;
        this.dayOfWeek = builder.dayOfWeek;
        this.weekIndex = builder.weekIndex;
        this.offset = builder.offset == null ? HolidayOffset.DAY_OF : builder.offset;
    }

    public HolidayType getType() {
        return type;
    }

    public Month getMonth() {
        return month;
    }

    public int getDate() {
        return date;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public int getWeekIndex() {
        return weekIndex;
    }

    public HolidayOffset getOffset() {
        return offset;
    }

    /**
     * Creates a new builder instance.
     * @return new builder instance.
     */
    public static Builder builder() {
        return new Builder();
    }
}
