# url-shortener-kata

Practice for backend live-coding interviews: an in-memory URL shortener
in Java (Maven, JUnit 5).

## Run

    mvn test

## Features

- shorten(longUrl) / getOriginalUrl(shortCode)
- Base62-encoded short codes (compact, readable)
- Thread-safe: ConcurrentHashMap for storage, AtomicInteger for the counter
- Custom exception when a short code is not found
