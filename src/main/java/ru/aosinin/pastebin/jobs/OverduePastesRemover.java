package ru.aosinin.pastebin.jobs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aosinin.pastebin.repository.PasteRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
// TODO: 30.05.2023 если будет запущено несколько инстансов приложения,
//  то нужно добавлять библиотеку ShedLock, либо выносить эту логику в отдельный сервис
public class OverduePastesRemover {

    private final PasteRepository pasteRepository;

    @Transactional
    @Scheduled(cron = "@daily")
    public void removeAllOldPastes() {
        log.debug("Запускаю процесс удаления устаревших паст");
        long removed = pasteRepository.removeByExpirationTimeLessThan(LocalDateTime.now());
        log.debug("Удалено элементов: {}", removed);
    }
}
