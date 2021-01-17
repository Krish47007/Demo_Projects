package com.krish.urlshortenerservice.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ShortURL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private boolean expired;
    @Column(nullable = false)
    private String domain;
    @Column(nullable = false)
    private String protocol;
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @OneToOne(cascade = CascadeType.ALL)
    private LongURL longURL;
    

}
