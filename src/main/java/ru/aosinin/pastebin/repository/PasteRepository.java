package ru.aosinin.pastebin.repository;

import ru.aosinin.pastebin.exception.NotFoundEntityException;

import java.util.List;

public interface PasteRepository {

    PasteEntity getByHash(String hash) throws NotFoundEntityException;

    List<PasteEntity> getLatestPublishedPublicPastes(int amount);

    void add(PasteEntity pasteEntity);

}
