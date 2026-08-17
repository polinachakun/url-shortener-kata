package com.urlshortener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class UrlShortener {

    private final Map<String, String> storage = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);
    private static final String ALPHABET =
            "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";


    public String shorten(String longUrl) {

        String code = encodeBase62(counter.getAndIncrement());
        storage.put(code, longUrl);

        return code;
    }

    public String getOriginalUrl(String shortCode) {
        String longUrl = storage.get(shortCode);
        if (longUrl == null) {
            throw new ShortUrlNotFoundException(shortCode);
        }
        return longUrl;
    }

     public String encodeBase62(int number) { //better to have private, but gor test it is public
        if (number == 0) {
            return String.valueOf(ALPHABET.charAt(0));
        }
        StringBuilder sb = new StringBuilder();
        while (number > 0) {
            sb.append(ALPHABET.charAt(number % 62));
            number = number / 62;

        }
        return sb.reverse().toString();
    }

}
