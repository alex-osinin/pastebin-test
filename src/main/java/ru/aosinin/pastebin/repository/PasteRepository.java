package ru.aosinin.pastebin.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.aosinin.pastebin.model.PasteEntity;
import ru.aosinin.pastebin.model.PasteVisibility;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PasteRepository extends JpaRepository<PasteEntity, Long> {

    Optional<PasteEntity> getByHash(String hash);

    Slice<PasteEntity> findByVisibilityAndExpirationTimeGreaterThanEqual(PasteVisibility visibility,
                                                                         LocalDateTime expirationTime, Pageable pageable);

    long removeByExpirationTimeLessThan(LocalDateTime currentTime);
}