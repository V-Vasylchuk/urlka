package userway.test.urlka.service;

import userway.test.urlka.dto.UrlDto;
import userway.test.urlka.model.Url;

public interface UrlService {
    void save(Url url);

    String getShortLink(UrlDto urlDto);

    String getOriginalLink(String shortLink);
}
