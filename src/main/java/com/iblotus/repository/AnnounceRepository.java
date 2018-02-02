package com.iblotus.repository;

import com.iblotus.domain.Announce;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by xiezhiyan on 18-1-28.
 */
@Repository
public interface AnnounceRepository extends MongoRepository<Announce, String> {

//    @Query(value = "{'disclosureTitle'}")
    Page<Announce> findByDisclosureTitleRegex(String regex, Pageable pageable);
}
