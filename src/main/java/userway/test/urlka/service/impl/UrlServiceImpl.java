package userway.test.urlka.service.impl;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import userway.test.urlka.dto.UrlDto;
import userway.test.urlka.exceptions.UrlNotFoundException;
import userway.test.urlka.model.Url;
import userway.test.urlka.repository.UrlRepository;
import userway.test.urlka.service.UrlService;
import userway.test.urlka.util.Shortener;

@Service
@Slf4j
@AllArgsConstructor
public class UrlServiceImpl implements UrlService {
    private UrlRepository urlRepository;
    private Shortener shortener;

    @Override
    public void save(Url url) {
        urlRepository.save(url);
    }

    @Override
    public String getShortLink(UrlDto urlDto) {
        Optional<Url> optionalUrl = urlRepository.findByOriginalLink(urlDto.getUrl());
        if (optionalUrl.isPresent()) {
            return optionalUrl.get().getShortLink();
        }
        Url url = new Url(urlDto.getUrl());
        url.setShortLink(shortener.generateShortLink(urlDto.getUrl()));
        save(url);
        return url.getShortLink();
    }

    @Override
    public String getOriginalLink(String shortLink) {
        Optional<Url> optionalUrl = urlRepository.findByShortLink(shortLink);
        if (optionalUrl.isPresent()) {
            return optionalUrl.get().getOriginalLink();
        }
        throw new UrlNotFoundException("Original URL not found by short link: " + shortLink);
    }
}
