package com.songlinzheng.subscription.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "subscription")
@Data
public class Subscription implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    private long fromUID;

    @Column(nullable = false)
    private long toUID;

    @Column(nullable = false, columnDefinition = "char(2) default '0'")
    private String tier;
}
