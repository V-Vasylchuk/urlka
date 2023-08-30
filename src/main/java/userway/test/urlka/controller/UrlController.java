package userway.test.urlka.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import userway.test.urlka.dto.UrlDto;
import userway.test.urlka.service.UrlService;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("urlka")
public class UrlController {
    private final UrlService urlService;

    @PostMapping("/generate")
    public ResponseEntity<UrlDto> generateShortUrl(@RequestBody @Valid UrlDto urlDto) {
        return ResponseEntity.ok(new UrlDto(urlService.getShortLink(urlDto)));
    }

    @GetMapping("/restore")
    public ResponseEntity<UrlDto> getOriginByShortUrl(@RequestParam String shortLink) {
        return ResponseEntity.ok(new UrlDto(urlService.getOriginalLink(shortLink)));
    }
}
