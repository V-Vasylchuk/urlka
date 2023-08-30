package userway.test.urlka.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import userway.test.urlka.exceptions.InvalidLinkException;
import userway.test.urlka.repository.UrlRepository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

@SpringBootTest
class ShortenerTest {

    @Test
    public void testGenerateShortLink_ValidLink() {
        UrlRepository mockRepository = mock(UrlRepository.class);
        Shortener shortener = new Shortener(mockRepository);
        String originalLink = "http://example.com";
        String shortLink = shortener.generateShortLink(originalLink);
        assertNotNull(shortLink);
    }

    @Test
    public void testGenerateShortLink_InvalidLink() {
        UrlRepository mockRepository = mock(UrlRepository.class);
        Shortener shortener = new Shortener(mockRepository);
        String originalLink = "invalid-link";
        assertThrows(InvalidLinkException.class, () -> shortener.generateShortLink(originalLink));
    }

    @Test
    public void testIsLinkValid_ValidLink() {
        Shortener shortener = new Shortener(null);
        String validLink = "http://example.com";
        boolean isValid = shortener.isLinkValid(validLink);
        assertTrue(isValid);
    }

    @Test
    public void testIsLinkValid_InvalidLink() {
        Shortener shortener = new Shortener(null);
        String invalidLink = "invalid-link";
        boolean isValid = shortener.isLinkValid(invalidLink);
        assertFalse(isValid);
    }
}
