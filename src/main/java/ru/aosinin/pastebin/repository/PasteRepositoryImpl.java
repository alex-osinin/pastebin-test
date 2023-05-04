package ru.aosinin.pastebin.repository;

import org.springframework.stereotype.Repository;
import ru.aosinin.pastebin.exception.NotFoundEntityException;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class PasteRepositoryImpl implements PasteRepository {

    // FIXME: 04.05.2023 временное решение, пока нет бд
    private final Map<String, PasteEntity> vault = new ConcurrentHashMap<>();

    private boolean validatePaste(PasteEntity entity) {
        if (entity == null) {
            return false;
        }
        ZonedDateTime now = ZonedDateTime.now();
        return now.isAfter(entity.getExpirationTime());
    }

    @Override
    public PasteEntity getByHash(String hash) throws NotFoundEntityException {
        PasteEntity entity = vault.get(hash);
        if (!validatePaste(entity)) {
            throw new NotFoundEntityException("Paste not found with hash=" + hash);
        }
        return entity;
    }

    @Override
    public List<PasteEntity> getLatestPublishedPublicPastes(int amount) {
        return vault.values().stream()
                .filter(this::validatePaste)
                .filter(PasteEntity::isPublic)
                .sorted(Comparator.comparing(PasteEntity::getId).reversed())
                .limit(amount)
                .collect(Collectors.toList());
    }

    @Override
    public void add(PasteEntity pasteEntity) {
        // TODO: 04.05.2023 исключение при дублировании
        vault.put(pasteEntity.getHash(), pasteEntity);
    }
}
