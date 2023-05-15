package ru.aosinin.pastebin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.aosinin.pastebin.api.request.AddPasteRequest;
import ru.aosinin.pastebin.api.responce.AddPasteResponse;
import ru.aosinin.pastebin.api.responce.PasteResponse;
import ru.aosinin.pastebin.service.PasteService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class PastebinController {
    private final PasteService pasteService;

    @GetMapping("/")
    public Collection<PasteResponse> getLatestPublicPostedPastes() {
        return pasteService.getLatestPublicPostedPastes();
    }

    @GetMapping("/{hash}")
    public PasteResponse getPasteByHash(@PathVariable String hash) {
        return pasteService.getByHash(hash);
    }

    @PostMapping("/")
    public ModelAndView addPaste(@RequestBody AddPasteRequest request) {
        AddPasteResponse addPasteResponse = pasteService.create(request);
        return new ModelAndView("redirect:/" + addPasteResponse.hash());
    }
}

