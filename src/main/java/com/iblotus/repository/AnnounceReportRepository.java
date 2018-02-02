package com.iblotus.repository;

import com.iblotus.domain.AnnounceReport;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by xiezhiyan on 18-1-29.
 */
@Repository
public interface AnnounceReportRepository extends MongoRepository<AnnounceReport, String> {
}
