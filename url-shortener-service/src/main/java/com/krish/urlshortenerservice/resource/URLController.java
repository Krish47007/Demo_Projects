package com.krish.urlshortenerservice.resource;

import com.krish.urlshortenerservice.model.UrlShorteningRequest;
import com.krish.urlshortenerservice.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class URLController {

    @Autowired
    private UrlShortenerService urlShortenerService;

    @PostMapping("/longurl")
    ResponseEntity shortenUrl(@RequestBody UrlShorteningRequest urlShorteningRequest)
    {
         return ResponseEntity.ok(urlShortenerService.shortenURL(urlShorteningRequest.getUrl()));
    }
    @GetMapping("/{shorturl}")
    ResponseEntity getLongUrl(@PathVariable String shorturl)
    {
        return ResponseEntity.ok(urlShortenerService.getLongURL(shorturl));
    }

    @PutMapping("/shorturl/expiration")
    ResponseEntity expire()
    {
        urlShortenerService.expireURL();
        return ResponseEntity.ok().build();
    }
}
