package com.krish.urlshortenerservice.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LongURL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String url;
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @OneToOne(cascade = CascadeType.ALL)
    private ShortURL shortURL;


}
