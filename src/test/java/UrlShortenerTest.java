import com.urlshortener.ShortUrlNotFoundException;
import com.urlshortener.UrlShortener;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UrlShortenerTest {

    @Test
    public void testShortenAndRetrieveUrl() {
        UrlShortener urlShortener = new UrlShortener();
        String originalUrl = "https://www.example.com";
        String shortCode = urlShortener.shorten(originalUrl);
        String retrievedUrl = urlShortener.getOriginalUrl(shortCode);
        assertEquals(originalUrl, retrievedUrl);
    }

    @Test
    void throwsWhenNoLongCodeExist() {
        UrlShortener urlShortener = new UrlShortener();
        assertThrows(ShortUrlNotFoundException.class, () -> urlShortener.getOriginalUrl("nonexistent"));
    }
}
