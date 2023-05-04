package ru.aosinin.pastebin.api.request;

import lombok.Value;
import ru.aosinin.pastebin.repository.PasteLifetime;
import ru.aosinin.pastebin.repository.Visibility;

@Value
public class AddPasteRequest {
    String text;
    PasteLifetime lifetime;
    Visibility visibility;
}
