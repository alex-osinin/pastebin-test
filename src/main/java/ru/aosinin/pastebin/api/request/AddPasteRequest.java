package ru.aosinin.pastebin.api.request;

import lombok.Value;
import ru.aosinin.pastebin.model.PasteLifetime;
import ru.aosinin.pastebin.model.PasteVisibility;

@Value
public class AddPasteRequest {
    String text;
    PasteLifetime lifetime;
    PasteVisibility visibility;
}
