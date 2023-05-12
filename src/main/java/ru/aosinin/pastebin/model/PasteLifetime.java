package ru.aosinin.pastebin.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.Period;
import java.time.temporal.TemporalAmount;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum PasteLifetime {
    TEN_MINUTES("10m", Duration.ofMinutes(2)),
    HOUR("1H", Duration.ofHours(1)),
    DAY("1D", Period.ofDays(1)),
    WEEK("1W", Period.ofWeeks(1)),
    TWO_WEEKS("2W", Period.ofWeeks(2)),
    MONTH("1M", Period.ofMonths(1)),
    SIX_MONTHS("6M", Period.ofMonths(6)),
    YEAR("1Y", Period.ofYears(1)),
    NEVER("N", Period.ZERO);

    private final String code;
    private final TemporalAmount temporalAmount;

    @JsonValue
    public String value() {
        return code;
    }

    public static PasteLifetime fromString(String code) {
        if (code == null) {
            return null;
        }
        return Stream.of(PasteLifetime.values())
                .filter(i -> i.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
