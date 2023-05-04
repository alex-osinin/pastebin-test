package ru.aosinin.pastebin.repository;

import lombok.Builder;
import lombok.Value;

import java.time.ZonedDateTime;

@Value
@Builder
public class PasteEntity {
    int id;
    String text;
    String hash;

    PasteLifetime lifetime;
    ZonedDateTime createdTime;
    ZonedDateTime expirationTime;
    Visibility visibility;

    public boolean isPublic() {
        return visibility == Visibility.PUBLIC;
    }
}
