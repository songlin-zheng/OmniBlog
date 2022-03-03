package com.songlinzheng.image.service;

import com.songlinzheng.image.entity.Image;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@RestController
@Slf4j
@AllArgsConstructor
public class ImageController {
    @Autowired
    private MongoTemplate mongoTemplate;
    @PostMapping("/uploadImage")
    public ResponseEntity<?> uploadImage(@RequestParam(value = "image")MultipartFile file) {
        log.warn("receive request");
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file uploaded.");
        }
        try {
            String fileName = file.getOriginalFilename();
            Image image = new Image()
                    .setName(fileName)
                    .setCreatedTime(LocalDateTime.now())
                    .setContent(new Binary(file.getBytes()))
                    .setContentType(file.getContentType())
                    .setSize(file.getSize());
            Image savedImage = mongoTemplate.save(image);
            return ResponseEntity.ok(savedImage.getId());
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.internalServerError().body("Error uploading the image. Try again.");
        }
    }

    @GetMapping(value = "/image/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
    public byte[] image(@PathVariable("id") String id)
    {
        byte[] data = null;
        Image file = mongoTemplate.findById(id, Image.class);
        if (file != null) data = file.getContent().getData();
        return data;
    }


}
