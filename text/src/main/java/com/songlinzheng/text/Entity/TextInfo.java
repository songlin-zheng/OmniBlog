package com.songlinzheng.text.Entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="text")
@Data
public class TextInfo implements Serializable {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private long id;

    @Column(nullable = false, name = "uid")
    private long uid;

    @Column(nullable = false, name = "title")
    private String title;

    @Column(nullable = false, name = "creationTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;

    @Lob
    @Column(nullable = false, name = "content")
    private String text;
}
