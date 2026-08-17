import com.urlshortener.ShortUrlNotFoundException;
import com.urlshortener.UrlShortener;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void concurrentCallsAreThreadSafe() throws InterruptedException {
        UrlShortener urlShortener = new UrlShortener();
        int totalCalls = 1000;
        ExecutorService executor = Executors.newFixedThreadPool(20);
        CountDownLatch latch = new CountDownLatch(totalCalls);
        List<Throwable> errors = new CopyOnWriteArrayList<>();

        for (int i = 0; i < totalCalls; i++) {
            final int index = i;
            executor.submit(() -> {
                try {
                    String originalUrl = "https://www.example.com/" + index;
                    String shortCode = urlShortener.shorten(originalUrl);
                    String retrievedUrl = urlShortener.getOriginalUrl(shortCode);
                    assertEquals(originalUrl, retrievedUrl);
                } catch (Throwable t) {
                    errors.add(t);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();

        assertTrue(errors.isEmpty(), "Concurrent test failures: " + errors);
    }

}
