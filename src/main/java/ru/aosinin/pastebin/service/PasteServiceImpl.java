package ru.aosinin.pastebin.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import ru.aosinin.pastebin.api.request.AddPasteRequest;
import ru.aosinin.pastebin.api.responce.AddPasteResponse;
import ru.aosinin.pastebin.api.responce.PasteResponse;
import ru.aosinin.pastebin.model.PasteEntity;
import ru.aosinin.pastebin.model.PasteLifetime;
import ru.aosinin.pastebin.model.PasteVisibility;
import ru.aosinin.pastebin.repository.PasteRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Setter
@ConfigurationProperties(prefix = "app")
public class PasteServiceImpl implements PasteService {

    private int publicListSize;

    private final PasteRepository repository;

    @Override
    public PasteResponse getByHash(String hash) {
        Optional<PasteEntity> pasteEntity = repository.getByHash(hash);
        // TODO: 12.05.2023 обработать ошибки (не найдена запись)
        return pasteEntity.map(entity -> new PasteResponse(entity.getText())).orElseGet(() -> new PasteResponse("null"));
    }

    @Override
    public List<PasteResponse> getLatestPublicPostedPastes() {
        Slice<PasteEntity> slice = repository.findByVisibilityAndExpirationTimeGreaterThanEqual(PasteVisibility.PUBLIC,
                LocalDateTime.now(), PageRequest.of(0, publicListSize));
        // TODO: 12.05.2023 проверить на сколько использование стрима для слайса корректно
        return slice.stream().map(pasteEntity -> new PasteResponse(pasteEntity.getText())).toList();
    }

    @Override
    public AddPasteResponse create(AddPasteRequest request) {
        String hash = generateHash();
        LocalDateTime createdTime = LocalDateTime.now();
        PasteLifetime lifetime = request.getLifetime();
        LocalDateTime expirationTime = calculateExpirationTime(createdTime, lifetime);

        PasteEntity paste = new PasteEntity();
        paste.setHash(hash);
        paste.setText(request.getText());
        paste.setLifetime(lifetime);
        paste.setCreatedTime(createdTime);
        paste.setExpirationTime(expirationTime);
        paste.setVisibility(request.getVisibility());
        repository.save(paste);

        return new AddPasteResponse(paste.getHash());
    }

    private String generateHash() {
        // TODO: 04.05.2023 дописать метод генерации случайного и уникального хеша
        return RandomStringUtils.random(8, true, true);
    }

    private LocalDateTime calculateExpirationTime(LocalDateTime createdTime, PasteLifetime lifetime) {
        if (PasteLifetime.NEVER.equals(lifetime)) {
            return null;
        }
        return createdTime.plus(lifetime.getTemporalAmount());
    }
}
