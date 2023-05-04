package ru.aosinin.pastebin.repository;

import java.time.Duration;
import java.time.Period;
import java.time.temporal.TemporalAmount;

public enum PasteLifetime {
    TEN_MINUTES(Duration.ofMinutes(2)),
    HOUR(Duration.ofHours(1)),
    DAY(Period.ofDays(1)),
    WEEK(Period.ofWeeks(1)),
    TWO_WEEKS(Period.ofWeeks(2)),
    MONTH(Period.ofMonths(1)),
    SIX_MONTHS(Period.ofMonths(6)),
    YEAR(Period.ofYears(1)),
    NEVER(Period.ZERO);

    private final TemporalAmount temporalAmount;

    PasteLifetime(TemporalAmount temporalAmount) {
        this.temporalAmount = temporalAmount;
    }

    public TemporalAmount getTemporalAmount() {
        return temporalAmount;
    }
}
