package userway.test.urlka.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import userway.test.urlka.dto.UrlDto;
import userway.test.urlka.exceptions.UrlNotFoundException;
import userway.test.urlka.model.Url;
import userway.test.urlka.repository.UrlRepository;
import userway.test.urlka.service.impl.UrlServiceImpl;
import userway.test.urlka.util.Shortener;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UrlServiceTest {

    @Mock
    private UrlRepository mockUrlRepository;

    @Mock
    private Shortener mockShortener;

    @InjectMocks
    private UrlServiceImpl urlService;

    @Test
    public void testGetShortLinkExisting() {
        String originalLink = "http://example.com";
        UrlDto urlDto = new UrlDto(originalLink);
        Url url = new Url(originalLink);
        url.setShortLink("short-link");
        when(mockUrlRepository.findByOriginalLink(originalLink)).thenReturn(Optional.of(url));

        String shortLink = urlService.getShortLink(urlDto);

        assertEquals("short-link", shortLink);
        verify(mockShortener, never()).generateShortLink(anyString());
        verify(mockUrlRepository, never()).save(any());
    }

    @Test
    public void testGetShortLinkNew() {
        String originalLink = "http://example.com";
        UrlDto urlDto = new UrlDto(originalLink);
        when(mockUrlRepository.findByOriginalLink(originalLink)).thenReturn(Optional.empty());
        when(mockShortener.generateShortLink(originalLink)).thenReturn("short-link");

        String shortLink = urlService.getShortLink(urlDto);

        assertEquals("short-link", shortLink);
        verify(mockUrlRepository, times(1)).save(any());
    }

    @Test
    public void testGetOriginalLinkExisting() {
        String shortLink = "short-link";
        Url url = new Url("http://example.com");
        when(mockUrlRepository.findByShortLink(shortLink)).thenReturn(Optional.of(url));

        String originalLink = urlService.getOriginalLink(shortLink);

        assertEquals("http://example.com", originalLink);
    }

    @Test
    public void testGetOriginalLinkNotFound() {
        String shortLink = "short-link";
        when(mockUrlRepository.findByShortLink(shortLink)).thenReturn(Optional.empty());

        assertThrows(UrlNotFoundException.class, () -> urlService.getOriginalLink(shortLink));
    }
}
