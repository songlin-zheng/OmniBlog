package com.songlinzheng.image.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
@Accessors(chain = true)
@Getter
@Setter
public class Image {
    @Id
    private String id;
    private String name;
    private LocalDateTime createdTime;
    private Binary content;
    private String contentType;
    private long size;
}
