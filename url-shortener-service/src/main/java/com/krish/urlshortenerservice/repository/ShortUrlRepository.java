package com.krish.urlshortenerservice.repository;

import com.krish.urlshortenerservice.entity.LongURL;
import com.krish.urlshortenerservice.entity.ShortURL;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShortUrlRepository extends CrudRepository<ShortURL,Long> {

    //Get Short URL if its not expired
    Optional<ShortURL> findByIdAndAndExpired(long id,boolean expired);
    //Optional<ShortURL> findByLongURLIdAndExpired(long id,boolean expired);
}
