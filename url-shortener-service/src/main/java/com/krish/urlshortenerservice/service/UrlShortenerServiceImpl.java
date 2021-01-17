package com.krish.urlshortenerservice.service;

import com.krish.urlshortenerservice.entity.LongURL;
import com.krish.urlshortenerservice.entity.ShortURL;
import com.krish.urlshortenerservice.repository.LongUrlRepository;
import com.krish.urlshortenerservice.repository.ShortUrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;



@Service
public class UrlShortenerServiceImpl implements UrlShortenerService{

    @Autowired
    private LongUrlRepository longUrlRepository;
    @Autowired
    private ShortUrlRepository shortUrlRepository;
    
    @Override
    public String shortenURL(String longUrlString) {

        LongURL longURL = persist(longUrlString);

        //We're actually encoding the id of LongURL and sending it to the user so user is under the illusion that his url is shortened.
        String shortenedPath = encodeURL(longURL.getShortURL().getId());

        //Building short url
        ShortURL shortURL = longURL.getShortURL();
        URI uri = URI.create(shortURL.getProtocol()+"://"+shortURL.getDomain()+"/"+shortenedPath);

        return uri.toString();
    }

    private String encodeURL(long id) {

        return Base64.getEncoder().encodeToString(Long.toString(id).getBytes());
    }
    private Long decodeURL(String url) {

        byte[] bytes = Base64.getDecoder().decode(url);

        String str = new String(bytes, StandardCharsets.UTF_8);
        return Long.parseLong(str);
    }

    private LongURL persist(String longUrlString) {

        //Building ShortURL instance
        ShortURL shortURL = buildShortURL();

        //Building LongURL instance
        LongURL longURL = buildLongURL(longUrlString, shortURL);

        shortURL.setLongURL(longURL);

        //Save LongURL along with ShortURL
        longURL = longUrlRepository.save(longURL);

        return longURL;

    }

    private LongURL buildLongURL(String longUrl, ShortURL shortURL) {
        return LongURL.builder()
                                .url(longUrl)
                                .shortURL(shortURL)
                                .createdAt(LocalDateTime.now())
                                .build();
    }

    private ShortURL buildShortURL() {
        return ShortURL.builder()
                                .domain("localhost:8080")
                                .createdAt(LocalDateTime.now())
                                .protocol("http")
                                .build();
    }

    @Override
    public String getLongURL(String urlKey) {

        try {
            //Decoding the ID
            Long shortUrlId = decodeURL(urlKey);

            ShortURL shortURL = shortUrlRepository.findByIdAndAndExpired(shortUrlId,false)
                    .orElseThrow(()-> new Exception("URL doesn't exist or has expired"));

            return shortURL.getLongURL().getUrl();


        }catch (Exception e)
        {
            return e.getMessage();
        }

    }

    public void expireURL()
    {
        shortUrlRepository.findAll().forEach(s ->{
            if(!s.isExpired())
            {
                if(s.getCreatedAt().isBefore(LocalDateTime.now().minusMinutes(5))) {
                    s.setExpired(true);
                    shortUrlRepository.save(s);
                }
            }
        });
    }
}
