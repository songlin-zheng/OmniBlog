package com.songlinzheng.text.Repository;

import com.songlinzheng.text.Entity.TextInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TextInfoRepository extends PagingAndSortingRepository<TextInfo, Long> {
    List<TextInfo> findAllByUid(long uid, Pageable pageable);
}
