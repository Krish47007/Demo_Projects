package com.krish.urlshortenerservice.repository;

import com.krish.urlshortenerservice.entity.LongURL;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LongUrlRepository extends CrudRepository<LongURL,Long> {
}
