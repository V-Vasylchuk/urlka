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
    public Url save(Url url) {
        return urlRepository.save(url);
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

    /*    public String generateShortLink(String originalLink) {
        String prefix = "http://short:";
        if (isLinkValid(originalLink)) {
            String hash = DigestUtils.md5DigestAsHex(
                    originalLink.getBytes(StandardCharsets.UTF_8));
            String shortLink = prefix + hash.substring(0, 4);
            for (int i = 0; i < hash.length() - 4; i++) {
                if (urlRepository.findByShortLink(prefix + hash.substring(i, i + 4)).isEmpty()) {
                    shortLink = prefix + hash.substring(i, i + 4);
                    break;
                }
                if (i == 27) {
                    hash = DigestUtils.md5DigestAsHex(
                            hash.getBytes(StandardCharsets.UTF_8));
                    i = 0;
                }
            }
            return shortLink;
        }
        throw new InvalidLinkException("Please write a valid link");
    }

    private boolean isLinkValid(String link) {
        return link.matches("((http|https)://)(www.)?"
        + "[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]"
        + "{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)");
    }*/
}
