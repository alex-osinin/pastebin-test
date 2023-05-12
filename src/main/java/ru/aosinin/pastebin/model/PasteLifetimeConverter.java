package ru.aosinin.pastebin.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PasteLifetimeConverter implements AttributeConverter<PasteLifetime, String> {

    @Override
    public String convertToDatabaseColumn(PasteLifetime lifetime) {
        return lifetime != null ? lifetime.getCode() : null;
    }

    @Override
    public PasteLifetime convertToEntityAttribute(String code) {
        return PasteLifetime.fromString(code);
    }
}