package ru.aosinin.pastebin.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import ru.aosinin.pastebin.api.request.AddPasteRequest;
import ru.aosinin.pastebin.api.responce.AddPasteResponse;
import ru.aosinin.pastebin.api.responce.PasteResponse;
import ru.aosinin.pastebin.exception.NotFoundEntityException;
import ru.aosinin.pastebin.repository.PasteEntity;
import ru.aosinin.pastebin.repository.PasteLifetime;
import ru.aosinin.pastebin.repository.PasteRepositoryImpl;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Setter
@ConfigurationProperties(prefix = "app")
public class PasteServiceImpl implements PasteService {

    private String host;
    private int publicListSize;

    private final PasteRepositoryImpl repository;

    @Override
    public PasteResponse getByHash(String hash) {
        PasteEntity pasteEntity;
        try {
            pasteEntity = repository.getByHash(hash);
        } catch (NotFoundEntityException e) {
            // FIXME: 04.05.2023 реализовать корректную обработку ошибки
            return new PasteResponse("null");
        }
        return new PasteResponse(pasteEntity.getText());
    }

    @Override
    public List<PasteResponse> getLatestPublishedPastes() {
        List<PasteEntity> list = repository.getLatestPublishedPublicPastes(publicListSize);
        return list.stream().map(pasteEntity -> new PasteResponse(pasteEntity.getText())).collect(Collectors.toList());
    }

    @Override
    public AddPasteResponse create(AddPasteRequest request) {
        String hash = generateHash();
        ZonedDateTime createdTime = ZonedDateTime.now();
        PasteLifetime lifetime = request.getLifetime();
        ZonedDateTime expirationTime = createdTime.plus(lifetime.getTemporalAmount());

        PasteEntity paste = PasteEntity.builder()
                .hash(hash)
                .text(request.getText())
                .lifetime(lifetime)
                .createdTime(createdTime)
                .expirationTime(expirationTime)
                .visibility(request.getVisibility())
                .build();
        repository.add(paste);

        return new AddPasteResponse(host + "/" + paste.getHash());
    }

    private String generateHash() {
        // TODO: 04.05.2023 дописать метод генерации случайного и уникального хеша
        return RandomStringUtils.random(8, true, true);
    }
}
