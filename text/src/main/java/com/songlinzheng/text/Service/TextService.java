package com.songlinzheng.text.Service;

import com.songlinzheng.text.Entity.TextInfo;
import com.songlinzheng.text.Repository.TextInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RestController
@RequestMapping("/text")
public class TextService {
    @Autowired
    TextInfoRepository textInfoRepository;

    @GetMapping
    public ResponseEntity<?> getText(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "5") Integer pageSize,
            @RequestParam(defaultValue = "uid") String sortBy,
            @RequestParam(defaultValue = "0") Long uid
    ) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        List<TextInfo> pagedResult = textInfoRepository.findAllByUid(uid, paging);
        return ResponseEntity.ok(pagedResult);
    }

    @PostMapping
    public ResponseEntity<?> saveText(
            @RequestBody TextInfo textInfo
    ) {
        try {
            textInfo.setCreationTime(new Date());
            textInfoRepository.save(textInfo);
            return ResponseEntity.ok("Successfully save the article");
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.badRequest().body(e.toString());
        }
    }
}
