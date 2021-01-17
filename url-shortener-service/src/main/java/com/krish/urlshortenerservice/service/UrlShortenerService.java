package com.krish.urlshortenerservice.service;

public interface UrlShortenerService {

    String shortenURL(String longUrl);
    String getLongURL(String urlKey);
    void expireURL();
}
