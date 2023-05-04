package ru.aosinin.pastebin.service;

import ru.aosinin.pastebin.api.request.AddPasteRequest;
import ru.aosinin.pastebin.api.responce.AddPasteResponse;
import ru.aosinin.pastebin.api.responce.PasteResponse;

import java.util.List;

public interface PasteService {

    PasteResponse getByHash(String hash);

    List<PasteResponse> getLatestPublishedPastes();

    AddPasteResponse create(AddPasteRequest request);

}
