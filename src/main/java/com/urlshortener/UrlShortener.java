package com.urlshortener;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class UrlShortener {

    private final Map<String, String> storage = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    public String shorten(String longUrl) {

        String code = String.valueOf(counter.getAndIncrement());
        storage.put(code, longUrl);

        return code;
    }

    public String getOriginalUrl(String shortCode) {
        String longUrl = storage.get(shortCode);
        if(longUrl==null){
            throw new ShortUrlNotFoundException(shortCode);
        }
        return longUrl;
    }

}
