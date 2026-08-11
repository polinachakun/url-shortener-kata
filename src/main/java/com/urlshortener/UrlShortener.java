package com.urlshortener;

import java.util.HashMap;
import java.util.Map;

public class UrlShortener {

    private final Map<String, String> storage = new HashMap<>();
    private int counter = 0;

    public String shorten(String longUrl) {

        String code = String.valueOf(counter);
        storage.put(code, longUrl);
        counter++;

        return String.valueOf(code);
    }

    public String getOriginalUrl(String shortCode) {
        String longUrl = storage.get(shortCode);
        if(longUrl==null){
            throw new ShortUrlNotFoundException(shortCode);
        }
        return longUrl;
    }

}
