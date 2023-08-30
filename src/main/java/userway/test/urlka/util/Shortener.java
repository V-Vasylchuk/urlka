package userway.test.urlka.util;

import java.nio.charset.StandardCharsets;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import userway.test.urlka.exceptions.InvalidLinkException;
import userway.test.urlka.repository.UrlRepository;

@Component
@AllArgsConstructor
public class Shortener {
    private UrlRepository urlRepository;

    public String generateShortLink(String originalLink) {
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

    public boolean isLinkValid(String link) {
        return link.matches("((http|https)://)(www.)?"
                + "[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]"
                + "{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)");
    }
}
