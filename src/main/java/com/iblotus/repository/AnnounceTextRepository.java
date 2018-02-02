package com.iblotus.repository;

import com.iblotus.domain.AnnounceText;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by xiezhiyan on 18-1-30.
 */
public interface AnnounceTextRepository extends MongoRepository<AnnounceText, String> {

    Optional<AnnounceText> findTopByDisclosureTitleRegexAndCompanyCdEquals(String regex, String companyCd);

    List<AnnounceText> findByDisclosureTitleRegexAndCompanyCdIn(String regex, List<String> cds);
}
